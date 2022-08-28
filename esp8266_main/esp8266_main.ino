#include <EEPROM.h>
#ifdef ESP8266
 #include <ESP8266WiFi.h>
#else //ESP32
 #include <WiFi.h>
#endif
#include <ModbusTCP.h>
#include "placements.h"
#include "atm_esp_exchange.h"
#include <math.h>

#include <SPI.h>
#include <Wire.h>
//#include <Adafruit_GFX.h>
//#include <Adafruit_SSD1306.h>
#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64

#define OLED_MOSI   D7
#define OLED_CLK   D5
#define OLED_DC    D2
#define OLED_CS    D8
#define OLED_RESET D3
//Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT,
//  OLED_MOSI, OLED_CLK, OLED_DC, OLED_RESET, OLED_CS);



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

char rqInputRegs[] = {INPUTS_RQ_CODE, 21};
struct _readings {
  union _btt {
    uint16_t analogReadings[21];
    uint8_t bytes[21 * 2];
  } u;
    uint16_t crc16;
};

unsigned long lastIRegRq =0;
#define RQ_INPUTS_PERIOD_MS  100

unsigned long eeprom_write_request = 0;
#define EEPROM_COMMIT_DELAY 500
bool eepromWriteRequest;

int g_input_register_read = -1;
bool g_needFlush = false;

enum exchange_state {
  ST_NONE =0,
  RD_INPUTS = 1
  , WR_MANUAL_SET
  , WR_AUTO_SET
  , RD_MODE_CNF
  , TGT_PRESSURE_CHANGED
};
enum exchange_state STATE = ST_NONE;
uint8_t g_uint8_MODE;

unsigned long sent_message_moment;
#define WAIT_TIMEOUT_MS   500UL

void clrDisp()
{
static bool clrd = false;
if (! clrd) {
  //display.clearDisplay();
  clrd = true;
}
}

bool getInputRegs()
{
  if (STATE != ST_NONE)
  {
    unsigned long now = millis();
    if(now - sent_message_moment > WAIT_TIMEOUT_MS )
    {
      STATE = ST_NONE;
      //clrDisp(); display.setCursor(0,0); display.println("wait timeout. resetting"); display.print("reg_read= "); display.println(g_input_register_read); display.display();
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
    switch( STATE) {
        case ST_NONE:
            break;
        case RD_INPUTS:
            getNext2ReadInputs();
            break;
        case WR_MANUAL_SET:
            getSettingsConfirm_RqMode(MANUAL_SETS_CODE);
            break;
        case WR_AUTO_SET:
            getSettingsConfirm_RqMode(AUTO_SETS_CODE);
            break;
        case RD_MODE_CNF:
            getCMDConfirm(MODE_CODE);
            break;
        case TGT_PRESSURE_CHANGED:
            getCMDConfirm(ALTER_PRESSURE_TGT);
            break;
    }
    return;
}

void getNext2ReadInputs()
{
    static struct _readings rdBuff{ {{0}},0xFFFF} ;
    //static uint8_t currentByte =0;

  if(STATE != RD_INPUTS)
    return;

    while( Serial.available() && g_input_register_read < 2 * 21)
    {
        int bt = Serial.read();
        rdBuff.u.bytes[g_input_register_read] = (uint8_t) bt;
        rdBuff.crc16 = crc16_update(rdBuff.crc16, rdBuff.u.bytes[g_input_register_read]);
        g_input_register_read++;
    }

  if (Serial.available() >= 2 && g_input_register_read == 2 * 21)
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
        //display.clearDisplay(); display.setCursor(0,0); display.println(millis()); display.println("updated input "); display.display();
      } else {
        
        rdBuff.crc16 = 0xFFFF;
        g_needFlush = true;
        //clrDisp(); display.setCursor(0,0); display.println("need flush requested"); display.print("reg_read="); display.println(g_input_register_read); display.display();
      }
      g_input_register_read = 0;
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

  switch(reg->address.address)
  {
    case MANUAL_INPUT_DETAIL_DETECTOR:
        //TODO send data to Atmega, so that it can start counting
        sendManualDetectorValue(val);
        break;
    case LAST_SELECTED_MODE:
        // TODO send to Atmega to switch operating mode
        sendOperatingModeSettings(val);
  }

  return val;
}

void InformAtmegaOnManualGoalRequest(uint16_t cylinderIndex, uint16_t value)
{
  uint16_t crc = 0xFFFF;
  struct _alter_pressure_tgt msg { ALTER_PRESSURE_TGT, (uint8_t) cylinderIndex, value, 0};
  for(int i = 0; i < 4; ++i)
  {
    crc = crc16_update(crc, *( (uint8_t*)&msg + i));
  }
  msg.crc = crc;
  Serial.write((uint8_t*)&msg, sizeof(struct _alter_pressure_tgt));

  STATE = TGT_PRESSURE_CHANGED;
  //clrDisp(); display.setCursor(0,0);  display.println(millis()); display.println("manual goal sent"); display.print("cyl="); display.print(cylinderIndex); display.print("  val="); display.println(value);
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

#if 0
  if( display.begin(SSD1306_SWITCHCAPVCC)) {
    display.display();
    display.setTextSize(1);
    display.setCursor(0, 0);
    display.setTextColor(WHITE);
    display.setTextWrap(1);
  }
#endif

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

void sendManualDetectorValue(uint16_t val)
{
    // TODO implement this method
    //Serial.print("Manual register state is "); Serial.println(val? " true" : " false");
}

void sendOperatingModeSettings(uint16_t val)
{
    //Serial.print("Received Last Selected mode == "); Serial.println(val);
    //TODO introduce state machine on sending settings and after correct reply - send mode_code
    if (val == MANUAL_MODE_CODE)
    {
        struct _manual_settings_message manual_sets;
        for(int i =0; i < 8; ++i)
        {
            EEPROM.get(sizeof(uint16_t)*(HOLD_REG_MANUAL_TARGET_C1 -1 +i),
                                manual_sets.u.ms.manual_target_value[i]);
        }
        EEPROM.get(sizeof(uint16_t) * (HOLD_REG_ACT_DIRECTIONS - 1),  manual_sets.u.ms.directions);
        manual_sets.msg_code = MANUAL_SETS_CODE;
        uint16_t crc = 0xFFFF;
        //display.clearDisplay(); display.setCursor(0,0);
        for(int i =0; i < sizeof(struct _manual_settings) + 1; ++i)
        {
          uint8_t v = ((uint8_t*)&manual_sets)[i];
          //display.print(v); display.print(" ");
            crc = crc16_update(crc, ((uint8_t*)&manual_sets)[i]);
        }
        //display.display();
        manual_sets.crc = crc;
        Serial.write( (uint8_t*)&manual_sets, sizeof(struct _manual_settings_message));
        //display.setCursor(3, 0); display.print(millis()); display.print("sizeof(man_set)="); display.println( sizeof(struct _manual_settings));
        g_uint8_MODE = val & 0xFF;
        STATE = WR_MANUAL_SET;
        //TODO confirm somehow
    } else if (val >= 21 && val <= 24)
    {
        //TODO implement method
        sendOperatingModeVal(val & 0xFF);
        STATE = RD_MODE_CNF;
    } else if ( val == 0)
    {
        sendOperatingModeVal(val & 0xFF);
        STATE = RD_MODE_CNF;
    } else {
        sendOperatingModeVal(0);
        STATE = RD_MODE_CNF;
    }

}

void sendOperatingModeVal(uint8_t val)
{
    uint8_t data[]={MODE_CODE, val, 0, 0};
    int16_t crc = 0xFFFF;
    crc = crc16_update(crc, data[0]);
    crc = crc16_update(crc, data[1]);
    *((uint16_t *)(data +2)) = crc;

    Serial.write(data, sizeof(data));
}

void flushSerial()
{
    while( Serial.available())
    {
        Serial.read();
    }
}

void getSettingsConfirm_RqMode(uint8_t MODE_SET_CODE)
{
    static uint8_t data[4] = {0, 0, 0, 0};
    static uint8_t currentByte =0;

    while( Serial.available() && currentByte < 4)
    {
        int bt = Serial.read();
        data[currentByte++] = (uint8_t)bt;
    }
    if (currentByte < 4)
        return;
    // TODO check CRC
    if (data[0] != MODE_SET_CODE)
    {
        // FIXME WTF? here should be another reply
        // How to inform on this issue?
    }
    if (data[1] == 0) {
    // there's an error, but how to handle it?
    }
    
    // FIXME sending setting of mode in any case
    sendOperatingModeVal(g_uint8_MODE);
    currentByte = 0;
    STATE = RD_MODE_CNF;
}

void getCMDConfirm(uint8_t code)
{
    static uint8_t data[4] = {0, 0, 0, 0};
    static uint8_t currentByte =0;

    while( Serial.available() && currentByte < 4)
    {
        int bt = Serial.read();
        data[currentByte++] = (uint8_t)bt;
    }
    if (currentByte < 4)
        return;
    // TODO check CRC
    if (data[0] != code)
    {
        // FIXME WTF? here should be another reply
        // How to inform on this issue?
    }
    if (data[1] == 0) {
    // there's an error, but how to handle it?
    }
    
    // FIXME sending setting of mode in any case
    STATE = ST_NONE;
    currentByte = 0;
}
