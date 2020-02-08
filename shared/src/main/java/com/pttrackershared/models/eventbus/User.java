package com.pttrackershared.models.eventbus;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pttrackershared.plugins.ValidatorUtils;
import com.pttrackershared.utils.Constants;
import com.pttrackershared.utils.SaveUserPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Green dao uses this class as a type of tuple in it.
 */

public class User {

    private static User CURRENT_USER = null;

    private String MEASUREMENT_KG = "Kg";
    private String MEASUREMENT_POUND ="Pound";
    private String MEASUREMENT_CM = "Cm";
    private String MEASUREMENT_FEET = "Feet";

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
    @Expose
    private Integer user_group;


    @SerializedName("package_item")
    @Expose
    private Integer package_item;

    @SerializedName("trn_date")
    @Expose
    private String trn_date;

    @SerializedName("intro")
    @Expose
    private Integer intro;

    @SerializedName("profile")
    @Expose
    private Integer profile;

    @SerializedName("package")
    @Expose
    private Integer _package;

    @SerializedName("fitness_goal")
    @Expose
    private int fitness_goal;

    @SerializedName("pass2")
    @Expose
    private String code;

    private String weekGoalKCal;

    private String weekGoalSteps;

    private String weekGoalWorkout;

    private String weightMeasurement;

    private String heightMeasurement;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFitness_goal() {
        return fitness_goal;
    }

    public Integer get_package() {
        return _package;
    }

    public void set_package(Integer _package) {
        this._package = _package;
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Integer getIntro() {
        return intro;
    }

    public void setIntro(Integer intro) {
        this.intro = intro;
    }

    public String getTrn_date() {
        return trn_date;
    }

    public void setTrn_date(String trn_date) {
        this.trn_date = trn_date;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDobString() {
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
//            return dateFormat.format(this.dob);
//        } catch (Exception e) {
            return this.dob;
//        }
    }

    public void setDob(String dob) {
//        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
//        try {
//            Date date = format.parse(dob);
////            this.dob = String.valueOf(date);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        this.dob = dob;
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
        if (goalDate == null || goalDate.isEmpty()){
            goalDate = String.valueOf(new Date().getTime());
        }
        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        try {
            Date date = format.parse(goalDate);
            this.goalDate = String.valueOf(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setFitness_goal(int fitness_goal) {
        this.fitness_goal = fitness_goal;
    }

    public static int getSelectedWeightUnit(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        User user = SaveUserPreferences.getUser(context);
        return sharedPreferences.getInt("weight_unit_" + user.getUserId(), Constants.WEIGHT_UNIT_KILOGRAMS);
    }

    public static String getSelectedLanguageCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int selectedLanguage = sharedPreferences.getInt("language", Constants.LANGUAGE_ENGLISH);
        String languageCode = "en";
        switch (selectedLanguage) {
            case Constants.LANGUAGE_SPANISH:
                languageCode = "es";
                break;
        }
        return languageCode;
    }

    public static int getSelectedLanguageCodeChange(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int selectedLanguage = sharedPreferences.getInt("language", Constants.LANGUAGE_ENGLISH);
        int languageCode = 1;
        switch (selectedLanguage) {
            case Constants.LANGUAGE_ENGLISH:
                languageCode = 1;
                break;
            case Constants.LANGUAGE_SPANISH:
                languageCode = 2;
                break;
        }
        return languageCode;
    }

    public static int getSelectedLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("language", Constants.LANGUAGE_ENGLISH);
    }

    public static User convertToUser(User userJsonModel) {
        User user = new User();
        user.setFitness_goal(userJsonModel.getFitness_goal());
        user.setCode(userJsonModel.getCode());
        user.setUserId(userJsonModel.getClientId());
        user.setFirstname(userJsonModel.getFirstname());
        user.setSurname(userJsonModel.getSurname());
        user.setClientemail(userJsonModel.getClientemail());
        if (userJsonModel.getDobString() == null) user.setDob("");
        user.setDob(userJsonModel.getDobString());

        if (userJsonModel.getGoalDate() == null) user.setGoalDate("");
        user.setGoalDate(userJsonModel.getGoalDate());

        if (userJsonModel.getHeight() == null) user.setHeight("0");
        user.setHeight(userJsonModel.getHeight());

        if (userJsonModel.getWeightgoal() == null) user.setWeightgoal("0");
        user.setWeightgoal(userJsonModel.getWeightgoal());

        user.setPTemail(userJsonModel.getPTemail());
        user.setTrainerId(userJsonModel.getTrainerId());

        if (userJsonModel.getBmi() == null) user.setBmi("0");
        user.setBmi(userJsonModel.getBmi());

        user.setImage(userJsonModel.getImage());
        user.setTelephone(userJsonModel.getTelephone());
        user.setWeight(userJsonModel.getWeight());
        user.setGender(userJsonModel.getGender());
        user.setPackage_item(userJsonModel.getPackage_item());
        user.setUser_group(userJsonModel.getUser_group());
        user.setTrn_date(userJsonModel.getTrn_date());

        if(userJsonModel.getIntro()==null) user.setIntro(0);
        user.setIntro(userJsonModel.getIntro());

        user.setProfile(userJsonModel.getProfile());
        user.set_package(userJsonModel.get_package());


        return user;
    }

    public String getWeekGoalKCal() {
        return weekGoalKCal;
    }

    public void setWeekGoalKCal(String weekGoalKCal) {
        this.weekGoalKCal = weekGoalKCal;
    }

    public String getWeekGoalSteps() {
        return weekGoalSteps;
    }

    public void setWeekGoalSteps(String weekGoalSteps) {
        this.weekGoalSteps = weekGoalSteps;
    }

    public String getWeekGoalWorkout() {
        return weekGoalWorkout;
    }

    public void setWeekGoalWorkout(String weekGoalWorkout) {
        this.weekGoalWorkout = weekGoalWorkout;
    }

    public boolean isMeasurementKg() {
        return getWeightMeasurement().equals(MEASUREMENT_KG);
    }

    public boolean isMeasurementCm() {
        return getHeightMeasurement().equals(MEASUREMENT_CM);
    }

    public String getWeightMeasurement() {
        if (ValidatorUtils.IsNullOrEmpty(weightMeasurement)) {
            return MEASUREMENT_KG;
        }
        return weightMeasurement;
    }

    public void setWeightMeasurement(String weightMeasurement) {
        this.weightMeasurement = weightMeasurement;
    }

    public String getHeightMeasurement() {
        if (ValidatorUtils.IsNullOrEmpty(heightMeasurement)) {
            return MEASUREMENT_CM;
        }
        return heightMeasurement;
    }

    public void setHeightMeasurement(String heightMeasurement) {
        this.heightMeasurement = heightMeasurement;
    }

    public void setWeightMeasurementKg() {
        this.weightMeasurement = MEASUREMENT_KG;
    }

    public void setWeightMeasurementPound() {
        this.weightMeasurement = MEASUREMENT_POUND;
    }

    public void setHeightMeasurementCm() {
        this.heightMeasurement = MEASUREMENT_CM;
    }

    public void setHeightMeasurementFeet() {
        this.heightMeasurement = MEASUREMENT_FEET;
    }
}
