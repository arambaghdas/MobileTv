package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for deserializing Workout information from json string
 */


public class WorkoutJsonModel {
    @SerializedName("client_workout_id")
    @Expose
    private Integer clientWorkoutId;
    @SerializedName("workout_id")
    @Expose
    private Integer workoutId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("workoutstatus")
    @Expose
    private Integer workoutstatus;
    @SerializedName("Date_completed")
    @Expose
    private String dateCompleted;
    @SerializedName("Time_completed")
    @Expose
    private String timeCompleted;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("Max_BPM")
    @Expose
    private String maxBpm;
    @SerializedName("Min_BPM")
    @Expose
    private String minBpm;
    @SerializedName("Avg_BPM")
    @Expose
    private String avgBpm;
    @SerializedName("workoutname")
    @Expose
    private String workoutname;
    @SerializedName("circuits")
    @Expose
    private List<CircuitJsonModel> circuits = null;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("calories")
    @Expose
    private String calories;

    @SerializedName("effort_zone1")
    @Expose
    private String effort_zone1;

    @SerializedName("effort_zone2")
    @Expose
    private String effort_zone2;

    @SerializedName("effort_zone3")
    @Expose
    private String effort_zone3;

    @SerializedName("effort_zone4")
    @Expose
    private String effort_zone4;

    @SerializedName("effort_zone5")
    @Expose
    private String effort_zone5;


    public String getEffort_zone1() {
        return effort_zone1;
    }

    public void setEffort_zone1(String effort_zone1) {
        this.effort_zone1 = effort_zone1;
    }

    public String getEffort_zone2() {
        return effort_zone2;
    }

    public void setEffort_zone2(String effort_zone2) {
        this.effort_zone2 = effort_zone2;
    }

    public String getEffort_zone3() {
        return effort_zone3;
    }

    public void setEffort_zone3(String effort_zone3) {
        this.effort_zone3 = effort_zone3;
    }

    public String getEffort_zone4() {
        return effort_zone4;
    }

    public void setEffort_zone4(String effort_zone4) {
        this.effort_zone4 = effort_zone4;
    }

    public String getEffort_zone5() {
        return effort_zone5;
    }

    public void setEffort_zone5(String effort_zone5) {
        this.effort_zone5 = effort_zone5;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getClientWorkoutId() {
        return clientWorkoutId;
    }

    public void setClientWorkoutId(Integer clientWorkoutId) {
        this.clientWorkoutId = clientWorkoutId;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getWorkoutstatus() {
        return workoutstatus;
    }

    public void setWorkoutstatus(Integer workoutstatus) {
        this.workoutstatus = workoutstatus;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getTimeCompleted() {
        return timeCompleted;
    }

    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMaxBpm() {
        return maxBpm;
    }

    public void setMaxBpm(String maxBpm) {
        this.maxBpm = maxBpm;
    }

    public String getMinBpm() {
        return minBpm;
    }

    public void setMinBpm(String minBpm) {
        this.minBpm = minBpm;
    }

    public String getAvgBpm() {
        return avgBpm;
    }

    public void setAvgBpm(String avgBpm) {
        this.avgBpm = avgBpm;
    }

    public String getWorkoutname() {
        return workoutname;
    }

    public void setWorkoutname(String workoutname) {
        this.workoutname = workoutname;
    }

    public List<CircuitJsonModel> getCircuits() {
        return circuits;
    }

    public void setCircuits(List<CircuitJsonModel> circuits) {
        this.circuits = circuits;
    }
}
