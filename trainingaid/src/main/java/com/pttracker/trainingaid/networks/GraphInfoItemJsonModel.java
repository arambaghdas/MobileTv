package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing Graph item information from json string
 */


public class GraphInfoItemJsonModel {
    @SerializedName("update_id")
    @Expose
    private Integer updateId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("KW")
    @Expose
    private Integer kW;
    @SerializedName("BMI")
    @Expose
    private Float bmi;
    @SerializedName("workoutname")
    @Expose
    private String workoutname;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("time_complete")
    @Expose
    private String timeComplete;
    @SerializedName("workoutstatus")
    @Expose
    private Integer workoutstatus;

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getKW() {
        return kW;
    }

    public void setKW(Integer kW) {
        this.kW = kW;
    }

    public Float getBmi() {
        return bmi;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public String getWorkoutname() {
        return workoutname;
    }

    public void setWorkoutname(String workoutname) {
        this.workoutname = workoutname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public Integer getWorkoutstatus() {
        return workoutstatus;
    }

    public void setWorkoutstatus(Integer workoutstatus) {
        this.workoutstatus = workoutstatus;
    }
}
