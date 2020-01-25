package com.pttracker.trainingaid.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.youtube.player.YouTubePlayer;

import com.google.gson.Gson;
import com.pttracker.trainingaid.Class.TrainingAid;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.activities.BaseActivity;
import com.pttracker.trainingaid.activities.ExerciseActivity;
import com.pttracker.trainingaid.activities.NewGetReadyActivity;
import com.pttracker.trainingaid.activities.ResultActivity;
import com.pttracker.trainingaid.eventbus.HearRateChangedEvent;
import com.pttracker.trainingaid.services.HeartbeatService;
import com.pttracker.trainingaid.utils.CountDownTimerPausable;
import com.pttracker.trainingaid.utils.SharedPrefManager;
import com.pttracker.trainingaid.models.TrainingPlan;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.plugins.TimeUtils;
import com.pttrackershared.plugins.ValidatorUtils;
import com.pttrackershared.utils.Constants;
import com.pttrackershared.utils.SaveUserPreferences;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Show exercise info for all exercises in all circuits in workout in selected routine
 * Provide option to start, pause, cancel an exercise and saves current exercise and move to next exercise or ResultActivity (if there is not next exercise)
 */

public class ExerciseFragment extends BaseFragment implements Observer, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ExerciseFragment";

    private View rootView;

    TextView tvSerialNumber;
    TextView tvExerciseName;
    TextView tvCategoryName;
    TextView tvSetsCount;
    TextView tvWeight;
    TextView tvHeartRate;
    ImageView ivExerciseImage;
//    TextView tvCalories;
    public static TextView tvElapsedTime;
    RelativeLayout rlNext;
    RelativeLayout rlSkip;
    RelativeLayout rlCancel;

    TextView effort1;
    TextView effort2;
    TextView effort3;
    TextView effort4;
    TextView effort5;
    LinearLayout effortZoneLayout;

    //region Other Variables

    public static final String KEY_TOTAL_TIME = "key_total_time";
    public static final String KEY_TRAINING_LOG = "training_log";
    public static final String KEY_MIN_BPM = "key_min_bpm";
    public static final String KEY_MAX_BPM = "key_max_bpm";
    public static final String KEY_AVG_BPM = "key_avg_bpm";
    private Workout selectedWorkout;
    public static int exerciseIndex;
    public static List<Exercise> exerciseList;
    private Context context;
    public static int elapsedTime;
    public static int totalTime = -1;
    public boolean nextPressed = false;
    public boolean cancelPressed = false;
    public CountDownTimerPausable countDownTimer = null;
    public boolean countdownMade = false;
    static ExerciseFragment currentFragment;
    private static final int REST_REQUEST_CODE = 1125;
    private static final int GET_READY_REQUEST_CODE = 1124;
    CountDownTimer caloriesCountDownTimer;
    User user;
    public static int calories = 0;
    public int saveCalories = 0;
    private boolean skipped = false;
    public static Exercise exercise;
    public static YouTubePlayer youTubePlayer;
    private String youtubeVideoId;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rect_fragment_current_exercise, container, false);
        this.rootView = rootView;
        context = getActivity();


        initViews();
        initData();
        start();
//        String strUserAge = user.getDobString().substring(0, 4);
//        final int userAge = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(strUserAge);

//        caloriesCountDownTimer = new CountDownTimer(1000000000, 5000) {
//            @Override
//            public void onTick(long l) {
//                user.setGender("male");
//                if (user.getGender().equals("male")) {
//                    if (!BaseActivity.heartRate.equals("N/A")) {
////                        calories = mansCalculation();
////                        tvCalories.setText(calories + "");
//                    }else {
////                        tvCalories.setText("N/A");
//                    }
//                } else {
//                    if (!BaseActivity.heartRate.equals("N/A")) {
////                        calories = womansCalculation();
//                        tvCalories.setText(calories + "");
//                    }else {
//                        tvCalories.setText("N/A");
//                    }
//                }
//
//                if (!BaseActivity.heartRate.equals("N/A")){
//                    float zoneX = HeartbeatService.currentValue * 100;
////                    int zoney = 220 - userAge;
////                    float zone = zoneX / zoney;
////                    if (zone <= 60){
////                        setEffort(effort1);
////                    }else if (zone > 60 && zone <= 70){
////                        setEffort(effort2);
////                    }else if (zone > 70 && zone <= 80){
////                        setEffort(effort3);
////                    }else if (zone > 80 && zone <= 80){
////                        setEffort(effort4);
////                    }else if (zone > 90){
////                        setEffort(effort2);
////                    }
//                }
//            }
//
//            @Override
//            public void onFinish() {
//            }
//        };

//        caloriesCountDownTimer.start();

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

                nextPressed = true;
                totalTime = ExerciseActivity.restTimer;
                completeExercise();

            }
        });

        rlSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

                skipped = true;
                totalTime = ExerciseActivity.restTimer;
                completeExercise();
            }
        });

        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPressed = true;
                totalTime = ExerciseActivity.restTimer;
                if (!ExerciseActivity.timer.isPaused())
                    ExerciseActivity.timer.pause();

                if (BaseActivity.isExerciseStarted) {
                    cancelCurrentExercise();
                }

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

                exerciseIndex++;

                showNextExercise();
            }
        });

        ExerciseActivity.backButton.setOnClickListener(new View.OnClickListener() {
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
                                ExerciseActivity.timer.cancel();
                                ExerciseActivity.timer = null;

                                if (BaseActivity.isExerciseStarted) {
                                    cancelCurrentExercise();
                                }

                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                    countDownTimer = null;
                                }

                                if (NewGetReadyActivity.getInstance() != null){
                                    NewGetReadyActivity.getInstance().finish();
                                }

                                ExerciseActivity.finishExerciseActivity();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        currentFragment = null;
    }

    private void start() {
        selectedWorkout = BaseActivity.workout;

        if (selectedWorkout != null)
            if (selectedWorkout.getCircuitList() != null) {
                initData();
                prepareExerciseList();
                showNextExercise();
                startExercise();
            } else {
                Toast.makeText(context, R.string.error_message_no_circuit_available, Toast.LENGTH_SHORT).show();
                TrainingAid.getInstance().showLandingFragment();
            }
    }

    public void setEffort(View view){

        for (int i = 0; i < effortZoneLayout.getChildCount(); i++) {
            if (i != 0){
                View view1 = effortZoneLayout.getChildAt(i);
                if (view1.getId() == view.getId()){
                    view1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.effort_zone_square_green));
                }else {
                    view1.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.effort_zone_square));
                }
            }
        }

    }

    public boolean isSwipeable() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (nextPressed) {
            nextPressed = false;
            startExercise();
        }
        if (cancelPressed) {
            cancelPressed = false;
            startExercise();
        }
    }

    public void initViews() {
        ButterKnife.bind(this, rootView);
        tvSerialNumber = (TextView) rootView.findViewById(R.id.tv_serial_number);
        tvExerciseName = (TextView) rootView.findViewById(R.id.tv_exercise_name);
        tvCategoryName = (TextView) rootView.findViewById(R.id.tv_category_name);
        tvSetsCount = (TextView) rootView.findViewById(R.id.tv_sets_count);
        tvWeight = (TextView) rootView.findViewById(R.id.tv_weight);
        tvHeartRate = (TextView) rootView.findViewById(R.id.tv_heart_rate);
        ivExerciseImage = (ImageView) rootView.findViewById(R.id.iv_exercise_image);
        tvElapsedTime = (TextView) rootView.findViewById(R.id.tv_elapsed_time);
        rlNext = (RelativeLayout) rootView.findViewById(R.id.rl_nextController);
        rlSkip = (RelativeLayout) rootView.findViewById(R.id.rl_skipController);
        rlCancel = (RelativeLayout) rootView.findViewById(R.id.rl_cancelController);
//        tvCalories = (TextView) rootView.findViewById(R.id.tv_caloriestv);

        effort1 = (TextView) rootView.findViewById(R.id.effort1);
        effort2 = (TextView) rootView.findViewById(R.id.effort2);
        effort3 = (TextView) rootView.findViewById(R.id.effort3);
        effort4 = (TextView) rootView.findViewById(R.id.effort4);
        effort5 = (TextView) rootView.findViewById(R.id.effort5);
        effortZoneLayout = (LinearLayout) rootView.findViewById(R.id.effortZoneLayout);

    }

    public void initData() {
        BaseActivity.setExerciseDate();
        ExerciseActivity.timer.start();
        BaseActivity.isExerciseRunning = false;
        BaseActivity.isExerciseStarted = false;
        totalTime = -1;
        elapsedTime = 0;
        exerciseIndex = 0;
        exerciseList = new ArrayList<>();

        user = com.pttrackershared.utils.SaveUserPreferences.getUser(context);
        if (user == null){
            user = Constants.user;
        }

        SensorManager sensorMngr = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (sensor == null && tvHeartRate != null) {
            tvHeartRate.setText("N/A");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sensorMngr.registerListener(new SensorEventCallback() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    super.onSensorChanged(event);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    super.onAccuracyChanged(sensor, accuracy);
                    tvHeartRate.setText(accuracy);
                }

                @Override
                public void onFlushCompleted(Sensor sensor) {
                    super.onFlushCompleted(sensor);
                }

                @Override
                public void onSensorAdditionalInfo(SensorAdditionalInfo info) {
                    super.onSensorAdditionalInfo(info);
                }
            }, sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE), 1);
        }

    }

    private void prepareExerciseList() {
        if (!ValidatorUtils.IsNullOrEmpty(selectedWorkout.getCircuitList())) {
            for (Circuit circuit : selectedWorkout.getCircuitList()) {
                if (!ValidatorUtils.IsNullOrEmpty(circuit.getExerciseList())) {
                    for (int i = 0; i < circuit.getExerciseList().size(); i++) {
                        ExerciseJsonModel exerciseJsonModel = circuit.getExerciseList().get(i);
                        exerciseList.add(TrainingLog.convertToExercise(exerciseJsonModel));
                    }
                }
            }
        }

    }

    public static ExerciseFragment newInstance() {
        ExerciseFragment fragment = new ExerciseFragment();
        return fragment;
    }

    static ExerciseFragment getInstance() {
        return currentFragment;
    }


    public void showNextExercise() {
        selectedWorkout = BaseActivity.workout;
        if (ValidatorUtils.IsNullOrEmpty(selectedWorkout.getCircuitList())) {
            Toast.makeText(context, R.string.error_message_no_circuit_available, Toast.LENGTH_SHORT).show();
            TrainingAid.getInstance().showLandingFragment();
            return;
        }


        if (exerciseIndex < exerciseList.size()) {
            exercise = exerciseList.get(exerciseIndex);

            if (totalTime >= 0) {
                showGetReadyScreen(exercise.getRestTime(),true);
            }


            if (!ValidatorUtils.IsNullOrEmpty(exercise.getYoutubeLink()) && exercise.getYoutubeLink().matches(".*(youtube|youtu.be).*")) {
                Uri uri = Uri.parse(exercise.getYoutubeLink());
                youtubeVideoId = getVideoid(uri.toString());
            }


            tvExerciseName.setText(exercise.getName());
            tvCategoryName.setText(exercise.getCategory_name());

            if (exercise.getDuration() != 0) {
                if (!countdownMade) {
                    countDownTimer = new CountDownTimerPausable(exercise.getDuration() * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            if (tvSetsCount != null)
                                tvSetsCount.setText("Time: " + TimeUtils.getTimeString((int) l / 1000));
                        }

                        @Override
                        public void onFinish() {
                            countDownTimer = null;
                            if (tvSetsCount != null) {

                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                                if (vibrator != null)
                                    vibrator.vibrate(1000);

                                tvSetsCount.setText("Time: 00:00");
                                completeExercise();
                            }
                        }
                    }.start();

                    countdownMade = true;
                }
            } else if (exercise.getCalories() != 0) {
                tvSetsCount.setText("Calories: " + exercise.getCalories());
            } else {
                tvSetsCount.setText("Reps: " + exercise.getReps());
            }

            if (exercise.getReps() == 1000) {
                tvSetsCount.setText("FAIL");
            } else if (exercise.getReps() == 1001) {
                tvSetsCount.setText("LOW");
            } else if (exercise.getReps() == 1002) {
                tvSetsCount.setText("MEDIUM");
            } else if (exercise.getReps() == 1003) {
                tvSetsCount.setText("HIGH");
            }

            if (exercise.getWeight() > 0 && exercise.getWeight() < 1000) {
                tvWeight.setText(exercise.getWeight() + "");
            } else if (exercise.getWeight() == 1000) {
                tvWeight.setText("CUSTOM");
            } else if (exercise.getWeight() == 1001) {
                tvWeight.setText("LOW");
            } else if (exercise.getWeight() == 1002) {
                tvWeight.setText("MEDIUM");
            } else if (exercise.getWeight() == 1003) {
                tvWeight.setText("HIGH");
            } else {
                tvWeight.setText(exercise.getWeight() + "");
            }

            tvSerialNumber.setText((exerciseIndex + 1) + "/" + exerciseList.size());

//            if (exercise.getYoutubeLink() != null && !exercise.getYoutubeLink().isEmpty()){
//                addYoutubeVideo();
//            }else
            if (!exercise.getImageName().equalsIgnoreCase("")) {
                String imageName = exercise.getImageName();
                User user = SaveUserPreferences.getUser(context);
                if (user.getGender().equalsIgnoreCase("2")) {
                    Picasso.with(context).load(Constants.IMAGE_URL_FEMALE + imageName).
                            error(R.drawable.error_image).into(ivExerciseImage);
                } else {
                    Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                            imageName).
                            error(R.drawable.error_image).into(ivExerciseImage);
                }
            } else {
                ivExerciseImage.setImageResource(R.drawable.error_image);
            }
        } else {
            if (totalTime < 0) {
                Toast.makeText(context, R.string.error_message_no_exercise_completed, Toast.LENGTH_SHORT).show();
            } else {

                // add total time to timeCompleted to currently selected routine and update its info
                selectedWorkout.setStatus(1);
                createTrainingLog();

                if (!exercise.getName().equalsIgnoreCase("Tracking only function")) {
                    List<Workout> workouts = SharedPrefManager.getWorkoutsList(context);
                    for (int i = 0; i < workouts.size(); i++) {
                        Workout workout = workouts.get(i);
                        if (workout.getId() == selectedWorkout.getId()){
                            workout.setStatus(1);
                            SharedPrefManager.setWorkoutsList(context,workouts);
                        }
                    }
                }



                Intent intent = new Intent(getActivity(), ResultActivity.class);
                if (BaseActivity.getInstance().getMinBpm() != -1)
                    intent.putExtra(KEY_MIN_BPM, (BaseActivity.getInstance().getMinBpm()));
                else {
                    intent.putExtra(KEY_MIN_BPM, 0);
                }
                intent.putExtra(KEY_AVG_BPM, (BaseActivity.getInstance().getAvgBpm()));
                intent.putExtra(KEY_MAX_BPM, (BaseActivity.getInstance().getMaxBpm()));
                intent.putExtra(KEY_TOTAL_TIME, totalTime);
                intent.putExtra(KEY_TRAINING_LOG, new Gson().toJson(BaseActivity.getInstance().getCurrentTrainingLog()));

                context.startActivity(intent);
                getActivity().moveTaskToBack(true);
                getActivity().finish();
                // moves LandingFragment
            }
        }
    }

    public String getVideoid(String url){

        String[] firstSplit = url.split("//");

        String[] secondSplit = firstSplit[1].split("/");

        return secondSplit[1];

    }

//    private int mansCalculation() {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(user.getDobString());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String time = tvElapsedTime.getText().toString();
//        String timeSplit[] = time.split(":");
//        int seconds = 0;
//        if (!timeSplit[0].isEmpty() && !timeSplit[1].isEmpty() && !timeSplit[2].isEmpty()) {
//            seconds = Integer.parseInt(timeSplit[0]) * 60 * 60 + Integer.parseInt(timeSplit[1]) * 60 + Integer.parseInt(timeSplit[2]);
//        }
//        int age = getAge(year, month + 1, day);
//
//        if (age == 0) {
//            age = 27;
//        }
//        int userWeight = 80;
//
//        double caloriesBurned = ((age * 0.2017) - (userWeight * 0.09036) + (HeartbeatService.currentValue * 0.6309)) - 55.0969;
//        double separateAdded = (seconds / 4.184);
//        int finalCalories = (int) (caloriesBurned * separateAdded);
//
//        return finalCalories;
//    }
//
//    private int womansCalculation() {
//        User user = SaveUserPreferences.getUser(context);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(user.getDob());
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        String time = tvElapsedTime.getText().toString();
//        String timeSplit[] = time.split(":");
//        int seconds = 0;
//        if (!timeSplit[0].isEmpty() && !timeSplit[1].isEmpty() && !timeSplit[2].isEmpty()) {
//            seconds = Integer.parseInt(timeSplit[0]) * 60 * 60 + Integer.parseInt(timeSplit[1]) * 60 + Integer.parseInt(timeSplit[2]);
//        }
//        int age = getAge(year, month + 1, day);
//
//        if (age == 0) {
//            age = 27;
//        }
//
//        int userWeight = 80;
//
//        double caloriesBurned = ((age * 0.074) - (userWeight * 0.05741) + (HeartbeatService.currentValue * 0.4472) - 20.4022);
//        double separateAdded = (seconds / 4.184);
//        int finalCalories = (int) (caloriesBurned * separateAdded);
//        return finalCalories;
//    }

    private void createTrainingLog() {
        TrainingLog trainingLog = toTrainingLog(selectedWorkout);
        List<TrainingPlan> plan = SharedPrefManager.getTrainingPlanList(context);
        if (plan != null && plan.size() > 0) {
            trainingLog.setClientId(plan.get(0).getClientId());
        }
        trainingLog.setStatus(1);
        trainingLog.setTimeCompleted(totalTime + "");

        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = new Date();
        String dateTime = dateFormat.format(date);
        System.out.println("Current Date Time : " + dateTime);
        trainingLog.setDateCompleted(dateTime);

        if (BaseActivity.getInstance().getMinBpm() != -1) {
            trainingLog.setMinBpm(BaseActivity.getInstance().getMinBpm() + "");
        } else {
            trainingLog.setMinBpm("0");
        }

        if (calories > 0) {
            trainingLog.setCalories(calories + "");
        } else {
            trainingLog.setCalories("N/A");
        }

        trainingLog.setClientId(user.getUserId());

        trainingLog.setMinBpm(BaseActivity.getInstance().getMinBpm() + "");
        trainingLog.setMaxBpm(BaseActivity.getInstance().getMaxBpm() + "");
        trainingLog.setAvgBpm(BaseActivity.getInstance().getAvgBpm() + "");
        trainingLog.setTrainerId(user.getTrainerId());
        BaseActivity.getInstance().setCurrentTrainingLog(trainingLog);
    }
    /**
     * Starts/resumes timer for currently selected exercise
     */
    public void startExercise() {
        ExerciseActivity.timer.start();
        BaseActivity.isExerciseStarted = true;
        BaseActivity.isExerciseRunning = true;
        exercise = exerciseList.get(exerciseIndex);

        if (exercise.getDuration() != 0) {
            tvSetsCount.setText("" + TimeUtils.getTimeString(exercise.getDuration()));
        } else if (exercise.getCalories() != 0) {
            tvSetsCount.setText(exercise.getCalories() + "");
        } else if (exercise.getReps() == 1000) {
            tvSetsCount.setText("FAIL");
        } else if (exercise.getReps() == 1001) {
            tvSetsCount.setText("LOW");
        } else if (exercise.getReps() == 1002) {
            tvSetsCount.setText("MEDIUM");
        } else if (exercise.getReps() == 1003) {
            tvSetsCount.setText("HIGH");
        } else {
            tvSetsCount.setText(exercise.getReps() + "");
        }

        if (exercise.getWeight() > 0 && exercise.getWeight() < 1000) {
            tvWeight.setText(exercise.getWeight() + "");
        } else if (exercise.getWeight() == 1000) {
            tvWeight.setText("CUSTOM");
        } else if (exercise.getWeight() == 1001) {
            tvWeight.setText("LOW");
        } else if (exercise.getWeight() == 1002) {
            tvWeight.setText("MEDIUM");
        } else if (exercise.getWeight() == 1003) {
            tvWeight.setText("HIGH");
        } else {
            tvWeight.setText(exercise.getWeight() + "");
        }

        tvSerialNumber.setText((exerciseIndex + 1) + "/" + exerciseList.size());

        BaseActivity.getInstance().setShouldReadBpm(true);
    }

    /**
     * Add time elapsed for currently running exercise and moves to next exercise info
     */
    public void completeExercise() {
        if(!ExerciseActivity.timer.isPaused())
        ExerciseActivity.timer.pause();

        if (BaseActivity.isExerciseRunning) {
            elapsedTime = elapsedTime + (int) ((new Date().getTime() - BaseActivity.startExerciseTime.getTime()) / 1000);
        }

        BaseActivity.isExerciseRunning = false;
        BaseActivity.isExerciseStarted = true;
        exercise = exerciseList.get(exerciseIndex);

        ArrayList<Exercise>tempArray = new ArrayList<>();

        if (skipped){
            skipped = false;
            boolean skippedI = false;
            int lastPosition = exerciseList.size() - 1;
            for (int i = exerciseIndex; i < exerciseList.size(); i++) {
                if (skippedI){
                    i = i - 1;
                }
                Exercise exercise1 = exerciseList.get(i);
                if (exercise.getName().equals(exercise1.getName())){
                    exerciseList.remove(exercise1);
                    exerciseList.add(lastPosition,exercise1);
                    skippedI = true;
                }else {
                    break;
                }
            }
        }else {
            exerciseIndex++;
        }

        if (tempArray.size() > 0){

            exerciseList.addAll(tempArray);
        }

        saveCalories = SharedPrefManager.getCalories(context);
        saveCalories = saveCalories + calories;
        SharedPrefManager.setCalories(saveCalories, context);
        calories = 0;
//        caloriesCountDownTimer.cancel();
        showNextExercise();
    }

    /**
     * Ignore time elapsed for currently running exercise and moves to next exercise info
     */
    public void cancelCurrentExercise() {
        elapsedTime = 0;
        BaseActivity.isExerciseRunning = false;
        BaseActivity.isExerciseStarted = false;
        if (totalTime == 0) {
            BaseActivity.getInstance().makeHeartRateValuesDefault();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer != null && !countDownTimer.isPaused())
            countDownTimer.pause();
        countdownMade = false;
        currentFragment = this;
    }



    @Override
    public void onResume() {
        super.onResume();

        if (countDownTimer != null)
            countDownTimer.start();
        currentFragment = this;
    }

    public void showGetReadyScreen(int seconds, boolean shouldShowCancel) {
        if (exerciseList != null && exerciseList.size() > exerciseIndex) {
            Intent intent = new Intent(context, NewGetReadyActivity.class);
            intent.putExtra("Index",exerciseIndex);
            intent.putExtra(NewGetReadyActivity.SELECTED_EXERCISE, exerciseList.get(exerciseIndex));
            intent.putExtra(NewGetReadyActivity.COUNTDOWN, seconds);
            intent.putExtra(NewGetReadyActivity.SHOW_CANCEL, shouldShowCancel);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent,GET_READY_REQUEST_CODE);
        }
    }
    //endregion


    //region Callback Methods




//    private void showRestScreen() {
//        if (exerciseList != null && exerciseList.size() > exerciseIndex) {
//            Intent intent = new Intent(context, RestActivity.class);
//            intent.putExtra(RestActivity.SELECTED_EXERCISE, exerciseList.get(exerciseIndex));
//            intent.putExtra(KEY_TRAINING_LOG, new Gson().toJson(BaseActivity.getInstance().getCurrentTrainingLog()));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivityForResult(intent, REST_REQUEST_CODE);
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == GET_READY_REQUEST_CODE) {
                startExercise();
            } else if (requestCode == REST_REQUEST_CODE) {
                startExercise();
            }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent hearRateChangedEvent) {
        tvHeartRate.setText(hearRateChangedEvent.getBpm() + "");
    }
    //endregion

    //region "utility methods"

    public TrainingLog toTrainingLog(Workout workout) {
        TrainingLog trainingLog = new Gson().fromJson(new Gson().toJson(workout), TrainingLog.class);
        return trainingLog;

    }

    public int getAge(int year, int month, int day) {
        Date now = new Date();
        int nowMonth = now.getMonth() + 1;
        int nowYear = now.getYear() + 1900;
        int result = nowYear - year;

        if (month > nowMonth) {
            result--;
        } else if (month == nowMonth) {
            int nowDay = now.getDate();

            if (day > nowDay) {
                result--;
            }
        }
        return result;
    }

    public void createCountDownTimer(){

    }

    @Override
    public void update(Observable observable, Object o) {

    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {


//        if (arg2 != 0) {
//            //Actual work
//            DataHandler.getInstance().setID(arg2);
//            if (!h7 && ((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1]).getName().contains("H7") && DataHandler.getInstance().getReader() == null) {
//
//                Log.i("Main Activity", "Starting h7");
//                DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], this));
//                h7 = true;
//            } else if (!normal && DataHandler.getInstance().getH7() == null) {
//
//                Log.i("Main Activity", "Starting normal");
//                DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[arg2 - 1], this));
//                DataHandler.getInstance().getReader().start();
//                normal = true;
//            }
//            menuBool = true;

        }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

//    public void connectionError() {
//
//        Log.w("Main Activity", "Connection error occured");
//        if (menuBool) {//did not manually tried to disconnect
//            Log.d("Main Activity", "in the app");
//            menuBool = false;
//            final ExerciseFragment ac = this;
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(getContext(), getString(R.string.couldnotconnect), Toast.LENGTH_SHORT).show();
//                    //TextView rpm = (TextView) findViewById(R.id.rpm);
//                    //rpm.setText("0 BMP");
//                    // Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
//                    if (DataHandler.getInstance().getID() < spinner1.getCount())
//                        spinner1.setSelection(DataHandler.getInstance().getID());
//
//                    if (!h7) {
//
//                        Log.w("Main Activity", "starting H7 after error");
//                        DataHandler.getInstance().setReader(null);
//                        DataHandler.getInstance().setH7(new H7ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1],ac ));
//                        h7 = true;
//                    } else if (!normal) {
//                        Log.w("Main Activity", "Starting normal after error");
//                        DataHandler.getInstance().setH7(null);
//                        DataHandler.getInstance().setReader(new ConnectThread((BluetoothDevice) pairedDevices.toArray()[DataHandler.getInstance().getID() - 1], ac));
//                        DataHandler.getInstance().getReader().start();
//                        normal = true;
//                    }
//                }
//            });
//        }
//    }


    /**
     * Update Gui with new value from receiver
     */
//    public void receiveData() {
        //ANALYTIC
        //t.setScreenName("Polar Bluetooth Used");
        //t.send(new HitBuilders.AppViewBuilder().build());

//        runOnUiThread(new Runnable() {
//            public void run() {
        //menuBool=true;
//                tvHeartRate = (TextView)rootView.findViewById(R.id.tv_heart_rate);
//                tvHeartRate.setText(DataHandler.getInstance().getLastValue());
//
//                if (DataHandler.getInstance().getLastIntValue() != 0) {
//                    DataHandler.getInstance().getSeries1().addLast(0, DataHandler.getInstance().getLastIntValue());
//                    if (DataHandler.getInstance().getSeries1().size() > MAX_SIZE)
//                        DataHandler.getInstance().getSeries1().removeFirst();//Prevent graph to overload data.
//                    plot.redraw();
//                }
//
//                TextView min = (TextView) findViewById(R.id.min);
//                min.setText(DataHandler.getInstance().getMin());
//
//                TextView avg = (TextView) findViewById(R.id.avg);
//                avg.setText(DataHandler.getInstance().getAvg());
//
//                TextView max = (TextView) findViewById(R.id.max);
//                max.setText(DataHandler.getInstance().getMax());
//            }
//        });
//    }
//
//}

//    BluetoothAdapter mBluetoothAdapter;
//    List<BluetoothDevice> pairedDevices = new ArrayList<>();
//    boolean menuBool = false; //display or not the disconnect option
//    boolean searchBt = true;

//    boolean h7 = false; //Was the BTLE tested
//    boolean normal = false; //Was the BT tested
//    private Spinner spinner1;


//Verify if device is to old for BTLE
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
//
//            Log.i("Main Activity", "old device H7 disbled");
//            h7 = true;
//        }

//        DataHandler.getInstance().addObserver(this);
//  AdView mAdView = (AdView) findViewById(R.id.adView);

//    public void listBT() {
//        Log.d("Main Activity", "Listing BT elements");
//        if (searchBt) {
//            //Discover bluetooth devices
//            final List<String> list = new ArrayList<>();
//            list.add("");
//            pairedDevices.addAll(mBluetoothAdapter.getBondedDevices());
//            // If there are paired devices
//            if (pairedDevices.size() > 0) {
//                // Loop through paired devices
//                for (BluetoothDevice device : pairedDevices) {
//                    // Add the name and address to an array adapter to show in a ListView
//                    list.add(device.getName() + "\n" + device.getAddress());
//                }
//            }
//            if (!h7) {
//                Log.d("Main Activity", "Listing BTLE elements");
//                final BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
//                    public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
//                        if (!list.contains(device.getName() + "\n" + device.getAddress())) {
//                            Log.d("Main Activity", "Adding " + device.getName());
//                            list.add(device.getName() + "\n" + device.getAddress());
//                            pairedDevices.add(device);
//                        }
//                    }
//                };
//
//
//                Thread scannerBTLE = new Thread() {
//                    public void run() {
//                        Log.d("Main Activity", "Starting scanning for BTLE");
//                        mBluetoothAdapter.startLeScan(leScanCallback);
//                        try {
//                            Thread.sleep(5000);
//                            Log.d("Main Activity", "Stoping scanning for BTLE");
//                            mBluetoothAdapter.stopLeScan(leScanCallback);
//                        } catch (InterruptedException e) {
//                            Log.e("Main Activity", "ERROR: on scanning");
//                        }
//                    }
//                };
//
//                scannerBTLE.start();
//            }
//
//            //Populate drop down
//            //  spinner1 = (Spinner) findViewById(R.id.spinner1);
////            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
////                    android.R.layout.simple_spinner_item, list);
////            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////            spinner1.setOnItemSelectedListener(this);
////            spinner1.setAdapter(dataAdapter);
//
////            if (DataHandler.getInstance().getID() != 0 && DataHandler.getInstance().getID() < spinner1.getCount())
////                spinner1.setSelection(DataHandler.getInstance().getID());
//        }
//    }

//verify if bluetooth device are enabled
//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (DataHandler.getInstance().newValue) {
//            //Verify if bluetooth if activated, if not activate it
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            if (mBluetoothAdapter != null) {
//                if (!mBluetoothAdapter.isEnabled()) {
//                    new AlertDialog.Builder(getContext())
//                            .setTitle(R.string.bluetooth)
//                            .setMessage(R.string.bluetoothOff)
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    mBluetoothAdapter.enable();
//                                    try {
//                                        Thread.sleep(2000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    listBT();
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    searchBt = false;
//                                }
//                            })
//                            .show();
//                } else {
//                    listBT();
//                }
//
//            }
//        }
// Create Graph
//            plot = (XYPlot) findViewById(R.id.dynamicPlot);
//            if (plot.getSeriesSet().size() == 0) {
//                Number[] series1Numbers = {};
//                DataHandler.getInstance().setSeries1(new SimpleXYSeries(Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Heart Rate"));
//            }
//            DataHandler.getInstance().setNewValue(false);
//
//        } else {
//            listBT();
//            plot = (XYPlot) findViewById(R.id.dynamicPlot);
//
//        }
//        //LOAD Graph
//        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.rgb(0, 0, 255), Color.rgb(200, 200, 200), null, null);
//        series1Format.setPointLabelFormatter(new PointLabelFormatter());
//        plot.addSeries(DataHandler.getInstance().getSeries1(), series1Format);
//        plot.setTicksPerRangeLabel(3);
//        plot.getGraphWidget().setDomainLabelOrientation(-45);
//
//
//    public void onStop2() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://org.marco45.polarheartmonitor/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }


