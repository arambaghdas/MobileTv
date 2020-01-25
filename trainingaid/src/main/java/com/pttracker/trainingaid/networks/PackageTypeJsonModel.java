package com.pttracker.trainingaid.networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nitinsharma on 02-11-2018.
 */

public class PackageTypeJsonModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("pid")
    @Expose
    private int pid;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("duration")
    @Expose
    private int duration;

    @SerializedName("package_type")
    @Expose
    private int package_type;

    @SerializedName("user_group")
    @Expose
    private int user_group;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPackage_type() {
        return package_type;
    }

    public void setPackage_type(int package_type) {
        this.package_type = package_type;
    }

    public int getUser_group() {
        return user_group;
    }

    public void setUser_group(int user_group) {
        this.user_group = user_group;
    }
}
