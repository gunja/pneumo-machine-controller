package com.example.manager_pneumo;

import static java.nio.ByteOrder.BIG_ENDIAN;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.zgkxzx.modbus4And.requset.ModbusParam;
import com.zgkxzx.modbus4And.requset.ModbusReq;
import com.zgkxzx.modbus4And.requset.OnRequestBack;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
        //Log.d("ModBus thread", "handleMessage - what = " + message.what + "this threadId=" + Thread.currentThread().getId()) ;
        if( mlHandler!= null) mlHandler.sendEmptyMessage(6);

        switch(message.what)
        {
            case 1:
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
                break;
            case 30:
                getAccessPointName(41);
                break;
            case  41:
                getActuatorNames(42);
                break;
            case 42:
                getHeaderNamesHRs(43);
                break;
            case 43:
                getHeaderCalibrationCoefficients(44);
                break;
            case 44:
                getActuatorsCalibrationCoefficients(99);
                break;
            case 99:
                getAllInputRegs(99);
                break;

            case 101:
                setStringRegisters(message, 387, 23);
                break;
            case  102:
                setStringRegisters(message, 49, 10);
                break;
            case 103:
                setStringRegisters(message, 129, 10);
                break;
        }
        return true;
    }

    private void setStringRegisters(Message message, int baseOffset, int sz) {
        short[] convertedString = encodeString((String)message.obj, sz);
        ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "writeRegisters onSuccess " + s);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "writeRegisters onFailed " + msg);
            }
        }, 1,baseOffset + 10 * (message.arg1 -1) ,convertedString);
    }

    private void getAccessPointName(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                String apName = convertShortsArray(data);
                Log.d(TAG, "readHoldingRegisters onSuccess AP_NAME_" + Arrays.toString(data)+ "as string \"" + apName + "\"");
                mHandler.sendEmptyMessage(nextWhat);
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

    private void getActuatorNames(int nextWhat) {
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
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT1_NM_START" + msg);
            }
        }, 1, 129, 80);
    }

    private void getHeaderNamesHRs(int nextWhat) {
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
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 49, 80);
    }


    private void getHeaderCalibrationCoefficients(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                for(int i=0; i < 8; ++i)
                {
                    short[] sub = Arrays.copyOfRange(data, i * 6, (i+1)*6);
                    byte[] bts = new byte[12];
                    for(int s=0; s < 6; ++s) {bts[2*s] = (byte)(sub[s] &0xFF); bts[2*s + 1] = (byte)((sub[s]>>8) &0xFF);}
                    float v1=0, v2=0;
                    try {
                        v1 = ByteBuffer.wrap(bts, 2, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};
                    try {
                        v2 = ByteBuffer.wrap(bts, 8, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};

                    Log.d(TAG, "readHoldingRegisters onSuccess HeaderCalibrationCoefficients of " + (i+1) + "r1 =" + sub[0] + "  r2 = " + sub[3] + "  v1=" + v1 + "  v2 ="+v2);
                    Message msg = new Message();
                    msg.what = 43;
                    msg.arg1 = i+1;
                    msg.obj = new FeedCalibrationValues(sub[0], sub[3], v1, v2);
                    mlHandler.sendMessage(msg);
                }
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 1, 6 * 8);
    }

    private void getActuatorsCalibrationCoefficients(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                for(int i=0; i < 8; ++i)
                {
                    short[] sub = Arrays.copyOfRange(data, i * 12, (i+1)*12);
                    byte[] bts = new byte[12];
                    for(int s=0; s < 6; ++s) {bts[2*s] = (byte)(sub[s] &0xFF); bts[2*s + 1] = (byte)((sub[s]>>8) &0xFF);}
                    float v1k=0, v2k=0, v1b = 0, v2b =0;
                    try {
                        v1b = ByteBuffer.wrap(bts, 2, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};
                    try {
                        v1b = ByteBuffer.wrap(bts, 8, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};
                    try {
                        v1k = ByteBuffer.wrap(bts, 14, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};
                    try {
                        v1b = ByteBuffer.wrap(bts, 20, 4).order(BIG_ENDIAN).getFloat();
                    } catch (Exception e) {};

                    Log.d(TAG, "readHoldingRegisters onSuccess HeaderCalibrationCoefficients of " + (i+1) + "r1 =" + sub[0] + "  r2 = " + sub[3] + "  v1=" + v1b + "  v2 ="+v2b);
                    Message msg = new Message();
                    msg.what = 44;
                    msg.arg1 = i+1;
                    msg.obj = new ActuatorCalibrationValues(sub[0], sub[3], v1b, v2b, sub[6], sub[9], v1k, v2k);
                    mlHandler.sendMessage(msg);
                }
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 210, 12 * 8);
    }


    private void getAllInputRegs(int nextWhat) {
        ModbusReq.getInstance().readInputRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                short[] sub = Arrays.copyOfRange(data, 0, 8);
                Message msg = new Message();
                msg.what = 90;
                msg.obj = sub;
                mlHandler.sendMessage(msg);

                short[] subDetail = Arrays.copyOfRange(data, 8, 12);
                Message msgDet = new Message();
                msgDet.what = 91;
                msgDet.obj = subDetail;
                mlHandler.sendMessage(msgDet);

                short[] subActs = Arrays.copyOfRange(data, 12, 20);
                Message msgActs = new Message();
                msgActs.what = 92;
                msgActs.obj = subActs;
                mlHandler.sendMessage(msgActs);

                mHandler.sendEmptyMessageDelayed(nextWhat, 50);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 1, 20);
    }


    private short[] encodeString(String obj, int regsNum)  {
        byte[] bytss = new byte[2];
        try {
            bytss = obj.getBytes("UTF-8");
        } catch (Exception e) {};
        short[] rv = new short[regsNum];
        for(int i=0; i < bytss.length/2; i++) {
            int v1 = bytss[2 * i] & 0xFF;
            int v2 = bytss[2 * i + 1] & 0xFF;
            rv[i] = (short) (v1+ 256 * v2);
        }

        if (bytss.length % 2> 0) {
            int v = bytss[bytss.length-1]&0xFF;
            rv[bytss.length/2] = (short) v;
        }
        return rv;
    }
}
