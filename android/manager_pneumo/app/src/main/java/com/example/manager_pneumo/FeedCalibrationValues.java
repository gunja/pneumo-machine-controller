package com.example.manager_pneumo;

public class FeedCalibrationValues {
    private final int r1;
    private final int r2;
    private final float val1;
    private final float val2;

    public float getVal1() {
        return val1;
    }

    public float getVal2() {
        return val2;
    }



    public int getR1() {
        return r1;
    }

    public int getR2() {
        return r2;
    }



    public FeedCalibrationValues() {r1 = 0; r2 = 0; val1 = 0.f; val2 = 0.f;};
    public FeedCalibrationValues(int r1, int r2, float v1, float v2)
    {
        this.r1 = r1;
        this.r2 = r2;
        this.val1 = v1;
        this.val2 = v2;
    }
    
}
