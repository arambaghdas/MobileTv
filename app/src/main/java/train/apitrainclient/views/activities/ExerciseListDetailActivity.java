package train.apitrainclient.views.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.adapters.WorkoutDetailsRecyclerAdapter;

import com.pttracker.trainingaid.activities.NewGetReadyActivity;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.CircuitsModel;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;

import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.TimeUtils;

public class ExerciseListDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_weight_label_unit)
    TextView tvWeightLabelUnit;

    @BindView(R.id.rv_exercises)
    RecyclerView rvExercises;

    @BindView(R.id.tv_exercises)
    TextView tvExercises;

    @BindView(R.id.tv_duration)
    TextView tvDuration;

    @BindColor(R.color.dark_blue)
    int darkBlue;

    @BindDrawable(R.drawable.ic_custom_back)
    Drawable backArrow;

    public static final String SELECTED_WORKOUT = "selected_workout";
    public static Workout selectedWorkout;
    private WorkoutDetailsRecyclerAdapter workoutDetailsRecyclerAdapter;
    private List<Exercise> exerciseList;
    public static String day = "";
    public static String month = "";

    @Override
    public void preStart() {
        super.preStart();
        setContentView(R.layout.activity_workout_detail);
        context = this;
    }

    /**
     * Initializes independently all data(not dependent on view or controller) variables to be used in this activity.
     */
    @Override
    public void initData() {
        super.initData();
        exerciseList = new ArrayList<>();
        CircuitsModel circuitsModel = SharedPrefManager.getCircuits(context);
        if(selectedWorkout.getCircuitList() != null) {
            for(Circuit circuit : selectedWorkout.getCircuitList()) {
                for (Circuit circuit1: circuitsModel.circuits){
                    if (circuit.getName().equalsIgnoreCase(circuit1.getName())){
                       circuit.setExerciseList(circuit1.getExerciseList());
                    }
                }
            }

            for (Circuit circuit:selectedWorkout.getCircuitList()){
                if(circuit.getExerciseList() != null) {
                    for (int i = 0; i < circuit.getExerciseList().size(); i++) {
                        ExerciseJsonModel exerciseJsonModel = circuit.getExerciseList().get(i);
                        exerciseList.add(TrainingLog.convertToExercise(exerciseJsonModel));
                    }
                }
            }
        }
    }

    @Override
    public void initControllers() {
        super.initListeners();

    }


    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }

    @Override
    public void postStart() {
        super.postStart();

        initCollectionAdapters();
        setupToolbar();
        showData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backArrow.setColorFilter(darkBlue, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showData(){
        tvName.setText(selectedWorkout.getName());
        String add = "";
        if (User.getSelectedWeightUnit(context) == Constants.WEIGHT_UNIT_POUNDS) {
            tvWeightLabelUnit.setText("WEIGHT\n(lbs)");
        }else
        {
            tvWeightLabelUnit.setText("WEIGHT\n(kg)");
        }

        tvDuration.setText("  " + TimeUtils.getEstimatedDurationTimeString(selectedWorkout.getDuration()));
        tvExercises.setText("  " + workoutDetailsRecyclerAdapter.getItemCount());
    }

    private void initCollectionAdapters() {
        workoutDetailsRecyclerAdapter = new WorkoutDetailsRecyclerAdapter(context, exerciseList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvExercises.setLayoutManager(layoutManager);
        rvExercises.setAdapter(workoutDetailsRecyclerAdapter);
    }



    @OnClick(R.id.startWorkout)
    public void StartWorkout(){
        BaseActivity.workout = selectedWorkout;
        com.pttracker.trainingaid.activities.BaseActivity.workout = selectedWorkout;
        Intent intent = new Intent(context, NewGetReadyActivity.class);
        startActivity(intent);
        finish();
    }



    //endregion

    //region Callback Methods
    //endregion
}

