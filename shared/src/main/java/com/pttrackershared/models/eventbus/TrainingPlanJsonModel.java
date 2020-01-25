package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for deserializing Plan information from json string
 */


public class TrainingPlanJsonModel {
    @SerializedName("plan_id")
    @Expose
    private Integer planId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("client_workouts")
    @Expose
    private List<WorkoutJsonModel> clientWorkouts = null;

    @SerializedName("trn_date_start")
    @Expose
    private String startDate;
    @SerializedName("trn_date_finish")
    @Expose
    private String finishDate;

    @SerializedName("client_workout_id1")
    @Expose
    private int workoutId1;

    @SerializedName("client_workout_id2")
    @Expose
    private int workoutId2;

    @SerializedName("client_workout_id3")
    @Expose
    private int workoutId3;

    @SerializedName("client_workout_id4")
    @Expose
    private int workoutId4;

    @SerializedName("client_workout_id5")
    @Expose
    private int workoutId5;

    @SerializedName("client_workout_id6")
    @Expose
    private int workoutId6;

    @SerializedName("client_workout_id7")
    @Expose
    private int workoutId7;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("week")
    @Expose
    private int week;

    @SerializedName("users_plan_id")
    @Expose
    private int users_plan_id;

    public int getUsers_plan_id() {
        return users_plan_id;
    }

    public void setUsers_plan_id(int users_plan_id) {
        this.users_plan_id = users_plan_id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public List<WorkoutJsonModel> getClientWorkouts() {
        return clientWorkouts;
    }

    public void setClientWorkouts(List<WorkoutJsonModel> clientWorkouts) {
        this.clientWorkouts = clientWorkouts;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String date) {
        this.startDate = date;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String date) {
        this.finishDate = date;
    }
    public int getWorkoutId1() {
        return workoutId1;
    }

    public void setWorkoutId1(int workoutId1) {
        this.workoutId1 = workoutId1;
    }

    public int getWorkoutId2() {
        return workoutId2;
    }

    public void setWorkoutId2(int workoutId2) {
        this.workoutId2 = workoutId2;
    }

    public int getWorkoutId3() {
        return workoutId3;
    }

    public void setWorkoutId3(int workoutId3) {
        this.workoutId3 = workoutId3;
    }

    public int getWorkoutId4() {
        return workoutId4;
    }

    public void setWorkoutId4(int workoutId4) {
        this.workoutId4 = workoutId4;
    }

    public int getWorkoutId5() {
        return workoutId5;
    }

    public void setWorkoutId5(int workoutId5) {
        this.workoutId5 = workoutId5;
    }

    public int getWorkoutId6() {
        return workoutId6;
    }

    public void setWorkoutId6(int workoutId6) {
        this.workoutId6 = workoutId6;
    }

    public int getWorkoutId7() {
        return workoutId7;
    }

    public void setWorkoutId7(int workoutId7) {
        this.workoutId7 = workoutId7;
    }
}
