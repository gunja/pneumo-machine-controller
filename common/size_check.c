#include "placements.h"
#include <stdio.h>
#include <stdlib.h>

int main(int argc, char * argv[])
{
    struct memory_layout ml;
    printf("size of memory_layout = %zd\n", sizeof(ml));
    printf("offset of ActivPosis = %zd\n", (char*)(&ml.activation_position[0]) - (char *)(&ml));
    printf("offset of ap_name = %zd\n", (char*)(&ml.ap_name) - (char*)(&ml));

   printf("last reg used = %d\n", HOLD_REG_AUTO_D4_TARGET_C8);
    printf("2 times last reg = %d\n", 2 * HOLD_REG_AUTO_D4_TARGET_C8); 
    printf("register AP_name=%d  offset =%d \n", AP_NAME_, 2 * AP_NAME_ );
    printf("activation_position = %d in bytes = %d\n", HOLD_REG_D1_FWD_T0, 2* HOLD_REG_D1_FWD_T0);

    printf("start of H1_name = %d  in bytes = %d  real: %d\n", HOLD_REG_H1_NM_START, HOLD_REG_H1_NM_START * 2, (char*)(&ml.header_names[0]) - (char*)(&ml) + 1 * 2); 
    printf("start of CYL_names = %d in bytes = %d offset =%d\n", HOLD_REG_ACT1_NM_START, HOLD_REG_ACT1_NM_START * 2,  (char*)(&ml.cylinder_names[0]) - (char*)(&ml) + 2);
    printf("start of calibr_settings = %d in bytes =%d  offset = %d\n", HOLD_REG_ACT1_RAW_1_PR, 2 * HOLD_REG_ACT1_RAW_1_PR, (char*)(&ml.calibration_settings[0]) - (char*)(&ml) + 2);
    printf("hold_reg_directions = %d in bytes = %d offset = %d\n", HOLD_REG_ACT_DIRECTIONS, 2*HOLD_REG_ACT_DIRECTIONS, (char*)(&ml.directions) - (char*)(&ml) + 2);
};

