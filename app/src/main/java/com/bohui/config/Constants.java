package com.bohui.config;

import android.content.Context;
import android.os.Environment;

/**
 * Created by yangwei on 16/8/5.
 */
public class Constants {
    public static final int MESSAGE_SHOW_VERSION_DIALOG2 = 12;
    public static final int MESSAGE_CANEL_UP_APK = 15;
    public static final String APK_NAME = "zyy.apk";
    public static final boolean iscanelUp_App = true;// 是否强制更新
    public static final String CHAT_CACHE = "user.db";
    public static final String HOST = "http://172.16.0.60/";
    public static final String IMAGE_HOST = "http://172.16.0.60/images/box/";
    public static final String txt_link = "txt_link";
    public static final String img_link = "img_link";
    public static final String txt = "txt";
    public static final String img = "img";
    public static final String CONFIGDB = "configdb";
    //资源文件路径
    public static final String LOCAL_IMAGE_LOCATION = Environment.getExternalStorageDirectory()+"/box";//本地存放路径
    public static String getLocalPath(Context context){
        return context.getExternalCacheDir().getPath();
    }
}
