package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedsViewModel extends ViewModel {
    MutableLiveData<String> titleValue;
    MutableLiveData<String> valueAsText;
    MutableLiveData<Float> val1Bar;



    MutableLiveData<Float> val2Bar;
    MutableLiveData<Integer> raw1;
    MutableLiveData<Integer> raw2;

    MutableLiveData<Integer> lastReceivedValue ;


    public FeedsViewModel()
    {
        titleValue = new MutableLiveData<String>("");
        valueAsText = new MutableLiveData<String>("");
        val1Bar= new MutableLiveData<Float>(0.f);
        val2Bar = new MutableLiveData<Float>(0.f);
        raw1 = new MutableLiveData<Integer>(0);
        raw2= new MutableLiveData<Integer>(0);
        lastReceivedValue= new MutableLiveData<Integer>(0);
    }

    public LiveData<Integer> getlastReceivedValue() { return lastReceivedValue;};

    public LiveData<String> getTitle() {
        if (titleValue == null)
            titleValue = new MutableLiveData<String>();
        return titleValue;
    };

    public LiveData<String> getValueAsString() {
        if (valueAsText == null)
            valueAsText = new MutableLiveData<String>();
        return valueAsText;
    };

    public void setTitle(String title){
        if (titleValue == null)
            titleValue = new MutableLiveData<String>();
        titleValue.setValue(title);
    }

    public void postTitle(String title){
        if (titleValue == null)
            titleValue = new MutableLiveData<String>();
        titleValue.postValue(title);
    }

    private String formatString(int v)
    {
        String rv;
        if (raw1.getValue() == raw2.getValue())
        {
            rv = String.format("r %d", v);
        }
        else
        {
            rv = String.format("%2.2f бар", (v - raw1.getValue())*(val2Bar.getValue() - val1Bar.getValue())/(raw2.getValue() - raw1.getValue()) + val1Bar.getValue());
        }
        return rv;
    }

    public void setValue(int v)  {
        if(lastReceivedValue == null)
            lastReceivedValue = new MutableLiveData<Integer>();
        lastReceivedValue.setValue(v);
        if (valueAsText ==null)
            valueAsText = new MutableLiveData<String>();
        valueAsText.setValue(formatString(lastReceivedValue.getValue()));
    }
    public void postValue(int v) {
        if(lastReceivedValue == null)
            lastReceivedValue = new MutableLiveData<Integer>();
        lastReceivedValue.postValue(v);
        if (valueAsText ==null)
            valueAsText = new MutableLiveData<String>();
        valueAsText.postValue(formatString(lastReceivedValue.getValue()));
    }

    public void setCalibrationValues(FeedCalibrationValues obj) {
        if (raw1 == null)
            raw1 = new MutableLiveData<Integer>();
        if(raw2 == null)
            raw2 = new MutableLiveData<Integer>();
        raw1.setValue(obj.getR1());
        raw2.setValue(obj.getR2());
        if (val1Bar == null)
            val1Bar = new MutableLiveData<Float>();
        val1Bar.setValue(obj.getVal1());
        if(val2Bar == null)
            val2Bar = new MutableLiveData<Float>();
        val2Bar.setValue(obj.getVal2());
        if (lastReceivedValue == null) {
            lastReceivedValue = new MutableLiveData<Integer>();
            lastReceivedValue.setValue(0);
        }
        setValue(lastReceivedValue.getValue());
    }

    public LiveData<Float> getVal1Bar() {
        return val1Bar;
    }

    public LiveData<Float> getVal2Bar() {
        return val2Bar;
    }

    public LiveData<Integer> getRaw1() {
        return raw1;
    }

    public LiveData<Integer> getRaw2() {
        return raw2;
    }
}
