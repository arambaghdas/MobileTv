package train.apitrainclient.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pttrackershared.models.eventbus.ProgressModel;
import com.pttrackershared.models.eventbus.User;

/**
 * Created by goran on 25.12.17.
 */

public class OfflineDataProgressPreferences {

    private static SharedPreferences getPreferences(Context c){
        return c.getApplicationContext().getSharedPreferences("Progress", Activity.MODE_PRIVATE);
    }

    public static void addProgress (ProgressModel model, Context c){
        Gson gson = new Gson();
        String mapString = gson.toJson(model);
        getPreferences(c).edit().putString("ProgressModel", mapString).apply();


    }
    public static ProgressModel getProgress (Context c){

        return new Gson().fromJson(getPreferences(c).getString("ProgressModel", ""), ProgressModel.class);
    }

    public static void removeUser(Context c){

        getPreferences(c).edit().remove("ProgressModel").apply();

    }
}
