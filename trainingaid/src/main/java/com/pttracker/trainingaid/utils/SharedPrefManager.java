package com.pttracker.trainingaid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pttracker.trainingaid.networks.CircuitJsonModel;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttracker.trainingaid.networks.GraphInfoJsonModel;
import com.pttracker.trainingaid.networks.UserJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanViewJsonModel;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttracker.trainingaid.models.TrainingPlan;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the Shared Preferences all through the app, insert new preferences code in this class.
 */

public class SharedPrefManager {
    //region "Preference Keys"
    private static final String GET_WORKOUTS_KEY = "getWorkouts";
    private static final String GET_CIRCUITS_KEY = "getCircuits";
    private static final String GET_TRAINING_PLAN_KEY = "getTrainingPlan";
    private static final String GET_PLAN_VIEW_KEY = "getTrainingPlanView";
    private static final String GET_EXERCISES_KEY = "getExercises";
    private static final String GET_STATS_KEY = "getStats";
    private static final String GET_LOGS_KEY = "getLogs";
    private static final String GET_PROFILE_KEY = "getProfile";
    private static final String TRAINING_LOG_PAGE_NUM = "getPageNum";
    private static final String TRAINING_LOG_PAGE_COUNT = "getPageCount";
    private static final String GET_TRAINING_PLAN_LIST = "getTrainingPlanList";
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

    public static void removeWorkoutsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_WORKOUTS_KEY);
    }
    //endregion

    //region "Circuits"
    public static List<CircuitJsonModel> getCircuitsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<CircuitJsonModel>>() {
        }.getType();
        if (preferences.getString(GET_CIRCUITS_KEY, "").equals("")) {
            return new ArrayList<CircuitJsonModel>();
        } else {
            List<CircuitJsonModel> circuits = new Gson().fromJson(preferences.getString(GET_CIRCUITS_KEY, ""), type);
            return circuits;
        }
    }

    public static void setCircuitsList(Context context, List<CircuitJsonModel> circuits) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(circuits);
        preferences.edit().putString(GET_CIRCUITS_KEY, jsonString).commit();
    }

    //endregion

    //region "Exercises"
    public static List<ExerciseJsonModel> getExercisesList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<ExerciseJsonModel>>() {
        }.getType();
        if (preferences.getString(GET_EXERCISES_KEY, "").equals("")) {
            return new ArrayList<ExerciseJsonModel>();
        } else {
            List<ExerciseJsonModel> exercises = new Gson().fromJson(preferences.getString(GET_EXERCISES_KEY, ""), type);
            return exercises;
        }
    }

    public static void setExercisesList(Context context, List<ExerciseJsonModel> exercises) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(exercises);
        preferences.edit().putString(GET_EXERCISES_KEY, jsonString).commit();
    }

    //endregion

    //region "Training Logs"
    public static List<WorkoutJsonModel> getTrainingLogsList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<WorkoutJsonModel>>() {
        }.getType();
        if (preferences.getString(GET_LOGS_KEY, "").equals("")) {
            return new ArrayList<WorkoutJsonModel>();
        } else {
            List<WorkoutJsonModel> workouts = new Gson().fromJson(preferences.getString(GET_LOGS_KEY, ""), type);
            return workouts;
        }
    }

    public static void setTrainingLogsList(Context context, List<WorkoutJsonModel> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_LOGS_KEY, jsonString).commit();
    }

    public static void addTrainingLogsToList(Context context, List<WorkoutJsonModel> workouts) {
        List<WorkoutJsonModel> prevLogs = getTrainingLogsList(context);
        prevLogs.addAll(workouts);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(prevLogs);
        preferences.edit().putString(GET_LOGS_KEY, jsonString).commit();
    }
    //endregion

    // region "Profile"
    public static UserJsonModel getUserInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_PROFILE_KEY, "").equals("")) {
            return new UserJsonModel();
        } else {
            UserJsonModel userinfo = new Gson().fromJson(preferences.getString(GET_PROFILE_KEY, ""), UserJsonModel.class);
            return userinfo;
        }
    }

    public static void setUserInfo(Context context, UserJsonModel userInfo) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(userInfo);
        preferences.edit().putString(GET_PROFILE_KEY, jsonString).commit();
    }
    //endregion

    // region "Progress Stats/Graphs"
    public static GraphInfoJsonModel getUserStats(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_STATS_KEY, "").equals("")) {
            return new GraphInfoJsonModel();
        } else {
            GraphInfoJsonModel userStats = new Gson().fromJson(preferences.getString(GET_STATS_KEY, ""), GraphInfoJsonModel.class);
            return userStats;
        }
    }

    public static void setUserStats(Context context, GraphInfoJsonModel userStats) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(userStats);
        preferences.edit().putString(GET_STATS_KEY, jsonString).commit();
    }
    //endregion

    // region "Training Plan View"
    public static TrainingPlanViewJsonModel getTrainingPlanView(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_PLAN_VIEW_KEY, "").equals("")) {
            return null;
        } else {
            TrainingPlanViewJsonModel planView = new Gson().fromJson(preferences.getString(GET_PLAN_VIEW_KEY, ""), TrainingPlanViewJsonModel.class);
            return planView;
        }
    }

    public static void setTrainingPlanView(Context context, TrainingPlanViewJsonModel planView) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(planView);
        preferences.edit().putString(GET_PLAN_VIEW_KEY, jsonString).commit();
    }
    //endregion

    // region "Training Plan"
    public static TrainingPlanJsonModel getTrainingPlan(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_TRAINING_PLAN_KEY, "").equals("")) {
            return new TrainingPlanJsonModel();
        } else {
            TrainingPlanJsonModel plan = new Gson().fromJson(preferences.getString(GET_TRAINING_PLAN_KEY, ""), TrainingPlanJsonModel.class);
            return plan;
        }
    }

    public static void setTrainingPlan(Context context, TrainingPlanJsonModel plan) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(plan);
        preferences.edit().putString(GET_TRAINING_PLAN_KEY, jsonString).commit();
    }
    //endregion

    // region "Page Count"
    public static int getTrainingPlanPageCount(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(TRAINING_LOG_PAGE_COUNT, 0);
    }

    public static void setTrainingPlanPageCount(Context context, int count) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(TRAINING_LOG_PAGE_COUNT, count).commit();
    }
    //endregion

    // region "Page Number"
    public static int getTrainingPlanPage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(TRAINING_LOG_PAGE_NUM, 0);
    }

    public static void setTrainingPlanPage(Context context, int count) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(TRAINING_LOG_PAGE_NUM, count).commit();
    }
    //endregion

    //region "reset"

    public static void resetAll(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear();
    }
    //endregion
    public static void verifyAndDeleteLogs(Context context, String data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String savedLogs = preferences.getString(GET_LOGS_KEY, "");
        if (data.length() == savedLogs.length()) {
            preferences.edit().putString(GET_LOGS_KEY, "");//records deleted
        }
    }

    public static List<TrainingLog> getTrainingLogsList2(Context context) {
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

    public static void setTrainingLogsList2(Context context, List<TrainingLog> workouts) {
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
        setTrainingLogsList2(context, workouts);

    }

    public static void  setCalories (int calories , Context c){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().putInt("Calories",calories).apply();
    }

    public static int getCalories (Context c){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getInt("Calories",0);
    }

    public static void removeCalories(Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().remove("Calories").apply();
    }

    public static List<TrainingPlan> getTrainingPlanList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<TrainingPlan>>() {
        }.getType();
        if (preferences.getString(GET_TRAINING_PLAN_LIST, "").equals("")) {
            return new ArrayList<TrainingPlan>();
        } else {
            return new Gson().fromJson(preferences.getString(GET_TRAINING_PLAN_LIST, ""), type);
        }
    }

    public static void setTrainingPlanList(Context context, List<TrainingPlan> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_TRAINING_PLAN_LIST, jsonString).commit();
    }

    public static void removeTrainingPlanList(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_TRAINING_PLAN_LIST);
    }
}
