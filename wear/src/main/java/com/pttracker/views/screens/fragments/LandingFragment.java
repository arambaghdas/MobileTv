package com.pttracker.views.screens.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pttracker.R;
import com.pttracker.controllers.TrainingPlanController;
import com.pttracker.datamanagers.TrainingPlanDataManager;
import com.pttracker.datamanagers.UserAccountDataManager;
import com.pttracker.models.eventbus.RoutineDataFetchingEvent;
import com.pttracker.models.eventbus.RoutineDataReceivedEvent;
import com.pttracker.utils.SharedPrefManager;
import com.pttracker.views.screens.activities.DashboardActivity;
import com.pttracker.views.screens.activities.HomeActivity;
import com.pttrackershared.models.Circuit;
import com.pttrackershared.models.Exercise;
import com.pttrackershared.models.TrainingLog;
import com.pttrackershared.models.TrainingPlan;
import com.pttrackershared.models.User;
import com.pttrackershared.models.Workout;
import com.pttrackershared.plugins.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R.id.tv_workout_name)
    public TextView tvWorkoutName;

    @BindView(R.id.tv_date)
    public TextView tvDate;

    @BindView(R.id.tv_message)
    public TextView tvMessage;

    @BindView(R.id.tv_last_synced)
    TextView tvLastSynced;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_change_workout)
    TextView btnChangeWorkout;

    @BindView(R.id.tv_workout_duration)
    TextView tvWorkoutDuration;

    @BindView(R.id.tv_trn_finish_date)
    TextView tvTrnFinishDate;

    @BindView(R.id.tv_age)
    TextView tvAge;

    @BindView(R.id.tv_weight)
    TextView tvWeight;

    @BindView(R.id.tv_gender)
    TextView tvGender;
    //endregion

    //region Other Variables
    private Context context;
    private int currentWorkoutIndex = -1;
    private List<TrainingPlan> routineList;
    private Unbinder unbinder;
    private boolean isViewAttached = false;
    private boolean isSynced = false;
    //endregion

    //region Overridden Methods from BaseFragment Class
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragament_landing, container, false);
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
        return rootView;
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
    public boolean isSwipeable() {
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Activity activity = getActivity();
            Workout selectedWorkout = null;
            if (activity != null) {
                selectedWorkout = ((HomeActivity) getActivity()).getSelectedWorkout();
            }
            if (selectedWorkout != null && selectedWorkout.getStatus() == 1) {
                initData();
                showNextWorkout(false);
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
        isSynced = false;
        currentWorkoutIndex = -1;

//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
        routineList = TrainingPlanDataManager.getInstance(context).getStoredTrainingPlans();
        Collections.sort(routineList, new Comparator<TrainingPlan>() {
            @Override
            public int compare(TrainingPlan routine1, TrainingPlan routine2) {
                return routine1.getStartDate().compareTo(routine2.getStartDate());
            }
        });
//                return null;
//            }
//        }.execute();

    }

    private void initListeners() {
    }

    private void postStart() {
        showNextWorkout(false);
        setLastSynced();
    }
    //endregion

    //region Business Logic Specific to this Fragment

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LandingFragment newInstance() {
        LandingFragment fragment = new LandingFragment();
        return fragment;
    }

    /**
     * Shows next workout if available and handle all alternate scenarios
     */
    private void showNextWorkout(boolean showMessage) {
        if (routineList != null)
            if (!routineList.isEmpty()) {
                if (haveFinishDatePassed(routineList.get(0).getFinishDate())) {
                    showDatePassedWorkouts(showMessage);
                } else {
                    showDateAheadWorkouts(showMessage);
                }
            } else {
                isSynced = false;
                showDisabledInfo(getString(R.string.error_message_routines_not_synced));
                TrainingPlanController.getInstance(context).loadRoutinesData();
            }


    }

    private void showDatePassedWorkouts(boolean showMessage) {
        if (areAllWorkoutCompleted(routineList.get(0).getWorkoutList())) {
            showDisabledInfo(getString(R.string.txt_all_workouts_completed));
        } else {
            refreshWorkouts();
            Workout workout = getNextPendingWorkout();
            if (workout != null) {
                showWorkoutInfo(workout);

                if (workout.getCircuitList() != null && hasExercises(workout)) {
                    showEnabledInfo(getString(R.string.txt_default_message));
                } else {
                    showEnabledInfo("No Exercise Available");
                    ((HomeActivity) getActivity()).setSwipeable(false);
                }

            } else {
                if (showMessage)
                    Toast.makeText(context, R.string.error_message_no_next_workout, Toast.LENGTH_SHORT).show();
            }

        }
        isSynced = true;
    }

    private void showDateAheadWorkouts(boolean showMessage) {
        if (areAllWorkoutCompleted(routineList.get(0).getWorkoutList())) {
            showDisabledInfo(getString(R.string.txt_all_workouts_completed));
            refreshWorkouts();
            showDateAheadWorkouts(showMessage);
        } else {
            Workout workout = getNextPendingWorkout();
            if (workout != null) {
                showWorkoutInfo(workout);

                if (workout.getCircuitList() != null && hasExercises(workout)) {
                    showEnabledInfo(getString(R.string.txt_default_message));
                } else {
                    showEnabledInfo("No Exercise Available");
                    ((HomeActivity) getActivity()).setSwipeable(false);
                }
            } else {
                if (showMessage)
                    Toast.makeText(context, R.string.error_message_no_next_workout, Toast.LENGTH_SHORT).show();
            }

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

    public void setLastSynced() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy - HH:mm", Locale.US);
        Date lastSyncedDate = UserAccountDataManager.getInstance(context).getLastSynced();

        if (lastSyncedDate != null) {
            tvLastSynced.setText("Last Sync: " + sdf.format(lastSyncedDate));
        } else {
            tvLastSynced.setText("Not synced yet");
        }
    }

    /**
     * Checks whether all workouts have status 1
     */
    private boolean areAllWorkoutCompleted(List<Workout> routineList) {
        boolean allWorkoutCompleted = false;

        if (routineList != null && !routineList.isEmpty()) {
            boolean anyPendingRoutine = false;
            for (Workout workout : routineList) {
                if (workout != null) {
                    if (workout.getStatus() == 0) {
                        anyPendingRoutine = true;
                    }
                }
            }

            allWorkoutCompleted = !anyPendingRoutine;
        }
        return allWorkoutCompleted;
    }

    /**
     * Provides next routine with status 0 if available otherwise returns null
     */
    private Workout getNextPendingWorkout() {
        Workout nextWorkout = null;
//        for (int i = 0; i < routineList.size(); i++) {//Nitin
            TrainingPlan routine = routineList.get(DashboardActivity.SELECTED_PLAN);
            if (routine.getWorkoutList() != null) {
                int j = 0;
                if (currentWorkoutIndex < routine.getWorkoutList().size() - 1)
                    j = currentWorkoutIndex + 1;

                while (j < routine.getWorkoutList().size()) {
                    Workout workout = routine.getWorkoutList().get(j);
                    if (workout.getStatus() == 0 /*&& workout.getCircuitList().size() != 0*/) {
                        nextWorkout = workout;
                        currentWorkoutIndex = j;

                        ((HomeActivity) getActivity()).setSelectedWorkout(nextWorkout);
                        break;
                    } else {
                        if (j + 1 < routine.getWorkoutList().size())
                            j = j + 1;
                        else
                            j = 0;
                    }
                }
            }
//        }
        return nextWorkout;
    }

    /**
     * Refresh status of workouts as UNDONE
     */
    private Workout refreshWorkouts() {
        Workout nextWorkout = null;
        for (int i = 0; i < routineList.size(); i++) {
            TrainingPlan routine = routineList.get(i);
            if (routine.getWorkoutList() != null) {
                int j = 0;
                while (j < routine.getWorkoutList().size()) {
                    Workout workout = routine.getWorkoutList().get(j);
                    workout.setStatus(0);
                    j = j + 1;
                }
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            tvDate.setText(sdf.format(new Date()));
            if (tvWorkoutDuration != null)
                tvWorkoutDuration.setText("Duration:" + workout.getDuration());


            User user = UserAccountDataManager.getInstance(context).getCurrentUser();
            String userWeight = routineList.get(0).getWeight();
            if (tvAge != null && user != null)
                tvAge.setText("Age:" + TimeUtils.getDiffYears(user.getDobString(), TimeUtils.getCurrentDate()));
            if (tvWeight != null && user != null) {
                if (Integer.valueOf(userWeight) > 0) {
                    tvWeight.setText("Weight:" + userWeight);
                } else {
                    tvWeight.setText("Weight:" + user.getWeight());
                }
            }

            if (tvTrnFinishDate != null) {
                tvTrnFinishDate.setText("Finish Date : " + routineList.get(0).getFinishDateString());
            }

            if (tvGender != null && user != null) {
                if (user.getGender().equalsIgnoreCase("1")) {
                    tvGender.setText("Gender:" + "Male");
                }
                if (user.getGender().equalsIgnoreCase("2")) {
                    tvGender.setText("Gender:" + "Female");
                }

            }


        }
    }

    public boolean hasExercises(Workout workout) {
        boolean hasExercise = false;
        for (Circuit circuit : workout.getCircuitList()) {
            for (Exercise exercise : circuit.getExerciseList()) {
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
            ((HomeActivity) getActivity()).setSwipeable(false);
            btnChangeWorkout.setEnabled(false);
            tvDate.setVisibility(View.INVISIBLE);
            tvWorkoutName.setVisibility(View.INVISIBLE);
            tvMessage.setText(message);
        }
    }

    /**
     * Shows labels for workout and date, enables "Change Workout" button, enables viewpager swiping and shows default message below
     */
    public void showEnabledInfo(String message) {
        if (isViewAttached) {
            ((HomeActivity) getActivity()).setSwipeable(true);
            btnChangeWorkout.setEnabled(true);
            tvDate.setVisibility(View.VISIBLE);
            tvWorkoutName.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        }
    }


    //endregion

    //region Callback Methods

    @OnClick(R.id.btn_change_workout)
    public void onChangeWorkClicked(View view) {
        showNextWorkout(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoutineDataReceivedEvent(RoutineDataReceivedEvent routineDataReceivedEvent) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        initData();
        showNextWorkout(false);
        setLastSynced();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRoutineDataReceivedEvent(RoutineDataFetchingEvent fetchingEvent) {
        if (fetchingEvent.isFetched()) {
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
            initData();
            showNextWorkout(false);
            setLastSynced
                    ();
        } else {
            if (progressBar != null && tvLastSynced != null) {
                progressBar.setVisibility(View.VISIBLE);
                tvLastSynced.setText("Syncing..");
            }
        }
    }

    @OnClick(R.id.btn_back)
    void backButnClicked() {
        getActivity().onBackPressed();
    }
    //endregion
}
