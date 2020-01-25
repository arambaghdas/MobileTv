package com.pttracker.views.screens.fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pttracker.R;
import com.pttracker.controllers.TrainingPlanController;
import com.pttracker.datamanagers.ExerciseImagesDataManager;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.models.eventbus.HearRateChangedEvent;
import com.pttracker.views.screens.activities.GetReadyActivity;
import com.pttracker.views.screens.activities.HomeActivity;
import com.pttracker.views.screens.activities.RestActivity;
import com.pttrackershared.models.Circuit;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.User;
import com.pttrackershared.models.Workout;
import com.pttrackershared.plugins.ResourceUtils;
import com.pttrackershared.plugins.StopWatchUtils;
import com.pttrackershared.plugins.TimeUtils;
import com.pttrackershared.plugins.ValidatorUtils;
import com.pttrackershared.utils.Constants;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Show exercise info for all exercises in all circuits in workout in selected routine
 * Provide option to start, pause, cancel an exercise and saves current exercise and move to next exercise or ResultActivity (if there is not next exercise)
 */

public class ExerciseFragment extends BaseFragment {

    //region View References
    private View rootView;

    @Nullable
    @BindView(R.id.tv_serial_number)
    TextView tvSerialNumber;

    @Nullable
    @BindView(R.id.tv_exercise_name)
    TextView tvExerciseName;

    @Nullable
    @BindView(R.id.tv_sets_count)
    TextView tvSetsCount;

    @Nullable
    @BindView(R.id.tv_weight)
    TextView tvWeight;

    @Nullable
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;

    @Nullable
    @BindView(R.id.tv_sets_count_info)
    TextView tvSetsCountInfo;

    @Nullable
    @BindView(R.id.tv_weight_info)
    TextView tvWeightInfo;

    @Nullable
    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @Nullable
    @BindView(R.id.tv_zone)
    TextView tv_zone;

    @Nullable
    @BindView(R.id.tv_effort)
    TextView tv_Effortzone;

    @Nullable
    @BindView(R.id.iv_exercise_image)
    ImageView ivExerciseImage;

    @Nullable
    @BindView(R.id.tv_elapsed_time)
    TextView tvElapsedTime;

    @Nullable
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;

    @Nullable
    @BindView(R.id.iv_play)
    ImageView ivPlay;

    @Nullable
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @Nullable
    @BindView(R.id.ll_next)
    LinearLayout llNext;

    @Nullable
    @BindView(R.id.ll_play)
    LinearLayout llPlay;

    @Nullable
    @BindView(R.id.ll_cancel)
    LinearLayout llStop;

    @Nullable
    @BindView(R.id.view_exercise_info)
    View viewExerciseInfo;

    @Nullable
    @BindView(R.id.tv_date)
    TextView tvDate;

    @Nullable
    @BindView(R.id.view_time_left)
    View viewLineTimeLeft;

    @Nullable
    @BindView(R.id.view_timer_info)
    View viewTimerInfo;

    @Nullable
    @BindView(R.id.ll_time_left)
    View viewTimeLeft;

    @Nullable
    @BindView(R.id.ll_reps_count)
    View viewRepsCount;

    @Nullable
    @BindView(R.id.layout_reps_weight_tab)
    LinearLayout layout_reps_weight_tab;

    @Nullable
    @BindView(R.id.layout_main)
    LinearLayout layout_main;

    TextView tv_raps;
    TextView tv_weight;

    Button btn_submit;
    Button btn_cancle;

    @Nullable
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    //endregion

    //region Other Variables
    private static final int GET_READY_REQUEST_CODE = 1124;
    private static final int REST_REQUEST_CODE = 1125;
    private Workout selectedWorkout;
    private int exerciseIndex = 0;
    private List<Exercise> exerciseList;
    private boolean isViewAttached = false;
    private Unbinder unbinder;
    private Context context;
    private boolean isExerciseRunning;
    private boolean isExerciseStarted;
    private int totalTime;
    private Date startExerciseTime;
    private int elapsedTime;
    private StopWatchUtils stopWatchUtils;

    final Handler h = new Handler();
    private int timeInSec = 0;
    //this is the timer to perform nextclick after start exercise is triggered
    private Runnable timerTask = new Runnable() {

        @Override
        public void run() {
            if (exerciseList != null && exerciseList.size() > exerciseIndex && tvTimeLeft != null) {
                timeInSec++;
                final int minusSeconds = exerciseList.get(exerciseIndex).getDuration() - timeInSec;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (minusSeconds >= 0) {
                            tvTimeLeft.setText(TimeUtils.getTimeString(minusSeconds));
                            h.postDelayed(timerTask, 1000);
                        } else if (llNext != null) {
                            h.removeCallbacks(timerTask);
                            llNext.performClick();
                        }
                    }
                });
            }
        }
    };
    //endregion


    //region Overridden Methods from BaseFragment Class

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
            if (requestCode == GET_READY_REQUEST_CODE) {
                startExercise();
            } else if (requestCode == REST_REQUEST_CODE) {
                showGetReadyScreen(10, true);
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragament_exercise, container, false);
        this.rootView = rootView;
        context = getActivity();

        final WatchViewStub stub = (WatchViewStub) rootView.findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                initViews();
                initData();
                initListeners();
                postStart();
            }
        });

//        initViews();
//        initData();
//        initListeners();
//        postStart();
        return rootView;
    }

    @Override
    public void onDestroy() {
        h.removeCallbacks(timerTask);
        super.onDestroy();
        isViewAttached = false;
        unbinder.unbind();
    }

    @Override
    public boolean isSwipeable() {
        return false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // make all date to default and show exercise info from currently selected routine from LandingFragment
            selectedWorkout = ((HomeActivity) context).getSelectedWorkout();
            if (selectedWorkout != null)
                if (selectedWorkout.getCircuitList() != null) {
                    initData();
                    prepareExerciseList();
                    showNextExercise();
                } else {
                    Toast.makeText(context, R.string.error_message_no_circuit_available, Toast.LENGTH_SHORT).show();
                    ((HomeActivity) context).showLandingFragment();
                }
        } else {
            if (isExerciseStarted) {
                //cancel currently started exercise when this fragment is hidden
                cancelCurrentExercise();
            }
        }
    }
    //endregion


    //region Performs All Operations Right after Showing View in Fragment
    private void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        isViewAttached = true;
    }

    private void initData() {
        isExerciseRunning = false;
        isExerciseStarted = false;
        elapsedTime = 0;
        totalTime = 0;
        exerciseIndex = 0;
        exerciseList = new ArrayList<>();
        SensorManager sensorMngr = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (sensor == null && tvHeartRate != null) {
            tvHeartRate.setText("N/A");
        }
    }

    private void prepareExerciseList() {
        if (!ValidatorUtils.IsNullOrEmpty(selectedWorkout.getCircuitList())) {
            for (Circuit circuit : selectedWorkout.getCircuitList()) {
                if (!ValidatorUtils.IsNullOrEmpty(circuit.getExerciseList())) {
                    exerciseList.addAll(circuit.getExerciseList());
                }
            }
        }

    }

    private void initListeners() {
    }

    private void postStart() {
        if (viewExerciseInfo != null)
            viewExerciseInfo.setVisibility(View.VISIBLE);
        if (viewTimerInfo != null)
            viewTimerInfo.setVisibility(View.GONE);
    }
    //endregion

    //region Business Logic Specific to this Fragment

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ExerciseFragment newInstance() {
        ExerciseFragment fragment = new ExerciseFragment();
        return fragment;
    }

    /**
     * Moves to next exercise from next circuit
     * If next circuit is null (means exercise for all circuits are completed), moves to LandingFragment
     */
    private void showNextExercise() {
        if (!isViewAttached) {
            return;
        }
        if (exerciseIndex < exerciseList.size()) {
            showDialogForGetRapsWeight(exerciseList.get(exerciseIndex));
        } else {
            showData(0, 0, "");
        }

    }

    void showData(int enterrapsValue, int enterWeightValue, String btnClicked) {
        {
            if (ValidatorUtils.IsNullOrEmpty(selectedWorkout.getCircuitList())) {
                Toast.makeText(context, R.string.error_message_no_circuit_available, Toast.LENGTH_SHORT).show();
                ((HomeActivity) context).showLandingFragment();
                return;
            }

            if (exerciseIndex < exerciseList.size()) {
                Exercise exercise = exerciseList.get(exerciseIndex);
                exercise.setReps(enterrapsValue);
                exercise.setWeight(enterWeightValue);
                if (btnClicked.equalsIgnoreCase("cancel")) {
                    exercise.setCancel("1");
                } else {
                    exercise.setCancel("");
                }


                if (totalTime != 0) {
                    if (exercise.getRestTime() > 10)
                        showRestScreen();
                    else
                        showGetReadyScreen(10, true);
                }
                tvExerciseName.setText(exercise.getName());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                tvDate.setText(dateFormat.format(new Date()));

//                Log.d("checknitin", "exerciseData:: " + exercise.getName());
//                Log.d("checknitin", "enterrapsValue:: " + enterrapsValue);
//                Log.d("checknitin", "enterWeightValue:: " + enterWeightValue);
                if (exercise.getDuration() != 0)
                    tvSetsCount.setText("Time: " + TimeUtils.getTimeString(exercise.getDuration()));
                else if (exercise.getCalories() != 0) {
                    tvSetsCount.setText("Calories: " + exercise.getCalories());
                } else {
                    tvSetsCount.setText("Reps: " + exercise.getReps());
                }
//                if (exercise.getWeight() > 0)
//                    tvWeight.setText("Weight: " + exercise.getWeight() + " KG");
//                else
//                    tvWeight.setText("Weight: - ");
//
                if (exercise.getWeight() == 0) {
                    tvWeight.setText("Custom");
                    tvWeight.setBackgroundColor(context.getResources().getColor(R.color.purple));
                } else if (exercise.getWeight() == 1) {
                    tvWeight.setText("Low");
                    tvWeight.setBackgroundColor(context.getResources().getColor(R.color.green));
                } else if (exercise.getWeight() == 2) {
                    tvWeight.setText("Medium");
                    tvWeight.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                } else if (exercise.getWeight() == 3) {
                    tvWeight.setText("High");
                    tvWeight.setBackgroundColor(context.getResources().getColor(R.color.red));
                } else if (exercise.getWeight() > 3) {
                    tvWeight.setBackgroundColor(context.getResources().getColor(R.color.grey_txt));
                    tvWeight.setText(exercise.getWeight() + " KG");
                }
                if (exercise.getDuration() != 0) {
                    tvSetsCountInfo.setText("Time " + TimeUtils.getTimeString(exercise.getDuration()));
                    tvTimeLeft.setText(TimeUtils.getTimeString(exercise.getDuration()));
                } else {
                    tvSetsCountInfo.setText(exercise.getReps() + " Reps");
                }


//                if (exercise.getWeight() > 0)
//                    tvWeightInfo.setText(exercise.getWeight() + " KG");
//                else
//                    tvWeightInfo.setText(" - ");

                if (exercise.getWeight() == 0) {
                    tvWeightInfo.setText("Custom");
                    tvWeightInfo.setBackgroundColor(context.getResources().getColor(R.color.purple));
                } else if (exercise.getWeight() == 1) {
                    tvWeightInfo.setText("Low");
                    tvWeightInfo.setBackgroundColor(context.getResources().getColor(R.color.green));
                } else if (exercise.getWeight() == 2) {
                    tvWeightInfo.setText("Medium");
                    tvWeightInfo.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                } else if (exercise.getWeight() == 3) {
                    tvWeightInfo.setText("High");
                    tvWeightInfo.setBackgroundColor(context.getResources().getColor(R.color.red));
                } else if (exercise.getWeight() > 3) {
                    tvWeightInfo.setBackgroundColor(context.getResources().getColor(R.color.grey_txt));
                    tvWeightInfo.setText(exercise.getWeight() + " KG");
                }

                if(tvDistance!=null){
                    tvDistance.setText(""+exercise.getDistance());
                }

                tv_Effortzone.setText("E.Z - "+exercise.getEffort_zone_show());

                tv_zone.setText("Zone " + calculateZone(tv_zone));
                exercise.setEffort_zone("" + calculateZone(tv_zone));



                tvSerialNumber.setText((exerciseIndex + 1) + "/" + exerciseList.size());
                if (ivExerciseImage != null && ExerciseImagesDataManager.getInstance(context).hasImage(exercise.getExerciseId())) {
                    ivExerciseImage.setImageBitmap(ExerciseImagesDataManager.getInstance(context).
                            getExerciseImage(exercise.getExerciseId()).getImageBitmap());
                } else {
//                    ivExerciseImage.setImageResource(R.drawable.error_image);
                    String imageName = exercise.getImageName();
                    User user = UserAccountDataManager.getInstance(context).getCurrentUser();
                    if (user.getGender().equalsIgnoreCase("2")) {
                        Picasso.with(context).load(Constants.IMAGE_URL_FEMALE +
                                imageName).
                                error(R.drawable.error_image).into(ivExerciseImage);
                    } else {
                        Picasso.with(context).load(Constants.IMAGE_URL_MALE +
                                imageName).
                                error(R.drawable.error_image).into(ivExerciseImage);
                    }
                }
            } else {
                if (totalTime == 0) {
                    Toast.makeText(context, R.string.error_message_no_exercise_completed, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("checknitin", "totalTime:: " + totalTime);
                    // add total time to timeCompleted to currently selected routine and update its info
                    selectedWorkout.setStatus(1);
                    createTrainingLog();
                    TrainingPlanController.getInstance(context).updateWorkout(selectedWorkout);
                    // moves LandingFragment
                    ((HomeActivity) context).showLandingFragment(totalTime, selectedWorkout.getId());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (((HomeActivity) context).resultsPublished)
            showDefaultUI();
    }

    private void createTrainingLog() {
        TrainingLog trainingLog = toTrainingLog(selectedWorkout);
        List<TrainingPlan> plan = TrainingPlanDataManager.getInstance(context).getStoredTrainingPlans();
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

        if (((HomeActivity) context).getMinBpm() != -1)
            trainingLog.setMinBpm(((HomeActivity) context).getMinBpm() + "");
        else
            trainingLog.setMinBpm("0");

        Log.d("checknitin", "getMaxBpm:: " + ((HomeActivity) context).getMaxBpm());
        trainingLog.setMaxBpm(((HomeActivity) context).getMaxBpm() + "");
        trainingLog.setAvgBpm(((HomeActivity) context).getAvgBpm() + "");
        ((HomeActivity) getActivity()).setCurrentTrainingLog(trainingLog);
    }

    /**
     * Starts/resumes timer for currently selected exercise
     */
    private void startExercise() {
        isExerciseStarted = true;
        isExerciseRunning = true;
        ((HomeActivity) context).setShouldReadBpm(true);
        startExerciseTime = new Date();
        if (stopWatchUtils != null) {
            stopWatchUtils.stop();
            stopWatchUtils = null;
        }
        stopWatchUtils = new StopWatchUtils(startExerciseTime, tvElapsedTime, 3, totalTime + elapsedTime);
        stopWatchUtils.start();
        showStartedUI();
    }

    /**
     * Pause timer, calculate time elapsed and change pause icon to play icon
     */
    private void pauseExercise() {
        h.removeCallbacks(timerTask);
        isExerciseRunning = false;
        elapsedTime = elapsedTime + (int) ((new Date().getTime() - startExerciseTime.getTime()) / 1000);
        stopWatchUtils.stop();
        ivPlay.setImageDrawable(ResourceUtils.getDrawableById(context, R.drawable.ic_play));
    }

    /**
     * Add time elapsed for currently running exercise and moves to next exercise info
     */
    private void completeExercise() {
        h.removeCallbacks(timerTask);
        if (isExerciseRunning) {
            elapsedTime = elapsedTime + (int) ((new Date().getTime() - startExerciseTime.getTime()) / 1000);
        }
        totalTime = totalTime + elapsedTime + 1;
        elapsedTime = 0;
        isExerciseRunning = false;
        isExerciseStarted = false;
        stopWatchUtils.reset();
        timeInSec = 0;
        exerciseIndex++;
        showNextExercise();
    }

    /**
     * Ignore time elapsed for currently running exercise and moves to next exercise info
     */
    private void cancelCurrentExercise() {
        elapsedTime = 0;
        isExerciseRunning = false;
        isExerciseStarted = false;
        stopWatchUtils.reset();
        if (totalTime == 0) {
            ((HomeActivity) context).setShouldReadBpm(false);
            ((HomeActivity) context).makeHeartRateValuesDefault();
            totalTime++;
        }
    }

    /**
     * Changes UI to show currently selected exercise info
     */
    private void showDefaultUI() {
        initData();
        timeInSec = 0;
        h.removeCallbacks(timerTask);
        if (!isViewAttached) {
            return;
        }

        if (totalTime == 0) {
            ((HomeActivity) context).setSwipeable(true, true);
        }

        if (llPlay.getLayoutParams() instanceof LinearLayout.LayoutParams)
            ((LinearLayout.LayoutParams) llPlay.getLayoutParams()).weight = 2.5f;
        ivPlay.setImageDrawable(ResourceUtils.getDrawableById(context, R.drawable.ic_play));
        llNext.setVisibility(View.GONE);
        ivNext.setVisibility(View.GONE);
        llStop.setVisibility(View.VISIBLE);
        ivCancel.setVisibility(View.VISIBLE);
        viewExerciseInfo.setVisibility(View.VISIBLE);
        viewTimerInfo.setVisibility(View.GONE);
        tvDate.setVisibility(View.GONE);
    }


    /**
     * Changes UI to show running timer for currently selected exercise
     */
    private void showStartedUI() {
        if (!isViewAttached) {
            return;
        }

        ((HomeActivity) context).setSwipeable(false, false);
        if (llPlay.getLayoutParams() instanceof LinearLayout.LayoutParams)
            ((LinearLayout.LayoutParams) llPlay.getLayoutParams()).weight = 1;
        ivPlay.setImageDrawable(ResourceUtils.getDrawableById(context, R.drawable.ic_pause));
        ivNext.setVisibility(View.VISIBLE);
        llNext.setVisibility(View.VISIBLE);
        ivCancel.setVisibility(View.VISIBLE);
        llStop.setVisibility(View.VISIBLE);
        viewExerciseInfo.setVisibility(View.GONE);
        viewTimerInfo.setVisibility(View.VISIBLE);

        if (exerciseList.get(exerciseIndex).getDuration() != 0) {
            viewTimeLeft.setVisibility(View.VISIBLE);
            viewLineTimeLeft.setVisibility(View.VISIBLE);
            viewRepsCount.setVisibility(View.GONE);
            h.removeCallbacks(timerTask);
            h.postDelayed(timerTask, 1000);
        } else {
            viewTimeLeft.setVisibility(View.GONE);
            viewLineTimeLeft.setVisibility(View.GONE);
            viewRepsCount.setVisibility(View.VISIBLE);
        }
        tvDate.setVisibility(View.GONE);
    }
    //endregion

    @Override
    public void onPause() {
        super.onPause();
//        showDefaultUI();
    }


    //region Callback Methods

    @Optional
    @OnClick(R.id.iv_cancel)
    public void onCancelClicked(View view) {

        if (isExerciseStarted) {
            cancelCurrentExercise();
        }
        exerciseIndex++;
        showNextExercise();
    }

    @Optional
    @OnClick(R.id.ll_play)
    public void onPlayClicked(View view) {
        totalTime++;
        if (isExerciseRunning) {
            pauseExercise();
        } else if (isExerciseStarted) {
            startExercise();
        } else {
            showGetReadyScreen(3, false);
        }
    }

    private void showGetReadyScreen(int seconds, boolean shouldShowCancel) {
        if (exerciseList != null && exerciseList.size() > exerciseIndex) {
            Intent intent = new Intent(context, GetReadyActivity.class);
            GetReadyActivity.selectedExercise=exerciseList.get(exerciseIndex);
//            intent.putExtra(GetReadyActivity.SELECTED_EXERCISE, exerciseList.get(exerciseIndex));
            intent.putExtra(GetReadyActivity.COUNTDOWN, seconds);
//            intent.putExtra(GetReadyActivity.SHOW_CANCEL, shouldShowCancel);
            GetReadyActivity.showCancel=shouldShowCancel;
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, GET_READY_REQUEST_CODE);
        }
    }

    private void showRestScreen() {
        if (exerciseList != null && exerciseList.size() > exerciseIndex) {
            Intent intent = new Intent(context, RestActivity.class);
            RestActivity.selectedExercise=exerciseList.get(exerciseIndex);
//            intent.putExtra(RestActivity.SELECTED_EXERCISE, exerciseList.get(exerciseIndex));
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivityForResult(intent, REST_REQUEST_CODE);
        }
    }

    @Optional
    @OnClick(R.id.ll_next)
    public void onNextClicked(View view) {
        completeExercise();
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

    void showDialogForGetRapsWeight(Exercise exercise) {

//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);

        layout_reps_weight_tab.setVisibility(View.VISIBLE);
        layout_main.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.upgrade_weightraps_dialog, null);
        View view =  inflater.inflate(R.layout.upgrade_weightraps_dialog, layout_reps_weight_tab);

//        alertDialogBuilder.setView(view);
//        alertDialogBuilder.setCancelable(true);
//        final AlertDialog dialog = alertDialogBuilder.create();
//        dialog.show();

        tv_raps = (TextView) view.findViewById(R.id.tv_raps);
        tv_weight = (TextView) view.findViewById(R.id.tv_weight);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_cancle = (Button) view.findViewById(R.id.btn_cencel);

        TextView tv_exercise_name = (TextView) view.findViewById(R.id.tv_exercise_name);
        tv_exercise_name.setText(exercise.getName());

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(Integer.valueOf(tv_raps.getText().toString()), Integer.valueOf(tv_weight.getText().toString()), "submit");
//                dialog.dismiss();
                layout_reps_weight_tab.setVisibility(View.GONE);
                layout_main.setVisibility(View.VISIBLE);
            }
        });

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData(0, 0, "cancel");
//                dialog.dismiss();
                layout_reps_weight_tab.setVisibility(View.GONE);
                layout_main.setVisibility(View.VISIBLE);
            }
        });


        final SeekBar raps_seekBar = (SeekBar) view.findViewById(R.id.seekbar_raps);
        raps_seekBar.setProgress(0);
        raps_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                tv_raps.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar weight_seekbar = (SeekBar) view.findViewById(R.id.seekbar_weight);
        weight_seekbar.setProgress(0);
        weight_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                tv_weight.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    int calculateZone(TextView tv_zone) {
        int zone = 1;
        try {
            User user = UserAccountDataManager.getInstance(context).getCurrentUser();
            int Age = 1;
            try {
                Age = TimeUtils.getDiffYears(user.getDobString(), TimeUtils.getCurrentDate());
            } catch (Exception e) {
                e.printStackTrace();
            }

        int zoneValuePersent = (((HomeActivity) context).getMaxBpm() * 100) / (220 - Age);
//            int zoneValuePersent = (186 * 100) / (220 - 20);
            Log.d("checkNitin", "zoneValuePersent:" + zoneValuePersent);

            if (zoneValuePersent > 50 && zoneValuePersent < 60) {
                zone = 1;
                tv_zone.setBackgroundColor(getResources().getColor(R.color.light_grey));
            } else if (zoneValuePersent > 60 && zoneValuePersent < 70) {
                zone = 2;
                tv_zone.setBackgroundColor(getResources().getColor(R.color.blue));
            } else if (zoneValuePersent > 70 && zoneValuePersent < 80) {
                zone = 3;
                tv_zone.setBackgroundColor(getResources().getColor(R.color.green));
            } else if (zoneValuePersent > 80 && zoneValuePersent < 90) {
                zone = 4;
                tv_zone.setBackgroundColor(getResources().getColor(R.color.yellow));
            } else if (zoneValuePersent > 90) {
                zone = 5;
                tv_zone.setBackgroundColor(getResources().getColor(R.color.red));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zone;
    }

}
