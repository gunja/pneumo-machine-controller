package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class PointsSettingViewModel extends ViewModel {
    class MLTI extends MutableLiveData<Integer>{ public MLTI(int val){super(val);}; };
    private MLTI[][] d1_fwd;

    private MLTI[][] d1_bwd;

    public PointsSettingViewModel()
    {
        d1_fwd = new MLTI[4][];

        d1_fwd[0] =   new MLTI[] {
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0)
        };
        d1_fwd[1] = new MLTI[] {
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0)
                    };
        d1_fwd[2] = new MLTI[] {
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0)
                    };
        d1_fwd[3] = new MLTI[] {
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0),
                            new MLTI(0)
        };
        d1_bwd = new MLTI[4][];

        d1_bwd[0] =   new MLTI[] {
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0)
        };
        d1_bwd[1] = new MLTI[] {
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0)
        };
        d1_bwd[2] = new MLTI[] {
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0)
        };
        d1_bwd[3] = new MLTI[] {
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0),
                new MLTI(0)
        };

    }

    public LiveData<Integer> getFWD_X_Y(int x, int y)
    {
        return d1_fwd[x][y];
    }

    public LiveData<Integer> getBWD_X_Y(int x, int y)
    {
        return d1_bwd[x][y];
    }

    public void setFWDValue(int x, int y, int val)
    {
        d1_fwd[x][y].setValue(val);
    }

    public void setBWDValue(int x, int y, int val)
    {
        d1_bwd[x][y].setValue(val);
    }
}
