package com.bohui;

import android.app.Application;
import android.app.ApplicationErrorReport;

import com.bohui.bean.AlarmType;
import com.bohui.db.AuthCacheConfig;
import com.bohui.db.ConfigDB;
import com.bohui.db.UserInfoDB;
import com.bohui.utils.ImageLoaderUtils;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/4/18 0018.
 */

public class BoHuiApplication extends Application {
    private static BoHuiApplication application;
    public UserInfoDB userdb;// 配置类
    private AuthCacheConfig authConfig = null;
    private ConfigDB configDB;
    private int count;
    private List<AlarmType> datas = new ArrayList<>();
    private double lat;
    private double lng;
    private int fragmentType;//0首页、1.地图、2图表
    private boolean isHomeCreate = false;
    private boolean isClickHome = false;
    private boolean isMainHome = false;
    private String latlng;//告警定位的经纬度
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public boolean isMainHome() {
        return isMainHome;
    }

    public void setMainHome(boolean mainHome) {
        isMainHome = mainHome;
    }

    public boolean isClickHome() {
        return isClickHome;
    }

    public void setClickHome(boolean clickHome) {
        isClickHome = clickHome;
    }

    public boolean isHomeCreate() {
        return isHomeCreate;
    }

    public void setHomeCreate(boolean homeCreate) {
        isHomeCreate = homeCreate;
    }

    public int getFragmentType() {
        return fragmentType;
    }

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public List<AlarmType> getDatas() {
        return datas;
    }

    public void setDatas(List<AlarmType> datas) {
        this.datas = datas;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        authConfig = new AuthCacheConfig(this);
        userdb = new UserInfoDB(this);
        configDB = new ConfigDB(this);
        ImageLoaderUtils.getInstance().initImageLoader(this);
        CrashReport.initCrashReport(getApplicationContext(),"4c2d51dcbd",false);
        JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this); // 初始化 JPush

    }

    public static BoHuiApplication getInstance() {
        return application;
    }

    public AuthCacheConfig getAuthConfig() {
        return authConfig;
    }

    public ConfigDB getConfigDB() {
        return configDB;
    }
}
