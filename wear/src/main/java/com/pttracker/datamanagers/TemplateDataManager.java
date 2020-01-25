package com.pttracker.datamanagers;

import android.content.Context;

import com.pttrackershared.plugins.LoggerUtils;


/**
 * TemplateDataManager manages <MODEL> information. Stores <MODEL> information to local storage and provides to whole app.
 * Handles <MODEL> related actions with server.
 * This class is just a template and can be used to create data manager.
 */

public class TemplateDataManager {

    private static TemplateDataManager mInstance = null;
    private Context context;

    private TemplateDataManager(Context context) {
        this.context = context;
    }

    public static TemplateDataManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TemplateDataManager(context);
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

    //region App Related Actions

    public void action(String input, final OnActionCompletionListener onActionCompletionListener) {
    }

    //endregion

    //region All Interfaces Provided By this DataManager class

    public interface OnActionCompletionListener {
        void onSuccess();

        void onFailure(int errorCode, String errorMessage);
    }


    //endregion

    //region Data Storage Related Functions

    //endregion
}