package com.pttracker.datamanagers;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pttracker.controllers.ExercisesImagesController;
import com.pttracker.models.eventbus.RoutineDataFetchingEvent;
import com.pttracker.models.eventbus.RoutineDataReceivedEvent;
import com.pttracker.utils.SharedPrefManager;
import com.pttracker.views.screens.activities.HomeActivity;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;
import com.pttrackershared.models.Circuit;
import com.pttrackershared.models.CircuitDao;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.TrainingPlanDao;
import com.pttrackershared.models.Workout;
import com.pttrackershared.models.WorkoutDao;
import com.pttrackershared.plugins.LoggerUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * TrainingPlanDataManager manages TrainingPlan information. Stores Routine information to local storage and provides to whole app.
 * Handles Routine related actions with server.
 */

public class TrainingPlanDataManager {

    private static TrainingPlanDataManager mInstance = null;
    private Context context;

    private TrainingPlanDataManager(Context context) {
        this.context = context;
    }

    public static TrainingPlanDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TrainingPlanDataManager(context);
        }
        return mInstance;
    }

    /**
     * Destroys mInstance from memory. As mInstance is a static object so this object will exist
     * even all activities are destroyed.
     */
    public void release() {
        if (mInstance != null) {
            mInstance = null;
            LoggerUtils.d("Destroying shared instance");
        }
    }

    public List<TrainingPlan> getUnsyncedTrainingPlans() {

        GreenDaoDataManager.getInstance(context).getDaoSession().clear();
        List<TrainingPlan> routineList = GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().queryBuilder()
                .where(TrainingPlanDao.Properties.IsSynced.eq(false))
                .list();

        return routineList;
    }

    //endregion

    //region All Interfaces Provided By this DataManager class

    //endregion

    //region Data Storage Related Functions

    public void saveTrainingPlan(String jsonData) {
        try {
            UserAccountDataManager.getInstance(context).setLastSynced(new Date());

            List<TrainingPlan> trainingPlanList = new ArrayList<>();
            List<Workout> workoutList = new ArrayList<>();
            List<Circuit> circuitList = new ArrayList<>();
            List<Exercise> exerciseList = new ArrayList<>();

            Gson gson = new Gson();
            JSONArray trainingPlanJsonArray = new JSONArray(jsonData);


            for (int tr = 0; tr < trainingPlanJsonArray.length(); tr++) {

                TrainingPlan trainingPlan = gson.fromJson(trainingPlanJsonArray.getJSONObject(tr).toString(), TrainingPlan.class);
                trainingPlan.setIsSynced(true);

                JSONObject trainingPlanJson = trainingPlanJsonArray.getJSONObject(tr);
                JSONArray workoutJsonArray = trainingPlanJson.getJSONArray("workoutList");
                for (int wo = 0; wo < workoutJsonArray.length(); wo++) {
                    JSONObject workoutJson = workoutJsonArray.getJSONObject(wo);
                    Workout workout = gson.fromJson(workoutJson.toString(), Workout.class);
                    workout.setTrainingPlanId(trainingPlanList.size() + 1);


                    JSONArray circuitJsonArray = workoutJson.getJSONArray("circuitList");
                    for (int cr = 0; cr < circuitJsonArray.length(); cr++) {
                        JSONObject circuitJson = circuitJsonArray.getJSONObject(cr);
                        Circuit circuit = gson.fromJson(circuitJson.toString(), Circuit.class);
//                            circuit.setWorkoutId(workouts.size() + 1);


                        JSONArray exerciseJsonArray = circuitJson.getJSONArray("exerciseList");
                        for (int ex = 0; ex < exerciseJsonArray.length(); ex++) {
                            JSONObject exerciseJson = exerciseJsonArray.getJSONObject(ex);
                            Exercise exercise = gson.fromJson(exerciseJson.toString(), Exercise.class);
//                                exercise.setCircuitId(circuits.size() + 1);
                            exerciseList.add(exercise);
                        }
                        circuitList.add(circuit);
                    }

                    workoutList.add(workout);
                }

                trainingPlanList.add(trainingPlan);

            }

            if (trainingPlanList.size() > 0) {
                GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().deleteAll();
                GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().insertInTx(trainingPlanList);
            }

            if (workoutList.size() > 0) {
                GreenDaoDataManager.getInstance(context).getDaoSession().getWorkoutDao().deleteAll();
                GreenDaoDataManager.getInstance(context).getDaoSession().getWorkoutDao().insertOrReplaceInTx(workoutList);
            }

            if (circuitList.size() > 0) {
                GreenDaoDataManager.getInstance(context).getDaoSession().getCircuitDao().deleteAll();
                GreenDaoDataManager.getInstance(context).getDaoSession().getCircuitDao().insertOrReplaceInTx(circuitList);
            }

            if (exerciseList.size() > 0) {
                GreenDaoDataManager.getInstance(context).getDaoSession().getExerciseDao().deleteAll();
                GreenDaoDataManager.getInstance(context).getDaoSession().getExerciseDao().insertOrReplaceInTx(exerciseList);
            }


            ExercisesImagesController.getInstance(context).loadImagesData();
            EventBus.getDefault().post(new RoutineDataFetchingEvent(true));
            EventBus.getDefault().post(new RoutineDataReceivedEvent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void receiveUpdatedTrainingPlans(String data) {
        Gson gson = new Gson();
        List<TrainingPlan> routineList = gson.fromJson(data, new TypeToken<List<TrainingPlan>>() {
        }.getType());

        for (TrainingPlan routine : routineList) {
            List<TrainingPlan> routineListOld = GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().queryBuilder()
                    .where(TrainingPlanDao.Properties.TrainingPlanId.eq(routine.getTrainingPlanId()))
                    .list();

            TrainingPlan trainingPlanOld = routineListOld.get(0);
            trainingPlanOld.setIsSynced(true);
            GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().update(trainingPlanOld);
        }
        UserAccountDataManager.getInstance(context).setLastSynced(new Date());
        EventBus.getDefault().post(new RoutineDataFetchingEvent(true));
    }

    public void updateTrainingPlan(TrainingPlan trainingPlan) {
        trainingPlan.setIsSynced(false);
        GreenDaoDataManager.getInstance(context).getDaoSession().getWorkoutDao().update(trainingPlan.getWorkoutList().get(0));
        GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().update(trainingPlan);
    }

    public List<TrainingPlan> getStoredTrainingPlans() {
        GreenDaoDataManager.getInstance(context).getDaoSession().clear();
        List<TrainingPlan> trainingPlanList = GreenDaoDataManager.getInstance(context).getDaoSession().getTrainingPlanDao().queryBuilder()
                .list();
        return trainingPlanList;
    }

    public void setUserFeedback(TrainingLog trainingLog, int userFeedback) {
        trainingLog.setFeedback(userFeedback + "");
        trainingLog.setIsSynced(false);
        SharedPrefManager.addTrainingLog(context, trainingLog);
    }

    //endregion
}