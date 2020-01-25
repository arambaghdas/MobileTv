package com.pttracker.trainingaid.models;
import java.util.List;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class GraphInfo {

    private Long id;


    private List<GraphInfoItem> graphInfoItemList;

    private String currentTrainingPlan;
    private float weightGoal;
    private float bmiGoal;
    private int daysTrained;
    private float currentWeight;
    private float currentBmi;

    private Integer total_training_logs;
    private String trn_start_date;

    public Integer getTotal_training_logs() {
        return total_training_logs;
    }

    public void setTotal_training_logs(Integer total_training_logs) {
        this.total_training_logs = total_training_logs;
    }

    public String getTrn_start_date() {
        return trn_start_date;
    }

    public void setTrn_start_date(String trn_start_date) {
        this.trn_start_date = trn_start_date;
    }



    public GraphInfo(Long id, String currentTrainingPlan, float weightGoal, float bmiGoal,
                     int daysTrained, float currentWeight, float currentBmi, Integer total_training_logs,
                     String trn_start_date) {
        this.id = id;
        this.currentTrainingPlan = currentTrainingPlan;
        this.weightGoal = weightGoal;
        this.bmiGoal = bmiGoal;
        this.daysTrained = daysTrained;
        this.currentWeight = currentWeight;
        this.currentBmi = currentBmi;
        this.total_training_logs = total_training_logs;
        this.trn_start_date = trn_start_date;
    }

    public GraphInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentTrainingPlan() {
        return this.currentTrainingPlan;
    }

    public void setCurrentTrainingPlan(String currentTrainingPlan) {
        this.currentTrainingPlan = currentTrainingPlan;
    }

    public float getWeightGoal() {
        return this.weightGoal;
    }

    public void setWeightGoal(float weightGoal) {
        this.weightGoal = weightGoal;
    }

    public float getBmiGoal() {
        return this.bmiGoal;
    }

    public void setBmiGoal(float bmiGoal) {
        this.bmiGoal = bmiGoal;
    }

    public int getDaysTrained() {
        return this.daysTrained;
    }

    public void setDaysTrained(int daysTrained) {
        this.daysTrained = daysTrained;
    }

    public float getCurrentWeight() {
        return this.currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public float getCurrentBmi() {
        return this.currentBmi;
    }

    public void setCurrentBmi(float currentBmi) {
        this.currentBmi = currentBmi;
    }
}
