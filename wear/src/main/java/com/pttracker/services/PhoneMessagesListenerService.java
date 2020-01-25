package com.pttracker.services;

import android.preference.PreferenceManager;

import com.google.android.gms.wearable.MessageEvent;
import com.pttracker.controllers.UserAccountController;
import com.pttracker.datamanagers.ExerciseImagesDataManager;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.models.eventbus.RoutineDataFetchingEvent;
import com.pttracker.utils.SharedPrefManager;
import com.pttrackershared.services.MessagesListenerService;

import org.greenrobot.eventbus.EventBus;

/**
 * PhoneMessagesListenerService handles messages received from phone
 */

public class PhoneMessagesListenerService extends MessagesListenerService {
    //region Business Logic Specific to this Service
    public static String TRAINING_PLAN_DATA_KEY = "trainingPlanData";
    public static String EXERCISE_IMAGES_DATA_KEY = "exercisesImagesData";
    public static String TRAINING_LOGS_DATA_KEY = "trainingLogsData";

    private void receiveRoutineData(String data) {
        TrainingPlanDataManager.getInstance(this).saveTrainingPlan(data);
    }

    private void receiveRoutineUpdate(String data) {
        TrainingPlanDataManager.getInstance(this).receiveUpdatedTrainingPlans(data);
    }

    private void receiveLogoutUpdate() {
//        UserAccountController.getInstance(this).logoutUser();
    }

    private void receiveLoginUpdate(String jsonData) {
        if (jsonData != null && !jsonData.isEmpty())
            UserAccountController.getInstance(this).loginUser(jsonData);
    }


    private void receiveImagesData(String data) {
        ExerciseImagesDataManager.getInstance(this).saveImagesData(data);
    }

    private void receiveTrainingLogs(String data) {
        SharedPrefManager.verifyAndDeleteLogs(getApplicationContext(), data);
        EventBus.getDefault().post(new RoutineDataFetchingEvent(true));
    }
    //endregion

    //region Overridden Methods from base class
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        String path = messageEvent.getPath();
        String data = new String(messageEvent.getData());

        switch (path) {
            case LOG_IN_UPDATE:
                receiveLoginUpdate(data);
                break;

            case ROUTINE_DATA_REQUEST:
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                String partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TRAINING_PLAN_DATA_KEY, "");
                partialData = partialData + data;
                receiveRoutineData(partialData);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(TRAINING_PLAN_DATA_KEY, "").apply();
                break;

            case ROUTINE_DATA_UPDATE:
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TRAINING_PLAN_DATA_KEY, "");
                partialData = partialData + data;
                receiveRoutineUpdate(partialData);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(TRAINING_PLAN_DATA_KEY, "").apply();
                break;

            case ROUTINE_DATA_PARTIAL_UPDATE:
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TRAINING_PLAN_DATA_KEY, "");
                partialData = partialData + data;
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(TRAINING_PLAN_DATA_KEY, partialData).commit();
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));

                break;

            case EXERCISES_IMAGES_REQUEST:
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(EXERCISE_IMAGES_DATA_KEY, "");
                partialData = partialData + data;
                receiveImagesData(partialData);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(EXERCISE_IMAGES_DATA_KEY, "").apply();
                break;

            case EXERCISES_IMAGES_PARTIAL_UPDATE:
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(EXERCISE_IMAGES_DATA_KEY, "");
                partialData = partialData + data;
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(EXERCISE_IMAGES_DATA_KEY, partialData).commit();
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                break;

            case TRAINING_LOGS_DATA_UPDATE:
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TRAINING_LOGS_DATA_KEY, "");
                partialData = partialData + data;
                receiveTrainingLogs(partialData);
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(TRAINING_LOGS_DATA_KEY, "").apply();
                break;

            case TRAINING_LOGS_PARTIAL_UPDATE:
                partialData = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(TRAINING_LOGS_DATA_KEY, "");
                partialData = partialData + data;
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                        .edit().putString(TRAINING_LOGS_DATA_KEY, partialData).commit();
                EventBus.getDefault().post(new RoutineDataFetchingEvent(false));
                break;

            case LOGGED_OUT_UPDATE:
                receiveLogoutUpdate();
                break;
            default:
                showToast("Handle unknown message path: " + path);
                break;
        }
    }
    //endregion
}
