package com.pttracker.trainingaid.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.pttracker.trainingaid.fragments.LandingFragment;
import java.util.ArrayList;


/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public SectionsPagerAdapter(FragmentManager fm, boolean firstRemoved) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(LandingFragment.newInstance());


    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
