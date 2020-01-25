package com.pttracker.views.screens.activities;

import android.content.Intent;
import android.os.Handler;

import com.pttracker.R;

import butterknife.ButterKnife;

/**
 * ResultActivity shows time to complete recent workout. Gets value for completed time from activity starting this activity
 */
public class SplashActivity extends BaseActivity {

    //region View References
    //endregion

    //region Other Variables
    private static final int SPLASH_TIME = 2500;
    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class
    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_splash);
        context = this;
        ButterKnife.bind(this);
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initListeners();
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    @Override
    public void initViews() {
        super.initViews();
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    @Override
    public void initListeners() {
        super.initListeners();
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
        waitForSplashTime();
    }

    //endregion
    //region Business Logic Specific to this Activity
    private void waitForSplashTime() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveToHomeScreen();
            }
        }, SPLASH_TIME);
    }

    private void moveToHomeScreen() {
        Intent i = new Intent(context,
                DashboardActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        finishThisOnPause = true;
    }
    //endregion

    //region Callback Methods
    //endregion

}



