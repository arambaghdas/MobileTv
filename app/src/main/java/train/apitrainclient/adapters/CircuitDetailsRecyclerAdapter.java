package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pttracker.trainingaid.models.Exercise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.TimeUtils;


/**************************************CircuitDetailsRecyclerAdapter**************************************
 * Exercises are aggregated in Circuits which are aggregated in the workout models and this adapter class bridges the model and view,
 * hence it behaves as the controller in MVC architecture. It shows the list of exercises which are actually the details in respective
 * circuit, The constructor of this adapter takes the list of exercises which are being supplied by the circuits list being controlled by
 * @see train.apitrainclient.adapters.CircuitsRecyclerAdapter*/


public class CircuitDetailsRecyclerAdapter extends RecyclerView.Adapter<CircuitDetailsRecyclerAdapter.ViewHolder> {

    private Context context;//context of the activity being displaying circuits list
    private List<Exercise> exerciseList;

    //Holder class custom definition to exercise list item
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_reps_count)
        TextView tvRepsCount;

        @BindView(R.id.tv_rest_time)
        TextView tvRestTime;

        @BindView(R.id.tv_weight)
        TextView tvWeight;

        public ViewHolder(View view) {
            super(view);
            //injecting the annotated views
            ButterKnife.bind(this, view);
        }
    }

    /*
     * Overloaded constructor that takes @Context of activity and @Exercise List*/
    public CircuitDetailsRecyclerAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    /**
     * returns the type of view for the
     *
     * @param position position of list item
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position % 2;//returns 0 or 1, 1 for odd value 0 for even
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;
        //alternate grey and white rows condition
        if (viewType == 0) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_exercise_normal, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_exercise_grayed, parent, false);
        }

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        //setting name text to the view holders field @tvName
        holder.tvName.setText(exercise.getName());
        //setting repscount to the view holders field @tvRepsCount
        if (exercise.getReps() == 1000) {
            holder.tvRepsCount.setText("FAIL");
        } else {
            if (exercise.getDuration() != 0) {
                holder.tvRepsCount.setText(TimeUtils.getTimeString(exercise.getDuration(), 3));
            } else if (exercise.getReps() != 0) {
                holder.tvRepsCount.setText(exercise.getReps() + " reps");
            } else if (exercise.getCalories() != 0) {
                holder.tvRepsCount.setText(exercise.getCalories() + " cals");
            } else {
                holder.tvRepsCount.setText(exercise.getReps() + " reps");
            }
        }
        //setting rest time to the view holders field @tvRestTime
        holder.tvRestTime.setText(TimeUtils.getTimeString(exercise.getRestTime(), 3));
        double weight = exercise.getWeight();
        //setting weight according to the preferences of unit to the view holders field @tvWeight

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
        return exerciseList.size();//returns the number of list items
    }
}
