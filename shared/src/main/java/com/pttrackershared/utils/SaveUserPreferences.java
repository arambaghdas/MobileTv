package com.pttrackershared.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pttrackershared.models.eventbus.User;

/**
 * Created by goran on 25.12.17.
 */

public class SaveUserPreferences {

    private static SharedPreferences getPreferences(Context c){
        return c.getApplicationContext().getSharedPreferences("User", Activity.MODE_PRIVATE);
    }

    public static void addUser (User model, Context c){

        Gson gson = new Gson();
        String mapString = gson.toJson(model);
        getPreferences(c).edit().putString("SavedUser", mapString).apply();


    }
    public static User getUser (Context c){

        return new Gson().fromJson(getPreferences(c).getString("SavedUser", ""), User.class);
    }

    public static void removeUser(Context c){

        getPreferences(c).edit().remove("SavedUser").apply();

    }
}
