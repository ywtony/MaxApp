package com.bohui.bean;

import java.io.Serializable;

public class ButtomBean implements Serializable {
    private String id;
    private String msg;
    private String enMsg;
    private int contentId;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public ButtomBean(String id, int contentId) {
        this.id = id;
        this.contentId = contentId;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public ButtomBean() {
    }

    public ButtomBean(String id, String msg, String enMsg) {
        this.id = id;
        this.msg = msg;
        this.enMsg = enMsg;
    }
    public ButtomBean(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
