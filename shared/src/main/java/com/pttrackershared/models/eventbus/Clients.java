package com.pttrackershared.models.eventbus;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Green dao uses this class as a type of tuple in it.
 */

public class Clients implements Serializable {

    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("clientemail")
    @Expose
    private String clientemail;
    @SerializedName("DoB")
    @Expose
    private String dob;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weightgoal")
    @Expose
    private String weightgoal;
    @SerializedName("PTemail")
    @Expose
    private String pTemail;
    @SerializedName("trainer_id")
    @Expose
    private Integer trainerId;
    @SerializedName("goal_date")
    @Expose
    private String goalDate;
    @SerializedName("BMI")
    @Expose
    private String bmi;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("Telephone")
    @Expose
    private String telephone;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("user_group")
    private Integer user_group;


    @SerializedName("package_item")
    private Integer package_item;

    @SerializedName("plan_name")
    @Expose
    private String plan_name;

    @SerializedName("is_active")
    @Expose
    private int is_active;

    @SerializedName("goal_description")
    @Expose
    private String goal_description;

    @SerializedName("fitness_goal")
    @Expose
    private int fitness_goal;

    @SerializedName("trn_date")
    @Expose
    private String trnDate;

    public String getTrnDate() {
        return trnDate;
    }

    public void setTrnDate(String trnDate) {
        this.trnDate = trnDate;
    }

    public int getFitness_goal() {
        return fitness_goal;
    }

    public void setFitness_goal(int fitness_goal) {
        this.fitness_goal = fitness_goal;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public Integer getUser_group() {
        return user_group;
    }

    public void setUser_group(Integer user_group) {
        this.user_group = user_group;
    }

    public Integer getPackage_item() {
        return package_item;
    }

    public void setPackage_item(Integer package_item) {
        this.package_item = package_item;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClientemail() {
        return clientemail;
    }

    public void setClientemail(String clientemail) {
        this.clientemail = clientemail;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeightgoal() {
        return weightgoal;
    }

    public void setWeightgoal(String weightgoal) {
        this.weightgoal = weightgoal;
    }

    public String getPTemail() {
        return pTemail;
    }

    public void setPTemail(String pTemail) {
        this.pTemail = pTemail;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(String goalDate) {
        this.goalDate = goalDate;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
