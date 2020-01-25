package com.pttrackershared.models.eventbus;

import java.util.ArrayList;
import java.util.List;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class Workout {

    private Long id;

    private long trainingPlanId;
    private List<Circuit> circuitList;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Circuit> getCircuitList() {
        return circuitList;
    }

    public void setCircuitList(List<Circuit> circuitList) {
        this.circuitList = circuitList;
    }

    private int workoutId;
    private int trainerId;
    private int status;
    private String name;

    private int duration;

    public Workout() {
    }

    public Workout(Long id, long trainingPlanId, int workoutId, int trainerId, int status,
                   String name, int duration) {
        this.id = id;
        this.trainingPlanId = trainingPlanId;
        this.workoutId = workoutId;
        this.trainerId = trainerId;
        this.status = status;
        this.name = name;
        this.duration = duration;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTrainingPlanId() {
        return this.trainingPlanId;
    }

    public void setTrainingPlanId(long trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public int getWorkoutId() {
        return this.workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public int getTrainerId() {
        return this.trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static List<Workout> makeWorkouts(List<WorkoutJsonModel> workoutJsonModelList) {

        if (workoutJsonModelList == null) {
            return new ArrayList<>();
        }

        WorkoutJsonModelMapper workoutJsonModelMapper = new WorkoutJsonModelMapper();
        CircuitJsonModelMapper circuitJsonModelMapper = new CircuitJsonModelMapper();
        ExerciseJsonModelMapper exerciseJsonModelMapper = new ExerciseJsonModelMapper();

        List<Workout> workoutList = new ArrayList<>();

        for (WorkoutJsonModel workoutJsonModel : workoutJsonModelList) {
            Workout workout = workoutJsonModelMapper.convertToWorkout(workoutJsonModel);
            List<Circuit> circuitList = new ArrayList<>();
            if (workoutJsonModel.getCircuits() != null)
                for (CircuitJsonModel circuitJsonModel : workoutJsonModel.getCircuits()) {
                    List<Exercise> exerciseList = new ArrayList<>();

                    Circuit circuit = circuitJsonModelMapper.convertToCircuit(circuitJsonModel);

                    if (circuitJsonModel.getExercises() != null) {
                        for (ExerciseJsonModel exerciseJsonModel : circuitJsonModel.getExercises()) {
                            Exercise exercise = exerciseJsonModelMapper.convertToExercise(exerciseJsonModel);
                            exerciseList.add(exercise);
                        }
                    }
//                    circuit.setExerciseList(exerciseList);
                    circuitList.add(circuit);
                }

            workout.setCircuitList(circuitList);
            workoutList.add(workout);
        }
        return workoutList;
    }
}
