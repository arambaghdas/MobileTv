package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Atif Ali
 * @since November 02, 2017 3:35 PM
 */

public class ExerciseJsonModel {
    @SerializedName("circuit_exercise_id")
    @Expose
    private Integer circuitExerciseId;
    @SerializedName("exercise_id")
    @Expose
    private Integer exerciseId;
    @SerializedName("exercisename")
    @Expose
    private String exercisename;
    @SerializedName("reps")
    @Expose
    private Integer reps;
    @SerializedName("LWeight")
    @Expose
    private Integer lweight;
    @SerializedName("calories")
    @Expose
    private Integer calories;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("set")
    @Expose
    private Integer set;
    @SerializedName("rest_time")
    @Expose
    private Integer restTime;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("youtubelink")
    @Expose
    private String youtubelink;
    @SerializedName("exerciseimage")
    @Expose
    private String exerciseimage;

    @SerializedName("cancel")
    @Expose
    private String cancel;

    @SerializedName("effort_zone")
    @Expose
    private String effort_zone;

    @SerializedName("distance")
    @Expose
    private String distance;


    @SerializedName("Category_name")
    @Expose
    private String Category_name;

    @SerializedName("exercise_description")
    @Expose
    private String exercise_description;

    @SerializedName("exercise_image")
    @Expose
    private String exercise_image;

    public String getExercise_image() {
        return exercise_image;
    }

    public void setExercise_image(String exercise_image) {
        this.exercise_image = exercise_image;
    }

    public String getExercise_description() {
        return exercise_description;
    }

    public void setExercise_description(String exercise_description) {
        this.exercise_description = exercise_description;
    }


    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getEffort_zone() {
        return effort_zone;
    }

    public void setEffort_zone(String effort_zone) {
        this.effort_zone = effort_zone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getCircuitExerciseId() {
        return circuitExerciseId;
    }


    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }

    public void setCircuitExerciseId(Integer circuitExerciseId) {
        this.circuitExerciseId = circuitExerciseId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExercisename() {
        return exercisename;
    }

    public void setExercisename(String exercisename) {
        this.exercisename = exercisename;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getLweight() {
        return lweight;
    }

    public void setLweight(Integer lweight) {
        this.lweight = lweight;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSet() {
        return set;
    }

    public void setSet(Integer set) {
        this.set = set;
    }

    public Integer getRestTime() {
        return restTime;
    }

    public void setRestTime(Integer restTime) {
        this.restTime = restTime;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYoutubelink() {
        return youtubelink;
    }

    public void setYoutubelink(String youtubelink) {
        this.youtubelink = youtubelink;
    }

    public String getExerciseimage() {
        return exerciseimage;
    }

    public void setExerciseimage(String exerciseimage) {
        this.exerciseimage = exerciseimage;
    }
}
