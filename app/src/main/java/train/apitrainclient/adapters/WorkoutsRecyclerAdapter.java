package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.models.eventbus.Workout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;

import train.apitrainclient.utils.TimeUtils;
import train.apitrainclient.utils.ValidatorUtils;

/*
 * WorkoutsRecyclerAdapter acts as a controller between the @ViewHolder and @Workout model
 * performs the conventional bridging in the specified format of RecyclerView and defining a Custom
 * ViewHolder
 *
 * */
public class WorkoutsRecyclerAdapter extends RecyclerView.Adapter<WorkoutsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Workout> workoutList;
    public List<Circuit>  circuitList;
    private OnItemSelectedListener onItemSelectedListener;
    private String month;
    private int day;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.new_tv_duration)
        TextView tvDuration;

        @BindView(R.id.new_tv_number_workouts)
        TextView tv_number_workouts;

        @BindView(R.id.new_tv_circuit)
        TextView tv_number_circuit;

//        @BindView(R.id.tv_date)
//        TextView tv_date;
//
//        @BindView(R.id.tv_date_month)
//        TextView tv_date_month;

        TextView tv_workout_id;

        View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            tvDuration = (TextView) rootView.findViewById(R.id.tv_duration);

            ButterKnife.bind(this, view);
        }
    }

    public WorkoutsRecyclerAdapter(Context context, List<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_workout_updated, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.tvName.setText(workout.getName());

        String circuits = "";
        if (!ValidatorUtils.IsNullOrEmpty(workout.getCircuitList())) {
            for (int i = 0; i < workout.getCircuitList().size() - 1; i++) {
                workout.getCircuitList().get(i).getExerciseList();
                if (workout.getCircuitList().size() > 0)
                    circuits = workout.getCircuitList().get(0).getName() + "/";
            }
            circuits = circuits + workout.getCircuitList().get(workout.getCircuitList().size() - 1).getName() + "/";
        }
        holder.tvDuration.setText(" " + TimeUtils.getTimeString(workout.getDuration()));
        holder.tv_number_workouts.setText(" " + workout.getCircuitList().size());
        holder.tv_number_circuit.setText("");
//        holder.tv_number_circuit.setText();
        getCurrentDayandHour(position);
//        holder.tv_date.setText(day + "");
//        holder.tv_date_month.setText(month);

        initItemClickedListener(holder.rootView, position);
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    private void initItemClickedListener(final View view, final int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);
                }
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public void getCurrentDayandHour(int position) {
        if (position == 0) {
            Date now = new Date();
            Calendar calander = Calendar.getInstance();
            calander.setTime(now);
            day = calander.get(Calendar.DAY_OF_MONTH);
            String months = calander.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            month = months.substring(0, 3);
        } else {
            Date now = new Date();
            Calendar calander = Calendar.getInstance();
            calander.setTime(now);
            day = calander.get(Calendar.DAY_OF_MONTH) + position;
            String months = calander.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            month = months.substring(0, 3);
        }

    }
}
