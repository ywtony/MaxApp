package com.bohui.bean;

import com.bohui.utils.JSONUtil;

import java.io.Serializable;

/**
 * 加密数据处理类
 * create by yangwei
 * on 2020/10/8 14:20
 */
public class LoginData implements Serializable {
    /**
     * 用户名
     */
    private String UserName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 固定值
     */
    private String PageId="1";
    /**
     * 登录码固定值
     */
    private String LoginCode="0";
    /**
     * 固定传3
     */
    private String TerminalType="3";
    /**
     * 固定传false
     */
    private boolean Checked=false;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPageId() {
        return PageId;
    }

    public void setPageId(String pageId) {
        PageId = pageId;
    }

    public String getLoginCode() {
        return LoginCode;
    }

    public void setLoginCode(String loginCode) {
        LoginCode = loginCode;
    }

    public String getTerminalType() {
        return TerminalType;
    }

    public void setTerminalType(String terminalType) {
        TerminalType = terminalType;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    /**
     * 将实体类转换为Json字符串
     * @return
     */
    public String toJson(){
        return JSONUtil.getInstance().getString(this);
    }
}
