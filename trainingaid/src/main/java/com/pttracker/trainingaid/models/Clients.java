package com.pttracker.trainingaid.models;

import com.pttrackershared.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Green dao uses this class as a type of tuple in it.
 */


public class Clients {

    private Long id;

    private int userId;
    private String firstName;
    private String surName;
    private String clientEmail;
    private Date dob;
    private String height;
    private String weightGoal;
    private String ptEmail;
    private int trainerId;
    private Date goalDate;
    private String bmi;
    private String image;
    private String accessToken;
    private String telephone;
    private String weight;
    private String gender;

    private int user_group;
    private int package_item;
    private String plan_name;
    private int is_active;
    private String goal_description;
    private int fitness_goal;
    private String trn_date;

    public Clients() {
    }

    public Clients(Long id, int userId, String firstName, String surName, String clientEmail, Date dob,
            String height, String weightGoal, String ptEmail, int trainerId, Date goalDate, String bmi,
            String image, String accessToken, String telephone, String weight, String gender, int user_group,
            int package_item, String plan_name, int is_active, String goal_description, int fitness_goal,
            String trn_date) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.surName = surName;
        this.clientEmail = clientEmail;
        this.dob = dob;
        this.height = height;
        this.weightGoal = weightGoal;
        this.ptEmail = ptEmail;
        this.trainerId = trainerId;
        this.goalDate = goalDate;
        this.bmi = bmi;
        this.image = image;
        this.accessToken = accessToken;
        this.telephone = telephone;
        this.weight = weight;
        this.gender = gender;
        this.user_group = user_group;
        this.package_item = package_item;
        this.plan_name = plan_name;
        this.is_active = is_active;
        this.goal_description = goal_description;
        this.fitness_goal = fitness_goal;
        this.trn_date = trn_date;
    }

    public int getFitness_goal() {
        return fitness_goal;
    }

    public void setFitness_goal(int fitness_goal) {
        this.fitness_goal = fitness_goal;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
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

    public int getUser_group() {
        return user_group;
    }

    public void setUser_group(int user_group) {
        this.user_group = user_group;
    }

    public int getPackage_item() {
        return package_item;
    }

    public void setPackage_item(int package_item) {
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


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getClientEmail() {
        return this.clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getDobString() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            return dateFormat.format(this.dob);
        } catch (Exception e) {
            return "";
        }
    }

    public void setDob(String dob) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date date = format.parse(dob);
            this.dob = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeightGoal() {
        return this.weightGoal;
    }

    public void setWeightGoal(String weightGoal) {
        this.weightGoal = weightGoal;
    }

    public String getPtEmail() {
        return this.ptEmail;
    }

    public void setPtEmail(String ptEmail) {
        this.ptEmail = ptEmail;
    }

    public int getTrainerId() {
        return this.trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public Date getGoalDate() {
        return this.goalDate;
    }

    public String getGoalDateString() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
            return dateFormat.format(this.goalDate);
        } catch (Exception e) {
            return "";
        }
    }

    public void setGoalDate(String goalDate) {
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date date = format.parse(goalDate);
            this.goalDate = date;
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getBmi() {
        return this.bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFullName() {
        return firstName + " " + surName;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public String getTrn_date() {
        return trn_date;
    }

    public void setTrn_date(String trn_date) {
        this.trn_date = trn_date;
    }
}
