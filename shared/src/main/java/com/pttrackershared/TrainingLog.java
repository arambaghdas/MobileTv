package com.pttrackershared;
import com.pttrackershared.models.eventbus.Circuit;
import com.pttrackershared.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Green dao uses this class as a type of tuple in it.
 */
public class TrainingLog {

    private Long id;
    private int trainingPlanId;
    private List<Circuit> circuitList;

    private int clientWorkoutId;
    private int workoutId;
    private int clientId;
    private int trainerId;
    private int status;
    private String dateCompleted;
    private String timeCompleted;
    private String feedback;
    private String maxBpm;
    private String minBpm;
    private String avgBpm;
    private String name;
    private boolean isSynced;
    private String calories;

    private int effort_zone1;
    private int effort_zone2;
    private int effort_zone3;
    private int effort_zone4;
    private int effort_zone5;

    private int planOrLiveWorkout;

    public TrainingLog(Long id, int trainingPlanId, int clientWorkoutId, int workoutId,
                       int clientId, int trainerId, int status, String dateCompleted,
                       String timeCompleted, String feedback, String maxBpm, String minBpm,
                       String avgBpm, String name, boolean isSynced, String calories, int effort_zone1,
                       int effort_zone2, int effort_zone3, int effort_zone4, int effort_zone5,
                       int planOrLiveWorkout) {
        this.id = id;
        this.trainingPlanId = trainingPlanId;
        this.clientWorkoutId = clientWorkoutId;
        this.workoutId = workoutId;
        this.clientId = clientId;
        this.trainerId = trainerId;
        this.status = status;
        this.dateCompleted = dateCompleted;
        this.timeCompleted = timeCompleted;
        this.feedback = feedback;
        this.maxBpm = maxBpm;
        this.minBpm = minBpm;
        this.avgBpm = avgBpm;
        this.name = name;
        this.isSynced = isSynced;
        this.calories = calories;
        this.effort_zone1 = effort_zone1;
        this.effort_zone2 = effort_zone2;
        this.effort_zone3 = effort_zone3;
        this.effort_zone4 = effort_zone4;
        this.effort_zone5 = effort_zone5;
        this.planOrLiveWorkout = planOrLiveWorkout;
    }

    public int getPlanOrLiveWorkout() {
        return planOrLiveWorkout;
    }

    public void setPlanOrLiveWorkout(int planOrLiveWorkout) {
        this.planOrLiveWorkout = planOrLiveWorkout;
    }

    public TrainingLog() {
    }


    public int getEffort_zone1() {
        return effort_zone1;
    }

    public void setEffort_zone1(int effort_zone1) {
        this.effort_zone1 = effort_zone1;
    }

    public int getEffort_zone2() {
        return effort_zone2;
    }

    public void setEffort_zone2(int effort_zone2) {
        this.effort_zone2 = effort_zone2;
    }

    public int getEffort_zone3() {
        return effort_zone3;
    }

    public void setEffort_zone3(int effort_zone3) {
        this.effort_zone3 = effort_zone3;
    }

    public int getEffort_zone4() {
        return effort_zone4;
    }

    public void setEffort_zone4(int effort_zone4) {
        this.effort_zone4 = effort_zone4;
    }

    public int getEffort_zone5() {
        return effort_zone5;
    }

    public void setEffort_zone5(int effort_zone5) {
        this.effort_zone5 = effort_zone5;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTrainingPlanId() {
        return this.trainingPlanId;
    }

    public void setTrainingPlanId(int trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public int getClientWorkoutId() {
        return this.clientWorkoutId;
    }

    public void setClientWorkoutId(int clientWorkoutId) {
        this.clientWorkoutId = clientWorkoutId;
    }

    public int getWorkoutId() {
        return this.workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTrainerId() {
        return this.trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateCompleted() {
        return this.dateCompleted;
    }

    public void setDateCompleted(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        this.dateCompleted = dateFormat.format(date);
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getTimeCompleted() {
        return this.timeCompleted;
    }

    public void setTimeCompleted(String timeCompleted) {
        this.timeCompleted = timeCompleted;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMaxBpm() {
        return this.maxBpm;
    }

    public void setMaxBpm(String maxBpm) {
        this.maxBpm = maxBpm;
    }

    public String getMinBpm() {
        return this.minBpm;
    }

    public void setMinBpm(String minBpm) {
        this.minBpm = minBpm;
    }

    public String getAvgBpm() {
        return this.avgBpm;
    }

    public void setAvgBpm(String avgBpm) {
        this.avgBpm = avgBpm;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }


    public boolean isSynced() {
        return isSynced;
    }

    public void setCircuitList(List<Circuit> circuitList) {
        this.circuitList = circuitList;
    }

    public boolean getIsSynced() {
        return this.isSynced;
    }

    public String getCalories() {
        return this.calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

}
