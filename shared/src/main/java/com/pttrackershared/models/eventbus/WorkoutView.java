package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing Workout View information from json string
 */


public class WorkoutView {
    @SerializedName("workout_id")
    @Expose
    private Integer workoutId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("workoutname")
    @Expose
    private String workoutname;

    @SerializedName("duration")
    @Expose
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getWorkoutname() {
        return workoutname;
    }

    public void setWorkoutname(String workoutname) {
        this.workoutname = workoutname;
    }
}
