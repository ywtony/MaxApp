package com.bohui.bean;

import java.io.Serializable;

/**
 * 页面属性
 */
public class MainPage implements Serializable {
    private int ID;
    private int PID;
    private String Name;
    private String BackgroundImage;
    private int OrderID;
    private String Boxs;
    private boolean IsFloat;
    private int Width;
    private int Height;
    private int PageLevel;
    private String BackColor;
    private int PageType;
    private boolean Hide;
    private String ExAttrs;

    public String getExAttrs() {
        return ExAttrs;
    }

    public void setExAttrs(String exAttrs) {
        ExAttrs = exAttrs;
    }

    public int getPageType() {
        return PageType;
    }

    public void setPageType(int pageType) {
        PageType = pageType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBackgroundImage() {
        return BackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        BackgroundImage = backgroundImage;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public String getBoxs() {
        return Boxs;
    }

    public void setBoxs(String boxs) {
        Boxs = boxs;
    }

    public boolean isFloat() {
        return IsFloat;
    }

    public void setFloat(boolean aFloat) {
        IsFloat = aFloat;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public int getPageLevel() {
        return PageLevel;
    }

    public void setPageLevel(int pageLevel) {
        PageLevel = pageLevel;
    }

    public String getBackColor() {
        return BackColor;
    }

    public void setBackColor(String backColor) {
        BackColor = backColor;
    }

    public boolean isHide() {
        return Hide;
    }

    public void setHide(boolean hide) {
        Hide = hide;
    }
}
