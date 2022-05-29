package com.example.manager_pneumo;

import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import javax.security.auth.callback.Callback;

public class ModbusExchangeThread extends Thread implements Handler.Callback  {
    Handler mHandler;
    Handler mlHandler;
    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler(this);
        mHandler.sendEmptyMessage(1);
        if( mlHandler!= null) mlHandler.sendEmptyMessage(5);
        System.out.println("mHandler =" + mHandler);
        System.out.println("mHandler =" + mlHandler);
        Looper.loop();
    }

    public void setMUIHandler(Handler muiH)
    {
        mlHandler = muiH;
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        Log.d("ModBus thread", "handleMessage - what = " + message.what + "this threadId=" + Thread.currentThread().getId()) ;
        if( mlHandler!= null) mlHandler.sendEmptyMessage(6);
        if (message.what  != 3)
            mHandler.sendEmptyMessage(3);
        return true;
    }
}
