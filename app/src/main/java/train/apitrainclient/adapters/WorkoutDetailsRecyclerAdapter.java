package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;

import com.pttrackershared.models.eventbus.Exercise;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.TimeUtils;
import train.apitrainclient.views.activities.ExerciseDetailActivity;
import train.apitrainclient.views.fragments.ExerciseExerciseTabFragment;
import train.apitrainclient.views.fragments.ExercisesFragment;

/**************************************WorkoutDetailsRecyclerAdapter**************************************
 * Circuits are aggregated in Workout which are aggregated in the plan and this adapter class bridges the model and view,
 * hence it behaves as the controller in MVC architecture. It shows the list of exercises which are actually the details in respective
 * workout, The constructor of this adapter takes the list of circutis which are being supplied by the workout list being controlled by
 * @see train.apitrainclient.adapters.WorkoutDetailsRecyclerAdapter*/
/* extension to the RecyclerView */
public class WorkoutDetailsRecyclerAdapter extends RecyclerView.Adapter<WorkoutDetailsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Exercise> exerciseList;
    int sameExercise = 1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_sets_count)
        TextView tvSetsCount;

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_rest_time)
        TextView tvRestTime;

        @BindView(R.id.tv_weight)
        TextView tvWeight;

        @BindView(R.id.tv_reps)
        TextView tv_reps;

        @BindView(R.id.exerciseLayout)
        LinearLayout exerciseLayout;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public WorkoutDetailsRecyclerAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_circuit_normal, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_circuit_grayed, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.tvSetsCount.setText(exercise.getSet() + "");

//        if (position > 0){
//          Exercise prevExercise = exerciseList.get(position - 1);
//          if (exercise.getExercisename().equalsIgnoreCase(prevExercise.getExercisename()) && exercise.getSet() == 1){
//              sameExercise++;
//              holder.tvSetsCount.setText(sameExercise+"");
//          }else {
//              sameExercise = 1;
//          }
//        }

        holder.tvName.setText(exercise.getName());

        holder.tvRestTime.setText(TimeUtils.getTimeString(exercise.getRestTime(), 2));

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExercisesFragment.selectedEx = exercise;
                Intent intent = new Intent(context, ExerciseDetailActivity.class);
                intent.putExtra(ExerciseExerciseTabFragment.SELECTED_EXERCISE,exercise);
                context.startActivity(intent);
            }
        });

        double weight = exercise.getWeight();

        if (exercise.getReps() == 1000) {
            holder.tv_reps.setText("FAIL");
        } else {
            holder.tv_reps.setText(exercise.getReps() + "");
        }

        if (weight > 0 && weight < 1000) {
            if (User.getSelectedWeightUnit(context) == Constants.WEIGHT_UNIT_POUNDS) {
                weight = AppUtils.convertKgToPound(weight);
            }
            holder.tvWeight.setText(Double.toString(weight));

        } else if (weight == 1000) {
            holder.tvWeight.setText("CUSTOM");
        } else if (weight == 1001) {
            holder.tvWeight.setText("LOW");
        } else if (weight == 1002) {
            holder.tvWeight.setText("MEDIUM");
        } else if (weight == 1003) {
            holder.tvWeight.setText("HIGH");
        } else
            holder.tvWeight.setText("-");
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
