package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pttracker.trainingaid.models.Circuit;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import train.apitrainclient.utils.ValidatorUtils;

/*
 * CircuitsRecyclerAdapter acts as a controller between the @ViewHolder and @Circuit model
 * performs the conventional bridging in the specified format of RecyclerView and defining a Custom
 * ViewHolder
 *
 * */
//extension of @see RecyclerView.Adapter, most of the functions and parameters name define themselves
public class CircuitsRecyclerAdapter extends RecyclerView.Adapter<CircuitsRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Circuit> circuitList;
    private OnItemSelectedListener onItemSelectedListener;
    private String month;
    private int day;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_exercises)
        TextView tvExercises;

        @BindView(R.id.tv_time_excr)
        TextView tvDuration;

//        @BindView(R.id.tv_count)
//        TextView tvCount;

        View rootView;


        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    public CircuitsRecyclerAdapter(Context context, List<Circuit> circuitList) {
        this.context = context;
        this.circuitList = circuitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_circuit, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Circuit circuit = circuitList.get(position);
        holder.tvName.setText(circuit.getName());
//        holder.tvCount.setText(String.valueOf(position + 1));
        String exercises = "";

        if (!ValidatorUtils.IsNullOrEmpty(circuit.getExerciseList())) {
            if (circuit.getExerciseList().size() > 0)
                exercises = exercises + circuit.getExerciseList().get(0).getName();
        }
        holder.tvExercises.setText(" " + circuit.getExerciseList().size());
//        holder.tvDuration.setText(" " + TimeUtils.getTimeString(circuit.getDuration()));

        initItemClickedListener(holder.rootView, position);
    }

    @Override
    public int getItemCount() {
        return circuitList.size();
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
