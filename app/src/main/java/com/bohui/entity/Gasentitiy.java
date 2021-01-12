package com.bohui.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21 0021.
 * 气象详情实体类
 *
 */

public class Gasentitiy  implements Serializable{
    private String detectionName;
    private String detectionTime;
    private String detectionStatus;
    private String statusValue;
    private String number;
    private String other;
    public Gasentitiy (String detectionName, String detectionTime, String detectionStatus, String statusValue, String number, String other ){
        this.detectionName=detectionName;
        this.detectionTime=detectionTime;
        this.detectionStatus=detectionStatus;
        this.statusValue=statusValue;
        this.number=number;
        this.other=other;
    }

    public String getDetectionName() {
        return detectionName;
    }

    public void setDetectionName(String detectionName) {
        this.detectionName = detectionName;
    }

    public String getDetectionTime() {
        return detectionTime;
    }

    public void setDetectionTime(String detectionTime) {
        this.detectionTime = detectionTime;
    }

    public String getDetectionStatus() {
        return detectionStatus;
    }

    public void setDetectionStatus(String detectionStatus) {
        this.detectionStatus = detectionStatus;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
