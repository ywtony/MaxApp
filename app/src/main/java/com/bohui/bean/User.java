package com.bohui.bean;

import java.io.Serializable;

/**
 * 用户信息
 * Created by yangwei on 2017/5/16.
 */
public class User implements Serializable {
    private String avatar;//头像
    private long both;//出生日期
    private String city;//出生地
    private int curTime;//剩余可观看时间
    private int gender;//性别
    private String identity;//身份
    private String username;//用户名
    private String nickName;//
    private boolean bindQQ;
    private boolean bindWB;
    private boolean bindWX;
    private String price;//余额

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isBindQQ() {
        return bindQQ;
    }

    public void setBindQQ(boolean bindQQ) {
        this.bindQQ = bindQQ;
    }

    public boolean isBindWB() {
        return bindWB;
    }

    public void setBindWB(boolean bindWB) {
        this.bindWB = bindWB;
    }

    public boolean isBindWX() {
        return bindWX;
    }

    public void setBindWX(boolean bindWX) {
        this.bindWX = bindWX;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getBoth() {
        return both;
    }

    public void setBoth(long both) {
        this.both = both;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCurTime() {
        return curTime;
    }

    public void setCurTime(int curTime) {
        this.curTime = curTime;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
