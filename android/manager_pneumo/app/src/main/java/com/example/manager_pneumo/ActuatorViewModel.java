package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActuatorViewModel extends ViewModel
{
    MutableLiveData<String> titleValue = new MutableLiveData<>();
    MutableLiveData<String> actualValueAsText= new MutableLiveData<>();
    MutableLiveData<Float> val1Bar= new MutableLiveData<>();
    MutableLiveData<Float> val2Bar= new MutableLiveData<>();
    MutableLiveData<Integer> raw1bar= new MutableLiveData<>();
    MutableLiveData<Integer> raw2bar= new MutableLiveData<>();

    MutableLiveData<Float> val1Kg= new MutableLiveData<Float>();
    MutableLiveData<Float> val2Kg= new MutableLiveData<Float>();
    MutableLiveData<Integer> raw1Kg= new  MutableLiveData<Integer>();
    MutableLiveData<Integer> raw2Kg= new  MutableLiveData<Integer>();

    MutableLiveData<Boolean> showInKg= new MutableLiveData<Boolean>(false);

    MutableLiveData<Integer> lastRawReading= new MutableLiveData<Integer>();

    MutableLiveData<Integer> requestedValue= new  MutableLiveData<Integer>();
    MutableLiveData<String> rqValueAsText= new MutableLiveData<>();
    MutableLiveData<Boolean> reactUpwards= new MutableLiveData<Boolean>();

    public ActuatorViewModel(){
        super();

    }
    public LiveData<String> getTitle() { return titleValue;};
    public LiveData<String> getValueAsString() { return actualValueAsText;};
    public void setTitle(String title){
        titleValue.setValue(title);
    }
    public void postTitle(String title){
        titleValue.postValue(title);
    }
    public void setLastRawReading(int val){
        lastRawReading.setValue(val);
        updateValueDisplayed();
    }

    private void updateValueDisplayed() {
        //TODO implement this changes
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
        raw1bar.setValue((int)obj.getR1_bar());
        raw2bar.setValue((int)obj.getR2_bar());
        val1Bar.setValue(obj.getVal1_bar());
        val2Bar.setValue(obj.getVal2_bar());

        raw1Kg.setValue((int)obj.getR1_kgs());
        raw2Kg.setValue((int)obj.getR2_kgs());
        val1Kg.setValue(obj.getVal1_kgs());
        val2Kg.setValue(obj.getVal2_kgs());
    }

    public void setReactionDirection(boolean i) {
        reactUpwards.setValue(i);
    }
}
