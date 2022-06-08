package com.example.manager_pneumo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;

import com.example.manager_pneumo.ui.main.ui.login.LoginViewModel;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.manager_pneumo.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements  TabLayout.OnTabSelectedListener,  Handler.Callback
{

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

    private String apName;

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
        mbThread.start();
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
        Log.d(TAG, "handleMessage - what = " + msg.what+ "this threadId=" + Thread.currentThread().getId());
        if(msg.what == 30)
        {
            apName = new String((String) msg.obj);
        }
        if (msg.what == 41)
        {
            //FeedsViewModel fwm = new ViewModelProvider(this).get(String.format("%d", msg.arg1), FeedsViewModel.class);
            //fwm.setTitle((String) msg.obj);
        }
        if (msg.what == 42)
        {
            FeedsViewModel fwm = new ViewModelProvider(this).get(String.format("%d", msg.arg1), FeedsViewModel.class);
            fwm.setTitle((String) msg.obj);
        }
        return false;
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
    }
}