package com.bohui.db;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bohui.config.Constants;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;


/**
 * 登录验证config
 *
 * @author tony
 */
public class AuthCacheConfig {
    private SharedPreferences sp;// 配置�?

    public AuthCacheConfig(Context context) {
        sp = context.getSharedPreferences(Constants.CHAT_CACHE,
                context.MODE_PRIVATE);
    }

    /**
     * 加密存储token
     *
     * @param
     */
    public void setToken(String token) {
        try {
            if (token == null || "".equals(token)) {
                return;
            }
//            String aesToken = Aes.encrypt(token);
            String aesToken = token;
            DevLog.e("aesToken:"+aesToken);
            Editor edit = sp.edit();
            edit.putString("token", aesToken);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解密token
     *
     * @return
     */
    public String getToken() {

        String aesToken = null;
        try {

            String token = sp.getString("token", "");
            if (token == null || "".equals(token)) {
                return null;
            }
//            aesToken = Aes.decrypt(token);
            aesToken = token;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aesToken;

    }

    /**
     * 删除登陆信息
     */
    public void removeToken() {
        Editor edit = sp.edit();
        edit.remove("token");
        edit.commit();
        HttpUtil.getClient().removeHeader("x-auth-token");
    }

    //设置登录时间
    public void setLoginTime(long time) {
        Editor edit = sp.edit();
        edit.putLong("logintime", time);
        edit.commit();
    }

    //获取登录时间
    public long getLoginTime() {
        return sp.getLong("logintime", 0);
    }

    //删除登录时间
    public void removeLoginTime() {
        Editor edit = sp.edit();
        edit.remove("logintime");
        edit.commit();
    }

    /*计算时间间隔*/
    public void setLastTime(long lastTime) {
        Editor edit = sp.edit();
        edit.remove("lastTime");
        edit.commit();
    }

    public long getLiastTime() {
        return sp.getLong("lastTime", System.currentTimeMillis());
    }
}
