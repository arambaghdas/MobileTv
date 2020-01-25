package com.pttrackershared.models.eventbus;

import com.pttrackershared.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

    public static List<TrainingLog> makeTrainingLogs(List<WorkoutJsonModel> workoutJsonModelList) {

        if (workoutJsonModelList == null) {
            return new ArrayList<>();
        }

        List<TrainingLog> workoutList = new ArrayList<>();

        for (WorkoutJsonModel workoutJsonModel : workoutJsonModelList) {
            TrainingLog workout = convertToTrainingLog(workoutJsonModel);
            List<Circuit> circuitList = new ArrayList<>();
            if (workoutJsonModel.getCircuits() != null)
                for (CircuitJsonModel circuitJsonModel : workoutJsonModel.getCircuits()) {
                    List<Exercise> exerciseList = new ArrayList<>();

                    Circuit circuit = convertToCircuit(circuitJsonModel);

                    if (circuit.getExerciseList() != null) {
                        for (ExerciseJsonModel exerciseJsonModel : circuit.getExerciseList()) {
                            Exercise exercise = convertToExercise(exerciseJsonModel);
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

    public static TrainingLog convertToTrainingLog(WorkoutJsonModel workoutJsonModel) {
        TrainingLog trainingLog = new TrainingLog();
        trainingLog.setClientWorkoutId(workoutJsonModel.getClientWorkoutId() != null ? workoutJsonModel.getClientWorkoutId() : 0);
        trainingLog.setWorkoutId(workoutJsonModel.getWorkoutId());
        trainingLog.setClientId(workoutJsonModel.getClientId() != null ? workoutJsonModel.getClientId() : 0);
        trainingLog.setTrainerId(workoutJsonModel.getTrainerId());
        trainingLog.setStatus(workoutJsonModel.getWorkoutstatus());

        if (workoutJsonModel.getTimeCompleted() != null)
            trainingLog.setTimeCompleted(workoutJsonModel.getTimeCompleted());

        if (workoutJsonModel.getFeedback() != null)
            trainingLog.setFeedback(workoutJsonModel.getFeedback());

        if (workoutJsonModel.getMaxBpm() != null)
            trainingLog.setMaxBpm(workoutJsonModel.getMaxBpm());

        if (workoutJsonModel.getMinBpm() != null)
            trainingLog.setMinBpm(workoutJsonModel.getMinBpm());

        if (workoutJsonModel.getAvgBpm() != null)
            trainingLog.setAvgBpm(workoutJsonModel.getAvgBpm());

        if (workoutJsonModel.getWorkoutname() != null)
            trainingLog.setName(workoutJsonModel.getWorkoutname());



        if (workoutJsonModel.getDateCompleted() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
//                trainingLog.setDateCompleted(dateFormat.parse(workoutJsonModel.getDateCompleted()));
                trainingLog.setDateCompleted(workoutJsonModel.getDateCompleted());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (workoutJsonModel.getCalories() != null)
            trainingLog.setCalories(workoutJsonModel.getCalories());
        else{
            trainingLog.setCalories("0");
        }
        return trainingLog;
    }

    public static Circuit convertToCircuit(CircuitJsonModel circuitJsonModel){
        Circuit circuit = new Circuit();
            circuit.setCircuitId(circuitJsonModel.getCircuitId());
            circuit.setTrainerId(circuitJsonModel.getTrainerId());
        if(circuitJsonModel.getCircuitname() != null)
            circuit.setName(circuitJsonModel.getCircuitname());
            circuit.setIs_internal(circuitJsonModel.getIs_internal());

        return circuit;
    }

    public static Exercise convertToExercise(ExerciseJsonModel exerciseJsonModel) {
        Exercise exercise = new Exercise();
        if (exerciseJsonModel.getCircuitExerciseId() != null)
            exercise.setCircuitExerciseId(exerciseJsonModel.getCircuitExerciseId());

        if (exerciseJsonModel.getExerciseId() != null)
            exercise.setExerciseId(exerciseJsonModel.getExerciseId());

        if (exerciseJsonModel.getExercisename() != null)
            exercise.setName(exerciseJsonModel.getExercisename());

        if (exerciseJsonModel.getReps() != null)
            exercise.setReps(exerciseJsonModel.getReps());

        if (exerciseJsonModel.getLweight() != null)
            exercise.setWeight(exerciseJsonModel.getLweight());

        if (exerciseJsonModel.getCalories() != null)
            exercise.setCalories(exerciseJsonModel.getCalories());

        if (exerciseJsonModel.getDuration() != null)
            exercise.setDuration(exerciseJsonModel.getDuration());

        if (exerciseJsonModel.getSet() != null)
            exercise.setSet(exerciseJsonModel.getSet());

        if (exerciseJsonModel.getRestTime() != null)
            exercise.setRestTime(exerciseJsonModel.getRestTime());

        if (exerciseJsonModel.getTrainerId() != null)
            exercise.setTrainerId(exerciseJsonModel.getTrainerId());

        if (exerciseJsonModel.getCategory() != null)
            exercise.setCategory(exerciseJsonModel.getCategory());

        if (exerciseJsonModel.getYoutubelink() != null)
            exercise.setYoutubeLink(exerciseJsonModel.getYoutubelink());

        if (exerciseJsonModel.getExerciseimage() != null)
            exercise.setImageLink(exerciseJsonModel.getExerciseimage());


        if (exerciseJsonModel.getCategory_name() != null)
            exercise.setCategory_name(exerciseJsonModel.getCategory_name());

        if (exerciseJsonModel.getExercise_description() != null)
            exercise.setExercise_description(exerciseJsonModel.getExercise_description());

        if (exerciseJsonModel.getCancel() != null)
            exercise.setCancel(exerciseJsonModel.getCancel());

        if (exerciseJsonModel.getEffort_zone() == null) exerciseJsonModel.setEffort_zone("0");
        exercise.setEffort_zone(exerciseJsonModel.getEffort_zone());

        if (exerciseJsonModel.getDistance() == null) exerciseJsonModel.setDistance("0");
        exercise.setDistance(exerciseJsonModel.getDistance());

        if (exerciseJsonModel.getExercise_image() != null)
            exercise.setImageName(exerciseJsonModel.getExercise_image());

        return exercise;
    }

    public static ExerciseJsonModel convertFromExercise(Exercise exercise) {
        ExerciseJsonModel exerciseJsonModel = new ExerciseJsonModel();

        exerciseJsonModel.setCircuitExerciseId(exercise.getCircuitExerciseId());
        exerciseJsonModel.setExerciseId(exercise.getExerciseId());

        if (exercise.getName() != null)
            exerciseJsonModel.setExercisename(exercise.getName());

            exerciseJsonModel.setReps(exercise.getReps());
            exerciseJsonModel.setLweight(exercise.getWeight());
            exerciseJsonModel.setCalories(exercise.getCalories());
            exerciseJsonModel.setDuration(exercise.getDuration());
            exerciseJsonModel.setSet(exercise.getSet());
            exercise.setRestTime(exercise.getRestTime());
            exercise.setTrainerId(exercise.getTrainerId());
            exerciseJsonModel.setCategory(exercise.getCategory());

        if (exercise.getYoutubeLink() != null)
            exerciseJsonModel.setYoutubelink(exercise.getYoutubeLink());

        if (exercise.getImageName() != null)
            exerciseJsonModel.setExercise_image(exercise.getImageName());

        if (exercise.getCategory_name() != null)
            exerciseJsonModel.setCategory_name(exercise.getCategory_name());

        if (exercise.getExercise_description() != null)
            exerciseJsonModel.setExercise_description(exercise.getExercise_description());

        if (exercise.getCancel() != null)
            exerciseJsonModel.setCancel(exercise.getCancel());

        if (exercise.getEffort_zone() == null) exercise.setEffort_zone("0");
        exerciseJsonModel.setEffort_zone(exercise.getEffort_zone());

        if (exercise.getDistance() == null) exercise.setDistance("0");
        exerciseJsonModel.setDistance(exercise.getDistance());

        if (exercise.getImageName() != null)
            exerciseJsonModel.setExercise_image(exercise.getImageName());

        return exerciseJsonModel;
    }
}