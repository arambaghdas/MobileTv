package com.pttracker.trainingaid.models;


import java.util.Date;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class GraphInfoItem {

    private Long id;

    private Long graphInfoId;

    private float weight;
    private float bmi;
    private Date date;
    private String workoutName;
    private String timeComplete;

    public GraphInfoItem(Long id, Long graphInfoId, float weight, float bmi,
                         Date date, String workoutName, String timeComplete) {
        this.id = id;
        this.graphInfoId = graphInfoId;
        this.weight = weight;
        this.bmi = bmi;
        this.date = date;
        this.workoutName = workoutName;
        this.timeComplete = timeComplete;
    }

    public GraphInfoItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getGraphInfoId() {
        return this.graphInfoId;
    }
    public void setGraphInfoId(Long graphInfoId) {
        this.graphInfoId = graphInfoId;
    }
    public float getWeight() {
        return this.weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }
    public float getBmi() {
        return this.bmi;
    }
    public void setBmi(float bmi) {
        this.bmi = bmi;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getWorkoutName() {
        return this.workoutName;
    }
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
    public String getTimeComplete() {
        return this.timeComplete;
    }
    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }
}
