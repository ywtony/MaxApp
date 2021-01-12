package com.bohui.bean;

import java.io.Serializable;
import java.util.List;

public class Polylines implements Serializable{
    private String Boxname;
    private int UID;
    private int RowIndex;
    private int MinLevel;
    private int MaxLevel;
    private int Datatype;
    private int DistriType;
    private int DataID;
    private String RelMeasObjIDs;
    private int Strokeweight;
    private String Color;
    private List<PolyLines_Child> Arealist;

    public List<PolyLines_Child> getArealist() {
        return Arealist;
    }

    public void setArealist(List<PolyLines_Child> arealist) {
        Arealist = arealist;
    }

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

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int rowIndex) {
        RowIndex = rowIndex;
    }

    public int getMinLevel() {
        return MinLevel;
    }

    public void setMinLevel(int minLevel) {
        MinLevel = minLevel;
    }

    public int getMaxLevel() {
        return MaxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        MaxLevel = maxLevel;
    }

    public int getDatatype() {
        return Datatype;
    }

    public void setDatatype(int datatype) {
        Datatype = datatype;
    }

    public int getDistriType() {
        return DistriType;
    }

    public void setDistriType(int distriType) {
        DistriType = distriType;
    }

    public int getDataID() {
        return DataID;
    }

    public void setDataID(int dataID) {
        DataID = dataID;
    }

    public String getRelMeasObjIDs() {
        return RelMeasObjIDs;
    }

    public void setRelMeasObjIDs(String relMeasObjIDs) {
        RelMeasObjIDs = relMeasObjIDs;
    }

    public int getStrokeweight() {
        return Strokeweight;
    }

    public void setStrokeweight(int strokeweight) {
        Strokeweight = strokeweight;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
