package com.pttracker.trainingaid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pttracker.trainingaid.R;
import com.pttrackershared.models.eventbus.Exercise;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Goran on movie2/6/2018.
 */

public class ExercisesRecyclerViewAdapter extends RecyclerView.Adapter<ExercisesRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Exercise> workouts;


    public ExercisesRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<Exercise> workouts){
        this.workouts = workouts;
        notifyDataSetChanged();
    }

    @Override
    public ExercisesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder view = new ViewHolder (inflater.inflate(R.layout.exercise_layout_view_training_aid,parent,false));

        return view;

    }

    @Override
    public void onBindViewHolder(ExercisesRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Exercise exercise = workouts.get(position);

        if (position % 2 == 0){
            holder.exerciseLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            holder.exerciseLayout.setBackgroundColor(context.getResources().getColor(R.color.blue_grey_100));
        }

        holder.exerciseSet.setText(exercise.getSet() + "");

        int number = position + 1;
        holder.exerciseName.setText(exercise.getName());
        holder.exerciseReps.setText(exercise.getReps() + "");
        holder.exerciseWeight.setText(exercise.getWeight() + "");

        if (exercise.getCalories() != 0) {
            holder.exerciseReps.setText("Calories: " + exercise.getCalories());
        } else {
            holder.exerciseReps.setText("Reps: " + exercise.getReps());
        }

        if (exercise.getReps() == 1000) {
            holder.exerciseReps.setText("FAIL");
        } else if (exercise.getReps() == 1001) {
            holder.exerciseReps.setText("LOW");
        } else if (exercise.getReps() == 1002) {
            holder.exerciseReps.setText("MEDIUM");
        } else if (exercise.getReps() == 1003) {
            holder.exerciseReps.setText("HIGH");
        }

        if (exercise.getWeight() > 0 && exercise.getWeight() < 1000) {
            holder.exerciseWeight.setText(exercise.getWeight() + "");
        } else if (exercise.getWeight() == 1000) {
            holder.exerciseWeight.setText("CUSTOM");
        } else if (exercise.getWeight() == 1001) {
            holder.exerciseWeight.setText("LOW");
        } else if (exercise.getWeight() == 1002) {
            holder.exerciseWeight.setText("MEDIUM");
        } else if (exercise.getWeight() == 1003) {
            holder.exerciseWeight.setText("HIGH");
        } else {
            holder.exerciseWeight.setText(exercise.getWeight() + "");
        }


        holder.exerciseRest.setText(exercise.getRestTime() + "");
    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        TextView exerciseReps;
        TextView exerciseWeight;
        TextView exerciseRest;
        LinearLayout exerciseLayout;
        TextView exerciseSet;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            exerciseName = (TextView)itemView.findViewById(R.id.exerciseName);
            exerciseReps = (TextView)itemView.findViewById(R.id.exerciseReps);
            exerciseWeight = (TextView)itemView.findViewById(R.id.exerciseWeight);
            exerciseRest = (TextView)itemView.findViewById(R.id.exerciseRest);
            exerciseLayout = (LinearLayout) itemView.findViewById(R.id.exerciseLayout);
            exerciseSet = (TextView) itemView.findViewById(R.id.exercisesSet);
        }
    }
}
