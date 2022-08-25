#include <EEPROM.h>
#include <stdint.h>

#include "atm_esp_exchange.h"
#include <util/crc16.h>
#include "Cylinder.h"
#include "placements.h"

struct _readings {
  union _btt {
    uint16_t analogReadings[21];
    uint8_t bytes[21 * 2];
  } u;
    uint16_t crc16;
};

struct _readings RDNGs;

uint8_t g_RunningMode;
uint8_t g_CanRunManual;
uint8_t g_CanRunAuto;

uint8_t g_moveDirection;
uint8_t g_counterVal;

Cylinder *cyls;

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

    Cylinder _cyls[] {
        Cylinder{22, 23, RDNGs.u.analogReadings[0]},
        Cylinder{24, 25, RDNGs.u.analogReadings[1]},
        Cylinder{26, 27, RDNGs.u.analogReadings[2]},
        Cylinder{28, 29, RDNGs.u.analogReadings[3]},
        Cylinder{30, 31, RDNGs.u.analogReadings[4]},
        Cylinder{32, 33, RDNGs.u.analogReadings[5]},
        Cylinder{34, 35, RDNGs.u.analogReadings[6]},
        Cylinder{36, 37, RDNGs.u.analogReadings[7]},
    };
    cyls = _cyls;
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
  //Serial.print("sizeof (analogReadings)="); Serial.println(sizeof(RDNGs));
  Serial3.write((char*)&RDNGs, sizeof(RDNGs));
}

int handleInputRqCode(char *buffer, uint8_t buf_u)
{
  unsigned long now = millis();
    if(buf_u < 2)
        return 0;

      Serial.print(now); Serial.println(" received I request. Sending raw data");
      determinedSendInputRegs();

    return 2;
}

int handleModeCode(char *buffer, uint8_t b_u)
{
    uint8_t data[]= {MODE_CODE, 0, 0, 0 };

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
        case 20: case 21: case 22: case 23: case 24:
            if (g_CanRunAuto)
                g_RunningMode = buffer[1];
            break;
    }
    Serial3.write(data, 4);
    Serial.print("Running mode switched to code "); Serial.println((int)buffer[1]);

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
    case MANUAL_SETS_CODE:
        consumed = handleManualSettingsReceive(buffer, buffer_used);
        break;
    case AUTO_SETS_CODE:
        consumed = handleAutomaticSettingsReceived(buffer, buffer_used);
        break;
    default:
        consumed = 1;
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
    case 21: case 22: case 23: case 24:
        makeAutoSet();
        break;
    }
}

void disableOutputs()
{
    for(uint8_t i =0; i < CYLINDER_PAIRS_COUNT; ++i)
    {
        cyls[i].setPinLow(0);
        cyls[i].setPinLow(1);
    }
}

void makeManualSet()
{
    for(uint8_t i=0; i < CYLINDER_PAIRS_COUNT; ++i)
    {
        cyls[i].performAction();
    }
}

void makeAutoSet()
{
  //TODO implement this method
    for(uint8_t i =0; i < CYLINDER_PAIRS_COUNT; ++i)
    {
        cyls[i].performAction(g_moveDirection, g_counterVal);
    }
  return;
}

int handleManualSettingsReceive(char *buffer, uint8_t buf_u)
{
    struct _manual_settings_message *msg = (struct _manual_settings_message*)buffer;
    if(buf_u < sizeof(struct _manual_settings_message))
        return 0;
    uint16_t crc = 0xFFFF;
    uint8_t data[] = {MANUAL_SETS_CODE, 0, 0, 0};

    for(int i=0; i <= sizeof(struct _manual_settings); ++i)
    {
        crc = _crc16_update(crc, buffer+i);
    }
    if (crc != msg->crc) {
        Serial.println("While receiving Manual Setting CRC16 diverged");
        Serial.print("calced ="); Serial.print(crc); Serial.print("   expected "); Serial.println(msg->crc);
        //TODO update CRC
        Serial3.write(data, 4);
        g_CanRunManual = 0;
        g_CanRunAuto = 0;
        return 1;// assume that only 1 byte can be consumed
    }
    for(uint8_t i = 0; i < CYLINDER_PAIRS_COUNT; ++i)
    {
        cyls[i].setTarget( msg->u.ms.manual_target_value[i]);
        cyls[i].setDirection((msg->u.ms.directions & (3<<(2*i))) != 0);
    }
    g_CanRunManual = 1;
    Serial.println("Setting for manual management applied");
    data[1] = 1;
    // TODO update CRC
    Serial3.write(data, 4);

    return sizeof(struct _manual_settings_message);
}

int handleAutomaticSettingsReceived(char *buffer, uint8_t buf_u)
{
    struct _automatic_settings_message *msg = (struct _automatic_settings_message*)buffer;
    if(buf_u < sizeof(struct _automatic_settings_message))
        return 0;
    uint16_t crc = 0xFFFF;
    uint8_t data[] = {AUTO_SETS_CODE, 0, 0, 0};

    for(int i=0; i <= sizeof(struct _automatic_settings); ++i)
    {
        crc = _crc16_update(crc, buffer+i);
    }
    if (crc != msg->crc) {
        Serial.println("While receiving Automatic Setting CRC16 diverged");
        //TODO update CRC
        Serial3.write(data, 4);
        g_CanRunManual = 0;
        g_CanRunAuto = 0;
        return 1;// assume that only 1 byte can be consumed
    }
    for(uint8_t i = 0; i < CYLINDER_PAIRS_COUNT; ++i)
    {
        cyls[i].setTarget( msg->u.ms.tgts_dirs.manual_target_value[i]);
        cyls[i].setDirection((msg->u.ms.tgts_dirs.directions & (3<<(2*i))) != 0);
    }
    // TODO find a proper way to assign points of reaction
    g_CanRunManual = 0;
    g_CanRunAuto = 1;
    Serial.println("Setting for automatic management applied");
    data[1] = 1;
    // TODO update CRC
    Serial3.write(data, 4);

    return sizeof(struct _automatic_settings_message);
}
