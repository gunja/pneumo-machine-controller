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
                pressureSettingsFragment rv1 = pressureSettingsFragment.newInstance();
                rv1.setMA(ma);
                rv = rv1;
                break;
            case 1:
                pointsSettingsFragment rv2 = pointsSettingsFragment.newInstance();
                rv2.setMA(ma);
                rv = rv2;
                break;
        }
        return rv;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
