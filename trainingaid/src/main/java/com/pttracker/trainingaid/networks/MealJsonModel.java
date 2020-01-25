package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class for deserializing User information from json string
 */

public class MealJsonModel {
    @SerializedName("fcp_id")
    @Expose
    private Integer fcp_id;
    @SerializedName("trainer_id")
    @Expose
    private String trainer_id;
    @SerializedName("client_id")
    @Expose
    private String client_id;
    @SerializedName("fup_id")
    @Expose
    private String fup_id;
    @SerializedName("fpn_id")
    @Expose
    private String fpn_id;
    @SerializedName("is_active")
    @Expose
    private String is_active;
    @SerializedName("trn_date_start")
    @Expose
    private String trn_date_start;
    @SerializedName("trn_date_finish")
    @Expose
    private String trn_date_finish;
    @SerializedName("is_history")
    @Expose
    private Integer is_history;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("calories")
    @Expose
    private String calories;
    @SerializedName("plan_uid")
    @Expose
    private String plan_uid;
    @SerializedName("day")
    @Expose
    private int day;
    @SerializedName("meal")
    @Expose
    private String meal;
    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("order_no")
    @Expose
    private String order_no;

    @SerializedName("quantity_value")
    @Expose
    private String quantity_value;

    @SerializedName("quantity_meas")
    private Integer quantity_meas;

    @SerializedName("foods")
    @Expose
    private List<FoodJsonModel> foods = null;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Integer getFcp_id() {
        return fcp_id;
    }

    public void setFcp_id(Integer fcp_id) {
        this.fcp_id = fcp_id;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getFup_id() {
        return fup_id;
    }

    public void setFup_id(String fup_id) {
        this.fup_id = fup_id;
    }

    public String getFpn_id() {
        return fpn_id;
    }

    public void setFpn_id(String fpn_id) {
        this.fpn_id = fpn_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getTrn_date_start() {
        return trn_date_start;
    }

    public void setTrn_date_start(String trn_date_start) {
        this.trn_date_start = trn_date_start;
    }

    public String getTrn_date_finish() {
        return trn_date_finish;
    }

    public void setTrn_date_finish(String trn_date_finish) {
        this.trn_date_finish = trn_date_finish;
    }

    public Integer getIs_history() {
        return is_history;
    }

    public void setIs_history(Integer is_history) {
        this.is_history = is_history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan_uid() {
        return plan_uid;
    }

    public void setPlan_uid(String plan_uid) {
        this.plan_uid = plan_uid;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getQuantity_value() {
        return quantity_value;
    }

    public void setQuantity_value(String quantity_value) {
        this.quantity_value = quantity_value;
    }

    public Integer getQuantity_meas() {
        return quantity_meas;
    }

    public void setQuantity_meas(Integer quantity_meas) {
        this.quantity_meas = quantity_meas;
    }

    public List<FoodJsonModel> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodJsonModel> foods) {
        this.foods = foods;
    }
}
