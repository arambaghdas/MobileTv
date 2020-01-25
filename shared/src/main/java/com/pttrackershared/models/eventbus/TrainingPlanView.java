package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for deserializing Plan view information from json string
 */

public class TrainingPlanView {
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
    private WorkoutView workoutId1;
    @SerializedName("workout_id2")
    @Expose
    private WorkoutView workoutId2;
    @SerializedName("workout_id3")
    @Expose
    private WorkoutView workoutId3;
    @SerializedName("workout_id4")
    @Expose
    private WorkoutView workoutId4;
    @SerializedName("workout_id5")
    @Expose
    private WorkoutView workoutId5;
    @SerializedName("workout_id6")
    @Expose
    private WorkoutView workoutId6;
    @SerializedName("workout_id7")
    @Expose
    private WorkoutView workoutId7;
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

    public WorkoutView getWorkoutId1() {
        return workoutId1;
    }

    public void setWorkoutId1(WorkoutView workoutId1) {
        this.workoutId1 = workoutId1;
    }

    public WorkoutView getWorkoutId2() {
        return workoutId2;
    }

    public void setWorkoutId2(WorkoutView workoutId2) {
        this.workoutId2 = workoutId2;
    }

    public WorkoutView getWorkoutId3() {
        return workoutId3;
    }

    public void setWorkoutId3(WorkoutView workoutId3) {
        this.workoutId3 = workoutId3;
    }

    public WorkoutView getWorkoutId4() {
        return workoutId4;
    }

    public void setWorkoutId4(WorkoutView workoutId4) {
        this.workoutId4 = workoutId4;
    }

    public WorkoutView getWorkoutId5() {
        return workoutId5;
    }

    public void setWorkoutId5(WorkoutView workoutId5) {
        this.workoutId5 = workoutId5;
    }

    public WorkoutView getWorkoutId6() {
        return workoutId6;
    }

    public void setWorkoutId6(WorkoutView workoutId6) {
        this.workoutId6 = workoutId6;
    }

    public WorkoutView getWorkoutId7() {
        return workoutId7;
    }

    public void setWorkoutId7(WorkoutView workoutId7) {
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

//    public static List<Workout> makeWorkouts(List<Workout> workoutJsonModelList) {
//
//        if (workoutJsonModelList == null) {
//            return new ArrayList<>();
//        }
//        List<Workout> workoutList = new ArrayList<>();
//
//        for (Workout workoutJsonModel : workoutJsonModelList) {
//            Workout workout = convertToWorkout(workoutJsonModel);
//            List<Circuit> circuitList = new ArrayList<>();
//            if (workoutJsonModel.getCircuitList() != null)
//                for (CircuitJsonModel circuitJsonModel : workoutJsonModel.getCircuitList()) {
//                    List<Exercise> exerciseList = new ArrayList<>();
//
//                    Circuit circuit = TrainingLog.convertToCircuit(circuitJsonModel);
//
//                    if (circuit.getExerciseList() != null) {
//                        for (Exercise exerciseJsonModel : circuit.getExerciseList()) {
//                            Exercise exercise = TrainingLog.convertToExercise(exerciseJsonModel);
//                            exerciseList.add(exercise);
//                        }
//                    }
//                    circuit.setExerciseList(exerciseList);
//                    circuitList.add(circuit);
//                }
//
//            workout.setCircuits(circuitList);
//            workoutList.add(workout);
//        }
//        return workoutList;
//    }

//    public static Workout convertToWorkout(Workout workoutJsonModel) {
//        Workout workout = new Workout();
//        workout.setWorkoutId(workoutJsonModel.getWorkoutId() != null ? workoutJsonModel.getWorkoutId() : 0);
//        workout.setTrainerId(workoutJsonModel.getTrainerId() != null ? workoutJsonModel.getTrainerId() : 0);
//        workout.setDuration(workoutJsonModel.getDuration() != null ? workoutJsonModel.getDuration() : 0);
//
//        if (workoutJsonModel.getWorkoutname() != null)
//            workout.setWorkoutname(workoutJsonModel.getWorkoutname());
//
//        return workout;
//    }
}
