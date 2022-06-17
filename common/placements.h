#ifndef PLACEMENTS_H
#define PLACEMENTS_H
#include <stdint.h>

#define HEADER_READING_COUNT 8
#define CYLINDER_PAIRS_COUNT 8

#define DISCRETE_INPUT_PAIR3_PRESENT 1
#define DISCRETE_INPUT_PAIR4_PRESENT 2

#define INPUT_REG_READING_H1 1
#define INPUT_REG_READING_H2 2
#define INPUT_REG_READING_H3 3
#define INPUT_REG_READING_H4 4
#define INPUT_REG_READING_H5 5
#define INPUT_REG_READING_H6 6
#define INPUT_REG_READING_H7 7
#define INPUT_REG_READING_H8 8

#define INPUT_REG_CNT_D1 9
#define INPUT_REG_CNT_D2 10
#define INPUT_REG_CNT_D3 11
#define INPUT_REG_CNT_D4 12

#define INPUT_REG_READING_ACT1 13
#define INPUT_REG_READING_ACT2 14
#define INPUT_REG_READING_ACT3 15
#define INPUT_REG_READING_ACT4 16
#define INPUT_REG_READING_ACT5 17
#define INPUT_REG_READING_ACT6 18
#define INPUT_REG_READING_ACT7 19
#define INPUT_REG_READING_ACT8 20

#define HOLD_REG_H1_RAW_1 1
#define HOLD_REG_H1_VAL_1_LOW 2
#define HOLD_REG_H1_VAL_1_HGH 3
#define HOLD_REG_H1_RAW_2 4
#define HOLD_REG_H1_VAL_2_LOW 5
#define HOLD_REG_H1_VAL_2_HGH 6

#define HOLD_REG_H2_RAW_1 7
#define HOLD_REG_H2_VAL_1_LOW 8
#define HOLD_REG_H2_VAL_1_HGH 9
#define HOLD_REG_H2_RAW_2 10
#define HOLD_REG_H2_VAL_2_LOW 11
#define HOLD_REG_H2_VAL_2_HGH 12

#define HOLD_REG_H3_RAW_1 13
#define HOLD_REG_H3_VAL_1_LOW 14
#define HOLD_REG_H3_VAL_1_HGH 15
#define HOLD_REG_H3_RAW_2 16
#define HOLD_REG_H3_VAL_2_LOW 17
#define HOLD_REG_H3_VAL_2_HGH 18

#define HOLD_REG_H4_RAW_1 19
#define HOLD_REG_H4_VAL_1_LOW 20
#define HOLD_REG_H4_VAL_1_HGH 21
#define HOLD_REG_H4_RAW_2 22
#define HOLD_REG_H4_VAL_2_LOW 23
#define HOLD_REG_H4_VAL_2_HGH 24

#define HOLD_REG_H5_RAW_1 25
#define HOLD_REG_H5_VAL_1_LOW 26
#define HOLD_REG_H5_VAL_1_HGH 27
#define HOLD_REG_H5_RAW_2 28
#define HOLD_REG_H5_VAL_2_LOW 29
#define HOLD_REG_H5_VAL_2_HGH 30

#define HOLD_REG_H6_RAW_1 31
#define HOLD_REG_H6_VAL_1_LOW 32
#define HOLD_REG_H6_VAL_1_HGH 33
#define HOLD_REG_H6_RAW_2 34
#define HOLD_REG_H6_VAL_2_LOW 35
#define HOLD_REG_H6_VAL_2_HGH 36

#define HOLD_REG_H7_RAW_1 37
#define HOLD_REG_H7_VAL_1_LOW 38
#define HOLD_REG_H7_VAL_1_HGH 39
#define HOLD_REG_H7_RAW_2 40
#define HOLD_REG_H7_VAL_2_LOW 41
#define HOLD_REG_H7_VAL_2_HGH 42

#define HOLD_REG_H8_RAW_1 43
#define HOLD_REG_H8_VAL_1_LOW 44
#define HOLD_REG_H8_VAL_1_HGH 45
#define HOLD_REG_H8_RAW_2 46
#define HOLD_REG_H8_VAL_2_LOW 47
#define HOLD_REG_H8_VAL_2_HGH 48

//10 register for each name of pressure heads
#define HX_NAME_LEN_REGS 10
#define HOLD_REG_H1_NM_START 49
#define HOLD_REG_H2_NM_START 59
#define HOLD_REG_H3_NM_START 69
#define HOLD_REG_H4_NM_START 79
#define HOLD_REG_H5_NM_START 89
#define HOLD_REG_H6_NM_START 99
#define HOLD_REG_H7_NM_START 109
#define HOLD_REG_H8_NM_START 119

//10 register for each name of actuating names
#define ACT_NAME_LEN_REGS 10
#define HOLD_REG_ACT1_NM_START 129
#define HOLD_REG_ACT2_NM_START 139
#define HOLD_REG_ACT3_NM_START 149
#define HOLD_REG_ACT4_NM_START 159
#define HOLD_REG_ACT5_NM_START 169
#define HOLD_REG_ACT6_NM_START 179
#define HOLD_REG_ACT7_NM_START 189
#define HOLD_REG_ACT8_NM_START 199

#define HOLD_REG_ACT1_RAW_1_PR 210
#define HOLD_REG_ACT1_VAL_1_LOW_PR 211
#define HOLD_REG_ACT1_VAL_1_HGH_PR 212
#define HOLD_REG_ACT1_RAW_2_PR 213
#define HOLD_REG_ACT1_VAL_2_LOW_PR 214
#define HOLD_REG_ACT1_VAL_2_HGH_PR 215
#define HOLD_REG_ACT1_RAW_1_WGT 216
#define HOLD_REG_ACT1_VAL_1_LOW_WGT 217
#define HOLD_REG_ACT1_VAL_1_HGH_WGT 218
#define HOLD_REG_ACT1_RAW_2_WGT 219
#define HOLD_REG_ACT1_VAL_2_LOW_WGT 220
#define HOLD_REG_ACT1_VAL_2_HGH_WGT 221

#define HOLD_REG_ACT2_RAW_1_PR 222
#define HOLD_REG_ACT2_VAL_1_LOW_PR 223
#define HOLD_REG_ACT2_VAL_1_HGH_PR 224
#define HOLD_REG_ACT2_RAW_2_PR 225
#define HOLD_REG_ACT2_VAL_2_LOW_PR 226
#define HOLD_REG_ACT2_VAL_2_HGH_PR 227
#define HOLD_REG_ACT2_RAW_1_WGT 228
#define HOLD_REG_ACT2_VAL_1_LOW_WGT 229
#define HOLD_REG_ACT2_VAL_1_HGH_WGT 230
#define HOLD_REG_ACT2_RAW_2_WGT 231
#define HOLD_REG_ACT2_VAL_2_LOW_WGT 232
#define HOLD_REG_ACT2_VAL_2_HGH_WGT 233

#define HOLD_REG_ACT3_RAW_1_PR 234
#define HOLD_REG_ACT3_VAL_1_LOW_PR 235
#define HOLD_REG_ACT3_VAL_1_HGH_PR 236
#define HOLD_REG_ACT3_RAW_2_PR 237
#define HOLD_REG_ACT3_VAL_2_LOW_PR 238
#define HOLD_REG_ACT3_VAL_2_HGH_PR 239
#define HOLD_REG_ACT3_RAW_1_WGT 240
#define HOLD_REG_ACT3_VAL_1_LOW_WGT 241
#define HOLD_REG_ACT3_VAL_1_HGH_WGT 242
#define HOLD_REG_ACT3_RAW_2_WGT 243
#define HOLD_REG_ACT3_VAL_2_LOW_WGT 244
#define HOLD_REG_ACT3_VAL_2_HGH_WGT 245

#define HOLD_REG_ACT4_RAW_1_PR 246
#define HOLD_REG_ACT4_VAL_1_LOW_PR 247
#define HOLD_REG_ACT4_VAL_1_HGH_PR 248
#define HOLD_REG_ACT4_RAW_2_PR 249
#define HOLD_REG_ACT4_VAL_2_LOW_PR 250
#define HOLD_REG_ACT4_VAL_2_HGH_PR 251
#define HOLD_REG_ACT4_RAW_1_WGT 252
#define HOLD_REG_ACT4_VAL_1_LOW_WGT 253
#define HOLD_REG_ACT4_VAL_1_HGH_WGT 254
#define HOLD_REG_ACT4_RAW_2_WGT 255
#define HOLD_REG_ACT4_VAL_2_LOW_WGT 256
#define HOLD_REG_ACT4_VAL_2_HGH_WGT 257

#define HOLD_REG_ACT5_RAW_1_PR 258
#define HOLD_REG_ACT5_VAL_1_LOW_PR 259
#define HOLD_REG_ACT5_VAL_1_HGH_PR 260
#define HOLD_REG_ACT5_RAW_2_PR 261
#define HOLD_REG_ACT5_VAL_2_LOW_PR 262
#define HOLD_REG_ACT5_VAL_2_HGH_PR 263
#define HOLD_REG_ACT5_RAW_1_WGT 264
#define HOLD_REG_ACT5_VAL_1_LOW_WGT 265
#define HOLD_REG_ACT5_VAL_1_HGH_WGT 266
#define HOLD_REG_ACT5_RAW_2_WGT 267
#define HOLD_REG_ACT5_VAL_2_LOW_WGT 268
#define HOLD_REG_ACT5_VAL_2_HGH_WGT 269

#define HOLD_REG_ACT6_RAW_1_PR 270
#define HOLD_REG_ACT6_VAL_1_LOW_PR 271
#define HOLD_REG_ACT6_VAL_1_HGH_PR 272
#define HOLD_REG_ACT6_RAW_2_PR 273
#define HOLD_REG_ACT6_VAL_2_LOW_PR 274
#define HOLD_REG_ACT6_VAL_2_HGH_PR 275
#define HOLD_REG_ACT6_RAW_1_WGT 276
#define HOLD_REG_ACT6_VAL_1_LOW_WGT 277
#define HOLD_REG_ACT6_VAL_1_HGH_WGT 278
#define HOLD_REG_ACT6_RAW_2_WGT 279
#define HOLD_REG_ACT6_VAL_2_LOW_WGT 280
#define HOLD_REG_ACT6_VAL_2_HGH_WGT 281

#define HOLD_REG_ACT7_RAW_1_PR 282
#define HOLD_REG_ACT7_VAL_1_LOW_PR 283
#define HOLD_REG_ACT7_VAL_1_HGH_PR 284
#define HOLD_REG_ACT7_RAW_2_PR 285
#define HOLD_REG_ACT7_VAL_2_LOW_PR 286
#define HOLD_REG_ACT7_VAL_2_HGH_PR 287
#define HOLD_REG_ACT7_RAW_1_WGT 288
#define HOLD_REG_ACT7_VAL_1_LOW_WGT 289
#define HOLD_REG_ACT7_VAL_1_HGH_WGT 290
#define HOLD_REG_ACT7_RAW_2_WGT 291
#define HOLD_REG_ACT7_VAL_2_LOW_WGT 292
#define HOLD_REG_ACT7_VAL_2_HGH_WGT 293

#define HOLD_REG_ACT8_RAW_1_PR 294
#define HOLD_REG_ACT8_VAL_1_LOW_PR 295
#define HOLD_REG_ACT8_VAL_1_HGH_PR 296
#define HOLD_REG_ACT8_RAW_2_PR 297
#define HOLD_REG_ACT8_VAL_2_LOW_PR 298
#define HOLD_REG_ACT8_VAL_2_HGH_PR 299
#define HOLD_REG_ACT8_RAW_1_WGT 300
#define HOLD_REG_ACT8_VAL_1_LOW_WGT 301
#define HOLD_REG_ACT8_VAL_1_HGH_WGT 302
#define HOLD_REG_ACT8_RAW_2_WGT 303
#define HOLD_REG_ACT8_VAL_2_LOW_WGT 304
#define HOLD_REG_ACT8_VAL_2_HGH_WGT 305

#define HOLD_REG_ACT_DIRECTIONS 306

#define HOLD_REG_D1_FWD_T0 307
#define HOLD_REG_D1_FWD_T1 308
#define HOLD_REG_D1_FWD_T2 309
#define HOLD_REG_D1_FWD_T3 310
#define HOLD_REG_D1_FWD_T4 311
#define HOLD_REG_D1_FWD_T5 312
#define HOLD_REG_D1_FWD_T6 313
#define HOLD_REG_D1_FWD_T7 314
#define HOLD_REG_D1_FWD_T8 315
#define HOLD_REG_D1_FWD_T9 316
#define HOLD_REG_D1_BWD_T0 317
#define HOLD_REG_D1_BWD_T1 318
#define HOLD_REG_D1_BWD_T2 319
#define HOLD_REG_D1_BWD_T3 320
#define HOLD_REG_D1_BWD_T4 321
#define HOLD_REG_D1_BWD_T5 322
#define HOLD_REG_D1_BWD_T6 323
#define HOLD_REG_D1_BWD_T7 324
#define HOLD_REG_D1_BWD_T8 325
#define HOLD_REG_D1_BWD_T9 326

#define HOLD_REG_D2_FWD_T0 327
#define HOLD_REG_D2_FWD_T1 328
#define HOLD_REG_D2_FWD_T2 329
#define HOLD_REG_D2_FWD_T3 330
#define HOLD_REG_D2_FWD_T4 331
#define HOLD_REG_D2_FWD_T5 332
#define HOLD_REG_D2_FWD_T6 333
#define HOLD_REG_D2_FWD_T7 334
#define HOLD_REG_D2_FWD_T8 335
#define HOLD_REG_D2_FWD_T9 336
#define HOLD_REG_D2_BWD_T0 337
#define HOLD_REG_D2_BWD_T1 338
#define HOLD_REG_D2_BWD_T2 339
#define HOLD_REG_D2_BWD_T3 340
#define HOLD_REG_D2_BWD_T4 341
#define HOLD_REG_D2_BWD_T5 342
#define HOLD_REG_D2_BWD_T6 343
#define HOLD_REG_D2_BWD_T7 344
#define HOLD_REG_D2_BWD_T8 345
#define HOLD_REG_D2_BWD_T9 346

#define HOLD_REG_D3_FWD_T0 347
#define HOLD_REG_D3_FWD_T1 348
#define HOLD_REG_D3_FWD_T2 349
#define HOLD_REG_D3_FWD_T3 350
#define HOLD_REG_D3_FWD_T4 351
#define HOLD_REG_D3_FWD_T5 352
#define HOLD_REG_D3_FWD_T6 353
#define HOLD_REG_D3_FWD_T7 354
#define HOLD_REG_D3_FWD_T8 355
#define HOLD_REG_D3_FWD_T9 356
#define HOLD_REG_D3_BWD_T0 357
#define HOLD_REG_D3_BWD_T1 358
#define HOLD_REG_D3_BWD_T2 359
#define HOLD_REG_D3_BWD_T3 360
#define HOLD_REG_D3_BWD_T4 361
#define HOLD_REG_D3_BWD_T5 362
#define HOLD_REG_D3_BWD_T6 363
#define HOLD_REG_D3_BWD_T7 364
#define HOLD_REG_D3_BWD_T8 365
#define HOLD_REG_D3_BWD_T9 366

#define HOLD_REG_D4_FWD_T0 367
#define HOLD_REG_D4_FWD_T1 368
#define HOLD_REG_D4_FWD_T2 369
#define HOLD_REG_D4_FWD_T3 370
#define HOLD_REG_D4_FWD_T4 371
#define HOLD_REG_D4_FWD_T5 372
#define HOLD_REG_D4_FWD_T6 373
#define HOLD_REG_D4_FWD_T7 374
#define HOLD_REG_D4_FWD_T8 375
#define HOLD_REG_D4_FWD_T9 376
#define HOLD_REG_D4_BWD_T0 377
#define HOLD_REG_D4_BWD_T1 378
#define HOLD_REG_D4_BWD_T2 379
#define HOLD_REG_D4_BWD_T3 380
#define HOLD_REG_D4_BWD_T4 381
#define HOLD_REG_D4_BWD_T5 382
#define HOLD_REG_D4_BWD_T6 383
#define HOLD_REG_D4_BWD_T7 384
#define HOLD_REG_D4_BWD_T8 385
#define HOLD_REG_D4_BWD_T9 386

#define AP_NAME_LEN_REGS 23
#define AP_NAME_ 387

// measurement tab has no targets. don't use it
//TODO
// tab "manual" has fields
// tab "auto" has field 4 times
#define HOLD_REG_MANUAL_TARGET_C1 410
#define HOLD_REG_MANUAL_TARGET_C2 411
#define HOLD_REG_MANUAL_TARGET_C3 412
#define HOLD_REG_MANUAL_TARGET_C4 413
#define HOLD_REG_MANUAL_TARGET_C5 414
#define HOLD_REG_MANUAL_TARGET_C6 415
#define HOLD_REG_MANUAL_TARGET_C7 416
#define HOLD_REG_MANUAL_TARGET_C8 417

#define HOLD_REG_AUTO_D1_TARGET_C1 418
#define HOLD_REG_AUTO_D1_TARGET_C2 419
#define HOLD_REG_AUTO_D1_TARGET_C3 420
#define HOLD_REG_AUTO_D1_TARGET_C4 421
#define HOLD_REG_AUTO_D1_TARGET_C5 422
#define HOLD_REG_AUTO_D1_TARGET_C6 423
#define HOLD_REG_AUTO_D1_TARGET_C7 424
#define HOLD_REG_AUTO_D1_TARGET_C8 425

#define HOLD_REG_AUTO_D2_TARGET_C1 426
#define HOLD_REG_AUTO_D2_TARGET_C2 427
#define HOLD_REG_AUTO_D2_TARGET_C3 428
#define HOLD_REG_AUTO_D2_TARGET_C4 429
#define HOLD_REG_AUTO_D2_TARGET_C5 430
#define HOLD_REG_AUTO_D2_TARGET_C6 431
#define HOLD_REG_AUTO_D2_TARGET_C7 432
#define HOLD_REG_AUTO_D2_TARGET_C8 433

#define HOLD_REG_AUTO_D3_TARGET_C1 434
#define HOLD_REG_AUTO_D3_TARGET_C2 435
#define HOLD_REG_AUTO_D3_TARGET_C3 436
#define HOLD_REG_AUTO_D3_TARGET_C4 437
#define HOLD_REG_AUTO_D3_TARGET_C5 438
#define HOLD_REG_AUTO_D3_TARGET_C6 439
#define HOLD_REG_AUTO_D3_TARGET_C7 440
#define HOLD_REG_AUTO_D3_TARGET_C8 441

#define HOLD_REG_AUTO_D4_TARGET_C1 442
#define HOLD_REG_AUTO_D4_TARGET_C2 443
#define HOLD_REG_AUTO_D4_TARGET_C3 444
#define HOLD_REG_AUTO_D4_TARGET_C4 445
#define HOLD_REG_AUTO_D4_TARGET_C5 446
#define HOLD_REG_AUTO_D4_TARGET_C6 447
#define HOLD_REG_AUTO_D4_TARGET_C7 448
#define HOLD_REG_AUTO_D4_TARGET_C8 449

#define LAST_SELECTED_MODE 450

#pragma pack(push, 1)
struct _input_regs
{
    uint16_t ir_h1;
    uint16_t ir_h2;
    uint16_t ir_h3;
    uint16_t ir_h4;
    uint16_t ir_h5;
    uint16_t ir_h6;
    uint16_t ir_h7;
    uint16_t ir_h8;

    uint16_t ir_cnt_d1;
    uint16_t ir_cnt_d2;
    uint16_t ir_cnt_d3;
    uint16_t ir_cnt_d4;

    uint16_t ir_act1;
    uint16_t ir_act2;
    uint16_t ir_act3;
    uint16_t ir_act4;
    uint16_t ir_act5;
    uint16_t ir_act6;
    uint16_t ir_act7;
    uint16_t ir_act8;
};

union fl_int16 {
    float f;
    uint16_t i[2];
};

union hdr_name_int16 {
    uint16_t v[HX_NAME_LEN_REGS];
    char name[HX_NAME_LEN_REGS * 2];
};

union cyl_name_int16 {
    uint16_t v[ACT_NAME_LEN_REGS];
    char name[ACT_NAME_LEN_REGS * 2];
};

union ap_name_int16 {
    uint16_t v[AP_NAME_LEN_REGS];
    char name[AP_NAME_LEN_REGS*2];
};

struct heading_read {
    uint16_t raw1;
    union fl_int16 fl1;
    uint16_t raw2;
    union fl_int16 fl2;
};

struct actuator_read {
    uint16_t raw_pressure1;
    union fl_int16 fl_pressure1;
    uint16_t raw_pressure2;
    union fl_int16 fl_pressure2;
    uint16_t raw_weight1;
    union fl_int16 fl_weight1;
    uint16_t raw_weight2;
    union fl_int16 fl_weight2;
};

struct activation_positions {
    uint16_t position_fwd[10];
    uint16_t position_bwd[10];
};

struct memory_layout {
    struct heading_read h[HEADER_READING_COUNT];
    union hdr_name_int16 header_names[HEADER_READING_COUNT];
    union cyl_name_int16 cylinder_names[CYLINDER_PAIRS_COUNT];
    uint16_t lost_reg1;
    struct actuator_read calibration_settings[CYLINDER_PAIRS_COUNT];
    uint16_t directions;
    struct activation_positions activation_position[4];
    union ap_name_int16 ap_name;

    int16_t manual_targets[CYLINDER_PAIRS_COUNT];
    int16_t auto_d1_targets[CYLINDER_PAIRS_COUNT];
    int16_t auto_d2_targets[CYLINDER_PAIRS_COUNT];
    int16_t auto_d3_targets[CYLINDER_PAIRS_COUNT];
    int16_t auto_d4_targets[CYLINDER_PAIRS_COUNT];

    int16_t last_selected_mode;
};

#pragma pack(pop)

#endif // PLACEMENTS_H
