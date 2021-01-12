package com.bohui.bean;

import java.io.Serializable;
import java.util.List;


public class ObjData implements Serializable {
    private int Id;//对应的meansobjdi
    private String Mo;
    private float CV;//要更新的值
//    private String Dcv;
    private List<Float> Dcv;
    private String Jcv;
    private String RefreshTime;
    private boolean Actived;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getMo() {
        return Mo;
    }

    public void setMo(String mo) {
        Mo = mo;
    }

    public float getCV() {
        return CV;
    }

    public void setCV(float CV) {
        this.CV = CV;
    }

//    public String getDcv() {
//        return Dcv;
//    }
//
//    public void setDcv(String dcv) {
//        Dcv = dcv;
//    }


    public List<Float> getDcv() {
        return Dcv;
    }

    public void setDcv(List<Float> dcv) {
        Dcv = dcv;
    }

    public String getJcv() {
        return Jcv;
    }

    public void setJcv(String jcv) {
        Jcv = jcv;
    }

    public String getRefreshTime() {
        return RefreshTime;
    }

    public void setRefreshTime(String refreshTime) {
        RefreshTime = refreshTime;
    }

    public boolean isActived() {
        return Actived;
    }

    public void setActived(boolean actived) {
        Actived = actived;
    }
}
