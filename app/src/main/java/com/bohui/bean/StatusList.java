package com.bohui.bean;

import java.io.Serializable;

public class StatusList implements Serializable{
    private int val;
    private String pic;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
