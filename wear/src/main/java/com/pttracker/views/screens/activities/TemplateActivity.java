package com.pttracker.views.screens.activities;


import com.pttracker.R;

/**
 * Template activity should used whenever creating a new activity.
 * This class contains a template structure for an activity
 */

public class TemplateActivity extends BaseActivity {

    //region View References
    //endregion

    //region Other Variables
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
        setContentView(R.layout.activity_home);
        context = this;
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
    }

    //endregion
    //region Business Logic Specific to this Activity
    //endregion

    //region Callback Methods
    //endregion
}

