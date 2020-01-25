package com.pttrackershared.models.eventbus;
import com.pttrackershared.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class TrainingPlan {
    private Long id;
    private List<Workout> workoutList;
    private int trainingPlanId;
    private int trainerId;
    private int workout_id1;
    private int workout_id2;
    private int workout_id3;
    private int workout_id4;
    private int workout_id5;
    private int workout_id6;
    private int workout_id7;
    private int clientId;
    private String name;
    private int isActive;
    private boolean isSynced;
    private Date startDate;
    private Date finishDate;
    private String weight;

    public TrainingPlan() {
    }

    public TrainingPlan(Long id, int trainingPlanId, int trainerId, int workout_id1, int workout_id2,
                        int workout_id3, int workout_id4, int workout_id5, int workout_id6, int workout_id7,
                        int clientId, String name, int isActive, boolean isSynced, Date startDate, Date finishDate,
                        String weight) {
        this.id = id;
        this.trainingPlanId = trainingPlanId;
        this.trainerId = trainerId;
        this.workout_id1 = workout_id1;
        this.workout_id2 = workout_id2;
        this.workout_id3 = workout_id3;
        this.workout_id4 = workout_id4;
        this.workout_id5 = workout_id5;
        this.workout_id6 = workout_id6;
        this.workout_id7 = workout_id7;
        this.clientId = clientId;
        this.name = name;
        this.isActive = isActive;
        this.isSynced = isSynced;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.weight = weight;
    }
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
    public int getTrainerId() {
        return this.trainerId;
    }
    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }
    public int getClientId() {
        return this.clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIsActive() {
        return this.isActive;
    }
    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
    public boolean getIsSynced() {
        return this.isSynced;
    }
    public void setIsSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public String getStartDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.format(this.startDate);
    }

    public void setStartDateString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date date = format.parse(dateString);
            this.startDate = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getFinishDate() {
        return this.finishDate;
    }
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    public String getFinishDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.format(this.finishDate);
    }

    public void setFinishDateString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date date = format.parse(dateString);
            this.finishDate = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public int getWorkout_id1() {
        return workout_id1;
    }

    public void setWorkout_id1(int workout_id1) {
        this.workout_id1 = workout_id1;
    }

    public int getWorkout_id2() {
        return workout_id2;
    }

    public void setWorkout_id2(int workout_id2) {
        this.workout_id2 = workout_id2;
    }

    public int getWorkout_id3() {
        return workout_id3;
    }

    public void setWorkout_id3(int workout_id3) {
        this.workout_id3 = workout_id3;
    }

    public int getWorkout_id4() {
        return workout_id4;
    }

    public void setWorkout_id4(int workout_id4) {
        this.workout_id4 = workout_id4;
    }

    public int getWorkout_id5() {
        return workout_id5;
    }

    public void setWorkout_id5(int workout_id5) {
        this.workout_id5 = workout_id5;
    }

    public int getWorkout_id6() {
        return workout_id6;
    }

    public void setWorkout_id6(int workout_id6) {
        this.workout_id6 = workout_id6;
    }

    public int getWorkout_id7() {
        return workout_id7;
    }

    public void setWorkout_id7(int workout_id7) {
        this.workout_id7 = workout_id7;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }
}
