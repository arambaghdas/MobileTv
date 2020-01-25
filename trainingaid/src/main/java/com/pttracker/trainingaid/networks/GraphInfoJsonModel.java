package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for deserializing Graph information from json string
 */


public class GraphInfoJsonModel {
    @SerializedName("goal")
    @Expose
    private String weightGoal;
    @SerializedName("goal_bmi")
    @Expose
    private String bmiGoal;
    @SerializedName("days_training")
    @Expose
    private String daysTraining;
    @SerializedName("current_weight")
    @Expose
    private String currentWeight;
    @SerializedName("current_bmi")
    @Expose
    private String currentBmi;
    @SerializedName("current_routine")
    @Expose
    private CurrentTrainingPlanJsonModel currentRoutine;
    @SerializedName("graph_data")
    @Expose
    private List<GraphInfoItemJsonModel> graphData = null;

    @SerializedName("trn_start_date")
    @Expose
    private String trn_start_date;

    @SerializedName("total_training_logs")
    @Expose
    private Integer total_training_logs;

    public String getTrn_start_date() {
        return trn_start_date;
    }

    public void setTrn_start_date(String trn_start_date) {
        this.trn_start_date = trn_start_date;
    }

    public Integer getTotal_training_logs() {
        return total_training_logs;
    }

    public void setTotal_training_logs(Integer total_training_logs) {
        this.total_training_logs = total_training_logs;
    }

    public String getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }

    public String getBmiGoal() {
        return bmiGoal;
    }

    public void setBmiGoal(String bmiGoal) {
        this.bmiGoal = bmiGoal;
    }

    public String getDaysTraining() {
        return daysTraining;
    }

    public void setDaysTraining(String daysTraining) {
        this.daysTraining = daysTraining;
    }

    public String getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(String currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getCurrentBmi() {
        return currentBmi;
    }

    public void setCurrentBmi(String currentBmi) {
        this.currentBmi = currentBmi;
    }

    public CurrentTrainingPlanJsonModel getCurrentRoutine() {
        return currentRoutine;
    }

    public void setCurrentRoutine(CurrentTrainingPlanJsonModel currentRoutine) {
        this.currentRoutine = currentRoutine;
    }

    public List<GraphInfoItemJsonModel> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphInfoItemJsonModel> graphData) {
        this.graphData = graphData;
    }
}
