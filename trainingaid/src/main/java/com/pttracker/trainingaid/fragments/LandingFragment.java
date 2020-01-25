package com.pttracker.trainingaid.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pttracker.trainingaid.Class.TrainingAid;
import com.pttracker.trainingaid.Class.TrainingAidFragment;
import com.pttracker.trainingaid.R;
import com.pttracker.trainingaid.activities.BaseActivity;
import com.pttracker.trainingaid.activities.NewGetReadyActivity;
import com.pttracker.trainingaid.adapters.ExercisesRecyclerViewAdapter;
import com.pttracker.trainingaid.eventbus.RoutineDataFetchingEvent;
import com.pttracker.trainingaid.eventbus.RoutineDataReceivedEvent;
import com.pttracker.trainingaid.utils.ApiCallsHandler;
import com.pttracker.trainingaid.utils.ListenersHandler;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanViewJsonModel;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.plugins.TimeUtils;
import com.pttrackershared.utils.AppUtils;
import com.pttrackershared.utils.SaveUserPreferences;
import com.pttrackershared.utils.TrainingPlanPreferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * LandingFragment shown next workout for which exercise can be started. Handle following stats for workout
 * 1. If routine data is not synced
 * 2. If there is no next workout available (currently shown workout is the last workout)
 * 3. All workout are completed.
 */

public class LandingFragment extends BaseFragment {

    //region View References
    private View rootView;
    TrainingAidFragment trainingAidFragment;

    public TextView tvWorkoutName,tvDuration, tvLastSynced,btnStartWorkout,numOfExercises;
    public static TextView btnChangeWorkout;
    public RecyclerView exercisesNameRv;
    public ProgressBar progressBar;
    public ExercisesRecyclerViewAdapter adapter;
    TrainingPlan trainingPlanAdd;
    User user;
    //endregion

    //region Other Variables
    private Context context;
    private int currentWorkoutIndex = -1;
    private Unbinder unbinder;
    private boolean isViewAttached = false;
    private boolean isSynced = false;
    private static LandingFragment fragment;
    TrainingPlanViewJsonModel trainingPlanViewJsonModel;
    //endregion

    static LandingFragment getInstance() {
        return fragment;
    }


    //region Overridden Methods from BaseFragment Class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragament_landing2, container, false);
        this.rootView = rootView;
        trainingAidFragment = new TrainingAidFragment();
        setHasOptionsMenu(true);

        loadTrainingInfo();

        context = getActivity();
        tvWorkoutName = (TextView) rootView.findViewById(R.id.tv_workout_name);
        tvDuration = (TextView) rootView.findViewById(R.id.tv_duration);
        btnChangeWorkout = (TextView) rootView.findViewById(R.id.btn_change_workout);
        btnStartWorkout = (TextView) rootView.findViewById(R.id.btn_start_workout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        numOfExercises = (TextView) rootView.findViewById(R.id.numOfExercises);
        exercisesNameRv = (RecyclerView) rootView.findViewById(R.id.exercisesNameRv);
        trainingAidFragment = new TrainingAidFragment();

        SensorManager sensorMngr = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorMngr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if (sensor == null) {
            BaseActivity.heartRate = "N/A";}
//        } else if (sensor!=null){
//            BaseActivity.heartRate = handler.getLastValue();
//        }


        initViews();
        initData();
        initListeners();
        postStart();

        onChangeWorkClicked();
        onStartWorkClicked();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);
        MenuItem add_client=menu.findItem(R.id.action_add_client);
        add_client.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        isViewAttached = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        ExerciseFragment.exerciseIndex = 0;
        showNextWorkout(false);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isSynced) {
            initData();
            showNextWorkout(false);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity activity = getActivity();
            Workout selectedWorkout = null;
            if (TrainingAid.getInstance() != null) {
                selectedWorkout = BaseActivity.workout;
                setUpRecyclerView();
            }
            if (selectedWorkout != null) {
                initData();
                showNextWorkout(false);
            }
        }
    }

    //endregion

    //region Performs All Operations Right after Showing View in Fragment
    public void initViews() {
        unbinder = ButterKnife.bind(this, rootView);
        isViewAttached = true;
    }

    public void initData() {

        user = SaveUserPreferences.getUser(context);
        isSynced = false;
        currentWorkoutIndex = -1;

        trainingPlanAdd = TrainingPlanPreferences.getPlan(context);


        if (trainingPlanAdd == null){
            trainingPlanAdd = new TrainingPlan();
            TrainingPlanPreferences.addPlan(trainingPlanAdd,getActivity());
        }

        Workout workout = createWorkout();
        if (trainingPlanAdd.getWorkoutList() == null)
            trainingPlanAdd.setWorkoutList(new ArrayList<Workout>());

        trainingPlanAdd.getWorkoutList().add(workout);

    }

    public void initListeners() {
    }


    public void postStart() {
        showNextWorkout(false);
//        setLastSynced();
    }

    private void loadTrainingInfo() {
        ApiCallsHandler.getTrainingPlans(getActivity(), new ListenersHandler.OnGetTrainingPlansCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanListView) {
                if (trainingPlanListView.size() > 0){
                    List<TrainingPlan> trainingPlanList = AppUtils.saveTrainingPlans(getActivity(),trainingPlanListView);
                    TrainingPlanPreferences.addPlan(trainingPlanList.get(0),getActivity());
                }

            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                Toast.makeText(getActivity(), ""+errorMessage, Toast.LENGTH_SHORT).show();

            }
        });
    }
    //endregion

    //region Business Logic Specific to this Fragment

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LandingFragment newInstance() {
       return fragment = new LandingFragment();
    }

    /**
     * Shows next workout if available and handle all alternate scenarios
     */
    private void showNextWorkout(boolean showMessage) {
            if (trainingPlanAdd.getWorkoutList() != null){
                if (!trainingPlanAdd.getWorkoutList().isEmpty())
                    showDatePassedWorkouts(showMessage);
            } else {
                isSynced = false;
//                showDisabledInfo("Training plans not synced yet");
            }

            setUpRecyclerView();

    }

    private void showDatePassedWorkouts(boolean showMessage) {
        refreshWorkouts();
        Workout workout = getNextPendingWorkout();
        if (workout != null) {
            showWorkoutInfo(workout);
            BaseActivity.workout = workout;
            if (workout.getCircuitList() != null && hasExercises(workout)) {
                showEnabledInfo("View Exercises");
            } else {
                showEnabledInfo("No Exercise Available");
            }
        } else {
            if (showMessage)
                Toast.makeText(context, R.string.error_message_no_next_workout, Toast.LENGTH_SHORT).show();
        }

        isSynced = true;
    }

    private boolean haveFinishDatePassed(Date finishDate) {
        Date currentDate = new Date();
        if (currentDate.getTime() > finishDate.getTime())
            return true;
        else
            return false;
    }

    private Workout getNextPendingWorkout() {
        Workout nextWorkout = null;

        if (trainingPlanAdd.getWorkoutList() != null) {
            int j = 0;
            if (currentWorkoutIndex < trainingPlanAdd.getWorkoutList().size() - 1)
                j = currentWorkoutIndex + 1;

            while (j < trainingPlanAdd.getWorkoutList().size()) {
                Workout workout = trainingPlanAdd.getWorkoutList().get(j);
                if (workout.getStatus() == 0 || workout.getStatus() == 1 /*&& workout.getCircuitList().size() != 0*/) {
                    nextWorkout = workout;
                    currentWorkoutIndex = j;

                    BaseActivity.workout = nextWorkout;
                    break;
                } else {
                    if (j + 1 < trainingPlanAdd.getWorkoutList().size())
                        j = j + 1;
                    else
                        j = 0;
                }
            }
        }
        return nextWorkout;
    }

    /**
     * Refresh status of workouts as UNDONE
     */
    private Workout refreshWorkouts() {
        Workout nextWorkout = null;
        if (trainingPlanAdd.getWorkoutList() != null) {
            int j = 0;
            while (j < trainingPlanAdd.getWorkoutList().size()) {
                Workout workout = trainingPlanAdd.getWorkoutList().get(j);
                workout.setStatus(0);
                j = j + 1;
            }
        }
        return nextWorkout;
    }

    /**
     * Shows workout name and date on screen with provide
     *
     * @param workout
     */
    public void showWorkoutInfo(Workout workout) {
        if (isViewAttached) {
            tvWorkoutName.setText(workout.getName());
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//            tvDate.setText(sdf.format(new Date()));
        }
    }

    public boolean hasExercises(Workout workout) {
        boolean hasExercise = false;
        for (Circuit circuit : workout.getCircuitList()) {
            for (ExerciseJsonModel exercise : circuit.getExerciseList()) {
                //if the workout is coming in this loop it means that it has exercises
                hasExercise = true;
                break;
            }
        }
        return hasExercise;
    }

    /**
     * Hides labels for workout and date, disables "Change Workout" button, disables viewpager swiping and shows provided
     *
     * @param message below
     */
    public void showDisabledInfo(String message) {
        if (isViewAttached) {
            btnChangeWorkout.setEnabled(false);
            tvWorkoutName.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Shows labels for workout and date, enables "Change Workout" button, enables viewpager swiping and shows default message below
     */
    public void showEnabledInfo(String message) {
        if (isViewAttached) {
            btnChangeWorkout.setEnabled(true);
            tvWorkoutName.setVisibility(View.VISIBLE);
        }
    }


    //endregion

    //region Callback Methods
//
    public void onChangeWorkClicked(){
        btnChangeWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextWorkout(true);
            }
        });
    }


    public void onStartWorkClicked(){
        btnStartWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExerciseFragment.totalTime = -1;
                ExerciseFragment.exerciseIndex = 0;
                Intent intent = new Intent(getActivity(), NewGetReadyActivity.class);
                BaseActivity.countDown = 3;
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoutineDataReceivedEvent(RoutineDataReceivedEvent routineDataReceivedEvent) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        initData();
        showNextWorkout(false);
//        setLastSynced();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoutineDataReceivedEvent(RoutineDataFetchingEvent fetchingEvent) {
        if (fetchingEvent.isFetched()) {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            initData();
            showNextWorkout(false);
//            setLastSynced();
        } else {
            if (progressBar != null && tvLastSynced != null) {
                progressBar.setVisibility(View.VISIBLE);
                tvLastSynced.setText("Syncing..");
            }
        }
    }
    //endregion
    public void setUpRecyclerView(){
        Workout workout = BaseActivity.workout;
        if (workout != null){
            ArrayList<Exercise> workoutNames = new ArrayList<>();
            for (int i = 0; i < workout.getCircuitList().size(); i++) {
                Circuit circuit = workout.getCircuitList().get(i);
                if (circuit.getExerciseList() != null && circuit.getExerciseList().size() > 0){
                    for (int j = 0; j < circuit.getExerciseList().size(); j++) {
                        Exercise exercise = TrainingLog.convertToExercise(circuit.getExerciseList().get(j));
                        workoutNames.add(exercise);
                    }
                }else {

                }
            }
            numOfExercises.setText(workoutNames.size() + " exercises");
            tvDuration.setText(TimeUtils.getEstimatedDurationTimeString(workout.getDuration()));
            adapter = new ExercisesRecyclerViewAdapter(getActivity());
            exercisesNameRv.setHasFixedSize(true);
            exercisesNameRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            exercisesNameRv.setAdapter(adapter);
            adapter.setItems(workoutNames);

        }

    }

    public Workout createWorkout(){
        Workout workout = new Workout();
        ArrayList<Circuit>list = new ArrayList<>();
        ArrayList<ExerciseJsonModel>newExercises = new ArrayList<>();
        Exercise exercise = new Exercise();
        exercise.setName("Tracking only function");
        exercise.setCategory("Free Training");
        exercise.setCategory_name("Free Training");
        exercise.setImageLink("");
        exercise.setImageName("");
        exercise.setExerciseId(1);
        exercise.setCircuitExerciseId(1);
        exercise.setCircuitId(1);
        exercise.setExercise_description("Default exercise");
        newExercises.add(TrainingLog.convertFromExercise(exercise));
        Circuit circuit = new Circuit();
        circuit.setExerciseList(newExercises);
        circuit.setCircuitId(1);
        circuit.setName("Free Training");
        list.add(circuit);
        workout.setCircuitList(list);
        workout.setName("Free Training");
        workout.setWorkoutId(1);
        return workout;
    }
}
