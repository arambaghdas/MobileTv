package com.pttracker.controllers;

import android.content.Context;

import com.pttracker.datamanagers.ExerciseImagesDataManager;
import com.pttracker.services.PhoneMessagesListenerService;
import com.pttrackershared.models.ExerciseImages;
import com.pttrackershared.models.eventbus.PendingMessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * ExerciseImagesController provide all the manipulation with the images saved in
 * the database and retrieval is implemented in this class.
 */

public class ExercisesImagesController {
    private static ExercisesImagesController mInstance = null;
    private Context context;

    private ExercisesImagesController(Context context) {
        this.context = context;
    }

    public static ExercisesImagesController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ExercisesImagesController(context);
        }
        return mInstance;
    }


    /**
     * Loads images data from mobile through sending a broadcast event through eventbus
     */
    public void loadImagesData() {
        PendingMessageEvent pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.EXERCISES_IMAGES_PARTIAL_UPDATE);
        EventBus.getDefault().postSticky(pendingMessageEvent);
        pendingMessageEvent = new PendingMessageEvent();
        pendingMessageEvent.setPath(PhoneMessagesListenerService.EXERCISES_IMAGES_REQUEST);
        EventBus.getDefault().postSticky(pendingMessageEvent);
    }
}
