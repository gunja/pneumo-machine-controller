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

    public FeedsViewModel(){
        super();
        titleValue = new MutableLiveData<>();
        valueAsText = new MutableLiveData<>();
        raw1 = new MutableLiveData<>();
        raw2 = new MutableLiveData<>();
        val1Bar = new MutableLiveData<>();
        val2Bar = new MutableLiveData<>();
        lastReceivedValue = new MutableLiveData<>();
        lastReceivedValue.setValue(0);
    }

    public LiveData<Integer> getlastReceivedValue() { return lastReceivedValue;};

    public LiveData<String> getTitle() { return titleValue;};
    public LiveData<String> getValueAsString() { return valueAsText;};

    public void setTitle(String title){
        titleValue.setValue(title);
    }
    public void postTitle(String title){
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
        lastReceivedValue.setValue(v);
        valueAsText.setValue(formatString(lastReceivedValue.getValue()));
    }
    public void postValue(int v) {
        lastReceivedValue.setValue(v);
        valueAsText.postValue(formatString(lastReceivedValue.getValue()));
    }

    public void setCalibrationValues(FeedCalibrationValues obj) {
        raw1.setValue(obj.getR1());
        raw2.setValue(obj.getR2());
        val1Bar.setValue(obj.getVal1());
        val1Bar.setValue(obj.getVal2());
        setValue(lastReceivedValue.getValue());
    }
}
