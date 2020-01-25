package com.pttrackershared.models.eventbus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nitinsharma on 18-10-2018.
 */

public class FitnessGoalModel {

    @SerializedName("fit_goal_id")
    @Expose
    int fit_goal_id;
    @SerializedName("trainer_id")
    @Expose
    int trainer_id;
    @SerializedName("alias")
    @Expose
    String alias;
    @SerializedName("goal_description")
    @Expose
    String goal_description;
    @SerializedName("plan_id")
    @Expose
    int plan_id;
    @SerializedName("intensity")
    @Expose
    int intensity;

    String pic = "";

    String days = "";

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public FitnessGoalModel(int fit_goal_id, int trainer_id, String alias, String goal_description, int plan_id, int intensity) {
        this.fit_goal_id = fit_goal_id;
        this.trainer_id = trainer_id;
        this.alias = alias;
        this.goal_description = goal_description;
        this.plan_id = plan_id;
        this.intensity = intensity;
    }

    public int getFit_goal_id() {
        return fit_goal_id;
    }

    public void setFit_goal_id(int fit_goal_id) {
        this.fit_goal_id = fit_goal_id;
    }

    public int getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(int trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    @Override
    public String toString() {
        return getGoal_description();
    }
}
