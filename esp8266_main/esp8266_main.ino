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
#define RQ_INPUTS_PERIOD_MS  20

unsigned long eeprom_write_request = 0;
#define EEPROM_COMMIT_DELAY 500
bool eepromWriteRequest;

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

void getIRSimulated()
{
  unsigned long now = millis();
  for(int i=1; i <= sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
    int16_t val = 32000 + 30000 * sin( now/100000. * i);
    mb.Ireg(i, val);
  }
}

uint16_t cbModbusSetHreg(TRegister* reg, uint16_t val)
{
  if(reg->address.type !=  TAddress::HREG){
    Serial.println("callback called not for set HREG");
    return val;
  }
    
  EEPROM.put((reg->address.address - 1) * sizeof(uint16_t), val);
  Serial.print("Writing register "); Serial.print(reg->address.address); Serial.print(" with value "); Serial.println(val);
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
    Serial.println("populating initial EEPROM");
   for(int i=0; i <= sizeof(struct memory_layout); ++i)
     EEPROM.put(i, '\0');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML)   , 'S');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +1, 'A');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +2, 'N');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +3, 'S');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +4, 'A');
    EEPROM.put((char*)(&ML.ap_name) - (char*)(&ML) +5, '\0' );
  
  EEPROM.put((char*)(&ML.header_names[0]) - (char*)(&ML), "Nasos");
  EEPROM.put((char*)(&ML.header_names[1]) - (char*)(&ML), "общее");
  EEPROM.put((char*)(&ML.header_names[2]) - (char*)(&ML), "ресивер");
  EEPROM.put((char*)(&ML.header_names[3]) - (char*)(&ML), "хрень");
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

  Serial.println(ML.ap_name.name);
  Serial.println(ML.header_names[0].name);
  Serial.println(ML.header_names[1].name);
  Serial.println(ML.header_names[2].name);
  Serial.println(ML.header_names[3].name);
  Serial.println(ML.header_names[4].name);

  WiFi.softAPConfig(local_IP, gateway, subnet);
  WiFi.softAP(ML.ap_name.name, "");
  
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.println(myIP);
/*
  Serial.write(rqInputRegs, 2);
  for(int i=0; i < sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
     int incomingByte1 = 0, incomingByte2 = 0 ;
     incomingByte1 = Serial.read();
     incomingByte2 = Serial.read();
     mb.addIreg(i, ((incomingByte1&0xFF)<<8) + (incomingByte2 & 0xFF));
  }
  Serial.write(rqHoldingRegs, 2);
  for(int i=0; i < sizeof(struct memory_layout)/ sizeof(uint16_t); ++i)
  {
     int incomingByte1 = 0, incomingByte2 = 0;
     incomingByte1 = Serial.read();
     incomingByte2 = Serial.read();
     mb.addHreg(i, ((incomingByte1 & 0xFF)<<8) + (incomingByte2 & 0xFF));
  }
*/
  eepromWriteRequest = false;

  for(int i=1; i <= sizeof(struct _input_regs)/sizeof(uint16_t); ++i)
  {
    mb.addIreg(i, (i+1)<<8 + (i +1));
  }
  for(int i=0; i < sizeof(struct memory_layout)/ sizeof(uint16_t); ++i)
  {
    mb.addHreg(i +1, *((uint16_t*)&ML + i) );
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
    //getInputRegs();
    getIRSimulated();
    lastIRegRq = millis();
  }
  if (eepromWriteRequest && (now - EEPROM_COMMIT_DELAY > eeprom_write_request))
  {
    Serial.println("making EEPROM commit");
    EEPROM.commit();
    eepromWriteRequest = false;
  }
}
