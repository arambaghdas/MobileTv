package train.apitrainclient.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import train.apitrainclient.views.fragments.ExerciseExerciseTabFragment;


/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class ExerciseTabsPagerAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public ExerciseTabsPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(ExerciseExerciseTabFragment.newInstance());

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
