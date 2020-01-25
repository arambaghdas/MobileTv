package train.apitrainclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pttrackershared.models.eventbus.Exercise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;

/*
* TrainingPlansRecyclerAdapter acts as a controller between the @ViewHolder and @WorkoutViewJsonModel model
* performs the conventional bridging in the specified format of RecyclerView and defining a Custom
* ViewHolder
*
* */
public class WorkoutListRecyclerAdapter extends RecyclerView.Adapter<WorkoutListRecyclerAdapter.ViewHolder> {

    private Context context;
    private  List<Exercise> workoutViewModelList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_workout)
        TextView tvWorkout;
        @BindView(R.id.workoutLayout)
        RelativeLayout workoutLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public WorkoutListRecyclerAdapter(Context context, List<Exercise> workoutViewModelList) {
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
        Exercise exercise = workoutViewModelList.get(position);
        holder.tvDay.setText(Integer.toString(position+1));
        holder.tvWorkout.setText(exercise.getName());

    }

    @Override
    public int getItemCount() {
        return workoutViewModelList.size();
    }
}
