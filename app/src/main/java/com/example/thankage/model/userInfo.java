package com.example.thankage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userInfo {

    @Expose
    @SerializedName("id") private String id;
    @Expose
    @SerializedName("pw") private String pw;
    @Expose
    @SerializedName("nickName") private String nickName;
    @Expose
    @SerializedName("phone") private String phone;
    @Expose
    @SerializedName("sex") private String sex;
    @Expose
    @SerializedName("birth") private String birth;
    @Expose
    @SerializedName("joinDate") private String joinDate;
    @Expose
    @SerializedName("coin") private String coin;
    @Expose
    @SerializedName("heart") private String heart;
    @Expose
    @SerializedName("success") private Boolean success;
    @Expose
    @SerializedName("message") private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
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