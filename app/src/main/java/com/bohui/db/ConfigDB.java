package com.bohui.db;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bohui.bean.ConfigBean;
import com.bohui.bean.User;
import com.google.gson.Gson;


/**
 * 用户缓存数据
 *
 * @author tony
 */
public class ConfigDB {
    private SharedPreferences sp;// 配置�?
    final static String regularEx = "|";

    public ConfigDB(Context context) {
        sp = context.getSharedPreferences("config.db",
                context.MODE_PRIVATE);
    }

    /**
     * 存储用户基本信息
     *
     * @param config
     */
    public void setConfig(String config) {
        Editor edit = sp.edit();
        edit.putString("configdb", config);
        edit.commit();
    }

    /**
     * 获取用户基本信息(返回user对象)
     *
     * @return
     */
    public ConfigBean getConfig() {
        String json = sp.getString("configdb", "");
        return new Gson().fromJson(json, ConfigBean.class);

    }

    public void removeConfig() {
        try {
            Editor edit = sp.edit();
            edit.remove("configdb");
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置枚举中的中英文显示
     *
     * @param language
     */
    public void setLanguage(String language) {
        Editor edit = sp.edit();
        edit.putString("language", language);
        edit.commit();
    }

    /**
     * 获取中英文类型
     *
     * @return
     */
    public String getLanguage() {
        String json = sp.getString("language", "zh");
        return json;

    }

    public void removeLanguage() {
        try {
            Editor edit = sp.edit();
            edit.remove("language");
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取sharedpreference对象
     *
     * @return
     */
    public SharedPreferences getSp() {
        return sp;
    }

}
