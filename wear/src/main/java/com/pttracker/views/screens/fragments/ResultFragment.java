package com.pttracker.views.screens.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pttracker.R;
import com.pttracker.controllers.TrainingPlanController;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.views.screens.activities.DashboardActivity;
import com.pttracker.views.screens.activities.ResultActivity;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.User;
import com.pttrackershared.plugins.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class ResultFragment extends BaseFragment {

    //region View References
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;

    @BindView(R.id.view_rate)
    LinearLayout llRate;

    @BindView(R.id.tv_min_heart_rate)
    TextView tvMinHeartRate;

    @BindView(R.id.tv_max_heart_rate)
    TextView tvMaxHeartRate;

    @BindView(R.id.tv_avg_heart_rate)
    TextView tvAvgHeartRate;

    @BindView(R.id.tv_calories)
    TextView tvCalories;

    private Unbinder unbinder;
    private boolean isViewAttached = false;
    private int totalTime;
    private TrainingLog trainingLog;
    private long minBpm;
    private long maxBpm;
    private long avgBpm;
    Context context;
    View rootView;

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        this.rootView = rootView;
        context = getActivity();
        trainingLog = ResultActivity.trainingLog;
        totalTime = ResultActivity.totalTime;
        minBpm = ResultActivity.minBpm;
        maxBpm = ResultActivity.maxBpm;
        avgBpm = ResultActivity.avgBpm;

        WatchViewStub stub = (WatchViewStub) rootView.findViewById(R.id.watch_view_stub);
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
        for (int i = 0; i < llRate.getChildCount(); i++) {
            View view = llRate.getChildAt(i);
            final int childPosition = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < llRate.getChildCount(); j++) {
                        v = llRate.getChildAt(j);
                        if (j <= childPosition) {
                            v.setSelected(true);
                        } else {
                            v.setSelected(false);
                        }
                    }
                    setUserFeedback(childPosition + 1);
                }
            });
        }

        tvTotalTime.setText(TimeUtils.getTimeString(totalTime));
        tvMinHeartRate.setText(minBpm + "");
        tvMaxHeartRate.setText(maxBpm + "");
        tvAvgHeartRate.setText(avgBpm + "");
        trainingLog.setCalories("" + String.format("%2.02f", calculateCalories(avgBpm, totalTime)));
        tvCalories.setText("" + trainingLog.getCalories());
        tvCalories.setText("");

    }

    public void setUserFeedback(int feedback) {
        trainingLog.setEffort_zone1(ZoneFragment.zone1sumPersent);
        trainingLog.setEffort_zone2(ZoneFragment.zone2sumPersent);
        trainingLog.setEffort_zone3(ZoneFragment.zone3sumPersent);
        trainingLog.setEffort_zone4(ZoneFragment.zone4sumPersent);
        trainingLog.setEffort_zone5(ZoneFragment.zone5sumPersent);
        trainingLog.setPlanOrLiveWorkout(DashboardActivity.SELECTED_PLAN);
        TrainingPlanController.getInstance(context).setUserFeedback(trainingLog, feedback);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 650);
    }

    public double calculateCalories(double heartReate, double totaltime) {
        double calCalories = 0;
        try {
            User user = UserAccountDataManager.getInstance(context).getCurrentUser();
            List<TrainingPlan> routineList = TrainingPlanDataManager.getInstance(context).getStoredTrainingPlans();
            double Age = TimeUtils.getDiffYears(user.getDobString(), TimeUtils.getCurrentDate());
            int weight = 0;
            if (routineList != null && Integer.valueOf(routineList.get(0).getWeight()) > 0) {
                weight = Integer.valueOf(routineList.get(0).getWeight());
            } else {
                weight = Integer.valueOf(user.getWeight());
            }

            calCalories = ((Age * 0.2017) - (weight * 0.09036) + (heartReate * 0.6309) - 55.0969) * totaltime / 4.184;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calCalories;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        isViewAttached = false;
    }

    //endregion
    //region Business Logic Specific to this Activity
    //endregion

    //region Callback Methods
    //endregion
}



