package com.pttracker.trainingaid.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pttracker.trainingaid.plugins.DialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseActivity manages basic operations needed for a fragment. Any common logic for all fragments can be implemented here.
 */

public class BaseFragment extends Fragment {

    public View rootView;
    public Context context;
    private Unbinder unbinder;
    private boolean isViewAttached;

    //region Overridden Methods from Fragment Base Class
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preStart(inflater,container);
        initViews();
        initData();
        initControllers();
        initListeners();
        postStart();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        DialogUtils.HideDialog();
        isViewAttached = false;
    }
    //endregion


    //region Basic Methods needed for Fragment Flow
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        context = getActivity();
    }

    public void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        isViewAttached = true;
    }

    public void initData() {
    }

    public void initControllers() {
    }

    public void initListeners() {
    }

    public void postStart() {
    }

    public boolean isViewAttached() {
        return isViewAttached;
    }
    //endregion


}