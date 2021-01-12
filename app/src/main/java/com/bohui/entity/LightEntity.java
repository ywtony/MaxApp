package com.bohui.entity;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class LightEntity {
    private String lightName;
    private String status;
    private boolean isOpen;
    public LightEntity(String lightName,String status,boolean isOpen){
        this.lightName=lightName;
        this.status=status;
        this.isOpen=isOpen;
    }

    public String getLightName() {
        return lightName;
    }

    public void setLightName(String lightName) {
        this.lightName = lightName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
