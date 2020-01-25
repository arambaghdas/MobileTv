package com.pttrackershared.models.eventbus;
import com.pttrackershared.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Converts WorkoutJsonModel to Workout
 */
public class WorkoutJsonModelMapper {

    public Workout convertToWorkout(WorkoutJsonModel workoutJsonModel) {
        Workout workout = new Workout();
//        workout.setClientWorkoutId(workoutJsonModel.getClientWorkoutId() != null ? workoutJsonModel.getClientWorkoutId() : 0);
        workout.setWorkoutId(workoutJsonModel.getWorkoutId() != null ? workoutJsonModel.getWorkoutId() : 0);
//        workout.setClientId(workoutJsonModel.getClientId() != null ? workoutJsonModel.getClientId() : 0);
        workout.setTrainerId(workoutJsonModel.getTrainerId() != null ? workoutJsonModel.getTrainerId() : 0);
//        workout.setStatus(workoutJsonModel.getWorkoutstatus() != null ? workoutJsonModel.getWorkoutstatus() : 0);
        workout.setDuration(workoutJsonModel.getDuration() != null ? workoutJsonModel.getDuration() : 0);

//        if (workoutJsonModel.getTimeCompleted() != null)
//            workout.setTimeCompleted(workoutJsonModel.getTimeCompleted());
//
//        if (workoutJsonModel.getFeedback() != null)
//            workout.setFeedback(workoutJsonModel.getFeedback());
//
//        if (workoutJsonModel.getMaxBpm() != null)
//            workout.setMaxBpm(workoutJsonModel.getMaxBpm());
//
//        if (workoutJsonModel.getMinBpm() != null)
//            workout.setMinBpm(workoutJsonModel.getMinBpm());
//
//        if (workoutJsonModel.getAvgBpm() != null)
//            workout.setAvgBpm(workoutJsonModel.getAvgBpm());

        if (workoutJsonModel.getWorkoutname() != null)
            workout.setName(workoutJsonModel.getWorkoutname());

//        if (workoutJsonModel.getDateCompleted() != null)
//        {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//            try {
//                workout.setDateCompleted(dateFormat.parse(workoutJsonModel.getDateCompleted()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
        return workout;
    }

    /**
     * Converts the workout json model and returns a TrainingLog
     * @param workoutJsonModel
     * @return
     */
    public TrainingLog convertToTrainingLog(WorkoutJsonModel workoutJsonModel) {
        TrainingLog trainingLog = new TrainingLog();
        trainingLog.setClientWorkoutId(workoutJsonModel.getClientWorkoutId() != null ? workoutJsonModel.getClientWorkoutId() : 0);
        trainingLog.setWorkoutId(workoutJsonModel.getWorkoutId() != null ? workoutJsonModel.getWorkoutId() : 0);
        trainingLog.setClientId(workoutJsonModel.getClientId() != null ? workoutJsonModel.getClientId() : 0);
        trainingLog.setTrainerId(workoutJsonModel.getTrainerId() != null ? workoutJsonModel.getTrainerId() : 0);
        trainingLog.setStatus(workoutJsonModel.getWorkoutstatus() != null ? workoutJsonModel.getWorkoutstatus() : 0);

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
}
