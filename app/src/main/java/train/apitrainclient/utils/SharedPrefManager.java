package train.apitrainclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.CircuitsModel;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExercisesModel;
import com.pttrackershared.models.eventbus.GraphInfo;
import com.pttrackershared.models.eventbus.MealsModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingLogsModel;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanView;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutJsonModel;
import com.pttrackershared.models.eventbus.WorkoutsModel;

public class SharedPrefManager {
    private static final String GET_WORKOUTS_KEY = "getWorkouts";
    private static final String GET_CIRCUITS_KEY = "getCircuits";
    private static final String GET_TRAINING_PLAN_KEY = "getTrainingPlan";
    private static final String GET_PLAN_VIEW_KEY = "getTrainingPlanView";
    private static final String GET_EXERCISES_KEY = "getExercises";
    private static final String GET_STATS_KEY = "getStats";
    private static final String GET_LOGS_KEY = "getLogs";
    private static final String GET_LOGS_KEY2 = "getLogs2";
    private static final String GET_PROFILE_KEY = "getProfile";
    private static final String TRAINING_LOG_PAGE_NUM = "getPageNum";
    private static final String TRAINING_LOG_PAGE_COUNT = "getPageCount";
    private static final String GET_USER_KEY = "userKey";
    private static final String GET_GRAPH_KEY = "graphKey";
    private static final String GET_TRAINING_LOGS_KEY = "trainingLogsKey";
    private static final String GET_WORKOUTSLIST_KEY = "workoutsListKey";
    private static final String GET_CIRCUITSLIST_KEY = "circuitsListKey";
    private static final String GET_EXERCISESLIST_KEY = "exercisesListKey";
    private static final String GET_TRAINING_LOGS_Sync_KEY = "trainingLogsSyncKey";
    private static final String GET_MEALS_KEY = "getMeals";

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

    public static void setWorkoutsList(Context context, List<WorkoutJsonModel> workouts) {
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

    //
//    //endregion
//
//    //region "Training Logs"
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

    public static void setTrainingLogsList(Context context, List<Workout> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_LOGS_KEY, jsonString).commit();
    }

    public static void setTrainingLogsList2(Context context, List<TrainingLog> workouts) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(workouts);
        preferences.edit().putString(GET_LOGS_KEY2, jsonString).commit();
    }

    public static void addTrainingLogsToList(Context context, List<WorkoutJsonModel> workouts) {
        List<WorkoutJsonModel> prevLogs = getTrainingLogsList(context);
        prevLogs.addAll(workouts);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(prevLogs);
        preferences.edit().putString(GET_LOGS_KEY, jsonString).commit();
    }

    public static List<TrainingLog> getTrainingLogsList2(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Type type = new TypeToken<List<TrainingLog>>() {
        }.getType();
        if (preferences.getString(GET_LOGS_KEY, "").equals("")) {
            return new ArrayList<TrainingLog>();
        } else {
            List<TrainingLog> workouts = new Gson().fromJson(preferences.getString(GET_LOGS_KEY2, ""), type);
            return workouts;
        }
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

    //    //endregion
//
//    // region "Progress Stats/Graphs"
    public static GraphInfo getUserStats(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_STATS_KEY, "").equals("")) {
            return new GraphInfo();
        } else {
            GraphInfo userStats = new Gson().fromJson(preferences.getString(GET_STATS_KEY, ""), GraphInfo.class);
            return userStats;
        }
    }

    public static void setUserStats(Context context, GraphInfo userStats) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(userStats);
        preferences.edit().putString(GET_STATS_KEY, jsonString).commit();
    }

    //    //endregion
//
//    // region "Training Plan View"
    public static TrainingPlanView getTrainingPlanView(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_PLAN_VIEW_KEY, "").equals("")) {
            return null;
        } else {
            TrainingPlanView planView = new Gson().fromJson(preferences.getString(GET_PLAN_VIEW_KEY, ""), TrainingPlanView.class);
            return planView;
        }
    }

    public static void setTrainingPlanView(Context context, TrainingPlanView planView) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(planView);
        preferences.edit().putString(GET_PLAN_VIEW_KEY, jsonString).commit();
    }
    //endregion

    // region "Training Plan"
    public static TrainingPlan getTrainingPlan(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_TRAINING_PLAN_KEY, "").equals("")) {
            return new TrainingPlan();
        } else {
            TrainingPlan plan = new Gson().fromJson(preferences.getString(GET_TRAINING_PLAN_KEY, ""), TrainingPlan.class);
            return plan;
        }
    }

    public static void setTrainingPlan(Context context, TrainingPlan plan) {
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
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
    }

    public static void verifyAndDeleteLogs(Context context, String data) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String savedLogs = preferences.getString(GET_LOGS_KEY, "");
        if (data.length() == savedLogs.length()) {
            preferences.edit().putString(GET_LOGS_KEY, "");//records deleted
        }
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
//        setTrainingLogsList2(context, workouts);

    }

    public static User getUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_USER_KEY, "").equals("")) {
            return new User();
        } else {
            User model = new Gson().fromJson(preferences.getString(GET_USER_KEY, ""), User.class);
            return model;
        }
    }

    public static void setUser(Context context, User model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_USER_KEY, jsonString).commit();
    }

    public static void removeUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_USER_KEY);
    }

    public static GraphInfo getGraphInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_GRAPH_KEY, "").equals("")) {
            return new GraphInfo();
        } else {
            GraphInfo model = new Gson().fromJson(preferences.getString(GET_GRAPH_KEY, ""), GraphInfo.class);
            return model;
        }
    }

    public static void setGraphInfo(Context context, GraphInfo model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_GRAPH_KEY, jsonString).commit();
    }

    public static void removeGraphInfo(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_GRAPH_KEY);
    }

    public static TrainingLogsModel getTrainingLogs(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_TRAINING_LOGS_KEY, "").equals("")) {
            return new TrainingLogsModel();
        } else {
            TrainingLogsModel model = new Gson().fromJson(preferences.getString(GET_TRAINING_LOGS_KEY, ""), TrainingLogsModel.class);
            return model;
        }
    }

    public static void setTrainingLogs(Context context, TrainingLogsModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_TRAINING_LOGS_KEY, jsonString).commit();
    }

    public static void removeTrainingLogs(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_TRAINING_LOGS_KEY);
    }

    public static TrainingLogsModel getTrainingLogsSync(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_TRAINING_LOGS_Sync_KEY, "").equals("")) {
            return new TrainingLogsModel();
        } else {
            TrainingLogsModel model = new Gson().fromJson(preferences.getString(GET_TRAINING_LOGS_Sync_KEY, ""), TrainingLogsModel.class);
            return model;
        }
    }

    public static void setTrainingLogsSync(Context context, TrainingLogsModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_TRAINING_LOGS_Sync_KEY, jsonString).commit();
    }

    public static void removeTrainingLogsSync(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_TRAINING_LOGS_Sync_KEY);
    }

    public static WorkoutsModel getWorkouts(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_WORKOUTSLIST_KEY, "").equals("")) {
            return new WorkoutsModel();
        } else {
            WorkoutsModel model = new Gson().fromJson(preferences.getString(GET_WORKOUTSLIST_KEY, ""), WorkoutsModel.class);
            return model;
        }
    }

    public static void setWorkouts(Context context, WorkoutsModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_WORKOUTSLIST_KEY, jsonString).commit();
    }

    public static void removeWorkouts(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_WORKOUTSLIST_KEY);
    }

    public static CircuitsModel getCircuits(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_CIRCUITSLIST_KEY, "").equals("")) {
            return new CircuitsModel();
        } else {
            CircuitsModel model = new Gson().fromJson(preferences.getString(GET_CIRCUITSLIST_KEY, ""), CircuitsModel.class);
            return model;
        }
    }

    public static void setCircuits(Context context, CircuitsModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_CIRCUITSLIST_KEY, jsonString).commit();
    }

    public static void removeCircuits(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_CIRCUITSLIST_KEY);
    }

    public static ExercisesModel getExercises(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_EXERCISESLIST_KEY, "").equals("")) {
            return new ExercisesModel();
        } else {
            ExercisesModel model = new Gson().fromJson(preferences.getString(GET_EXERCISESLIST_KEY, ""), ExercisesModel.class);
            return model;
        }
    }

    public static void setExercises(Context context, ExercisesModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_EXERCISESLIST_KEY, jsonString).commit();
    }

    public static void removeExercises(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_EXERCISESLIST_KEY);
    }

    public static MealsModel getMeals(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString(GET_MEALS_KEY, "").equals("")) {
            return new MealsModel();
        } else {
            MealsModel model = new Gson().fromJson(preferences.getString(GET_MEALS_KEY, ""), MealsModel.class);
            return model;
        }
    }

    public static void setMeals(Context context, MealsModel model) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = new Gson().toJson(model);
        preferences.edit().putString(GET_MEALS_KEY, jsonString).commit();
    }

    public static void removeMeals(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(GET_MEALS_KEY);
    }

    public static void setCalories(int calories, Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().putInt("Calories", calories).commit();
    }

    public static int getCalories(Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getInt("Calories", 0);
    }

    public static void removeCalories(Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().remove("Calories").commit();
    }

    private static SharedPreferences getPreferences(Context c) {
        return c.getApplicationContext().getSharedPreferences("CashSettings", Activity.MODE_PRIVATE);
    }

    public static void addFoods(MealsModel model, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(model);
        getPreferences(c).edit().putString("saveFood", mapString).apply();


    }

    public static MealsModel getFoods(Context c) {

        return new Gson().fromJson(getPreferences(c).getString("saveFood", ""), MealsModel.class);
    }

    public static void removeLogsForSync(Context c) {

        getPreferences(c).edit().remove("saveFood").apply();

    }

    public static void addLogsForSync(TrainingLogsModel model, Context c) {

        Gson gson = new Gson();
        String mapString = gson.toJson(model);
        getPreferences(c).edit().putString("SyncLogs", mapString).apply();


    }

    public static TrainingLogsModel getLogsForSync(Context c) {

        return new Gson().fromJson(getPreferences(c).getString("SyncLogs", ""), TrainingLogsModel.class);
    }

    public static void removeTrainLogsForSync(Context c) {

        getPreferences(c).edit().remove("SyncLogs").apply();

    }

    public static void setTraininPlanName(String name, Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().putString("TrainingPlanName", name).commit();
    }

    public static String getTrainingPlanName(Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getString("TrainingPlanName", "No training plan");
    }

    public static void removeTrainingPlanName(Context c) {
        getPreferences(c).edit().remove("TrainingPlanName").commit();

    }

    public static void setFoodPlanName(String name, Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        preferences.edit().putString("FoodPlanName", name).commit();
    }

    public static String getFoodPlanName(Context c) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getString("FoodPlanName", "No training plan");
    }

    public static void removeFoodPlanName(Context c) {
        getPreferences(c).edit().remove("FoodPlanName").commit();

    }

    //endregion

}
