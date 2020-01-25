package com.pttracker.controllers;

import android.content.Context;
import android.util.Log;

import com.pttracker.R;
import com.pttracker.datamanagers.TemplateDataManager;
import com.pttracker.utils.StatusCodes;
import com.pttrackershared.plugins.LoggerUtils;
import com.pttrackershared.plugins.NetworkUtils;
import com.pttrackershared.plugins.ValidatorUtils;

/**
 * TemplateController implements complete app specific <MODEL> related business logic.
 * Handles actions, validates inputs to perform an action and get results from <DATA_MANAGER>.
 * This class is just a template and can be used to create controller.
 */

public class TemplateController {

    private static TemplateController mInstance = null;
    private Context context;

    private TemplateController(Context context) {
        this.context = context;
    }

    public static TemplateController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TemplateController(context);
        }
        return mInstance;
    }

    /**
     * Destroys mInstance from memory. As mInstance is a static object so this object will exist
     * even all activities are destroyed.
     */
    public void release() {
        if (mInstance != null) {
            //TODO release data manager class related with it
            TemplateDataManager.getInstance(context).release();
            LoggerUtils.d("Destroying shared instance");
            mInstance = null;
        }
    }

    //region <MODEL> Related Actions

    public void action(String input, final OnActionCompletionListener onActionCompletionListener) {

        //TODO: Apply validation on input here
        if (ValidatorUtils.IsNullOrEmpty(input)) {
            if (onActionCompletionListener != null) {
                onActionCompletionListener.onFailure(StatusCodes.ERROR_CODE_INVALID_INPUT, "invalid input value");
            } else {
                LoggerUtils.e("onFailure: " + "invalid input value");
            }
            return;
        }

        if (!NetworkUtils.IsNetworkAvailable(context)) {
            if (onActionCompletionListener != null) {
                onActionCompletionListener.onFailure(StatusCodes.ERROR_CODE_NETWORK_UNAVAILABLE, context.getString(R.string.error_message_network_unavailable));
            } else {
                LoggerUtils.e("onFailure: " + context.getString(R.string.error_message_network_unavailable));
            }
            return;
        }

        TemplateDataManager.getInstance(context).action(input, new TemplateDataManager.OnActionCompletionListener() {
            @Override
            public void onSuccess() {
                String message = "Success";
                if (onActionCompletionListener != null) {
                    onActionCompletionListener.onSuccess(message);
                } else {
                    LoggerUtils.d("onSuccess: " + message);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                if (onActionCompletionListener != null) {
                    onActionCompletionListener.onFailure(errorCode, errorMessage);
                } else {
                    LoggerUtils.e("onFailure: " + errorMessage);
                }
            }
        });
    }

    //region All Interface Provided by this Controller class

    public interface OnActionCompletionListener {
        void onSuccess(String message);

        void onFailure(int errorCode, String errorMessage);
    }
    //endregion
}