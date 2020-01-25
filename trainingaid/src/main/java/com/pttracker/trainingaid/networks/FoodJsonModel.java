package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model class for deserializing User information from json string
 */

public class FoodJsonModel {
    @SerializedName("food_id")
    @Expose
    private Integer food_id;
    @SerializedName("trainer_id")
    @Expose
    private String trainer_id;
    @SerializedName("foodname")
    @Expose
    private String foodname;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("Kcal")
    @Expose
    private String kcal;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Carbs")
    @Expose
    private String carbs;
    @SerializedName("Protein")
    @Expose
    private Integer protein;
    @SerializedName("Fat")
    @Expose
    private String fat;

    @SerializedName("mu")
    @Expose
    private String mu;

    @SerializedName("qty")
    @Expose
    private String qty;


    public String getMu() {
        return mu;
    }

    public void setMu(String mu) {
        this.mu = mu;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public Integer getFood_id() {
        return food_id;
    }

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCarbs() {
        return carbs;
    }

    public void setCarbs(String carbs) {
        this.carbs = carbs;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }
}
