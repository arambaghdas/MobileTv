package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pttrackershared.models.eventbus.ExerciseJsonModel;

import java.util.List;

/**
 * Model class for deserializing Circuit information from json string
 */

public class CircuitJsonModel {
    @SerializedName("circuit_id")
    @Expose
    private Integer circuitId;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("circuitname")
    @Expose
    private String circuitname;
    @SerializedName("exercises")
    @Expose
    private List<ExerciseJsonModel> exercises = null;

    @SerializedName("is_internal")
    @Expose
    private Integer is_internal;

    public Integer getIs_internal() {
        return is_internal;
    }

    public void setIs_internal(Integer is_internal) {
        this.is_internal = is_internal;
    }

    public Integer getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(Integer circuitId) {
        this.circuitId = circuitId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getCircuitname() {
        return circuitname;
    }

    public void setCircuitname(String circuitname) {
        this.circuitname = circuitname;
    }

    public List<ExerciseJsonModel> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseJsonModel> exercises) {
        this.exercises = exercises;
    }
}
