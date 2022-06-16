package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActuatorViewModel extends ViewModel
{
    MutableLiveData<String> titleValue;
    MutableLiveData<String> actualValueAsText;
    MutableLiveData<Float> val1Bar;
    MutableLiveData<Float> val2Bar;
    MutableLiveData<Integer> raw1bar;
    MutableLiveData<Integer> raw2bar;

    MutableLiveData<Float> val1Kg;
    MutableLiveData<Float> val2Kg;
    MutableLiveData<Integer> raw1Kg;
    MutableLiveData<Integer> raw2Kg;

    MutableLiveData<Boolean> showInKg;

    MutableLiveData<Integer> lastRawReading;

    MutableLiveData<Integer> requestedValueManual;
    MutableLiveData<Integer> requestedValueAuto1;
    MutableLiveData<Integer> requestedValueAuto2;
    MutableLiveData<Integer> requestedValueAuto3;
    MutableLiveData<Integer> requestedValueAuto4;

    MutableLiveData<String> rqValueAsText;
    MutableLiveData<Boolean> reactUpwards;

    MutableLiveData<Integer> mode;


    public ActuatorViewModel()
    {
        super();
        titleValue = new MutableLiveData<String>("");
        actualValueAsText= new MutableLiveData<String>("") ;
        val1Bar= new MutableLiveData<Float>(0.f) ;
        val2Bar= new MutableLiveData<Float>(0.f) ;
        raw1bar = new MutableLiveData<Integer>(0) ;
        raw2bar = new MutableLiveData<Integer>(0) ;

        val1Kg= new MutableLiveData<Float>(0.f) ;
        val2Kg= new MutableLiveData<Float>(0.f) ;
        raw1Kg = new MutableLiveData<Integer>(0) ;
        raw2Kg = new MutableLiveData<Integer>(0) ;

        showInKg = new MutableLiveData<Boolean>(false) ;

        lastRawReading= new MutableLiveData<Integer>(0) ;

        rqValueAsText= new MutableLiveData<String>("") ;
        reactUpwards= new MutableLiveData<Boolean>(false) ;
        requestedValueManual = new MutableLiveData<Integer>(0) ;
        requestedValueAuto1 = new MutableLiveData<Integer>(0) ;
        requestedValueAuto2 = new MutableLiveData<Integer>(0) ;
        requestedValueAuto3 = new MutableLiveData<Integer>(0) ;
        requestedValueAuto4 = new MutableLiveData<Integer>(0) ;

        mode = new MutableLiveData<Integer>(0) ;
    }

    public LiveData<String> getTitle() {
        if (titleValue == null)
            titleValue = new MutableLiveData<String>("");
        return titleValue;};
    public LiveData<String> getValueAsString() {
        if (actualValueAsText == null)
            actualValueAsText = new MutableLiveData<String>("");
        return actualValueAsText;};
    public void setTitle(String title){
        titleValue.setValue(title);
    }
    public void postTitle(String title){
        if (titleValue == null)
            titleValue = new MutableLiveData<String>();
        titleValue.postValue(title);
    }
    public void setLastRawReading(int val){
        lastRawReading.setValue(val);
        updateValueDisplayed();
    }

    private String getFormattedString()
    {
        String rv = String.format("r %05d", lastRawReading.getValue());
        if(showInKg.getValue())
        {
            if((raw1Kg.getValue() - raw2Kg.getValue()) != 0)
            {
                float valeur = (val2Kg.getValue() - val1Kg.getValue())
                        /(raw2Kg.getValue() - raw1Kg.getValue()) * (lastRawReading.getValue() -  raw1Kg.getValue())
                        + val1Kg.getValue();
                int rem = (int) valeur/10;
                rv = String.format("%d кг", rem * 10);
            }
        } else {
            if((raw1bar.getValue() - raw2bar.getValue()) != 0)
            {
                float valeur = (val2Bar.getValue() - val1Bar.getValue())
                        /(raw2bar.getValue() - raw1bar.getValue()) * (lastRawReading.getValue() -  raw1bar.getValue())
                        + val1Bar.getValue();
                rv = String.format("%.2f бар", valeur);
            }
        }
        return rv;
    }

    private void updateValueDisplayed() {
        actualValueAsText.setValue(getFormattedString());
    }

    private void postUpdateValueDisplayed() {
        //TODO implement this changes
        actualValueAsText.postValue(getFormattedString());
    }

    public LiveData<Float> getVal1Bar() {
        return val1Bar;
    }

    public void setVal1Bar(float val1Bar) {
        this.val1Bar.setValue(val1Bar);
        updateValueDisplayed();
    }

    public LiveData<Float> getVal2Bar() {
        return val2Bar;
    }

    public void setVal2Bar(float val2Bar) {
        this.val2Bar.setValue(val2Bar);
        updateValueDisplayed();
    }

    public LiveData<Integer> getRaw1bar() {
        return raw1bar;
    }

    public void setRaw1bar(int raw1bar) {
        this.raw1bar.setValue(raw1bar);
        updateValueDisplayed();
    }

    public LiveData<Integer> getRaw2bar() {
        return raw2bar;
    }

    public void setRaw2bar(int raw2bar) {
        this.raw2bar.setValue(raw2bar);
        updateValueDisplayed();
    }

    public LiveData<Float> getVal1Kg() {
        return val1Kg;
    }

    public void setVal1Kg(float val1Kg) {
        this.val1Kg.setValue(val1Kg);
        updateValueDisplayed();
    }

    public LiveData<Float> getVal2Kg() {
        return val2Kg;
    }

    public void setVal2Kg(float val2Kg) {
        this.val2Kg.setValue(val2Kg);
        updateValueDisplayed();
    }

    public LiveData<Integer> getRaw1Kg() {
        return raw1Kg;
    }

    public void setRaw1Kg(int raw1Kg) {
        this.raw1Kg.setValue(raw1Kg);
        updateValueDisplayed();
    }

    public LiveData<Integer> getRaw2Kg() {
        return raw2Kg;
    }

    public void setRaw2Kg(int raw2Kg) {
        this.raw2Kg.setValue(raw2Kg);
        updateValueDisplayed();
    }

    public LiveData<Boolean> getShowInKg() {
        return showInKg;
    }

    public void setShowInKg(boolean showInKg) {
        this.showInKg.setValue(showInKg);
        updateValueDisplayed();
    }

    public void setCalibrationValues(ActuatorCalibrationValues obj) {
        if (raw1bar == null)
            raw1bar = new MutableLiveData<Integer>();
        raw1bar.setValue((int)obj.getR1_bar());
        if (raw2bar == null)
            raw2bar = new MutableLiveData<Integer>();
        raw2bar.setValue((int)obj.getR2_bar());

        if (val1Bar == null)
            val1Bar = new MutableLiveData<Float>();
        val1Bar.setValue(obj.getVal1_bar());
        if (val2Bar == null)
            val2Bar = new MutableLiveData<Float>();
        val2Bar.setValue(obj.getVal2_bar());

        if (raw1Kg == null)
            raw1Kg = new MutableLiveData<Integer>();
        raw1Kg.setValue((int)obj.getR1_kgs());
        if (raw2Kg == null)
            raw2Kg = new MutableLiveData<Integer>();
        raw2Kg.setValue((int)obj.getR2_kgs());
        if (val1Kg == null)
            val1Kg = new MutableLiveData<Float>();
        if (val2Kg == null)
            val2Kg = new MutableLiveData<Float>();
        val1Kg.setValue(obj.getVal1_kgs());
        val2Kg.setValue(obj.getVal2_kgs());
    }

    public void setReactionDirection(boolean i) {
        reactUpwards.setValue(i);
    }

    public void postReactionDirection(boolean i) {
        reactUpwards.postValue(i);
    }


    public void postLastRawReading(int subAct) {
        lastRawReading.postValue(subAct);
        postUpdateValueDisplayed();
    }

    public LiveData<Integer> getLastRawReading() {
        return lastRawReading;
    }

    public LiveData<Boolean> getReactionDirection() { return reactUpwards;}

    public LiveData<String> getRQValueAsText()
    {
        return rqValueAsText;
    }

    public void postRequestedManualValue(int rqV)
    {
        requestedValueManual.postValue(rqV);
        postUpdateRQValAsText();
    }

    public void setRequestedManualValue(int rqV)
    {
        requestedValueManual.setValue(rqV);
        updateRQValAsText();
    }

    private void postUpdateRQValAsText() {
        rqValueAsText.postValue(rqValToString(requestedValueManual.getValue()));
    }
    private void updateRQValAsText() {
        rqValueAsText.setValue(rqValToString(requestedValueManual.getValue()));
    }

    private String rqValToString(Integer value) {
        return String.format("r %d", requestedValueManual.getValue());
    }
}
