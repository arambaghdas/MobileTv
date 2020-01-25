package com.pttracker.controllers;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.services.PhoneMessagesListenerService;
import com.pttracker.utils.SharedPrefManager;
import com.pttracker.views.screens.activities.HomeActivity;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;
import com.pttrackershared.models.User;
import com.pttrackershared.models.eventbus.PendingMessageEvent;
import com.pttrackershared.plugins.LoggerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

/**
 * UserAccountController implements complete app specific User related business logic.
 * Handles actions, validates inputs to perform an action and get results from UserAccountDataManager.
 * Acts as a middleware between app components and data layer
 */
public class UserAccountController {

    private static UserAccountController mInstance = null;
    private Context context;

    private UserAccountController(Context context) {
        this.context = context;
    }

    public static UserAccountController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserAccountController(context);
        }
        return mInstance;
    }

    /**
     * Destroys mInstance from memory. As mInstance is a static object so this object will exist
     * even all activities are destroyed.
     */
    public void release() {
        if (mInstance != null) {
            UserAccountDataManager.getInstance(context).release();
            LoggerUtils.d("Destroying shared instance");
            mInstance = null;
        }
    }
    //region Routine Related Actions

    //endregion

    public void logoutUser() {
        UserAccountDataManager.getInstance(context).setLastSynced(null);
        GreenDaoDataManager.getInstance(context).deleteAllData();
        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.LOGGED_OUT_UPDATE);
        EventBus.getDefault().post(pendingMessageEvent);
//        Intent i = new Intent(context, HomeActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(i);
    }

    public void loginUser(String jsonData) {
        User user = UserAccountDataManager.getInstance(context).getCurrentUser();
        UserAccountDataManager.getInstance(context).setLastSynced(null);
        GreenDaoDataManager.getInstance(context).deleteAllData();

        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.LOG_IN_UPDATE);
        EventBus.getDefault().post(pendingMessageEvent);
        if (user == null || user.getUserId() != new Gson().fromJson(jsonData, User.class).getUserId()) {
            SharedPrefManager.resetAll(context);//removing training logs saved unsynced
        }else
        {
            TrainingPlanController.getInstance(context).syncUpdatedLogs();
        }
        UserAccountDataManager.getInstance(context).saveUser(jsonData);
        TrainingPlanController.getInstance(context).loadRoutinesData();
//        Intent i = new Intent(context, HomeActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(i);
    }
}