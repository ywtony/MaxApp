package com.bohui.bean;

import java.io.Serializable;

/**
 * 周界安防的point实体
 */
public class ZJPoint implements Serializable {
    private int x1;
    private int y1;
    private int emd;
    private int smd;

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getEmd() {
        return emd;
    }

    public void setEmd(int emd) {
        this.emd = emd;
    }

    public int getSmd() {
        return smd;
    }

    public void setSmd(int smd) {
        this.smd = smd;
    }
}
