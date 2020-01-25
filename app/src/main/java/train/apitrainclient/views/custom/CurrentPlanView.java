package train.apitrainclient.views.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Calendar;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.SharedPrefManager;


/**
 * TODO: document your custom view class.
 */
public class CurrentPlanView extends RelativeLayout {

    Context context;
    TextView training_plan;
    TextView food_plan;
    TextView fit_goal;
    TextView workoutsCompleted;
    TextView caloriesBurned;

    Calendar calendar;
    User user;
    public static boolean bigScreen;

    public CurrentPlanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CurrentPlanView(Context context, Context context1) {
        super(context);
        this.context = context1;
        init();
    }

    public CurrentPlanView(Context context, AttributeSet attrs, int defStyleAttr, Context context1) {
        super(context, attrs, defStyleAttr);
        this.context = context1;
        init();
    }

    public CurrentPlanView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Context context1) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context1;
        init();
    }

    private void init() {
        if (bigScreen)
        LayoutInflater.from(getContext()).inflate(R.layout.plan_view_layout, this, true);
        else
        LayoutInflater.from(getContext()).inflate(R.layout.plan_view_layout_small, this, true);

        user = SharedPrefManager.getUser(context);

        training_plan = (TextView) findViewById(R.id.training_plan);
        food_plan = (TextView) findViewById(R.id.food_plan);
        fit_goal = (TextView) findViewById(R.id.fitness_goal);
        workoutsCompleted = (TextView) findViewById(R.id.workouts_completed_txt);
        caloriesBurned = (TextView) findViewById(R.id.calories_burned_txt);

        calendar = Calendar.getInstance();
    }





    public void setTraining_plan(String name){

    }


}
