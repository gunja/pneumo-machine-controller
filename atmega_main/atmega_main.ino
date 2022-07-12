#include <EEPROM.h>
#include <util/crc16.h>

struct _readings {
  union _btt {
    uint16_t analogReadings[21];
    uint8_t bytes[21 * 2];
  } u;
    uint16_t crc16;
};

struct _readings RDNGs;

void setupInputPins()
{
  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);
  pinMode(A4, INPUT);
  pinMode(A5, INPUT);
  pinMode(A6, INPUT);
  pinMode(A7, INPUT);
  pinMode(A8, INPUT);
  pinMode(A9, INPUT);
  pinMode(A10, INPUT);
  pinMode(A11, INPUT);
  pinMode(A12, INPUT);
  pinMode(A13, INPUT);
  pinMode(A14, INPUT);
  pinMode(A15, INPUT);
}

void setup()
{
  Serial.begin(115200);
  Serial3.begin(115200);
  Serial1.begin(115200);

  Serial1.println("Hello from serial1 line");

  setupInputPins();

}

void readAllAnalogs()
{
  int val = analogRead(A0);
  RDNGs.u.analogReadings[0] = (uint16_t) val;
  val = analogRead(A1);
  RDNGs.u.analogReadings[1] = (uint16_t) val;
  val = analogRead(A2);
  RDNGs.u.analogReadings[2] = (uint16_t) val;
  val = analogRead(A3);
  RDNGs.u.analogReadings[3] = (uint16_t) val;
  val = analogRead(A4);
  RDNGs.u.analogReadings[4] = (uint16_t) val;
  val = analogRead(A5);
  RDNGs.u.analogReadings[5] = (uint16_t) val;
  val = analogRead(A6);
  RDNGs.u.analogReadings[6] = (uint16_t) val;
  val = analogRead(A7);
  RDNGs.u.analogReadings[7] = (uint16_t) val;
  
  val = analogRead(A8);
  RDNGs.u.analogReadings[12] = (uint16_t) val;
  val = analogRead(A9);
  RDNGs.u.analogReadings[13] = (uint16_t) val;
  val = analogRead(A10);
  RDNGs.u.analogReadings[14] = (uint16_t) val;
  val = analogRead(A11);
  RDNGs.u.analogReadings[15] = (uint16_t) val;
  val = analogRead(A12);
  RDNGs.u.analogReadings[16] = (uint16_t) val;
  val = analogRead(A13);
  RDNGs.u.analogReadings[17] = (uint16_t) val;
  val = analogRead(A14);
  RDNGs.u.analogReadings[18] = (uint16_t) val;
  val = analogRead(A15);
  RDNGs.u.analogReadings[19] = (uint16_t) val;
}

void  determinedSendInputRegs()
{
  RDNGs.crc16 = 0xFFFF;
  for(uint8_t i=0; i < sizeof(RDNGs.u.analogReadings); ++i)
  {
      RDNGs.crc16 = _crc16_update( RDNGs.crc16, RDNGs.u.bytes[i]);
  }
  Serial.print("sizeof (analogReadings)="); Serial.println(sizeof(RDNGs));
  Serial3.write((char*)&RDNGs, sizeof(RDNGs));
}

void analyzeSer3Input()
{
  static int state = 0;
  unsigned long now = millis();
  if( Serial3.available() < 1)
    return;
  int v = Serial3.read();
  switch(v)
  {
    case 'I':
      Serial.print(now); Serial.println(" received I request. Sending raw data");
      determinedSendInputRegs();
      break;
  }
  
}


void loop()
{
  readAllAnalogs();
  analyzeSer3Input();
  
}
