package com.pttracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pttrackershared.models.Circuit;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.User;
import com.pttrackershared.models.Workout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the Shared Preferences all through the app, insert new preferences code in this class.
 */

public class SharedPrefManager {
    //region "Preferences Keys"
    private static final String GET_WORKOUTS_KEY = "getWorkouts";
    private static final String GET_CIRCUITS_KEY = "getCircuits";
    private static final String GET_TRAINING_PLAN_KEY = "getTrainingPlan";
    private static final String GET_PLAN_VIEW_KEY = "getTrainingPlanView";
    private static final String GET_EXERCISES_KEY = "getExercises";
    private static final String GET_STATS_KEY = "getStats";
    private static final String GET_LOGS_KEY = "getLogs";
    private static final String GET_PROFILE_KEY = "getProfile";
    //endregion

    //region "Workouts"
    public static List<Workout> getWorkoutsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<Workout>>() {
        }.getType();
        if (preferences.getString(GET_WORKOUTS_KEY, "").equals("")) {
            return new ArrayList<Workout>();
        } else {
            List<Workout> workouts = new Gson().fromJson(preferences.getString(GET_WORKOUTS_KEY, ""), type);
            return workouts;
        }
    }

    public static void setWorkoutsList(Context context, List<Workout> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_WORKOUTS_KEY, jsonString).commit();
    }
    //endregion

    //region "Circuits"
    public static List<Circuit> getCircuitsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<Circuit>>() {
        }.getType();
        if (preferences.getString(GET_CIRCUITS_KEY, "").equals("")) {
            return new ArrayList<Circuit>();
        } else {
            List<Circuit> circuits = new Gson().fromJson(preferences.getString(GET_CIRCUITS_KEY, ""), type);
            return circuits;
        }
    }

    public static void setCircuitsList(Context context, List<Circuit> circuits) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(circuits);
        preferences.edit().putString(GET_CIRCUITS_KEY, jsonString).commit();
    }

    //endregion

    //region "Exercises"
    public static List<Exercise> getExercisesList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<Exercise>>() {
        }.getType();
        if (preferences.getString(GET_EXERCISES_KEY, "").equals("")) {
            return new ArrayList<Exercise>();
        } else {
            List<Exercise> exercises = new Gson().fromJson(preferences.getString(GET_EXERCISES_KEY, ""), type);
            return exercises;
        }
    }

    public static void setExercisesList(Context context, List<Exercise> exercises) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(exercises);
        preferences.edit().putString(GET_EXERCISES_KEY, jsonString).commit();
    }

    //endregion

    //region "Training Logs"
    public static List<TrainingLog> getTrainingLogsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<TrainingLog>>() {
        }.getType();
        if (preferences.getString(GET_LOGS_KEY, "").equals("")) {
            return new ArrayList<TrainingLog>();
        } else {
            List<TrainingLog> workouts = new Gson().fromJson(preferences.getString(GET_LOGS_KEY, ""), type);
            return workouts;
        }
    }

    //this method verifies if the training logs are completely synced, if yes then it will delete the logs
    public static void verifyAndDeleteLogs(Context context, String data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String savedLogs = preferences.getString(GET_LOGS_KEY, "");
        if (data.length() == savedLogs.length()) {
            preferences.edit().putString(GET_LOGS_KEY, "");//records deleted
        }
    }

    public static void setTrainingLogsList(Context context, List<TrainingLog> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_LOGS_KEY, jsonString).commit();
    }

    public static void addTrainingLog(Context context, TrainingLog workout) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<TrainingLog>>() {
        }.getType();
        List<TrainingLog> workouts = new Gson().fromJson(preferences.getString(GET_LOGS_KEY, ""), type);
        if (workouts == null) {
            workouts = new ArrayList<>();
        }
        if (workouts.size() + 1 > 60) {
            workouts.remove(0);
        }
        workouts.add(workout);
        setTrainingLogsList(context, workouts);

    }
    //endregion

    // region "Profile"
    public static User getUserInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_PROFILE_KEY, "").equals("")) {
            return new User();
        } else {
            User userinfo = new Gson().fromJson(preferences.getString(GET_PROFILE_KEY, ""), User.class);
            return userinfo;
        }
    }

    public static void setUserInfo(Context context, User userInfo) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(userInfo);
        preferences.edit().putString(GET_PROFILE_KEY, jsonString).commit();
    }
    //endregion
    //region "reset"

    public static void resetAll(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear();
    }
    //endregion
}
