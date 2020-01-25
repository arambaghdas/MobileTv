package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Green dao uses this class as a type of tuple in it.
 */


public class Circuit implements Serializable {
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

    private Long id;
    private long workoutId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }

    /** Used to resolve relations */

    /** Used for active entity operations. */
    public Circuit(int circuitId, int trainerId, String name,
                   int is_internal) {

        this.circuitId = circuitId;
        this.trainerId = trainerId;
        this.circuitname = name;
        this.is_internal = is_internal;
    }
    public Circuit() {
    }

    public int getCircuitId() {
        return this.circuitId;
    }
    public void setCircuitId(int circuitId) {
        this.circuitId = circuitId;
    }
    public int getTrainerId() {
        return this.trainerId;
    }
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
    public String getName() {
        return this.circuitname;
    }
    public void setName(String name) {
        this.circuitname = name;
    }

    public int getIs_internal() {
        return is_internal;
    }

    public void setIs_internal(int is_internal) {
        this.is_internal = is_internal;
    }

    public synchronized void resetExerciseList() {
        exercises = null;
    }

    public void setExerciseList(List<ExerciseJsonModel> exerciseList) {
        this.exercises = exerciseList;
    }

    public List<ExerciseJsonModel> getExerciseList() {
        return exercises;
    }

    public static Circuit convertToCircuit(Circuit circuitJsonModel){
        Circuit circuit = new Circuit();
            circuit.setCircuitId(circuitJsonModel.getCircuitId());
            circuit.setTrainerId(circuitJsonModel.getTrainerId());
        if(circuitJsonModel.getName() != null)
            circuit.setName(circuitJsonModel.getName());
            circuit.setIs_internal(circuitJsonModel.getIs_internal());

        return circuit;
    }

}
