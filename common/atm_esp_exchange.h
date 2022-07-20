#ifndef ATM_ESP_EXCHANGE_H
#define ATM_ESP_EXCHANGE_H

#define MODE_CODE  'M'
#define INPUTS_RQ_CODE 'I'
#define MANUAL_SETS_CODE 'H'

#define MANUAL_MODE_CODE 10

struct _manual_settings {
    uint16_t manual_target_value[8];
    uint16_t directions;
};

struct _manual_settings_message {
    uint8_t msg_code;
    union _dt_bytes {
        char bytes[9 * 2];
        struct _manual_settings ms;
    } u;
    uint16_t crc;
};

#endif //ATM_ESP_EXCHANGE_H
