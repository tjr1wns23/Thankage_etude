package com.example.thankage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisement {

    @Expose
    @SerializedName("id") private int aid;
    @Expose
    @SerializedName("ad_name") private String ad_name;
    @Expose
    @SerializedName("ad_sec") private int ad_sec;
    @Expose
    @SerializedName("view_num") private int view_num;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public int getId() {
        return aid;
    }

    public void setId(int id) {
        this.aid = id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public int getAd_sec() {
        return ad_sec;
    }

    public void setAd_sec(int ad_sec) {
        this.ad_sec = ad_sec;
    }

    public int getView_num() {
        return view_num;
    }

    public void setView_num(int view_num) {
        this.view_num = view_num;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
