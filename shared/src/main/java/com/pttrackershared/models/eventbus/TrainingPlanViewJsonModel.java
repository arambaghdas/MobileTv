package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing Plan view information from json string
 */

public class TrainingPlanViewJsonModel {
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
    @SerializedName("workout_id1")
    @Expose
    private WorkoutViewJsonModel workoutId1;
    @SerializedName("workout_id2")
    @Expose
    private WorkoutViewJsonModel workoutId2;
    @SerializedName("workout_id3")
    @Expose
    private WorkoutViewJsonModel workoutId3;
    @SerializedName("workout_id4")
    @Expose
    private WorkoutViewJsonModel workoutId4;
    @SerializedName("workout_id5")
    @Expose
    private WorkoutViewJsonModel workoutId5;
    @SerializedName("workout_id6")
    @Expose
    private WorkoutViewJsonModel workoutId6;
    @SerializedName("workout_id7")
    @Expose
    private WorkoutViewJsonModel workoutId7;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("trn_start_date")
    @Expose
    private String trnStartDate;
    @SerializedName("trn_finish_date")
    @Expose
    private String trnFinishDate;

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

    public WorkoutViewJsonModel getWorkoutId1() {
        return workoutId1;
    }

    public void setWorkoutId1(WorkoutViewJsonModel workoutId1) {
        this.workoutId1 = workoutId1;
    }

    public WorkoutViewJsonModel getWorkoutId2() {
        return workoutId2;
    }

    public void setWorkoutId2(WorkoutViewJsonModel workoutId2) {
        this.workoutId2 = workoutId2;
    }

    public WorkoutViewJsonModel getWorkoutId3() {
        return workoutId3;
    }

    public void setWorkoutId3(WorkoutViewJsonModel workoutId3) {
        this.workoutId3 = workoutId3;
    }

    public WorkoutViewJsonModel getWorkoutId4() {
        return workoutId4;
    }

    public void setWorkoutId4(WorkoutViewJsonModel workoutId4) {
        this.workoutId4 = workoutId4;
    }

    public WorkoutViewJsonModel getWorkoutId5() {
        return workoutId5;
    }

    public void setWorkoutId5(WorkoutViewJsonModel workoutId5) {
        this.workoutId5 = workoutId5;
    }

    public WorkoutViewJsonModel getWorkoutId6() {
        return workoutId6;
    }

    public void setWorkoutId6(WorkoutViewJsonModel workoutId6) {
        this.workoutId6 = workoutId6;
    }

    public WorkoutViewJsonModel getWorkoutId7() {
        return workoutId7;
    }

    public void setWorkoutId7(WorkoutViewJsonModel workoutId7) {
        this.workoutId7 = workoutId7;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getTrnStartDate() {
        return trnStartDate;
    }

    public void setTrnStartDate(String trnStartDate) {
        this.trnStartDate = trnStartDate;
    }

    public String getTrnFinishDate() {
        return trnFinishDate;
    }

    public void setTrnFinishDate(String trnFinishDate) {
        this.trnFinishDate = trnFinishDate;
    }
}
