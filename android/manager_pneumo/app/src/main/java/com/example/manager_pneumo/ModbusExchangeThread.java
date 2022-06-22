package com.example.manager_pneumo;

import static java.nio.ByteOrder.BIG_ENDIAN;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.zgkxzx.modbus4And.requset.ModbusParam;
import com.zgkxzx.modbus4And.requset.ModbusReq;
import com.zgkxzx.modbus4And.requset.OnRequestBack;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ModbusExchangeThread extends Thread implements Handler.Callback  {
    Handler mHandler;
    Handler mlHandler;
    MainActivity activity;
    final static String TAG="ModbusExchangeThread ";

    final public static int GET_ACCESS_POINT_NAME = 30;
    final public static int GET_ACTUATOR_NAMES =  41;
    final public static int GET_HEADER_NAMES_HRS = 42;
    final public static int GET_HEADER_CALIBRATION_COEFFICIENTS = 43;
    final public static int GET_ACTUATORS_CALIBRATION_COEFFICIENTS = 44;
    final public static int GET_REACTION_DIRECTION = 45;
    final public static int GET_POSITIONS_OF_REACTION = 46;
    final public static int GET_LATEST_SELECTED_TAB = 47;
    final public static int GET_GOAL_PRESSURES = 48;
    final public static int  GET_ALL_INPUT_REGS = 99;
    final public static int GET_ALL_HEADERS_INPUT_REGS = 90;
    final public static int GET_ALL_DATCHIK_INPUT_REGS = 91;
    final public static int GET_ALL_ACTUATOR_INPUT_REGS = 92;
    final public static int READ_INPUT_REGS_SUCCESS = 93;
    final public static int CONN_FAIL_MSG = 201;
    final public static int CONN_DONE_MSG = 202;

    ViewModelProvider vmp;
    FeedsViewModel fwms[];
    ActuatorViewModel awms[];
    boolean needHideDialog;

    public Looper lpr;

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public ModbusExchangeThread()
    {
        super();
        mHandler = new Handler(this);
    }

    @Override
    public void run()
    {
        Looper.prepare();
        lpr = Looper.myLooper();

        mHandler.sendEmptyMessage(1);
        if( mlHandler!= null) mlHandler.sendEmptyMessage(5);
        System.out.println("mHandler =" + mHandler);
        System.out.println("mHandler =" + mlHandler);
        vmp = new ViewModelProvider(activity);
        fwms = new FeedsViewModel[]{
                vmp.get("1", FeedsViewModel.class),
                vmp.get("2", FeedsViewModel.class),
                vmp.get("3", FeedsViewModel.class),
                vmp.get("4", FeedsViewModel.class),
                vmp.get("5", FeedsViewModel.class),
                vmp.get("6", FeedsViewModel.class),
                vmp.get("7", FeedsViewModel.class),
                vmp.get("8", FeedsViewModel.class)
        };

        awms = new ActuatorViewModel[] {
                vmp.get("11", ActuatorViewModel.class),
                vmp.get("12", ActuatorViewModel.class),
                vmp.get("13", ActuatorViewModel.class),
                vmp.get("14", ActuatorViewModel.class),
                vmp.get("15", ActuatorViewModel.class),
                vmp.get("16", ActuatorViewModel.class),
                vmp.get("17", ActuatorViewModel.class),
                vmp.get("18", ActuatorViewModel.class)
        };

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
        switch(message.what)
        {
            case 1:
                ModbusReq.getInstance().setParam(new ModbusParam()
                            .setHost("192.168.1.1")
                            .setPort(502)
                            .setEncapsulated(false)
                            .setKeepAlive(true)
                            .setTimeout(20000)
                            .setRetries(0))
                    .init(new OnRequestBack<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Log.d(TAG, "onSuccess " + s);
                            mHandler.sendEmptyMessage(30);
                            Message msg_connd = new Message();
                            msg_connd.what = CONN_DONE_MSG;
                            mlHandler.sendMessage(msg_connd);
                            needHideDialog= true;
                        }

                        @Override
                        public void onFailed(String msg) {
                            Log.d(TAG, "onFailed " + msg);
                            Message msg_fail = new Message();
                            msg_fail.what = CONN_FAIL_MSG;
                            mlHandler.sendMessage(msg_fail);
                        }
                    });
                break;
            case GET_ACCESS_POINT_NAME:
                getAccessPointName(41);
                break;
            case  GET_ACTUATOR_NAMES:
                getActuatorNames(42);
                break;
            case GET_HEADER_NAMES_HRS:
                getHeaderNamesHRs(43);
                break;
            case GET_HEADER_CALIBRATION_COEFFICIENTS:
                getHeaderCalibrationCoefficients(44);
                break;
            case GET_ACTUATORS_CALIBRATION_COEFFICIENTS:
                getActuatorsCalibrationCoefficients(45);
                break;
            case GET_REACTION_DIRECTION:
                getReactionDirection(46);
                break;
            case GET_POSITIONS_OF_REACTION:
                getPositionsOfReaction(47);
                break;
            case GET_LATEST_SELECTED_TAB:
                getLatestSelectedTab(48);
            case GET_GOAL_PRESSURES:
                getGoalPressures(99);
            case GET_ALL_INPUT_REGS:
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
            case 112:
                setHeaderCalibrationCoefficients(message);
                break;
            case 113:
                setActuatorsCalibrationCoefficients(message);
                break;
            case 1000:
                //mbThread.quit();
                break;
        }
        return true;
    }

    private void setStringRegisters(Message message, int baseOffset, int sz) {
        short[] convertedString = encodeString((String)message.obj, sz);
        ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "writeRegisters onSuccess " + s);
            }

            @Override
            public void onFailed(String msg) {
                Log.d(TAG, "writeRegisters onFailed " + msg);
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
                msg.what = GET_ACCESS_POINT_NAME;
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
                    //Log.d(TAG, "readHoldingRegisters onSuccess HOLD_REG_ACT" + (i+1) + "_NM_START as string \"" + v + "\"");
                    //todo send message, 41 to main thread
                    //Message msg = new Message();
                    //msg.what = GET_ACTUATOR_NAMES;
                    //msg.arg1 = i+1;
                    //msg.obj = v;
                    //mlHandler.sendMessage(msg);
                    awms[i].postTitle(v);
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
                    //Log.d(TAG, "readHoldingRegisters onSuccess HOLD_REG_H" + (i+1) + "_NM_START as string \"" + v + "\"");
                    //Message msg = new Message();
                    //msg.what = GET_HEADER_NAMES_HRS;
                    //msg.arg1 = i+1;
                    //msg.obj = v;
                    //mlHandler.sendMessage(msg);
                    fwms[i].postTitle(v);
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
                    ByteBuffer bb = ByteBuffer.allocate(4);
                    bb.putShort(sub[2]);
                    bb.putShort(sub[1]);
                    bb.rewind();
                    float v1 = bb.getFloat();
                    bb.rewind();
                    bb.putShort(sub[5]);
                    bb.putShort(sub[4]);
                    bb.rewind();
                    float v2 = bb.getFloat();

                    Log.d(TAG, "readHoldingRegisters onSuccess HeaderCalibrationCoefficients of " + (i+1) + "r1 =" + sub[0] + "  r2 = " + sub[3] + "  v1=" + v1 + "  v2 ="+v2);
                    Message msg = new Message();
                    msg.what = GET_HEADER_CALIBRATION_COEFFICIENTS;
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
                    float v1k=0, v2k=0, v1b = 0, v2b =0;
                    ByteBuffer bb = ByteBuffer.allocate(4);
                    bb.putShort(sub[2]);
                    bb.putShort(sub[1]);
                    bb.rewind();
                    v1b = bb.getFloat();
                    bb.rewind();
                    bb.putShort(sub[5]);
                    bb.putShort(sub[4]);
                    bb.rewind();
                    v2b = bb.getFloat();
                    bb.rewind();
                    bb.putShort(sub[8]);
                    bb.putShort(sub[7]);
                    bb.rewind();
                    v1k = bb.getFloat();
                    bb.rewind();
                    bb.putShort(sub[11]);
                    bb.putShort(sub[10]);
                    bb.rewind();
                    v2k = bb.getFloat();

                    Log.d(TAG, "readHoldingRegisters onSuccess getActuatorsCalibrationCoefficients of " + (i+1) + "r1 =" + sub[0] + "  r2 = " + sub[3] + "  v1=" + v1b + "  v2 ="+v2b
                         +"r1k =" + sub[6] + "  r2kg = " + sub[9] + "  v1k=" + v1k + "  v2k ="+v2k);
                    Message msg = new Message();
                    msg.what = GET_ACTUATORS_CALIBRATION_COEFFICIENTS;
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
        /*
        if (needHideDialog) {
            mlHandler.sendEmptyMessage(READ_INPUT_REGS_SUCCESS);
            needHideDialog = false;
        }
        return;
        */
        ModbusReq.getInstance().readInputRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                for(int i = 0; i < 8; ++i) {
                    fwms[i].postValue((int)data[0 + i]);
                }

                short[] subDetail = Arrays.copyOfRange(data, 8, 12);
                Message msgDet = new Message();
                msgDet.what = GET_ALL_DATCHIK_INPUT_REGS;
                msgDet.obj = subDetail;
                mlHandler.sendMessage(msgDet);

                for(int i=0; i < 8; ++i)
                {
                    awms[i].postLastRawReading(data[12 + i]);
                }

                if (needHideDialog) {
                    mlHandler.sendEmptyMessage(READ_INPUT_REGS_SUCCESS);
                    needHideDialog = false;
                }

                mHandler.sendEmptyMessageDelayed(nextWhat, 150);
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

    private void setHeaderCalibrationCoefficients(Message message) {
        //TODO
        short[] shorts = new short[6];
        FeedCalibrationValues vals = (FeedCalibrationValues) message.obj;
        shorts[0] = (short) vals.getR1();
        shorts[2] = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal1()).array()).getShort();
        shorts[1] = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal1()).array()).getShort(2);
        shorts[3] = (short) vals.getR2();
        shorts[5] = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal2()).array()).getShort();
        shorts[4] = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal2()).array()).getShort(2);

        ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "writeRegisters onSuccess setHeaderCalibrationCoefficients  " + s);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "setHeaderCalibrationCoefficients writeRegisters onFailed " + msg);
            }
        }, 1, 1+ 6 * (message.arg1 -1) ,shorts);
    }

    private void setActuatorsCalibrationCoefficients(Message message) {
        //TODO
        short[] shorts = new short[12];
        ActuatorCalibrationValues vals = (ActuatorCalibrationValues) message.obj;
        shorts[0] =  vals.getR1_bar();
        ByteBuffer val_float = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal1_bar()).array());
        shorts[2] = val_float.getShort(0);
        shorts[1] = val_float.getShort(2);
        shorts[3] = vals.getR2_bar();
        val_float = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal2_bar()).array());
        shorts[5] = val_float.getShort(0);
        shorts[4] = val_float.getShort(2);

        shorts[6] = vals.getR1_kgs();
        val_float = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal1_kgs()).array());
        shorts[8] = val_float.getShort(0);
        shorts[7] = val_float.getShort(2);
        shorts[9] = vals.getR2_kgs();
        val_float = ByteBuffer.wrap(ByteBuffer.allocate(4).putFloat(vals.getVal2_kgs()).array());
        shorts[11] = val_float.getShort(0);
        shorts[10] = val_float.getShort(2);

        ModbusReq.getInstance().writeRegisters(new OnRequestBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "writeRegisters onSuccess " + s);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "writeRegisters onFailed " + msg);
            }
        }, 1, 210+ 12 * (message.arg1 -1) ,shorts);

        short reactDirs = 0;
        for(int i=0; i < 8; ++i)
        {
            reactDirs |= (awms[i].getReactionDirection().getValue()?3:0)<<(2*i);
        }
        ModbusReq.getInstance().writeRegister(new OnRequestBack<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "writeRegisters onSuccess " + s);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "writeRegisters onFailed " + msg);
            }
        }, 1, 306 , reactDirs);
    }


    private void getReactionDirection(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                for(int i=0; i < 8; ++i)
                {
                    awms[i].postReactionDirection((data[0] & (3 <<(2*i))) != 0);
                }
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 306, 1);
    }

    private void getPositionsOfReaction(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                    Log.d(TAG, "readHoldingRegisters onSuccess getReactionDirection with " + data[0] );
                    Message msg = new Message();
                    msg.what = GET_POSITIONS_OF_REACTION;
                    msg.obj = data;
                    mlHandler.sendMessage(msg);
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 307, 4*20);
    }

    private void getLatestSelectedTab(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                    Log.d(TAG, "readHoldingRegisters onSuccess getReactionDirection with " + data[0] );
                    Message msg = new Message();
                    msg.what = GET_LATEST_SELECTED_TAB;
                    msg.arg1 = data[0];
                    mlHandler.sendMessage(msg);
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 450, 1);
    }

    private void getGoalPressures(int nextWhat) {
        ModbusReq.getInstance().readHoldingRegisters(new OnRequestBack<short[]>() {
            @Override
            public void onSuccess(short[] data) {
                    Log.d(TAG, "readHoldingRegisters onSuccess getGoalPressures with " + data[0] );
                    Message msg = new Message();
                    msg.what = GET_GOAL_PRESSURES;
                    msg.obj = data;
                    mlHandler.sendMessage(msg);
                mHandler.sendEmptyMessage(nextWhat);
            }

            @Override
            public void onFailed(String msg) {
                Log.e(TAG, "readHoldingRegisters onFailed HOLD_REG_ACT2_NM_START" + msg);
            }
        }, 1, 410, 5*8);
    }


}
