package com.example.manager_pneumo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.webkit.ConsoleMessage;

import com.example.manager_pneumo.ui.main.ui.login.LoginViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.manager_pneumo.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener, Handler.Callback, FragmentResultListener {

    private static final String TAG = "EAT";
    private ActivityMainBinding binding;
    private LoginViewModel loginViewModel;
    private TabLayout.Tab tab[] = new TabLayout.Tab[SectionsPagerAdapter.TAB_TITLES.length];
    private ViewPager2 viewPager;
    private final String CUR_PASS_PRM = "CurPass";
    private String cur_pass;
    private ModbusExchangeThread mbThread;
    private Handler uiHandler;
    private SharedPreferences sharedPref;
    private ViewModelProvider mvp;

    public static final String EXIT_REQUESTED ="DESIRE_EXIt";
    public static final String REPEAR_REQUESTED = "DESIRE_REPEAT";

    private String apName;
    ConnectionDialogFragment cdf;
    FeedsViewModel fwms[];
    ActuatorViewModel awms[];

    public void renderSettingsPage() {
        viewPager.setCurrentItem(3);
    }

    public String getCurPW() { return cur_pass;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        cur_pass = sharedPref.getString(CUR_PASS_PRM, "1111");

        apName = new String("");


        uiHandler = new Handler(this);
        mbThread = new ModbusExchangeThread();
        mbThread.setMUIHandler(uiHandler);
        mbThread.setActivity(this);

        //mUiHandler = new Handler(this);
        System.out.println("Main thread = " + Thread.currentThread().getId() );

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        sectionsPagerAdapter.setMA(this);
        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setUserInputEnabled(false);
        TabLayout tabs = binding.tabs;
        for(int i =0; i < tab.length;++i)
        {
            tab[i] = tabs.newTab().setText(getResources().getString(SectionsPagerAdapter.TAB_TITLES[i]));
            tabs.addTab(tab[i]);
        }
        tabs.addOnTabSelectedListener(this);
        mvp = new ViewModelProvider(this);

    }

    protected void onStart () {
        super.onStart();
        ViewModelProvider vmp = new ViewModelProvider(this);
        Log.d("MainActivity","вызов onStart. Создание fwms для " + this + "  от VMP =" + vmp);
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

        cdf = ConnectionDialogFragment.newInstance("", "");
        //TODO fix this call from unconditional to comditional
        mbThread.start();
        getSupportFragmentManager().setFragmentResultListener(EXIT_REQUESTED, this, this );
        getSupportFragmentManager().setFragmentResultListener(REPEAR_REQUESTED, this, this );

        cdf.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
       System.out.println("selected tab" + tab.getPosition());
       if (tab.getPosition() == 3) {
           viewPager.setCurrentItem(4);
           System.out.println("set current 4");
       } else {
           viewPager.setCurrentItem(tab.getPosition());
       }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        System.out.println("reselected tab" + tab.getPosition());

    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch(msg.what)
        {
            case ModbusExchangeThread.GET_ACCESS_POINT_NAME:
                apName = new String((String) msg.obj);
                break;
            case ModbusExchangeThread.GET_ACTUATOR_NAMES: //getActuatorNames
                awms[msg.arg1-1].postTitle((String) msg.obj);
                break;
            case ModbusExchangeThread.GET_HEADER_NAMES_HRS: //getHeaderNamesHRs
                fwms[msg.arg1-1].postTitle((String) msg.obj);
                break;
            case ModbusExchangeThread.GET_HEADER_CALIBRATION_COEFFICIENTS: //getHeaderCalibrationCoefficients
                fwms[msg.arg1-1].setCalibrationValues((FeedCalibrationValues)msg.obj);
                break;
            case ModbusExchangeThread.GET_ACTUATORS_CALIBRATION_COEFFICIENTS: //getActuatorsCalibrationCoefficients
                awms[msg.arg1-1].setCalibrationValues((ActuatorCalibrationValues)msg.obj);
                break;
            case ModbusExchangeThread.GET_REACTION_DIRECTION:
                setReactionDirections(msg.arg1);
                break;
            case ModbusExchangeThread.GET_LATEST_SELECTED_TAB:
                activateLatestSelectedTab(msg.arg1);
                break;
            case ModbusExchangeThread.GET_POSITIONS_OF_REACTION:
                assignReactionPosition(msg.obj);
                break;
            case ModbusExchangeThread.GET_GOAL_PRESSURES:
                assignGoalPressures(msg.obj);
                break;
            case ModbusExchangeThread.GET_ALL_HEADERS_INPUT_REGS:
                updateInputRegsHeaders(msg);
                break;
            case ModbusExchangeThread.READ_INPUT_REGS_SUCCESS: //INPUT_REG_READING_Hx
                if (cdf != null )
                {
                    cdf.dismiss();
                    cdf = null;
                }
                break;
            case ModbusExchangeThread.GET_ALL_DATCHIK_INPUT_REGS: // INPUT_REG_CNT_D1
                updateDetailCounter(msg);
                break;
            case ModbusExchangeThread.GET_ALL_ACTUATOR_INPUT_REGS: // INPUT_REG_READING_ACTx
                updateInputRegsActuators(msg);
                break;
            case ModbusExchangeThread.CONN_DONE_MSG:
                if (cdf != null) {
                    cdf.setConnectedMessage();
                }
                break;
            case ModbusExchangeThread.CONN_FAIL_MSG:
                if(cdf != null)
                {
                    cdf.enableButtons();
                }
                break;
        }
        return false;
    }

    private void assignReactionPosition(Object obj) {
        //TODO implement method assignReactionPosition
    }

    private void assignGoalPressures(Object obj) {
        //TODO implement method assignGoalPressures
    }

    private void updateDetailCounter(Message msg) {
    }

    private void updateInputRegsActuators(Message msg) {
        short[] vals = (short[]) msg.obj;
        for(int i=0; i < 8; ++i)
        {
            awms[i].setLastRawReading(vals[i]);
        }
    }

    private void updateInputRegsHeaders(Message msg) {
        short[] vals = (short[]) msg.obj;
        for(int i=0; i < 8; ++i)
        {
            fwms[i].postValue(vals[i]);
        }
    }

    public void logMsg()
    {
        System.out.println("hello from main thread");
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here

    public void onSaveInstanceState(@NonNull Bundle outState) {

        System.out.println("stroing to bundle cur_pass=" + cur_pass);
        outState.putString(CUR_PASS_PRM, cur_pass);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(CUR_PASS_PRM, cur_pass);
        editor.commit();
        // call superclass to save any view hierarch
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState (Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        cur_pass = savedInstanceState.getString(CUR_PASS_PRM, "1111");
    }

    public void setCurPW(String p)
    {
        System.out.println("New password set to " + p);
        cur_pass = p;
    }

    public String getAPName()
    {
        return apName;
    }

    public void sendAPNameToController(String string) {
        apName = string.substring(0, string.length()< 23 ? string.length(): 23);
        Message msg = new Message();
        msg.what = 101;
        msg.arg1 = 1;
        msg.obj = new String (apName);
        mbThread.getHandler().sendMessage(msg);
    }

    public void sendHeaderProperties(int id, String hdrName, int anInt, int anInt1,
                                     float aFloat, float aFloat1) {
        Message msg = new Message();
        msg.what = 102;
        msg.arg1 = id;
        msg.obj = new String (hdrName);
        mbThread.getHandler().sendMessage(msg);
        //TODO send parcelable anInt anInt1 aFloat aFloat1
        msg = new Message();
        msg.what = 112;
        msg.arg1 = id;
        msg.obj = new FeedCalibrationValues (anInt, anInt1, aFloat, aFloat1);
        mbThread.getHandler().sendMessage(msg);
    }

    public void sendActuatorSettings(int id, String hdrName,
                  int r1_bar, int r2_bar, float fl1_bar, float fl2_bar,
                  int r1_kgs, int r2_kgs, float fl1_kgs, float fl2_kgs) {
        Message msg = new Message();
        msg.what = 103;
        msg.arg1 = id;
        msg.obj = new String (hdrName);
        mbThread.getHandler().sendMessage(msg);
        msg = new Message();
        msg.what = 113;
        msg.arg1 = id;
        msg.obj = new ActuatorCalibrationValues((short)r1_bar, (short)r2_bar, fl1_bar, fl2_bar, (short)r1_kgs, (short)r2_kgs, fl1_kgs, fl2_kgs);
        mbThread.getHandler().sendMessage(msg);
    }


    private void setReactionDirections(int bitMask) {
        for(int i=0; i < 8; ++i)
        {
            //ActuatorViewModel awm = new ViewModelProvider(this).get(String.format("%d", i +1), ActuatorViewModel.class);
            //awm.setReactionDirection( (bitMask & (3<<i)) > 0);
            //awms[i].setReactionDirection( (bitMask & (3<<i)) > 0);
        }
    }

    private void activateLatestSelectedTab(int msg) {
        if (msg < 0 || msg > 3)
        {
            viewPager.setCurrentItem(0);
            return;
        }
        viewPager.setCurrentItem(msg);
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        switch (requestKey)
        {
            case REPEAR_REQUESTED:
                mbThread.getHandler().sendEmptyMessage(1);
                break;
            case EXIT_REQUESTED:
                mbThread.getHandler().sendEmptyMessage(1000);
                finishAffinity();
                System.exit(0);
                break;

        }

    }
}