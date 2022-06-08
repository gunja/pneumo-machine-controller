package com.example.manager_pneumo;

import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.zgkxzx.modbus4And.requset.ModbusParam;
import com.zgkxzx.modbus4And.requset.ModbusReq;
import com.zgkxzx.modbus4And.requset.OnRequestBack;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.security.auth.callback.Callback;

public class ModbusExchangeThread extends Thread implements Handler.Callback  {
    Handler mHandler;
    Handler mlHandler;
    final static String TAG="ModbusExchangeThread ";
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

    public Handler getHandler() {
        return mHandler;
    }

    public void setMUIHandler(Handler muiH)
    {
        mlHandler = muiH;
    }

    public String convertShortsArray(short[] data)
    {
        ByteBuffer byteBuf = ByteBuffer.allocate(2*data.length);
        int count = 0;
        for (short s : data) {
            byte v = (byte )(s & 0xFF);
            byteBuf.put(v);
            if (v == 0) break;
            count++;
            v = (byte) ((s>>8) & 0xFF);
            byteBuf.put(v);
            if (v == 0) break;
            count++;
        }
        String v = new String("");
        try {
            v =new String(byteBuf.array(), 0, count, "UTF-8");
        } catch(Exception e) {}
        return v;
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {
        Log.d("ModBus thread", "handleMessage - what = " + message.what + "this threadId=" + Thread.currentThread().getId()) ;
        if( mlHandler!= null) mlHandler.sendEmptyMessage(6);

        if (message.what == 1)
        {
            ModbusReq.getInstance().setParam(new ModbusParam()
                            .setHost("192.168.1.1")
                            .setPort(502)
                            .setEncapsulated(false)
                            .setKeepAlive(true)
                            .setTimeout(2000)
                            .setRetries(0))
                    .init(new OnRequestBack<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d(TAG, "onSuccess " + s);
                            mHandler.sendEmptyMessage(30);
                        }

                        @Override
                        public void onFailed(String msg) {
                            Log.d(TAG, "onFailed " + msg);
                        }
                    });

        }
        if(message.what == 30)
        {
            ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
                @Override
                public void onSuccess(short[] data) {
                    String apName = convertShortsArray(data);
                    Log.d(TAG, "readHoldingRegisters onSuccess AP_NAME_" + Arrays.toString(data)+ "as string \"" + apName + "\"");
                    mHandler.sendEmptyMessage(41);
                    Message msg = new Message();
                    msg.what = 30;
                    msg.obj = apName;
                    mlHandler.sendMessage(msg);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e(TAG, "readHoldingRegisters onFailed  AP_NAME_" + msg);
                }
            }, 1, 387, 23);

        }
        if (message.what == 41)
        {
            ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
                @Override
                public void onSuccess(short[] data) {
                    for(int i=0; i < 8; ++i)
                    {
                        short[] sub = Arrays.copyOfRange(data, i * 10, (i+1)*10);
                        String v = convertShortsArray(sub);
                        Log.d(TAG, "readHoldingRegisters onSuccess HOLD_REG_ACT" + (i+1) + "_NM_START as string \"" + v + "\"");
                        //todo send message, 41 to main thread
                        Message msg = new Message();
                        msg.what = 41;
                        msg.arg1 = i+1;
                        msg.obj = v;
                        mlHandler.sendMessage(msg);
                    }
                    mHandler.sendEmptyMessage(42);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT1_NM_START" + msg);
                }
            }, 1, 129, 80);

        }
        if (message.what == 42)
        {
            ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
                @Override
                public void onSuccess(short[] data) {
                    for(int i=0; i < 8; ++i)
                    {
                        short[] sub = Arrays.copyOfRange(data, i * 10, (i+1)*10);
                        String v = convertShortsArray(sub);
                        Log.d(TAG, "readHoldingRegisters onSuccess HOLD_REG_H" + (i+1) + "_NM_START as string \"" + v + "\"");
                        Message msg = new Message();
                        msg.what = 42;
                        msg.arg1 = i+1;
                        msg.obj = v;
                        mlHandler.sendMessage(msg);
                    }
                    mHandler.sendEmptyMessage(43);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
                }
            }, 1, 49, 80);
        }
        if (message.what == 101)
        {
            short[] convertedString = encodeString((String)message.obj, 23);
            ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.e(TAG, "writeRegisters onSuccess " + s);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e(TAG, "writeRegisters onFailed " + msg);
                }
            }, 1, 387,convertedString);
        }
        if (message.what == 102)
        {
            short[] convertedString = encodeString((String)message.obj, 10);
            ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
                @Override
                public void onSuccess(String s) {
                    Log.e(TAG, "writeRegisters onSuccess " + s);
                }

                @Override
                public void onFailed(String msg) {
                    Log.e(TAG, "writeRegisters onFailed " + msg);
                }
            }, 1,49 + 10 * (message.arg1 -1) ,convertedString);
        }


        return true;
    }

    private short[] encodeString(String obj, int regsNum) {
        byte[] bytss = obj.getBytes(StandardCharsets.UTF_8);
        short[] rv = new short[regsNum];
        for(int i=0; i < bytss.length/2; i++)
            rv[i] = (short) (bytss[2 * i] +  256 * (short) bytss[2 * i + 1]);

        if (bytss.length % 2> 0) {
            rv[bytss.length/2] = (short) (256 * (short)  bytss[bytss.length-1]);
        }
        return rv;
    }
}
