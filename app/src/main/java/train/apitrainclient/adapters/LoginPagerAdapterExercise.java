package train.apitrainclient.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import train.apitrainclient.views.fragments.LoginFragment;
import train.apitrainclient.views.fragments.TutorialOneFragment;
import train.apitrainclient.views.fragments.TutorialTwoFragment;

/**
 * SectionsPagerAdapter is an adapter class to provide fragments for different positions
 */

public class LoginPagerAdapterExercise extends FragmentPagerAdapter {
    public List<Fragment> fragments;

    public LoginPagerAdapterExercise(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(TutorialOneFragment.newInstance());
        fragments.add(TutorialTwoFragment.newInstance());
        fragments.add(LoginFragment.newInstance());

    }



    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
