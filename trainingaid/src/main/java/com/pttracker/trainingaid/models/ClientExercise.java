package com.pttracker.trainingaid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nitinsharma on 10-12-2018.
 */

public class ClientExercise {
    @SerializedName("client_exercise_id")
    @Expose
    int client_exercise_id;
    @SerializedName("trainer_id")
    @Expose
    int trainer_id;
    @SerializedName("client_id")
    @Expose
    int client_id;
    @SerializedName("users_plan_id")
    @Expose
    int users_plan_id;
    @SerializedName("clients_plan_id")
    @Expose
    int clients_plan_id;
    @SerializedName("workout_id")
    @Expose
    int workout_id;
    @SerializedName("exercise_id")
    @Expose
    int exercise_id;
    @SerializedName("date_exercise")
    @Expose
    String date_exercise;
    @SerializedName("reps")
    @Expose
    int reps;
    @SerializedName("LWeight")
    @Expose
    int LWeght;
    @SerializedName("calories")
    @Expose
    int calories;
    @SerializedName("duration")
    @Expose
    int duration;
    @SerializedName("rest_time")
    @Expose
    int rest_time;
    @SerializedName("order_no")
    @Expose
    int order_no;
    @SerializedName("Time_completed")
    @Expose
    int Time_completed;
    @SerializedName("Max_BPM")
    @Expose
    int Max_BPM;
    @SerializedName("Min_BPM")
    @Expose
    int Min_BPM;
    @SerializedName("Avg_BPM")
    @Expose
    int Avg_BPM;
    @SerializedName("effort_zone")
    @Expose
    int effort_zone;
    @SerializedName("cancel")
    @Expose
    int cancel;
    @SerializedName("effort_zone1")
    @Expose
    int effort_zone1;
    @SerializedName("effort_zone2")
    @Expose
    int effort_zone2;
    @SerializedName("effort_zone3")
    @Expose
    int effort_zone3;
    @SerializedName("effort_zone4")
    @Expose
    int effort_zone4;
    @SerializedName("effort_zone5")
    @Expose
    int effort_zone5;




    public int getClient_exercise_id() {
        return client_exercise_id;
    }

    public void setClient_exercise_id(int client_exercise_id) {
        this.client_exercise_id = client_exercise_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUsers_plan_id() {
        return users_plan_id;
    }

    public void setUsers_plan_id(int users_plan_id) {
        this.users_plan_id = users_plan_id;
    }

    public int getClients_plan_id() {
        return clients_plan_id;
    }

    public void setClients_plan_id(int clients_plan_id) {
        this.clients_plan_id = clients_plan_id;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public int getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getDate_exercise() {
        return date_exercise;
    }

    public void setDate_exercise(String date_exercise) {
        this.date_exercise = date_exercise;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getLWeght() {
        return LWeght;
    }

    public void setLWeght(int LWeght) {
        this.LWeght = LWeght;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRest_time() {
        return rest_time;
    }

    public void setRest_time(int rest_time) {
        this.rest_time = rest_time;
    }

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public int getTime_completed() {
        return Time_completed;
    }

    public void setTime_completed(int time_completed) {
        Time_completed = time_completed;
    }

    public int getMax_BPM() {
        return Max_BPM;
    }

    public void setMax_BPM(int max_BPM) {
        Max_BPM = max_BPM;
    }

    public int getMin_BPM() {
        return Min_BPM;
    }

    public void setMin_BPM(int min_BPM) {
        Min_BPM = min_BPM;
    }

    public int getAvg_BPM() {
        return Avg_BPM;
    }

    public void setAvg_BPM(int avg_BPM) {
        Avg_BPM = avg_BPM;
    }

    public int getEffort_zone() {
        return effort_zone;
    }

    public void setEffort_zone(int effort_zone) {
        this.effort_zone = effort_zone;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
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
}

