package com.bohui.bean;

import java.io.Serializable;

public class Arealist implements Serializable{
    private String Boxname;
    private int UID;
    private int Active;
    private int ColIndex;
    private int Startid;
    private  int Endid;
    private String Startlongitude;
    private String Startlatitude;
    private String Endlongitude;
    private String Endlatitude;
    private String Startheight;
    private String Endheight;

    public String getBoxname() {
        return Boxname;
    }

    public void setBoxname(String boxname) {
        Boxname = boxname;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public int getColIndex() {
        return ColIndex;
    }

    public void setColIndex(int colIndex) {
        ColIndex = colIndex;
    }

    public int getStartid() {
        return Startid;
    }

    public void setStartid(int startid) {
        Startid = startid;
    }

    public int getEndid() {
        return Endid;
    }

    public void setEndid(int endid) {
        Endid = endid;
    }

    public String getStartlongitude() {
        return Startlongitude;
    }

    public void setStartlongitude(String startlongitude) {
        Startlongitude = startlongitude;
    }

    public String getStartlatitude() {
        return Startlatitude;
    }

    public void setStartlatitude(String startlatitude) {
        Startlatitude = startlatitude;
    }

    public String getEndlongitude() {
        return Endlongitude;
    }

    public void setEndlongitude(String endlongitude) {
        Endlongitude = endlongitude;
    }

    public String getEndlatitude() {
        return Endlatitude;
    }

    public void setEndlatitude(String endlatitude) {
        Endlatitude = endlatitude;
    }

    public String getStartheight() {
        return Startheight;
    }

    public void setStartheight(String startheight) {
        Startheight = startheight;
    }

    public String getEndheight() {
        return Endheight;
    }

    public void setEndheight(String endheight) {
        Endheight = endheight;
    }
}
