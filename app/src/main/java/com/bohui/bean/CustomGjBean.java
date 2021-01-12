package com.bohui.bean;

import java.io.Serializable;

public class CustomGjBean implements Serializable {
    private String ID;
    private String Name;
    private String Type;
    private String OrganizationCode;
    private String PID;
    private String OrderID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOrganizationCode() {
        return OrganizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        OrganizationCode = organizationCode;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
}
