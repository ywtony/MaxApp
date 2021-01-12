package com.bohui.bean;

import java.io.Serializable;

/**
 * 登录配置的Bean
 */
public class ConfigBean implements Serializable {
    private String id;//用户id
    private String pwd;//用户密码
    private String ip;//用户的ip

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
