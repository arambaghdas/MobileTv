package com.pttrackershared.models.eventbus;

import java.io.Serializable;
import java.util.List;

/**
 * Model class for deserializing User information from json string
 */

public class Meals implements Serializable {

    private Integer fcp_id;
    private String trainer_id;
    private String client_id;
    private String fup_id;
    private String fpn_id;
    private String is_active;
    private String trn_date_start;
    private String trn_date_finish;
    private Integer is_history;
    private String name;
    private String calories;
    private String plan_uid;
    private int day;
    private String meal;
    private String time;
    private String order_no;
    private String quantity_value;
    private Integer quantity_meas;
    private List<Foods> foods = null;

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

    public List<Foods> getFoods() {
        return foods;
    }

    public void setFoods(List<Foods> foods) {
        this.foods = foods;
    }
}
