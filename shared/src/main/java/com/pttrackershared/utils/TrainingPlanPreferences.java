package com.pttrackershared.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pttrackershared.models.eventbus.TrainingPlan;

/**
 * Created by goran on 25.12.17.
 */

public class TrainingPlanPreferences {

    private static SharedPreferences getPreferences(Context c){
        return c.getApplicationContext().getSharedPreferences("TrainingPlanSave", Activity.MODE_PRIVATE);
    }

    public static void addPlan (TrainingPlan model, Context c){
        Gson gson = new Gson();
        String mapString = gson.toJson(model);
        getPreferences(c).edit().putString("SavedPlan", mapString).apply();


    }
    public static TrainingPlan getPlan (Context c){
        return new Gson().fromJson(getPreferences(c).getString("SavedPlan", ""), TrainingPlan.class);
    }

    public static void removePlan(Context c){
        getPreferences(c).edit().remove("SavedPlan").apply();

    }
}
