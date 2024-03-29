package com.example.manager_pneumo;

public class ActuatorCalibrationValues {
    private short r1_bar;
    private short r2_bar;
    private float val1_bar;
    private float val2_bar;

    private short r1_kgs;
    private short r2_kgs;
    private float val1_kgs;
    private float val2_kgs;

    public short getR1_bar() {
        return r1_bar;
    }

    public short getR2_bar() {
        return r2_bar;
    }

    public float getVal1_bar() {
        return val1_bar;
    }

    public float getVal2_bar() {
        return val2_bar;
    }

    public short getR1_kgs() {
        return r1_kgs;
    }

    public short getR2_kgs() {
        return r2_kgs;
    }

    public float getVal1_kgs() {
        return val1_kgs;
    }

    public float getVal2_kgs() {
        return val2_kgs;
    }



    public ActuatorCalibrationValues()
    {
        r1_bar = 0;
        r2_bar = 0;
        r1_kgs = 0;
        r2_kgs = 0;
        val1_bar = 0.f;
        val2_bar = 0.f;
        val1_kgs = 0.f;
        val2_kgs = 0.f;
    };

    public ActuatorCalibrationValues(short r1b, short r2b, float v1b, float v2b, short r1k, short r2k, float v1k, float v2k)
    {
        r1_bar = r1b;
        r2_bar = r2b;
        r1_kgs = r1k;
        r2_kgs = r2k;
        val1_bar = v1b;
        val2_bar = v2b;
        val1_kgs = v1k;
        val2_kgs = v2k;
    };
}
