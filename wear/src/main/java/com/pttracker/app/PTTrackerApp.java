package com.pttracker.app;

import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.pttracker.controllers.TrainingPlanController;
import com.pttracker.services.PhoneMessagesListenerService;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;

/**
 * PTTrackerApp handle business logic for whole app specific. Initializes components right after app starts.
 */

public class PTTrackerApp extends MultiDexApplication {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        preStart();
        initData();
        initControllers();
        initListeners();
        postStart();
    }

    private void preStart() {
        context = this;
    }

    private void initData() {
    }

    private void initControllers() {
        MultiDex.install(context);
        GreenDaoDataManager.getInstance(context).initGreenDaoDB();
        TrainingPlanController.getInstance(this);
    }

    private void initListeners() {
    }

    private void postStart() {
        startService(new Intent(this, PhoneMessagesListenerService.class));
    }
}



