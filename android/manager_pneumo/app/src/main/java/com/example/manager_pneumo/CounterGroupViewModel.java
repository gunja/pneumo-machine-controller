package com.example.manager_pneumo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CounterGroupViewModel extends ViewModel {
    private MutableLiveData<Integer> presence;
    private MutableLiveData<Integer> d1Value;
    private MutableLiveData<Integer> d2Value;
    private MutableLiveData<Integer> d3Value;
    private MutableLiveData<Integer> d4Value;

    public CounterGroupViewModel()
    {
        presence = new MutableLiveData<>(0);
        d1Value = new MutableLiveData<>(0);
        d2Value = new MutableLiveData<>(0);
        d3Value = new MutableLiveData<>(0);
        d4Value = new MutableLiveData<>(0);
    }

    public void postD1Value(int v)
    {
        d1Value.postValue(v);
    }
    public void postD2Value(int v)
    {
        d2Value.postValue(v);
    }
    public void postD3Value(int v)
    {
        d3Value.postValue(v);
    }
    public void postD4Value(int v)
    {
        d4Value.postValue(v);
    }
    public void postPresenceValue(int v)
    {
        presence.postValue(v);
    }

    public LiveData<Integer> getD1() { return d1Value;}
    public LiveData<Integer> getD2() { return d2Value;}
    public LiveData<Integer> getD3() { return d3Value;}
    public LiveData<Integer> getD4() { return d4Value;}
    public LiveData<Integer> presence() { return presence;}
}
