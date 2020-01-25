package com.pttracker.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import androidx.legacy.app.FragmentPagerAdapter;

import com.pttracker.views.screens.fragments.ResultFragment;
import com.pttracker.views.screens.fragments.ZoneFragment;

/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class ZonePagerAdapter extends FragmentPagerAdapter {

    public ZonePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ResultFragment.newInstance();
                break;

            case 1:
                fragment = ZoneFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
