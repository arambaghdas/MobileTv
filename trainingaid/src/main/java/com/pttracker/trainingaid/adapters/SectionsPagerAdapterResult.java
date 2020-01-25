package com.pttracker.trainingaid.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pttracker.trainingaid.fragments.ResultFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class SectionsPagerAdapterResult extends FragmentPagerAdapter {

    public SectionsPagerAdapterResult(FragmentManager fm) {
        super(fm);
    }

    public List<Fragment> fragments = new ArrayList<>();

    @Override
    public Fragment getItem(int position) {

        fragments.add(ResultFragment.newInstance());

        return fragments.get(position);
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
