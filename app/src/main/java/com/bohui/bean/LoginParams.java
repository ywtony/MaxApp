package com.bohui.bean;

import java.io.Serializable;

public class LoginParams implements Serializable {
    private String username;
    private String password;
    private boolean checked = true;
    private String type = "login";
    private int terminalType = 1;
    private String expand = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }
}
