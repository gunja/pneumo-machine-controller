package com.example.manager_pneumo.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.manager_pneumo.MainActivity;
import com.example.manager_pneumo.R;
import com.example.manager_pneumo.ui.main.ui.login.LoginFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {
    private MainActivity ma;

    @StringRes
    public static final int[] TAB_TITLES = new int[]{R.string.tab_measurements, R.string.tab_manual, R.string.tab_auto, R.string.tab_settings};

    public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment rv = null;
        System.out.println("create Fragment called with position =" + position);
        if (position == 4) {
            rv = LoginFragment.newInstance(ma);
        }
        else {
            rv = PlaceholderFragment.newInstance(position + 1);
        }
        return rv;
    }

    public void setMA(MainActivity m) {ma = m;}

    @Override
    public int getItemCount() {
        return TAB_TITLES.length+1;
    }
}