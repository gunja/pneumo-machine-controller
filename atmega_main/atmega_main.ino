#include <EEPROM.h>
#include <stdint.h>

#include "atm_esp_exchange.h"

uint16_t analogReadings[21];

uint8_t g_RunningMode;
uint8_t g_CanRunManual;
uint8_t g_CanRunAuto;


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

void setupDigitalInputs()
{
}

void setupDigitalOutputs()
{
}

void setup()
{
  Serial.begin(115200);
  Serial3.begin(115200);
  Serial1.begin(115200);

  Serial1.println("Hello from serial1 line");

  setupInputPins();
  setupDigitalInputs();
  setupDigitalOutputs();

    g_RunningMode = 0;
    g_CanRunManual = 0;
    g_CanRunAuto = 0;
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

int handleInputRqCode(char *buffer, uint8_t buf_u)
{
    if(buf_u < 2)
        return 0;

      Serial.print(now); Serial.println(" received I request. Sending raw data");
      determinedSendInputRegs();

    return 2;
}

int handleModeCode(char *buffer, uint8_t b_u)
{
    if (b_u < 4)
        return 0;

    switch(buffer[1])
    {
        case 0:
            g_RunningMode = 0;
            break;
        case 10:
            if(g_CanRunManual > 0)
                g_RunningMode = 10;
            break;
        case 20: 21: 22: 23: 24:
            if (g_CanRunAuto)
                g_RunningMode = buffer[1];
            break;
    }

    return 4;
}

void analyzeSer3Input()
{
    //TODO rework this method to final state machine
    // consider timeouts on data receive
    // consider full message reading
  static uint8_t state = 0;
  static char buffer[200];
  static uint8_t buffer_used = 0;

    // TODO add timeout estimator. in case of too long no data - reset indexes

  while( Serial3.available() > 0)
  {
    int val = Serial3.read();
    buffer[buffer_used++] = (char)(val & 0xFF);
  }

  if (buffer_used == 0)
      return;

  unsigned long now = millis();
  uint8_t consumed = 0;
  switch(buffer[0])
  {
    case INPUTS_RQ_CODE:
      consumed = handleInputRqCode(buffer, buffer_used);
      break;
    case MODE_CODE:
        //TODO
        consumed = handleModeCode(buffer, buffer_used);
        break;
    // TODO create set manual settings
    // TODO set auto settings
    // TODO other messages
  }
  if (consumed > 0)
  {
    memmove(buffer, buffer + consumed, buffer_used - consumed);
    buffer_used -= consumed;
  }
}


void loop()
{
  readAllAnalogs();
  analyzeSer3Input();
  switch(g_RunningMode) {
    case 0:
        disableOutputs();
        break;
    case 10:
        makeManualSet();
        break;
    case 21: 22: 23: 24:
        makeAutoSet();
        break;
    }
}

void disableOutputs()
{
    for( i =0; i < 8; ++i)
    {
        cylinders[i].setPinLow(0);
        cylinders[i].setPinLow(1);
    }
}
