package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class GraphInfoItem implements Serializable {
    @SerializedName("update_id")
    @Expose
    private Integer updateId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    private String weight;
    private String date;
    @SerializedName("KW")
    @Expose
    private Integer kW;
    @SerializedName("BMI")
    @Expose
    private Float bmi;
    private String workoutname;
    private Integer status;
    @SerializedName("time_complete")
    @Expose
    private String timeComplete;
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
