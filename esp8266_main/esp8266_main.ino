#include <EEPROM.h>
#ifdef ESP8266
 #include <ESP8266WiFi.h>
#else //ESP32
 #include <WiFi.h>
#endif
#include <ModbusTCP.h>
#include "placements.h"
#include <math.h>

#ifndef APSSID
#define APSSID "ESPap"
#define APPSK  "thereisnospoon"
#endif

const char *ssid = APSSID;

IPAddress local_IP(192,168,1,1);
IPAddress gateway(192,168,1,1);
IPAddress subnet(255,255,255,0);

ModbusTCP mb;
struct memory_layout ML;

struct _readings {
  union _btt {
    uint16_t analogReadings[21];
    uint8_t bytes[21 * 2];
  } u;
    uint16_t crc16;
};


char rqInputRegs[] = {'I', 21};
//char rqHoldingRegs[] = {'H', sizeof(struct memory_layout) };

unsigned long lastIRegRq =0;
#define RQ_INPUTS_PERIOD_MS  100

unsigned long eeprom_write_request = 0;
#define EEPROM_COMMIT_DELAY 500
bool eepromWriteRequest;

int g_input_register_read = -1;
bool g_needFlush = false;

enum exchange_state {
  ST_NONE,
  RD_INPUTS = 1
};
enum exchange_state STATE = ST_NONE;

unsigned long sent_message_moment;
#define WAIT_TIMEOUT_MS   500UL

bool getInputRegs()
{
  if (STATE != ST_NONE)
  {
    unsigned long now = millis();
    if(now - sent_message_moment > WAIT_TIMEOUT_MS )
    {
      STATE = ST_NONE;
      g_input_register_read = 0;
    }
    return false;
  }
  if (g_needFlush) {
    g_needFlush = false;
    flushSerial();
  }
  Serial.write(rqInputRegs, 2);
  sent_message_moment = millis();
  g_input_register_read = 0;
  STATE = RD_INPUTS;
  return true;
}

uint16_t crc16_update(uint16_t crc, uint8_t a)
{
int i;
crc ^= a;
for (i = 0; i < 8; ++i)
{
    if (crc & 1)
    crc = (crc >> 1) ^ 0xA001;
    else
    crc = (crc >> 1);
}
return crc;
}

void getNextInput()
{
    static struct _readings rdBuff{ {{0}},0xFFFF} ;
    static uint8_t currentByte =0;

  if(STATE != RD_INPUTS)
    return;

    while( Serial.available() && currentByte < 2 * 21)
    {
        int bt = Serial.read();
        rdBuff.u.bytes[currentByte] = (uint8_t) bt;
        rdBuff.crc16 = crc16_update(rdBuff.crc16, rdBuff.u.bytes[currentByte]);
        currentByte++;
    }

  if (Serial.available() >= 2 && currentByte == 2 * 21)
  {
      int incomingByte1 = 0, incomingByte2 = 0 ;
      incomingByte1 = Serial.read();
      incomingByte2 = Serial.read();
      uint16_t crc = (uint16_t)((incomingByte2&0xFF)<<8) + (incomingByte1 & 0xFF);
      if (crc == rdBuff.crc16)
      {
        rdBuff.crc16 = 0xFFFF;
        for(uint8_t i = 0; i < 21; ++i)
        {
            mb.Ireg(i + 1, rdBuff.u.analogReadings[i]);
        }
        STATE = ST_NONE;
        currentByte = 0;
      } else {
        currentByte = 0;
        rdBuff.crc16 = 0xFFFF;
        g_needFlush = true;
      }
  }
}

void getIRSimulated()
{
  static uint16_t presVal =0;
  static unsigned long lastPresenceToggle = millis();
  unsigned long now = millis();
  for(int i=1; i < sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
    int16_t val = 600 + 400 * sin( now/100000. * i);
    mb.Ireg(i, val);
  }
  if (now - lastPresenceToggle > 2000) {
    lastPresenceToggle = now;
    presVal = presVal ? 0: 1;
    mb.Ireg(21, presVal);
  }
}

uint16_t cbModbusSetHreg(TRegister* reg, uint16_t val)
{
  if(reg->address.type !=  TAddress::HREG){
    return val;
  }
    
  EEPROM.put((reg->address.address - 1) * sizeof(uint16_t), val);
  eepromWriteRequest = true;
  eeprom_write_request = millis();
  uint16_t last_sel_mode;
  EEPROM.get((LAST_SELECTED_MODE -1 ) * sizeof(uint16_t), last_sel_mode);
  if(reg->address.address >= HOLD_REG_MANUAL_TARGET_C1 && reg->address.address <= HOLD_REG_MANUAL_TARGET_C8 
      && last_sel_mode == 10)
  {
    InformAtmegaOnManualGoalRequest(reg->address.address - HOLD_REG_MANUAL_TARGET_C1, val);
  }

  if (reg->address.address == MANUAL_INPUT_DETAIL_DETECTOR )
  {
    //Serial.print("Manual register state is "); Serial.println(val? " true" : " false");
  }
  if (reg->address.address == LAST_SELECTED_MODE )
  {
    //Serial.print("Received Last Selected mode == "); Serial.println(val);
  }
  
  return val;
}

void InformAtmegaOnManualGoalRequest(uint16_t cylinderIndex, uint16_t value)
{
  uint8_t data[5];
  uint16_t reg;
  EEPROM.get((HOLD_REG_ACT_DIRECTIONS -1)*sizeof(uint16_t), reg);
  data[0] = 'M';
  data[1] = (uint8_t) cylinderIndex;
  data[2] = reg & (3<< cylinderIndex);
  data[3] = (value >>8) & 0xFF;
  data[4] = value & 0xFF;
  //Serial.write(data, 5); //TODO somehow make exchange procedure
}

void initEEPROM()
{
  char v;
  EEPROM.get(sizeof(struct memory_layout), v);
  if(v != 0)
  {
   for(int i=0; i <= sizeof(struct memory_layout); ++i)
     EEPROM.put(i, '\0');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML)   , 'S');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +1, 'A');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +2, 'N');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +3, 'S');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +4, 'A');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +5, '\0' );
  
  EEPROM.put((char*)(&ML.header_names[0]) - (char*)(&ML), "Nasos12");
  EEPROM.put((char*)(&ML.header_names[1]) - (char*)(&ML), "обще2е2");
  EEPROM.put((char*)(&ML.header_names[2]) - (char*)(&ML), "ресивер");
  EEPROM.put((char*)(&ML.header_names[3]) - (char*)(&ML), "четвёртый");
  EEPROM.commit();
    }
  
  EEPROM.get((char*)(&ML.ap_name) - (char*)(&ML), ML.ap_name.name);
  EEPROM.get((char*)(&ML.header_names[0]) - (char*)(&ML), ML.header_names[0].name);
  EEPROM.get((char*)(&ML.header_names[1]) - (char*)(&ML), ML.header_names[1].name);
  EEPROM.get((char*)(&ML.header_names[2]) - (char*)(&ML), ML.header_names[2].name);
  EEPROM.get((char*)(&ML.header_names[3]) - (char*)(&ML), ML.header_names[3].name);
  
}

void setup()
{
  Serial.begin(115200);
  EEPROM.begin(sizeof(struct memory_layout) +1);
  initEEPROM();

  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP(ML.ap_name.name, "");
  
  IPAddress myIP = WiFi.softAPIP();

  eepromWriteRequest = false;

  for(int i=1; i <= sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
    mb.addIreg(i, (i+1)<<8 + (i +1));
  }
  uint16_t memReg;
  for(int i=0; i < sizeof(struct memory_layout)/ sizeof(uint16_t); ++i)
  {
    EEPROM.get(i * sizeof(uint16_t), memReg);
    mb.addHreg(i + 1, memReg);
  }
  
  mb.onSetHreg(1, cbModbusSetHreg, sizeof(struct memory_layout)/ sizeof(uint16_t));
  mb.server();
}

void loop()
{
  delay(5);
  mb.task();
  unsigned long now = millis();
  if (now - lastIRegRq > RQ_INPUTS_PERIOD_MS)
  {
    if( getInputRegs())
      lastIRegRq = millis();
  }
  if (eepromWriteRequest && (now - EEPROM_COMMIT_DELAY > eeprom_write_request))
  {
    eepromWriteRequest = ! EEPROM.commit();
  }
  getNextInput();
}

void flushSerial()
{
    while( Serial.available())
    {
        Serial.read();
    }
}
