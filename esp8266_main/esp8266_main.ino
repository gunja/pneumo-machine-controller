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

char rqInputRegs[] = {'I', 20};
char rqHoldingRegs[] = {'H', sizeof(struct memory_layout) };

unsigned long lastIRegRq =0;
#define RQ_INPUTS_PERIOD_MS  100

unsigned long eeprom_write_request = 0;
#define EEPROM_COMMIT_DELAY 500
bool eepromWriteRequest;

int g_input_register_read = -1;

enum exchange_state {
  ST_NONE,
  RD_INPUTS = 1
};
enum exchange_state STATE = ST_NONE;

bool getInputRegs()
{
  if (STATE != ST_NONE)
    return false;
  Serial.write(rqInputRegs, 2);
  g_input_register_read = 0;
  STATE = RD_INPUTS;
  return true;
}

void getNextInput()
{
  if(STATE != RD_INPUTS)
    return;
  if (g_input_register_read >= 20)
     return;

  if (Serial.available() >= 2)
  {
      int incomingByte1 = 0, incomingByte2 = 0 ;
      incomingByte1 = Serial.read();
      incomingByte2 = Serial.read();
      mb.Ireg(g_input_register_read, ((incomingByte2&0xFF)<<8) + (incomingByte1 & 0xFF));
      g_input_register_read++;
  }
  if (g_input_register_read == 20)
    STATE = ST_NONE;
}

void getIRSimulated()
{
  unsigned long now = millis();
  for(int i=1; i <= sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
    int16_t val = 600 + 400 * sin( now/100000. * i);
    mb.Ireg(i, val);
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
  return val;
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
