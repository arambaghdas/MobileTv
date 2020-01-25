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

import com.pttrackershared.models.eventbus.Workout;
import com.pttrackershared.models.eventbus.WorkoutView;
import train.apitrainclient.views.activities.ExerciseListDetailActivity;
import train.apitrainclient.views.activities.HomeActivity;

/*
* TrainingPlansRecyclerAdapter acts as a controller between the @ViewHolder and @WorkoutViewJsonModel model
* performs the conventional bridging in the specified format of RecyclerView and defining a Custom
* ViewHolder
*
* */
public class TrainingPlansRecyclerAdapter extends RecyclerView.Adapter<TrainingPlansRecyclerAdapter.ViewHolder> {

    private Context context;
    private  List<WorkoutView> workoutViewModelList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_workout)
        TextView tvWorkout;
        @BindView(R.id.workoutLayout)
        LinearLayout workoutLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public TrainingPlansRecyclerAdapter(Context context, List<WorkoutView> workoutViewModelList) {
        this.context = context;
        this.workoutViewModelList = workoutViewModelList;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_workout_normal, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_workout_grayed, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WorkoutView workoutViewModel = workoutViewModelList.get(position);
        holder.tvDay.setText(Integer.toString(position+1));
        holder.tvWorkout.setText(workoutViewModel.getWorkoutname());
//        if (!workoutViewModel.getWorkoutname().equalsIgnoreCase("Rest")){
            holder.workoutLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < HomeActivity.workoutsModel.workouts.size(); i++) {
                        Workout workout = HomeActivity.workoutsModel.workouts.get(i);
                        if (workoutViewModel.getWorkoutId().equals(workout.getWorkoutId())){
                            ExerciseListDetailActivity.selectedWorkout = workout;
                            Intent intent = new Intent(context, ExerciseListDetailActivity.class);
                            context.startActivity(intent);
                            break;
                        }
                    }
                }
            });
//        }

    }

    @Override
    public int getItemCount() {
        return workoutViewModelList.size();
    }
}
