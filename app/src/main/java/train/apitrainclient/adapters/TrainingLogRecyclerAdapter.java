package train.apitrainclient.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import train.apitrainclient.R;
import com.pttrackershared.models.eventbus.TrainingLog;
import com.pttrackershared.models.eventbus.User;

import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.TimeUtils;

/*
 * TrainingLogRecyclerAdapter acts as a controller between the @ViewHolder and @TrainingLog model
 * performs the conventional bridging in the specified format of RecyclerView and defining a Custom
 * ViewHolder
 *
 * */
public class TrainingLogRecyclerAdapter extends RecyclerView.Adapter<TrainingLogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<TrainingLog> trainingLogs;
    private ArrayList<String>arrayListZones;
    private int userAge;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_day_count)
        TextView tvDate;

        @BindView(R.id.tv_month_count)
        TextView tvDateMonth;

        @BindView(R.id.tv_log_name)
        TextView tvWorkoutName;

        @BindView(R.id.tv_log_timer)
        TextView tvTimeComplete;

        @BindView(R.id.star1)
        View star1;

        @BindView(R.id.star2)
        View star2;

        @BindView(R.id.star3)
        View star3;

        @BindView(R.id.tv_log_zone)
        TextView tv_log_zone;

        @BindView(R.id.tv_log_cal)
        TextView tvCalories;


        public ViewHolder(View view) {
            super(view);
            //injecting the views in the holder
            ButterKnife.bind(this, view);
        }
    }

    public TrainingLogRecyclerAdapter(Context context, List<TrainingLog> trainingLogs,int userAge) {
        this.context = context;
        this.userAge = userAge;
        this.trainingLogs = trainingLogs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_trainig_log_view, parent, false);
        return new ViewHolder(itemView);
    }

    public void setEffortZonesList(ArrayList<String>arrayListZones){
        this.arrayListZones = arrayListZones;
        Collections.reverse(arrayListZones);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //taking the reference to the training log at position of the list being supplied inn the constructor
        TrainingLog trainingLog = trainingLogs.get(position);
        //setting date safely
        if(trainingLog.getDateCompleted() != null) {
            String day = trainingLog.getDateCompleted().substring(8);
            int getMonth = Integer.parseInt(trainingLog.getDateCompleted().substring(5, 7));
            String month = DateFormatSymbols.getInstance().getMonths()[getMonth - 1];
            month = month.substring(0, 3);
            month = month.toUpperCase();

            holder.tvDate.setText(day);
            holder.tvDateMonth.setText(month);
        }
        //setting the workout name
        holder.tvWorkoutName.setText(trainingLog.getName());
        holder.tvCalories.setText(trainingLog.getCalories());

        int avgBpm = Integer.parseInt(trainingLog.getAvgBpm());
        float zoneX = avgBpm * 100;
        int zoney = 220 - userAge;
        float zone = zoneX / zoney;
        String effortZone = "";
        if (zone <= 60) {
            effortZone = "Zone 1";
        } else if (zone > 60 && zone <= 70) {
            effortZone = "Zone 2";
        } else if (zone > 70 && zone <= 80) {
            effortZone = "Zone 3";
        } else if (zone > 80 && zone <= 90) {
            effortZone = "Zone 4";
        } else if (zone > 90) {
            effortZone = "Zone 5";
        }

        holder.tv_log_zone.setText(effortZone);

        //setting the time completed for the log, if the time is not null and it should not be null, a safe check thats it.
        if(trainingLog.getTimeCompleted() != null) {
            holder.tvTimeComplete.setText(TimeUtils.getTimeString((int)Long.parseLong(trainingLog.getTimeCompleted()), 3) + "");
        }
        //setting views of other attributes if training log
        if(trainingLog.getFeedback() != null) {
            if (trainingLog.getFeedback().matches("0")) {
                holder.star1.setBackgroundResource(R.mipmap.easy_white);
                holder.star2.setBackgroundResource(R.mipmap.medium_white);
                holder.star3.setBackgroundResource(R.mipmap.hard_white);
            } else if (trainingLog.getFeedback().matches("1")) {
                holder.star1.setBackgroundResource(R.mipmap.easy_colour);
                holder.star2.setBackgroundResource(R.mipmap.medium_white);
                holder.star3.setBackgroundResource(R.mipmap.hard_white);
            } else if (trainingLog.getFeedback().matches("2")) {
                holder.star1.setBackgroundResource(R.mipmap.easy_white);
                holder.star2.setBackgroundResource(R.mipmap.medium_colour);
                holder.star3.setBackgroundResource(R.mipmap.hard_white);
            } else if (trainingLog.getFeedback().matches("3")) {
                holder.star1.setBackgroundResource(R.mipmap.easy_white);
                holder.star2.setBackgroundResource(R.mipmap.medium_white);
                holder.star3.setBackgroundResource(R.mipmap.hard_colour);
            }
        }

//        holder.tvMaxBPM.setText(trainingLog.getMaxBpm());
//        holder.tvMinBPM.setText(trainingLog.getMinBpm());
//        holder.tvAvgBPM.setText(trainingLog.getAvgBpm());

    }

    @Override
    public int getItemCount() {
        return trainingLogs.size();//returns the number of rows
    }
}
