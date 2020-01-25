package com.pttracker.trainingaid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nitinsharma on 12-11-2018.
 */

public class ClientPlanRange {

    @SerializedName("noofclients")
    @Expose
    private String noofclients;

    @SerializedName("nooftrainingplans")
    @Expose
    private String nooftrainingplans;

    public String getNoofclients() {
        return noofclients;
    }

    public void setNoofclients(String noofclients) {
        this.noofclients = noofclients;
    }

    public String getNooftrainingplans() {
        return nooftrainingplans;
    }

    public void setNooftrainingplans(String nooftrainingplans) {
        this.nooftrainingplans = nooftrainingplans;
    }
}
