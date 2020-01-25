package com.pttracker.views.screens.activities;

import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.pttracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class DashboardActivity extends BaseActivity {


    @BindView(R.id.btn_liveworkout)
    TextView btn_liveworkout;

    @BindView(R.id.btn_training_plan)
    TextView btn_training_plan;

    static public int SELECTED_PLAN;

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_dashboard);
        context = this;
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
    }


    @Override
    public void initControllers() {
        super.initListeners();
    }


    @Override
    public void initViews() {
        super.initViews();
    }


    @Override
    public void initListeners() {
        super.initListeners();
    }


    @Override
    public void postStart() {
        super.postStart();

    }


    private void moveToLiveWorkoutScreen() {
        SELECTED_PLAN=1;
        Intent i = new Intent(context, HomeActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    private void moveToTrainingPlanScreen() {
        SELECTED_PLAN=0;
        Intent i = new Intent(context, HomeActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }

    @OnClick(R.id.btn_liveworkout)
    void liveWorkoutclicked() {
        moveToLiveWorkoutScreen();
    }

    @OnClick(R.id.btn_training_plan)
    void liveTrainingPlanclicked() {
        moveToTrainingPlanScreen();
    }

}



