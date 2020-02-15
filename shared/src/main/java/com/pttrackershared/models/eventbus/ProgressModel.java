package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProgressModel implements Serializable {

    private String Title;
    private String Description;
    private String Picture;
    private String fitness_goal;
    private String training_plan;
    private String pic_plan;
    private String food_plan;
    @SerializedName("Mon Steps")
    private int monSteps;
    @SerializedName("Mon Km")
    private int monKm;
    @SerializedName("Mon Workout")
    private int monWorkout;
    @SerializedName("Mon Workoutcalories")
    private int monWorkoutCalories;
    @SerializedName("Mon WorkoutName")
    private String monWorkoutName;
    @SerializedName("Tue Steps")
    private int tueSteps;
    @SerializedName("Tue Km")
    private int tueKm;
    @SerializedName("Tue Workout")
    private int tueWorkout;
    @SerializedName("Tue Workoutcalories")
    private int tueWorkoutCalories;
    @SerializedName("Tue WorkoutName")
    private String tueWorkoutName;
    @SerializedName("Wed Steps")
    private int wedSteps;
    @SerializedName("Wed Km")
    private int wedKm;
    @SerializedName("Wed Workout")
    private int wedWorkout;
    @SerializedName("Wed Workoutcalories")
    private int wedWorkoutCalories;
    @SerializedName("Wed WorkoutName")
    private String wedWorkoutName;
    @SerializedName("Thu Steps")
    private int thuSteps;
    @SerializedName("Thu Km")
    private int thuKm;
    @SerializedName("Thu Workout")
    private int thuWorkout;
    @SerializedName("Thu Workoutcalories")
    private int thuWorkoutCalories;

    public int getMonWorkoutCalories() {
        return monWorkoutCalories;
    }

    public void setMonWorkoutCalories(int monWorkoutCalories) {
        this.monWorkoutCalories = monWorkoutCalories;
    }

    public int getTueWorkoutCalories() {
        return tueWorkoutCalories;
    }

    public void setTueWorkoutCalories(int tueWorkoutCalories) {
        this.tueWorkoutCalories = tueWorkoutCalories;
    }

    public int getWedWorkoutCalories() {
        return wedWorkoutCalories;
    }

    public void setWedWorkoutCalories(int wedWorkoutCalories) {
        this.wedWorkoutCalories = wedWorkoutCalories;
    }

    public int getThuWorkoutCalories() {
        return thuWorkoutCalories;
    }

    public void setThuWorkoutCalories(int thuWorkoutCalories) {
        this.thuWorkoutCalories = thuWorkoutCalories;
    }

    public int getFriWorkoutCalories() {
        return friWorkoutCalories;
    }

    public void setFriWorkoutCalories(int friWorkoutCalories) {
        this.friWorkoutCalories = friWorkoutCalories;
    }

    public int getSatWorkoutCalories() {
        return satWorkoutCalories;
    }

    public void setSatWorkoutCalories(int satWorkoutCalories) {
        this.satWorkoutCalories = satWorkoutCalories;
    }

    public int getSunWorkoutCalories() {
        return sunWorkoutCalories;
    }

    public void setSunWorkoutCalories(int sunWorkoutCalories) {
        this.sunWorkoutCalories = sunWorkoutCalories;
    }

    @SerializedName("Thu WorkoutName")
    private String thuWorkoutName;
    @SerializedName("Fri Steps")
    private int friSteps;
    @SerializedName("Fri Km")
    private int friKm;
    @SerializedName("Fri Workout")
    private int friWorkout;
    @SerializedName("Fri Workoutcalories")
    private int friWorkoutCalories;
    @SerializedName("Fri WorkoutName")
    private String friWorkoutName;
    @SerializedName("Sat Steps")
    private int satSteps;
    @SerializedName("Sat Km")
    private int satKm;
    @SerializedName("Sat Workout")
    private int satWorkout;
    @SerializedName("Sat Workoutcalories")
    private int satWorkoutCalories;
    @SerializedName("Sat WorkoutName")
    private String satWorkoutName;
    @SerializedName("Sun Steps")
    private int sunSteps;
    @SerializedName("Sun Km")
    private int sunKm;
    @SerializedName("Sun Workout")
    private int sunWorkout;
    @SerializedName("Sun Workoutcalories")
    private int sunWorkoutCalories;
    @SerializedName("Sun WorkoutName")
    private String sunWorkoutName;

    public String getMonWorkoutName() {
        return monWorkoutName;
    }

    public void setMonWorkoutName(String monWorkoutName) {
        this.monWorkoutName = monWorkoutName;
    }

    public String getTueWorkoutName() {
        return tueWorkoutName;
    }

    public void setTueWorkoutName(String tueWorkoutName) {
        this.tueWorkoutName = tueWorkoutName;
    }

    public String getWedWorkoutName() {
        return wedWorkoutName;
    }

    public void setWedWorkoutName(String wedWorkoutName) {
        this.wedWorkoutName = wedWorkoutName;
    }

    public String getThuWorkoutName() {
        return thuWorkoutName;
    }

    public void setThuWorkoutName(String thuWorkoutName) {
        this.thuWorkoutName = thuWorkoutName;
    }

    public String getFriWorkoutName() {
        return friWorkoutName;
    }

    public void setFriWorkoutName(String friWorkoutName) {
        this.friWorkoutName = friWorkoutName;
    }

    public String getSatWorkoutName() {
        return satWorkoutName;
    }

    public void setSatWorkoutName(String satWorkoutName) {
        this.satWorkoutName = satWorkoutName;
    }

    public String getSunWorkoutName() {
        return sunWorkoutName;
    }

    public void setSunWorkoutName(String sunWorkoutName) {
        this.sunWorkoutName = sunWorkoutName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getFitness_goal() {
        return fitness_goal;
    }

    public void setFitness_goal(String fitness_goal) {
        this.fitness_goal = fitness_goal;
    }

    public String getTraining_plan() {
        return training_plan;
    }

    public void setTraining_plan(String training_plan) {
        this.training_plan = training_plan;
    }

    public String getPic_plan() {
        return pic_plan;
    }

    public void setPic_plan(String pic_plan) {
        this.pic_plan = pic_plan;
    }

    public String getFood_plan() {
        return food_plan;
    }

    public void setFood_plan(String food_plan) {
        this.food_plan = food_plan;
    }

    public int getMonSteps() {
        return monSteps;
    }

    public void setMonSteps(int monSteps) {
        this.monSteps = monSteps;
    }

    public int getMonKm() {
        return monKm;
    }

    public void setMonKm(int monKm) {
        this.monKm = monKm;
    }

    public int getMonWorkout() {
        return monWorkout;
    }

    public void setMonWorkout(int monWorkout) {
        this.monWorkout = monWorkout;
    }

    public int getTueSteps() {
        return tueSteps;
    }

    public void setTueSteps(int tueSteps) {
        this.tueSteps = tueSteps;
    }

    public int getTueKm() {
        return tueKm;
    }

    public void setTueKm(int tueKm) {
        this.tueKm = tueKm;
    }

    public int getTueWorkout() {
        return tueWorkout;
    }

    public void setTueWorkout(int tueWorkout) {
        this.tueWorkout = tueWorkout;
    }

    public int getWedSteps() {
        return wedSteps;
    }

    public void setWedSteps(int wedSteps) {
        this.wedSteps = wedSteps;
    }

    public int getWedKm() {
        return wedKm;
    }

    public void setWedKm(int wedKm) {
        this.wedKm = wedKm;
    }

    public int getWedWorkout() {
        return wedWorkout;
    }

    public void setWedWorkout(int wedWorkout) {
        this.wedWorkout = wedWorkout;
    }

    public int getThuSteps() {
        return thuSteps;
    }

    public void setThuSteps(int thuSteps) {
        this.thuSteps = thuSteps;
    }

    public int getThuKm() {
        return thuKm;
    }

    public void setThuKm(int thuKm) {
        this.thuKm = thuKm;
    }

    public int getThuWorkout() {
        return thuWorkout;
    }

    public void setThuWorkout(int thuWorkout) {
        this.thuWorkout = thuWorkout;
    }

    public int getFriSteps() {
        return friSteps;
    }

    public void setFriSteps(int friSteps) {
        this.friSteps = friSteps;
    }

    public int getFriKm() {
        return friKm;
    }

    public void setFriKm(int friKm) {
        this.friKm = friKm;
    }

    public int getFriWorkout() {
        return friWorkout;
    }

    public void setFriWorkout(int friWorkout) {
        this.friWorkout = friWorkout;
    }

    public int getSatSteps() {
        return satSteps;
    }

    public void setSatSteps(int satSteps) {
        this.satSteps = satSteps;
    }

    public int getSatKm() {
        return satKm;
    }

    public void setSatKm(int satKm) {
        this.satKm = satKm;
    }

    public int getSatWorkout() {
        return satWorkout;
    }

    public void setSatWorkout(int satWorkout) {
        this.satWorkout = satWorkout;
    }

    public int getSunSteps() {
        return sunSteps;
    }

    public void setSunSteps(int sunSteps) {
        this.sunSteps = sunSteps;
    }

    public int getSunKm() {
        return sunKm;
    }

    public void setSunKm(int sunKm) {
        this.sunKm = sunKm;
    }

    public int getSunWorkout() {
        return sunWorkout;
    }

    public void setSunWorkout(int sunWorkout) {
        this.sunWorkout = sunWorkout;
    }
}
