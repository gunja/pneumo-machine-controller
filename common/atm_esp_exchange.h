#ifndef ATM_ESP_EXCHANGE_H
#define ATM_ESP_EXCHANGE_H

#define MODE_CODE  'M'
#define INPUTS_RQ_CODE 'I'
#define MANUAL_SETS_CODE 'H'
#define AUTO_SETS_CODE 'A'
#define ALTER_PRESSURE_TGT 'T'

#define MANUAL_MODE_CODE 10

#define MIN_SIGNAL_ATMEGA   67

struct _manual_settings {
    uint16_t manual_target_value[8];
    uint16_t directions;
};

struct _automatic_settings {
    struct _manual_settings tgts_dirs;
    uint16_t pointsForward[10];
    uint16_t pointsBackward[10];
};

struct _manual_settings_message {
    uint8_t msg_code;
    union _dt_bytes {
        char bytes[9 * 2];
        struct _manual_settings ms;
    } u;
    uint16_t crc;
};

struct _automatic_settings_message {
    uint8_t msg_code;
    union _dt_bytes {
        char bytes[29*2];
        struct _automatic_settings ms;
    } u;
    uint16_t crc;
};

struct _alter_pressure_tgt {
    uint8_t code;
    uint8_t cyl_idx;
    uint16_t newVal;
    uint16_t crc;
};

#endif //ATM_ESP_EXCHANGE_H
