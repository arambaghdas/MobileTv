package com.pttrackershared.models.eventbus;

/**
 * Converts GraphInfoItemJsonModel to GraphInfoItem
 */


/**
 * Converts GraphInfoItemJsonModel to GraphInfoItem
 */
public class ExerciseJsonModelMapper {

    public Exercise convertToExercise(ExerciseJsonModel exerciseJsonModel) {
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
        exercise.setEffort_zone_show(exerciseJsonModel.getEffort_zone());

        if (exerciseJsonModel.getDistance() == null) exerciseJsonModel.setDistance("0");
        exercise.setDistance(exerciseJsonModel.getDistance());

        if (exerciseJsonModel.getExercise_image() != null)
            exercise.setImageName(exerciseJsonModel.getExercise_image());

        return exercise;
    }
}
