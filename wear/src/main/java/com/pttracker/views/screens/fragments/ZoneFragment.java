package com.pttracker.views.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pttracker.R;
import com.pttracker.controllers.TrainingPlanController;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.views.screens.activities.ResultActivity;
import com.pttrackershared.models.Circuit;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.User;
import com.pttrackershared.models.Workout;
import com.pttrackershared.plugins.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class ZoneFragment extends BaseFragment {
    @BindView(R.id.tv_zone1)
    TextView tvZone1;

    @BindView(R.id.tv_zone2)
    TextView tvZone2;

    @BindView(R.id.tv_zone3)
    TextView tvZone3;

    @BindView(R.id.tv_zone4)
    TextView tvZone4;

    @BindView(R.id.tv_zone5)
    TextView tvZone5;

    private Unbinder unbinder;
    private boolean isViewAttached = false;
    private TrainingLog trainingLog;
    Context context;
    View rootView;

   static int zone1sumPersent = 0;
    static int zone2sumPersent = 0;
    static int zone3sumPersent = 0;
    static int zone4sumPersent = 0;
    static int zone5sumPersent = 0;

    public static ZoneFragment newInstance() {
        ZoneFragment fragment = new ZoneFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_resul_zone, container, false);
        this.rootView = rootView;
        context = getActivity();
        trainingLog = ResultActivity.trainingLog;
        final WatchViewStub stub = (WatchViewStub) rootView.findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                initViews();
                initData();
            }
        });
        return rootView;
    }

    private void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        isViewAttached = true;
    }

    private void initData() {
        int zone1sum = 0;
        int zone2sum = 0;
        int zone3sum = 0;
        int zone4sum = 0;
        int zone5sum = 0;

        int totalExercise = 0;



        Circuit circuit = trainingLog.getCircuitList().get(0);
        for (int j = 0; j < circuit.getExerciseList().size(); j++) {
            ++totalExercise;
            Exercise exercise = circuit.getExerciseList().get(j);
            if(exercise.getEffort_zone()==null) exercise.setEffort_zone("1");
            Log.d("checkNitin", "Zone::" + exercise.getEffort_zone());
            if (Integer.valueOf(exercise.getEffort_zone()) == 1) {
                ++zone1sum;
            } else if (Integer.valueOf(exercise.getEffort_zone()) == 2) {
                ++zone2sum;
            } else if (Integer.valueOf(exercise.getEffort_zone()) == 3) {
                ++zone3sum;
            } else if (Integer.valueOf(exercise.getEffort_zone()) == 4) {
                ++zone4sum;
            } else if (Integer.valueOf(exercise.getEffort_zone()) == 5) {
                ++zone5sum;
            }
        }

        zone1sumPersent = zone1sum * 100 / totalExercise;
        zone2sumPersent = zone2sum * 100 / totalExercise;
        zone3sumPersent = zone3sum * 100 / totalExercise;
        zone4sumPersent = zone4sum * 100 / totalExercise;
        zone5sumPersent = zone5sum * 100 / totalExercise;

        tvZone1.setText("" + zone1sumPersent + "%");
        tvZone2.setText("" + zone2sumPersent + "%");
        tvZone3.setText("" + zone3sumPersent + "%");
        tvZone4.setText("" + zone4sumPersent + "%");
        tvZone5.setText("" + zone5sumPersent + "%");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        isViewAttached = false;
    }

}



