package com.example.manager_pneumo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SettingPagerAdapter extends FragmentStateAdapter {
    private MainActivity ma;

    public void setMA(MainActivity _ma)
    {
        ma = _ma;
    }

    public SettingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment rv = null;
        switch(position)
        {
            case 0: default:
                rv = pressureSettingsFragment.newInstance();
                break;
            case 1:
                rv = pointsSettingsFragment.newInstance();
                break;
        }
        return rv;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
