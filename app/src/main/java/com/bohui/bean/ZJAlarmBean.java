package com.bohui.bean;

import java.io.Serializable;

public class ZJAlarmBean implements Serializable {
    private String alarmSource;
    //告警级别
    private String alarmLevel;
    //告警类别
    private String alarmType;
    //告警严重程度
    private String alarmSeverity;
    //告警时间
    private String alarmTime;
    //告警id
    private String alarmId;

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public ZJAlarmBean() {
    }

    public ZJAlarmBean(String alarmSource, String alarmLevel, String alarmType, String alarmSeverity, String alarmTime) {
        this.alarmSource = alarmSource;
        this.alarmLevel = alarmLevel;
        this.alarmType = alarmType;
        this.alarmSeverity = alarmSeverity;
        this.alarmTime = alarmTime;
    }

    public String getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        this.alarmSource = alarmSource;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmSeverity() {
        return alarmSeverity;
    }

    public void setAlarmSeverity(String alarmSeverity) {
        this.alarmSeverity = alarmSeverity;
    }
}
