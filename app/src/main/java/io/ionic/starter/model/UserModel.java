package io.ionic.starter.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-17.
 */

public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userid;
    private String name;
    private String password;
    private String idvalue;
    private String token;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdvalue() {
        return idvalue;
    }

    public void setIdvalue(String idvalue) {
        this.idvalue = idvalue;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
