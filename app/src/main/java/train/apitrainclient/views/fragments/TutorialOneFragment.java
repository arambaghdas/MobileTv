package train.apitrainclient.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import train.apitrainclient.R;


/**
 * RoutinesFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class TutorialOneFragment extends BaseFragment {


    public static TutorialOneFragment newInstance() {
        TutorialOneFragment foodListFragments = new TutorialOneFragment();
        return foodListFragments;
    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.tutorial_one_fragment, container, false);
    }

    @Override
    public void initViews() {
        ButterKnife.bind(getActivity());
        super.initViews();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initControllers() {
        super.initControllers();

    }

    @Override
    public void initListeners() {
        super.initListeners();
    }


    @Override
    public void postStart() {
        super.postStart();
    }

    private void initCollectionAdapters() {
        if (!isViewAttached()) {
            return;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }
}