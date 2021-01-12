package com.bohui.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 * 故障实体
 */

public class Fault  implements Serializable{
    private String faultTime;
    private String faultType;
    private String faultLevel;
    private String address;
    private String describe;
    public Fault(String faultTime, String faultType, String faultLevel, String address ,String describe){
        this.faultTime=faultTime;
        this.faultType=faultType;
        this.faultLevel=faultLevel;
        this.address=address;
        this.describe=describe;
    }

    public String getFaultTime() {
        return faultTime;
    }

    public void setFaultTime(String faultTime) {
        this.faultTime = faultTime;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }

    public String getFaultLevel() {
        return faultLevel;
    }

    public void setFaultLevel(String faultLevel) {
        this.faultLevel = faultLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
