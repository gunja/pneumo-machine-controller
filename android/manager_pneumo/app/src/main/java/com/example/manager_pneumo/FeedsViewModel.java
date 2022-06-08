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

    public FeedsViewModel(){
        super();
        titleValue = new MutableLiveData<>();
        valueAsText = new MutableLiveData<>();
        raw1 = new MutableLiveData<>();
        raw2 = new MutableLiveData<>();
        val1Bar = new MutableLiveData<>();
        val2Bar = new MutableLiveData<>();
    }

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

    public void setValue(int v)  { valueAsText.setValue(formatString(v));  }
    public void postValue(int v)  { valueAsText.postValue(formatString(v));  }
}
