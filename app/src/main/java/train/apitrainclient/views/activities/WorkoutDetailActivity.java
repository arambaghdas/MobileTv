package train.apitrainclient.views.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import train.apitrainclient.R;
import train.apitrainclient.adapters.WorkoutDetailsRecyclerAdapter;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;
import com.pttrackershared.models.eventbus.Workout;

import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.TimeUtils;

public class WorkoutDetailActivity extends BaseActivity {

    //region ButterKnife Resource References
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

//    @BindView(R.id.tv_date)
//    TextView tv_date;
//
//    @BindView(R.id.tv_date_month)
//    TextView tv_date_month;

    @BindColor(R.color.dark_blue)
    int darkBlue;

    @BindDrawable(R.drawable.ic_custom_back)
    Drawable backArrow;
    //endregion

    //region Other Variables
    public static final String SELECTED_WORKOUT = "selected_workout";
    public static Workout selectedWorkout;
    private WorkoutDetailsRecyclerAdapter workoutDetailsRecyclerAdapter;
    private List<Exercise> exerciseList;
    public static String day = "";
    public static String month = "";

    //endregion

    //region Controllers Variables
    //endregion

    //region Overridden Methods from Activity Base Class
    //endregion

    //region Basic Methods needed for Activity flow

    /**
     * Performs all operations right after creation of activity
     */
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

        if(selectedWorkout.getCircuitList() != null) {
            for(Circuit circuit : selectedWorkout.getCircuitList()) {
                if(circuit.getExerciseList() != null) {
                    for (int i = 0; i < circuit.getExerciseList().size(); i++) {
                        ExerciseJsonModel exerciseJsonModel = circuit.getExerciseList().get(i);
                        exerciseList.add(TrainingLog.convertToExercise(exerciseJsonModel));
                    }
                }
            }
        }
    }

    /**
     * Initializes independently all controllers (not dependent on view) to be used in this activity
     */
    @Override
    public void initControllers() {
        super.initListeners();
        workoutDetailsRecyclerAdapter = new WorkoutDetailsRecyclerAdapter(context, exerciseList);
    }

    /**
     * Initializes view references from xml view and programmatically created view.
     */
    @Override
    public void initViews() {
        super.initViews();
    }

    /**
     * Implements listeners views actions and other items related to this activity.
     * Anonymous implementation of a listener is preferred as this helps in debugging
     */
    @Override
    public void initListeners() {
        super.initListeners();
    }

    /**
     * Performs activity specific business logic here after complete initialization of data, controllers and views.
     */
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

    //endregion
    //region Business Logic Specific to this Activity

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

        if (User.getSelectedWeightUnit(context) == Constants.WEIGHT_UNIT_POUNDS) {
            tvWeightLabelUnit.setText("WEIGHT\n(lbs)");
        }else
        {
            tvWeightLabelUnit.setText("WEIGHT\n(kg)");
        }

        tvDuration.setText("  " + TimeUtils.getTimeString(selectedWorkout.getDuration()));
        tvExercises.setText("  " + workoutDetailsRecyclerAdapter.getItemCount());
//        tv_date.setText(day);
//        tv_date_month.setText(month);
        /*if(selectedWorkout != null){
            tvDuration.setText(selectedWorkout.getDuration() + "");
            Log.d("WorkoutDetail", selectedWorkout.getDuration() + "");
        }
        else
            tvDuration.setText("NO DURATION");*/
    }

    private void initCollectionAdapters() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvExercises.setLayoutManager(layoutManager);
        rvExercises.setAdapter(workoutDetailsRecyclerAdapter);
    }
}

