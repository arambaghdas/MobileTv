package com.pttracker.views.screens.fragments;

import android.app.Fragment;

/**
 * BaseActivity manages basic operations needed for a fragment. Any common logic for all fragments can be implemented here.
 */

public class BaseFragment extends Fragment {

    public boolean isSwipeable(){
        return true;
    }

}
