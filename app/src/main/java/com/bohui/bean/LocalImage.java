package com.bohui.bean;

import java.io.Serializable;

public class LocalImage implements Serializable{
    private String localUrl;//本地url

    public LocalImage(String localUrl) {
        this.localUrl = localUrl;
    }

    public LocalImage() {
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
}
