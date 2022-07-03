package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AutoSensorSelectedViewModel extends ViewModel {
    public MutableLiveData<Integer> val;
    public AutoSensorSelectedViewModel()
    {
        val = new MutableLiveData<>(0);
    }

    public LiveData<Integer> getVal() { return val;}
    public void setVal(int v) {
        val.setValue(v);
    }
    public void postVal(int v)
    {
        val.postValue(v);
    }

}
