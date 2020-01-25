package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing Current Training Plan information from json string
 */


public class CurrentTrainingPlan {
    @SerializedName("client_workout_id")
    @Expose
    private Integer clientWorkoutId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    private Integer workoutstatus;
    @SerializedName("workout_id")
    @Expose
    private Integer workoutId;
    private String workoutname;

    public Integer getClientWorkoutId() {
        return clientWorkoutId;
    }

    public void setClientWorkoutId(Integer clientWorkoutId) {
        this.clientWorkoutId = clientWorkoutId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getWorkoutstatus() {
        return workoutstatus;
    }

    public void setWorkoutstatus(Integer workoutstatus) {
        this.workoutstatus = workoutstatus;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutname() {
        return workoutname;
    }

    public void setWorkoutname(String workoutname) {
        this.workoutname = workoutname;
    }
}
