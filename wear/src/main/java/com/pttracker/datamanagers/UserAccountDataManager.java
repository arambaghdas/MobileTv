package com.pttracker.datamanagers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pttracker.controllers.UserAccountController;
import com.pttracker.utils.SharedPrefManager;
import com.pttrackershared.datamanagers.greendao.GreenDaoDataManager;
import com.pttrackershared.models.User;
import com.pttrackershared.plugins.LoggerUtils;

import java.util.Date;
import java.util.List;


/**
 * UserAccountDataManager manages User information. Stores User information to local storage and provides to whole app.
 * Handles User related actions with server.
 */

public class UserAccountDataManager {

    private static UserAccountDataManager mInstance = null;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static User CURRENT_USER = null;

    private UserAccountDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static UserAccountDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserAccountDataManager(context);
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

    //region User Related Actions

    //endregion

    //region All Interfaces Provided By this DataManager class

    //endregion

    //region Data Storage Related Functions
    public void saveUser(String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        GreenDaoDataManager.getInstance(context).getDaoSession().getUserDao().insert(user);//insert new user in db
    }

    public User getCurrentUser() {
//        if (CURRENT_USER != null) {
//            return CURRENT_USER;
//        }
        GreenDaoDataManager.getInstance(context).getDaoSession().clear();
        List<User> userList = GreenDaoDataManager.getInstance(context).getDaoSession().getUserDao().loadAll();

        if (userList.size() > 1) {
            LoggerUtils.e("there should be only one user, so resolve this issue");
        }

        if (userList.isEmpty()) {
            return null;
        } else {
            CURRENT_USER = userList.get(0);
            return CURRENT_USER;
        }
    }

    public void setLastSynced(Date date) {
        if (date != null) {
            editor.putLong("last_synced", date.getTime());
        } else {
            editor.putLong("last_synced", -1);
        }
        editor.commit();
    }

    public Date getLastSynced() {
        Long lastSyncedTime = sharedPreferences.getLong("last_synced", -1);

        if (lastSyncedTime == -1) {
            return null;
        }
        Date date = new Date();
        date.setTime(lastSyncedTime);
        return date;
    }

    //endregion
}