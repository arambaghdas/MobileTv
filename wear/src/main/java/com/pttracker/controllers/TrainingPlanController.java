package com.pttracker.controllers;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.services.PhoneMessagesListenerService;
import com.pttracker.utils.SharedPrefManager;
import com.pttracker.views.screens.activities.HomeActivity;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.Workout;
import com.pttrackershared.models.eventbus.NodeConnectedEvent;
import com.pttrackershared.models.eventbus.PendingMessageEvent;
import com.pttrackershared.plugins.LoggerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * TrainingPlanController implements complete app specific Routine related business logic.
 * Handles actions, validates inputs to perform an action and get results from RoutineDataManger.
 * Acts as a middleware between app components and data layer
 */

public class TrainingPlanController {

    private static TrainingPlanController mInstance = null;
    private Context context;

    private TrainingPlanController(Context context) {
        this.context = context;
        EventBus.getDefault().register(this);
    }

    public static TrainingPlanController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TrainingPlanController(context);
        }
        return mInstance;
    }

    /**
     * Destroys mInstance from memory. As mInstance is a static object so this object will exist
     * even all activities are destroyed.
     */
    public void release() {
        if (mInstance != null) {
            TrainingPlanDataManager.getInstance(context).release();
            LoggerUtils.d("Destroying shared instance");
            mInstance = null;
            EventBus.getDefault().unregister(this);
        }
    }

    public void updateRoutine(TrainingPlan trainingPlan) {
        TrainingPlanDataManager.getInstance(context).updateTrainingPlan(trainingPlan);
    }

    public void syncUpdatedLogs() {
//        publishData(PhoneMessagesListenerService.TRAINING_LOGS_EXERCISE_DATA, dataArray.toString());
        List<TrainingLog> logs = SharedPrefManager.getTrainingLogsList(context);
        if (!logs.isEmpty()) {
            Gson gson = new Gson();
            JSONArray dataArray = new JSONArray();
            try {
                for (TrainingLog log : logs) {
                    if (!log.isSynced())
                        dataArray.put(new JSONObject(gson.toJson(log)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            byte[] data = dataArray.toString().getBytes();
            int bufferSize = 100000;
            int byteIndex = 0;
            if (data.length > bufferSize)
                while (byteIndex < data.length) {
                    if ((byteIndex + bufferSize) < data.length) {
                        publishData(PhoneMessagesListenerService.TRAINING_LOGS_PARTIAL_UPDATE, new String(Arrays.copyOfRange(data, byteIndex, byteIndex + bufferSize)));
                        byteIndex += bufferSize;
                    } else {
                        publishData(PhoneMessagesListenerService.TRAINING_LOGS_DATA_UPDATE, new String(Arrays.copyOfRange(data, byteIndex, data.length)));
                        byteIndex = data.length;
                    }
                }
            else
                publishData(PhoneMessagesListenerService.TRAINING_LOGS_DATA_UPDATE, dataArray.toString());
        }
    }

    private void publishData(String routineDataRequest, String data) {
        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(routineDataRequest);
        pendingMessageEvent.setData(data);
        EventBus.getDefault().post(pendingMessageEvent);
    }

    public void setUserFeedback(TrainingLog trainingLog, int userFeedback) {
        TrainingPlanDataManager.getInstance(context).setUserFeedback(trainingLog, userFeedback);
        syncUpdatedLogs();
    }

    //region Routine Related Actions

    //endregion

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNodeConnectedEvent(NodeConnectedEvent nodeConnectedEvent) {

        if (TrainingPlanDataManager.getInstance(context).getStoredTrainingPlans().isEmpty()) {
            loadRoutinesData();
        } else if (!TrainingPlanDataManager.getInstance(context).getUnsyncedTrainingPlans().isEmpty()) {
            syncUpdatedLogs();
        }

    }

    public void loadRoutinesData() {
        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.ROUTINE_DATA_REQUEST);
        EventBus.getDefault().postSticky(pendingMessageEvent);
    }

    public void logoutUser() {
        GreenDaoDataManager.getInstance(context).deleteAllData();

        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.LOGGED_OUT_UPDATE);
        EventBus.getDefault().post(pendingMessageEvent);

        Intent i = new Intent(context, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    public void updateWorkout(Workout selectedWorkout) {
        GreenDaoDataManager.getInstance(context).getDaoSession().getWorkoutDao().update(selectedWorkout);
    }
}