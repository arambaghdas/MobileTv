package com.pttracker.trainingaid.networks;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing User information from json string
 */

public class UserJsonModel {
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
