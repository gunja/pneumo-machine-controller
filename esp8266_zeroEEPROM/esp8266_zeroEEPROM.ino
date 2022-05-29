#include <EEPROM.h>
#ifdef ESP8266
 #include <ESP8266WiFi.h>
#else //ESP32
 #include <WiFi.h>
#endif
#include <ModbusTCP.h>
#include "placements.h"

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
#define RQ_INPUTS_PERIOD_MS  20

void getInputRegs()
{
  Serial.write(rqInputRegs, 2);
  for(int i=0; i < sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
     int incomingByte1 = 0, incomingByte2 = 0 ;
     yield();
     incomingByte1 = Serial.read();
     yield();
     incomingByte2 = Serial.read();
     mb.Ireg(i, ((incomingByte1&0xFF)<<8) + (incomingByte2 & 0xFF));
  }
}

uint16_t cbModbusSetHreg(TRegister* reg, uint16_t val)
{
  return 0;
}

void initEEPROM()
{
  for(int i=0; i < sizeof(struct memory_layout); ++i)
     EEPROM.put(i, '\0');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML)   , 'S');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +1, 'A');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +2, 'N');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +3, 'S');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +4, 'A');
  EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +5, '\0' );

  EEPROM.get((char*)(&ML.ap_name) - (char*)(&ML), ML.ap_name.name);

  EEPROM.put((char*)(&ML.header_names[0]) - (char*)(&ML), "Nasos");
  EEPROM.put((char*)(&ML.header_names[1]) - (char*)(&ML), "общее");
  EEPROM.put((char*)(&ML.header_names[2]) - (char*)(&ML), "ресивер");
  EEPROM.put((char*)(&ML.header_names[3]) - (char*)(&ML), "хрень");

  EEPROM.get((char*)(&ML.header_names[0]) - (char*)(&ML), ML.header_names[0].name);
  EEPROM.get((char*)(&ML.header_names[1]) - (char*)(&ML), ML.header_names[1].name);
  EEPROM.get((char*)(&ML.header_names[2]) - (char*)(&ML), ML.header_names[2].name);
  EEPROM.get((char*)(&ML.header_names[3]) - (char*)(&ML), ML.header_names[3].name);
  
}

void setup()
{
  Serial.begin(115200);
  EEPROM.begin(sizeof(struct memory_layout));
  initEEPROM();
  Serial.println("done");
 
}

void loop()
{
  delay(5);
  mb.task();
  unsigned long now = millis();
  if (now - lastIRegRq > RQ_INPUTS_PERIOD_MS)
  {
    //getInputRegs();
    lastIRegRq = millis();
  }
}
