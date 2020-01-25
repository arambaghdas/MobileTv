package train.apitrainclient.views.fragments;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.adapters.TrainingPlansRecyclerAdapter;
import train.apitrainclient.listeners.OnGetTrainingPlanListener;

import com.pttrackershared.models.eventbus.TrainingPlan;
import com.pttrackershared.models.eventbus.TrainingPlanJsonModel;
import com.pttrackershared.models.eventbus.TrainingPlanView;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutView;

import train.apitrainclient.listeners.OnGetTrainingPlansCompletionListener;
import train.apitrainclient.listeners.OnGetWorkoutsCompletionListener;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.SaveUserPreferences;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.UserPrefManager;
import train.apitrainclient.views.activities.HomeActivity;

/**
 * @author Atif Ali
 * @since August 29, 2017 5:08 PM
 * TrainingPlanFragment shows list of routines assigned to current user.
 * Provides option to user to filter out workout based on routine and date.
 */

public class TrainingPlanFragment extends BaseFragment {

    //region View References
    @BindView(R.id.tvPlanName)
    TextView tvPlanName;

    @BindView(R.id.tv_days_trained)
    TextView tvDaysTrained;

    @BindView(R.id.rv_workouts)
    RecyclerView rvWorkouts;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.tv_training_daysperweek)
    TextView tvTrainingDaysPerWeek;

    @BindView(R.id.rl_list)
    RelativeLayout rlList;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;


    TrainingPlansRecyclerAdapter trainingPlansRecyclerAdapter;
    TrainingPlanView trainingPlanViewJsonModel;
    List<WorkoutView> workoutsList;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);
        MenuItem add_client = menu.findItem(R.id.action_add_client);
        add_client.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void preStart(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.preStart(inflater, container);
        rootView = inflater.inflate(R.layout.fragment_training_plan, container, false);
        setHasOptionsMenu(true);

        if (HomeActivity.workoutsModel.workouts.size() == 0){
            TrainingPlan trainingPlan = SharedPrefManager.getTrainingPlan(getActivity());
            HomeActivity.workoutsModel.workouts.addAll(trainingPlan.getWorkoutList());
        }

}

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initControllers() {
        super.initControllers();
        workoutsList = new ArrayList<>();
        trainingPlansRecyclerAdapter = new TrainingPlansRecyclerAdapter(context, workoutsList);
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void postStart() {
        super.postStart();
        User user = SharedPrefManager.getUser(getActivity());
        RestApiManager.getTrainingPlans(getActivity(), user, new OnGetTrainingPlansCompletionListener() {
            @Override
            public void onSuccess(List<TrainingPlanJsonModel> trainingPlanList) {
                List<TrainingPlan>trainingPlansList = AppUtils.saveTrainingPlans(getActivity(),trainingPlanList);
                HomeActivity.workoutsModel.workouts.addAll(trainingPlansList.get(0).getWorkoutList());
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {

            }
        });

        if (HomeActivity.networkConnection) {
            DialogUtils.ShowProgress(context, getString(R.string.progress_message_loading_routines));
            RestApiManager.getSingleTrainingPlan(context, new OnGetTrainingPlanListener() {
                @Override
                public void onSuccess(TrainingPlanView trainingPlanViewJsonModelList) {
                    trainingPlanViewJsonModel = trainingPlanViewJsonModelList;
                    if (tvNoData != null) {
                        initCollectionAdapters();
                        tvNoData.setVisibility(View.GONE);
                    }
                    DialogUtils.HideDialog();
                }

                @Override
                public void onFailure(int errorCode, String errorMessage) {
                    if (tvNoData != null) {
                        tvNoData.setVisibility(View.VISIBLE);
                        rvWorkouts.setVisibility(View.INVISIBLE);
                        rlList.setVisibility(View.INVISIBLE);
                        tvTrainingDaysPerWeek.setVisibility(View.INVISIBLE);
                    }
                    DialogUtils.HideDialog();
                }
            });
        }else {
            trainingPlanViewJsonModel = SharedPrefManager.getTrainingPlanView(context);
            if (tvNoData != null) {
                initCollectionAdapters();
                tvNoData.setVisibility(View.GONE);
            }
        }
    }

    private void initCollectionAdapters() {

        UserPrefManager userPrefManager = new UserPrefManager(getActivity());
        if (!isViewAttached() || !userPrefManager.isLoggedIn()) {
            return;
        }
        workoutsList.clear();
        int numTrainingDays = 0;
        WorkoutView  restModel = new WorkoutView();
        restModel.setWorkoutname("Rest");
        if (trainingPlanViewJsonModel != null) {
            if (trainingPlanViewJsonModel.getWorkoutId1() != null && trainingPlanViewJsonModel.getWorkoutId1().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId1());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }

            if (trainingPlanViewJsonModel.getWorkoutId2() != null && trainingPlanViewJsonModel.getWorkoutId2().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId2());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            if (trainingPlanViewJsonModel.getWorkoutId3() != null && trainingPlanViewJsonModel.getWorkoutId3().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId3());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            if (trainingPlanViewJsonModel.getWorkoutId4() != null && trainingPlanViewJsonModel.getWorkoutId4().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId4());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            if (trainingPlanViewJsonModel.getWorkoutId5() != null && trainingPlanViewJsonModel.getWorkoutId5().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId5());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            if (trainingPlanViewJsonModel.getWorkoutId6() != null && trainingPlanViewJsonModel.getWorkoutId6().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId6());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            if (trainingPlanViewJsonModel.getWorkoutId7() != null && trainingPlanViewJsonModel.getWorkoutId7().getWorkoutId() != 0) {
                workoutsList.add(trainingPlanViewJsonModel.getWorkoutId7());
                numTrainingDays++;
            } else {
                workoutsList.add(restModel);
            }
            tvPlanName.setText(trainingPlanViewJsonModel.getPlanName());
        }
        tvDaysTrained.setText(Integer.toString(numTrainingDays));
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvWorkouts.setLayoutManager(layoutManager);
        rvWorkouts.setAdapter(trainingPlansRecyclerAdapter);
    }


    public static TrainingPlanFragment newInstance() {
        TrainingPlanFragment trainingPlanFragment = new TrainingPlanFragment();
        return trainingPlanFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.HideDialog();
    }
}