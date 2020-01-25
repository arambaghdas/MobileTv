package com.pttracker.trainingaid.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pttracker.trainingaid.eventbus.HearRateChangedEvent;
import com.pttracker.trainingaid.models.SyncingEvents;
import com.pttracker.trainingaid.plugins.DialogUtils;
import com.pttracker.trainingaid.plugins.KeyboardUtils;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.plugins.LocaleHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * BaseActivity manages basic operations needed for an activity. Any common logic for all activities can be implemented here.
 */

public class BaseActivity extends AppCompatActivity {

    //region View References
    //endregion

    //region Other Variables
    public Context context;
    public static android.app.AlertDialog dialog;
    public static Workout workout;
    private int minBpm = -1;
    private int maxBpm;
    private boolean shouldReadBpm;
    private TrainingLog currentTrainingLog;
    public static BaseActivity activity;
    public static String heartRate= "";
    public static Date startExerciseTime;
    public static boolean dateSet;
    public static boolean isExerciseRunning;
    public static boolean isExerciseStarted;
    public static int countDown;

    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from AppCompatActivity Base Class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //region necessary operations to do after activity creation
        preStart();
        initData();
        initControllers();
        initViews();
        initListeners();
        postStart();
//        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("showDialog") && this instanceof Training_aid_activity) {
//            showDialog();
//        }
        //endregion
    }

    public static BaseActivity getInstance(){return activity;}

    private void showDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getBaseContext());
        builder1.setMessage("Error is occurring due to data inconsistency on server.");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder1.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    public static void setExerciseDate(){
     if (!dateSet){
         dateSet = true;
         startExerciseTime = new Date();
     }
    }

    /**
     * Perform business logic on activity paused.
     */
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        activity = this;
    }

    /**
     * Perform business logic on activity resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        activity = this;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Can handle permission results at this single point in this base class
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    public void preStart() {
        context = this;
        if (dialog == null)
            dialog = new android.app.AlertDialog.Builder(context.getApplicationContext(), android.R.style.Theme_DeviceDefault_Light_Dialog)
                    .setTitle("Sync Stats")
                    .setMessage("Last Sync Stats")
                    .setCancelable(false)
                    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .create();
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    public void initData() {
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    public void initControllers() {
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    public void initViews() {
        ButterKnife.bind(this);
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    public void initListeners() {
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    public void postStart() {
        KeyboardUtils.SetupHidableKeyboard(context);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        DialogUtils.HideDialog();
        super.onDestroy();
    }

    public void showSyncingProgress() {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (dialog != null && !(BaseActivity.this instanceof Training_aid_activity)) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                    } else
//                        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                    dialog.show();
//                }
//            }
//        });

    }


    /**
     * Trigger Report Generation
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGenerateReportEvent(SyncingEvents.GenerateReport event) {
//        if (BaseActivity.this instanceof SettingsActivity) {
//            ((SettingsActivity) BaseActivity.this).updateSyncButtonAsSynced();
//        } else
//            showSyncingProgress();
    }

    //    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onGenerateReportEvent(SyncingEvents.UpdateImagesProgress event) {
//        showSyncingProgress();
//    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGenerateReportEvent(SyncingEvents.SyncingImages event) {
//        if (BaseActivity.this instanceof SettingsActivity) {
//            ((SettingsActivity) BaseActivity.this).updateSyncButtonAsSyncing();
//        } else
//            showSyncingProgress();
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onGenerateReportEvent(SyncingEvents.SyncingPlan event) {
//        if (BaseActivity.this instanceof SettingsActivity) {
//            ((SettingsActivity) BaseActivity.this).updateSyncButtonAsSyncing();
//        } else
//            showSyncingProgress();
//    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onGenerateReportEvent(SyncingEvents.UpdatePlanProgress event) {
//        showSyncingProgress();
//    }
    //endregion

    //region Business Logic Specific to this Activity
    //endregion

    //region Callback Methods
    //endregion

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent event) {
        int newValue = event.getBpm();
        if (!shouldReadBpm) {
            return;
        }

        if (minBpm == -1) {
            minBpm = newValue;
            maxBpm = newValue;
        }

        if (newValue < minBpm) {
            minBpm = newValue;
        } else if (newValue > maxBpm) {
            maxBpm = newValue;
        }

    }

    public void setShouldReadBpm(boolean shouldReadBpm) {
        this.shouldReadBpm = shouldReadBpm;
    }

    public void makeHeartRateValuesDefault() {
        minBpm = -1;
        maxBpm = 0;
    }

    public int getMinBpm() {
        return minBpm;
    }

    public int getMaxBpm() {
        return maxBpm;
    }

    public int getAvgBpm() {
        return (minBpm + maxBpm) / 2;
    }

    public TrainingLog getCurrentTrainingLog() {
        return currentTrainingLog;
    }

    public void setCurrentTrainingLog(TrainingLog currentTrainingLog) {
        this.currentTrainingLog = currentTrainingLog;
    }

}

