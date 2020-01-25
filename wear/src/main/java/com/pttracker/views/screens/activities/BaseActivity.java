package com.pttracker.views.screens.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * BaseActivity manages basic operations needed for an activity. Any common logic for all activities can be implemented here.
 */

public class BaseActivity extends Activity {

    //region View References
    //endregion

    //region Other Variables
    public Context context;
    public boolean finishThisOnPause;
    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region necessary operations to do after activity creation
        preStart();
        initData();
        initControllers();
        initViews();
        initListeners();
        postStart();
        //endregion
    }

    /**
     * Perform business logic on activity paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (finishThisOnPause)
            finish();
    }

    /**
     * Perform business logic on activity resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    public void preStart() {
        context = this;
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    public void initData() {
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    public void initControllers() {
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    public void initViews() {
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    public void initListeners() {
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    public void postStart() {
    }

    //endregion
    //region Business Logic Specific to this Activity
    //endregion

    //region Callback Methods
    //endregion
}


