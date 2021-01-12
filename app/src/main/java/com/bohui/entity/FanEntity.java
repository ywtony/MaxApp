package com.bohui.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class FanEntity implements Serializable{
    private String fanName;
    private String status;
    private boolean isOpen;
    public FanEntity(String fanName,String status,boolean isOpen){
        this.fanName=fanName;
        this.status=status;
        this.isOpen=isOpen;
    }

    public String getFanName() {
        return fanName;
    }

    public void setFanName(String fanName) {
        this.fanName = fanName;
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
