package com.bohui.entity;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class TemperatureEntity {
    private String temperatureName;
    private String status;
    private String temperatureValue;

    public TemperatureEntity(String temperatureName,String status,String temperatureValue){
        this.temperatureName=temperatureName;
        this.status=status;
        this.temperatureValue=temperatureValue;
    }

    public String getTemperatureName() {
        return temperatureName;
    }

    public void setTemperatureName(String temperatureName) {
        this.temperatureName = temperatureName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(String temperatureValue) {
        this.temperatureValue = temperatureValue;
    }
}
