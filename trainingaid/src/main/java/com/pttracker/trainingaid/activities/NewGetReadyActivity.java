package com.pttracker.trainingaid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.eventbus.HearRateChangedEvent;
import com.pttracker.trainingaid.fragments.ExerciseFragment;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class NewGetReadyActivity extends BaseActivity {

    ImageView ivExerciseImage;
    TextView tvSetsCount;
    TextView tvWeight;
    TextView tvExerciseName;
    TextView tvCategoryName;
    CircularProgressBar circularProgressBar;
    TextView tvGo;
    TextView tvCountDown;
    TextView tvHeartRate;
    TextView tvStartsIn;
    LinearLayout heartRateLayout;
    RelativeLayout relativeLayoutCancel;
    private Workout selectedWorkout;
    public static ImageView backButton;
    //endregion

    //region Other Variables
    public static final String SELECTED_EXERCISE = "selected_exercise";
    public static final String COUNTDOWN = "countdown";
    public static final String SHOW_CANCEL = "showCancel";

    private long startTime = 0;
    private long waitTime = 1000 * 11;
    private boolean showCancel = true;
    private Exercise selectedExercise;
    private Handler handler;
    private View rootView;
    private Context context;
    private int exerciseIndex;
    public int exercisePosition;
    private List<Exercise> exerciseList;
    boolean lastTickCalled = false;
    static NewGetReadyActivity currentActivity;
    private PowerManager.WakeLock wl;
    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class
    public static NewGetReadyActivity getInstance() {
        return currentActivity;
    }

    public static void finishNewGetReadyActivity () {
         currentActivity.finish();
    }

    private void initCollectionAdapters() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentActivity = this;
        exerciseList = ExerciseFragment.exerciseList;
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentActivity = this;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacks(goRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_new_get_ready);
        context = this;
        currentActivity = this;

        backButton = (ImageView) findViewById(R.id.backButton);

        ButterKnife.bind(NewGetReadyActivity.this);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "myapp:my_wake_lock_for_timer");

    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initListeners();
        handler = new Handler();
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void initData() {
        waitTime = (BaseActivity.countDown + 1) * 1000;
        exerciseIndex = ExerciseFragment.exerciseIndex;

        if (exerciseIndex == 0) {
            selectedExercise = TrainingLog.convertToExercise(BaseActivity.workout.getCircuitList().get(0).getExerciseList().get(0));
        }else {
            selectedExercise = ExerciseFragment.exerciseList.get(exerciseIndex);
        }
        handler = new Handler();

        exerciseList = new ArrayList<>();
        if (ExerciseFragment.exerciseList != null){
            exerciseList = ExerciseFragment.exerciseList;
        }

    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
    @Override
    public void postStart() {
        super.postStart();
        initCollectionAdapters();
        initData();
        initViews();

        selectedWorkout = BaseActivity.workout;

        if (selectedWorkout != null)
            if (selectedWorkout.getCircuitList() != null) {
                prepareExerciseList();
                prepareOther();
            }

//        selectedExercise = getIntent().getExtras().getParcelable(SELECTED_EXERCISE);
        startTime = System.currentTimeMillis();
        if (ExerciseActivity.getInstance() == null){
            selectedExercise.setRestTime(10);
        }else {
            if (ExerciseFragment.totalTime == 0){
                selectedExercise.setRestTime(10);
            }
        }
        if(selectedExercise != null){
            startTime = new Date().getTime();
            if (selectedExercise.getRestTime() <= 10) {
                waitTime = 11000;//11 seconds default rest time
            } else {
                waitTime = selectedExercise.getRestTime() * 1000;
                heartRateLayout.setVisibility(View.GONE);
            }
            handler.post(waitRunnable);
        } else {
            initData();
            handler.post(waitRunnable);
        }

        SensorManager sensorMngr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (sensor == null) {
            tvHeartRate.setText("N/A");
        }
        showExerciseInfo();

//        countDownTimer = new CountDownTimer(waitTime,1000) {
//            @Override
//            public void onTick(long l) {
//                long targetTime = startTime + waitTime;
//                int diff = (int) (targetTime - System.currentTimeMillis()) / 1000;
//                if (diff > 0) {
//                    tvCountDown.setText(Integer.toString(diff));
//                }
//
//                circularProgressBar.setProgressMax(waitTime / 1000);
//                circularProgressBar.setProgress(diff);
//
//                if (diff == 0){
//                    lastTickCalled = true;
//                    countDownTimer.cancel();
//                    Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
//                    if (vibrator != null) {
//                        vibrator.vibrate(1000);
//                    }
//                    showGoView();
//                    handler.postDelayed(goRunnable, 1000);
//                }
//            }
//
//            @Override
//            public void onFinish() {
//                if (!lastTickCalled) {
//                    circularProgressBar.setProgress(0);
//                    countDownTimer.cancel();
//                    Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
//                    if (vibrator != null) {
//                        vibrator.vibrate(1000);
//                    }
//                    showGoView();
//                    handler.postDelayed(goRunnable, 1000);
//                }
//            }
//        };
//        countDownTimer.start();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to leave the workout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                handler.removeCallbacks(waitRunnable);
                                ExerciseFragment.elapsedTime = 0;
                                BaseActivity.isExerciseRunning = false;
                                BaseActivity.isExerciseStarted = false;
                                BaseActivity.startExerciseTime = new Date();
                                ExerciseFragment.totalTime = 0;
                                if (ExerciseActivity.getInstance() != null){
                                    if (ExerciseActivity.timer != null){
                                        ExerciseActivity.timer.cancel();
                                        ExerciseActivity.timer = null;
                                    }
                                    ExerciseActivity.finishExerciseActivity();
                                }

                                finish();
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

        relativeLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                if (ExerciseFragment.exerciseIndex > 0){
                    setResult(Activity.RESULT_OK);
                }else {
                    startActivity(intent);
                }
                finish();
                handler.removeCallbacks(waitRunnable);
            }
        });



    }

    private Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            long targetTime = startTime + waitTime;
            int diff = (int) (targetTime - System.currentTimeMillis()) / 1000;
            circularProgressBar.setProgressMax(waitTime / 1000);
            circularProgressBar.setProgress(diff);
            tvCountDown.setText(diff + "");
            handler.postDelayed(waitRunnable, 500);

            if (diff == 0){
                vibrate();
                showGoView();
                handler.postDelayed(goRunnable, 1000);
            }

        }
    };

    public void initViews() {
        ivExerciseImage = (ImageView)findViewById(R.id.iv_exercise_image);
        tvSetsCount = (TextView) findViewById(R.id.tv_sets_count);
        tvWeight = (TextView) findViewById(R.id.tv_weight);
        tvExerciseName = (TextView) findViewById(R.id.tv_exercise_name);
        tvCategoryName = (TextView) findViewById(R.id.tv_category_name);
        circularProgressBar = (CircularProgressBar) findViewById(R.id.circularprogress);
        tvGo = (TextView) findViewById(R.id.tv_go);
        tvCountDown = (TextView) findViewById(R.id.tv_count_down);
        tvHeartRate = (TextView) findViewById(R.id.tv_heart_rate);
        tvStartsIn = (TextView) findViewById(R.id.tvstartsin);
        heartRateLayout = (LinearLayout) findViewById(R.id.heartRateLayout);
        relativeLayoutCancel = (RelativeLayout) findViewById(R.id.rl_cancel);
    }

    private void showExerciseInfo() {
        setIvExerciseImage();

        tvGo.setVisibility(View.GONE);
    }

    private Runnable goRunnable = new Runnable() {
        @Override
        public void run() {
            goBack();
        }
    };


    public void goBack() {
//        SharedPreferences sharedPref = getSharedPreferences("passImageId", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        editor.putString("FROM", "GetReady");
//        editor.apply();

        Intent intent = new Intent(context, ExerciseActivity.class);
        if (ExerciseFragment.totalTime != -1){
            setResult(Activity.RESULT_OK);
            finish();
        }else {
            startActivity(intent);
            finish();

        }
    }

    private void showGoView() {
        handler.removeCallbacks(waitRunnable);
        tvCountDown.setVisibility(View.GONE);
        tvStartsIn.setVisibility(View.GONE);
        heartRateLayout.setVisibility(View.GONE);
        relativeLayoutCancel.setVisibility(View.INVISIBLE);
        tvGo.setBackground(context.getResources().getDrawable(R.drawable.go_circle));
        tvGo.setVisibility(View.VISIBLE);
    }

    private void setIvExerciseImage() {
        Exercise exercise = exerciseList.get(exerciseIndex);

        if (!exercise.getImageName().equalsIgnoreCase("")) {
            String imageName = exercise.getImageName();
            User user = SaveUserPreferences.getUser(context);
            if (user.getGender().equalsIgnoreCase("2")) {
                Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            } else {
                Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                        imageName).
                        error(R.drawable.error_image).into(ivExerciseImage);
            }
        } else {
            ivExerciseImage.setImageResource(R.drawable.error_image);
        }
    }


    private void prepareOther(){

        if (exerciseIndex < exerciseList.size()) {

            Exercise exercise = exerciseList.get(exerciseIndex);

            tvExerciseName.setText(exercise.getName());
            tvCategoryName.setText(exercise.getCategory_name());

            if (exercise.getDuration() != 0)
                tvSetsCount.setText("Time: " + TimeUtils.getTimeString(exercise.getDuration()));
            else if (exercise.getCalories() != 0) {
                tvSetsCount.setText("Calories: " + exercise.getCalories());
            } else {
                tvSetsCount.setText("Reps: " + exercise.getReps());
            }
            if (exercise.getWeight() > 0)
                tvWeight.setText("weight: " + exercise.getWeight() + " KG");
            else
                tvWeight.setText("weight: - ");

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

            setIvExerciseImage();
        }
    }

    private void prepareExerciseList() {
        if (ExerciseFragment.exerciseList != null){
            exerciseList = ExerciseFragment.exerciseList;
        }else {
            if(!ValidatorUtils.IsNullOrEmpty(selectedWorkout.getCircuitList())) {
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

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHeartRateChangedEvent(HearRateChangedEvent hearRateChangedEvent) {
        tvHeartRate.setText(hearRateChangedEvent.getBpm() + "");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null)
            vibrator.vibrate(1000);
    }


}
