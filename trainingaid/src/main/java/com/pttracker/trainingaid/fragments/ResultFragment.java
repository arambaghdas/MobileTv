package com.pttracker.trainingaid.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.pttracker.trainingaid.Class.TrainingAid;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.activities.BaseActivity;
import com.pttracker.trainingaid.activities.ExerciseActivity;
import com.pttracker.trainingaid.activities.ResultActivity;
import com.pttracker.trainingaid.models.TrainingLogsModel;
import com.pttracker.trainingaid.plugins.SharedPrefManagerPlugIn;
import com.pttracker.trainingaid.utils.ApiCallsHandler;
import com.pttracker.trainingaid.utils.AppUtils;
import com.pttracker.trainingaid.utils.ListenersHandler;
import com.pttracker.trainingaid.utils.SaveUserPreferences;
import com.pttracker.trainingaid.utils.SharedPrefManager;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.plugins.TimeUtils;

import java.util.Calendar;
import java.util.Date;

public class ResultFragment extends BaseFragment {

    TextView tvTotalTime;
    TextView tvMinHeartRate;
    TextView tvMaxHeartRate;
    TextView tvAvgHeartRate;
    TextView tvCalories; // TO BE ADDED
    ImageView star1,star2,star3;

    private View rootView;
    private Context context;
    public static final String KEY_TOTAL_TIME = "key_total_time";
    public static final String KEY_MIN_BPM = "key_min_bpm";
    public static final String KEY_MAX_BPM = "key_max_bpm";
    public static final String KEY_AVG_BPM = "key_avg_bpm";
    public static final String KEY_TRAINING_LOG = "training_log";
    private int totalTime;
    private long minBpm;
    private long maxBpm;
    private long avgBpm;
    private int caloriesBurned;
    private TrainingLog trainingLog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rect_new_result_activity, container, false);
        this.rootView = rootView;
        context = getActivity();

        initData();
        initViews();

        ResultActivity.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Do you want to leave the workout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ExerciseFragment.elapsedTime = 0;
                                BaseActivity.isExerciseRunning = false;
                                BaseActivity.isExerciseStarted = false;
                                BaseActivity.startExerciseTime = new Date();
                                ExerciseFragment.totalTime = 0;
                                ExerciseActivity.finishExerciseActivity();
                                ResultActivity.finishResultActivity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        return rootView;
    }

    public void initData() {
        trainingLog = BaseActivity.getInstance().getCurrentTrainingLog();
        totalTime = getActivity().getIntent().getIntExtra(KEY_TOTAL_TIME,0);
        minBpm = getActivity().getIntent().getIntExtra(KEY_MIN_BPM, 0);
        maxBpm = getActivity().getIntent().getIntExtra(KEY_MAX_BPM, 0);
        avgBpm = getActivity().getIntent().getIntExtra(KEY_AVG_BPM, 0);
    }

    public void initViews() {

        ExerciseFragment.exerciseList = null;

        tvTotalTime = (TextView) rootView.findViewById(R.id.tv_total_time);
        tvMinHeartRate = (TextView) rootView.findViewById(R.id.tv_min_heart_rate);
        tvMaxHeartRate = (TextView) rootView.findViewById(R.id.tv_max_heart_rate);
        tvAvgHeartRate = (TextView) rootView.findViewById(R.id.tv_avg_heart_rate);
        tvCalories = (TextView) rootView.findViewById(R.id.tv_calories);
        star1 = (ImageView) rootView.findViewById(R.id.star1);
        star2 = (ImageView) rootView.findViewById(R.id.star2);
        star3 = (ImageView) rootView.findViewById(R.id.star3);
        star1.setImageResource(R.drawable.easywhite);
        star2.setImageResource(R.drawable.mediumwhite);
        star3.setImageResource(R.drawable.hardwhite);

        totalTime = getActivity().getIntent().getExtras().getInt(KEY_TOTAL_TIME);

        if (getActivity().getIntent().getSerializableExtra(KEY_TRAINING_LOG) != null)
        trainingLog = new Gson().fromJson(getActivity().getIntent().getStringExtra(KEY_TRAINING_LOG), TrainingLog.class);
        minBpm = getActivity().getIntent().getIntExtra(KEY_MIN_BPM, 0);
        maxBpm = getActivity().getIntent().getIntExtra(KEY_MAX_BPM, 0);
        avgBpm = getActivity().getIntent().getIntExtra(KEY_AVG_BPM, 0);

        if (getActivity().getIntent().hasExtra(KEY_AVG_BPM)) {
            tvTotalTime.setText(TimeUtils.getTimeString(totalTime));

            tvMinHeartRate.setText(minBpm + "");
            tvMaxHeartRate.setText(maxBpm + "");
            tvAvgHeartRate.setText(avgBpm + "");

        }

        if(avgBpm != 0) {
            // Calculation of the calories
            // Using the formula for men
            // Formula: ((Age x 0.2018) - (weight x 0.09036) + (Heart rate x 0.6309) - 55.0969) x Time(mins) / 4.184
            User user = SaveUserPreferences.getUser(context);
            String strUserAge = user.getDobString().substring(0, 4);
            int userAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(strUserAge);

            // Currently using GOAL weight, should be changed to current weight
            float weight = Float.parseFloat(user.getWeightgoal());

            // Converting time from seconds to minutes
//            float timeMin = totalTime / 60;
//
//            // final calculation
//            double calc1 = userAge * 0.2017;
//            double calc2 = weight * 0.09036;
//            double calc3 = avgBpm * 0.6309;
//            double calc4 = timeMin / 4.184;
//            double caloriesBurned = Math.abs((calc1 - calc2 + calc3 - 55.0969) * calc4);

            caloriesBurned = SharedPrefManager.getCalories(context);
            tvCalories.setText("Calories: " + caloriesBurned + " KCal");
        } else {
            tvCalories.setText("Calories: N/A");
            caloriesBurned = SharedPrefManager.getCalories(context);
            tvCalories.setText("Calories: " + caloriesBurned + " KCal");
            SharedPrefManager.removeCalories(context);
        }

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.easycolor);
                star2.setImageResource(R.drawable.mediumwhite);
                star3.setImageResource(R.drawable.hardwhite);
                setUserFeedback(1);
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.easywhite);
                star2.setImageResource(R.drawable.mediumcolor);
                star3.setImageResource(R.drawable.hardwhite);
                setUserFeedback(2);
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star1.setImageResource(R.drawable.easywhite);
                star2.setImageResource(R.drawable.mediumwhite);
                star3.setImageResource(R.drawable.hardcolor);
                setUserFeedback(3);
            }
        });
    }

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    public void setUserFeedback(int feedback) {

        trainingLog.setFeedback(String.valueOf(feedback));


        if (!AppUtils.networkConnection){
            TrainingLogsModel trainingLogsModel = SharedPrefManagerPlugIn.getTrainingLogs(getActivity());
            if (trainingLogsModel == null){
                trainingLogsModel = new TrainingLogsModel();
            }
            trainingLogsModel.trainingLogs.add(trainingLog);
            SharedPrefManagerPlugIn.setTrainingLogsSync(getActivity(),trainingLogsModel);
        }


                ApiCallsHandler apiCallsHandler = new ApiCallsHandler(context);
                apiCallsHandler.updateTrainingLogWithExercise(trainingLog, new ListenersHandler.OnUpdateTrainingLogCompletionListener() {
                    @Override
                    public void onSuccess(TrainingLog trainingLog) {
                        ExerciseFragment.elapsedTime = 0;
                        BaseActivity.isExerciseRunning = false;
                        BaseActivity.isExerciseStarted = false;
                        BaseActivity.startExerciseTime = new Date();
                        ExerciseFragment.totalTime = 0;
                        LandingFragment.btnChangeWorkout.performClick();
                        TrainingAid.restartHome = true;
                        Intent intent = new Intent("isr.LAUNCH");
                        intent.putExtra("fromResult", "fromResult");
                        startActivity(intent);
                        if (ResultActivity.getInstance() != null)
                            ResultActivity.finishResultActivity();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        Log.d("Feedback",errorMessage);
                    }
                });

    }

}
