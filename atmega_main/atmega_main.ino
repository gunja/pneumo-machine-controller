#include <EEPROM.h>

uint16_t analogReadings[21];

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
  analogReadings[0] = (uint16_t) val;
  val = analogRead(A1);
  analogReadings[1] = (uint16_t) val;
  val = analogRead(A2);
  analogReadings[2] = (uint16_t) val;
  val = analogRead(A3);
  analogReadings[3] = (uint16_t) val;
  val = analogRead(A4);
  analogReadings[4] = (uint16_t) val;
  val = analogRead(A5);
  analogReadings[5] = (uint16_t) val;
  val = analogRead(A6);
  analogReadings[6] = (uint16_t) val;
  val = analogRead(A7);
  analogReadings[7] = (uint16_t) val;
  
  val = analogRead(A8);
  analogReadings[12] = (uint16_t) val;
  val = analogRead(A9);
  analogReadings[13] = (uint16_t) val;
  val = analogRead(A10);
  analogReadings[14] = (uint16_t) val;
  val = analogRead(A11);
  analogReadings[15] = (uint16_t) val;
  val = analogRead(A12);
  analogReadings[16] = (uint16_t) val;
  val = analogRead(A13);
  analogReadings[17] = (uint16_t) val;
  val = analogRead(A14);
  analogReadings[18] = (uint16_t) val;
  val = analogRead(A15);
  analogReadings[19] = (uint16_t) val;
}

void  determinedSendInputRegs()
{
  Serial.print("sizeof (analogReadings)="); Serial.println(sizeof(analogReadings));
  Serial3.write((char*)analogReadings, sizeof(analogReadings));
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
