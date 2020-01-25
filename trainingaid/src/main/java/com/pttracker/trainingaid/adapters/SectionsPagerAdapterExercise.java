package com.pttracker.trainingaid.adapters;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pttracker.trainingaid.fragments.ExerciseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class SectionsPagerAdapterExercise extends FragmentPagerAdapter {

    public SectionsPagerAdapterExercise(FragmentManager fm) {
        super(fm);
    }

    public List<Fragment> fragments = new ArrayList<>();

    @Override
    public Fragment getItem(int position) {

        fragments.add(ExerciseFragment.newInstance());

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
