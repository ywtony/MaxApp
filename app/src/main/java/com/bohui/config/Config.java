package com.bohui.config;


/**
 * 配置类
 * <p>
 * 1. {@link #isAppReleased}应用是否已发布，默认为false。
 * <p>
 * 2. {@link }是否输出日志信息，默认应用发布时关闭。
 * <p>
 * 3. {@link #isDevToastDisplayOn}是否显示开发期临时Toast，默认应用发布时关闭。
 */
public class Config {

    /**
     * 应用是否已发布
     */
    public static boolean isAppReleased = false;
    /**
     * 是否输出日志信息
     */
    public static boolean isDevLogDisplayOn = !isAppReleased;
    /**
     * 是否显示开发期临时Toast
     */
    public static boolean isDevToastDisplayOn = !isAppReleased;

    public static String INTERFACE_URL = "http://114.55.129.5:9792/api/";

    public static String UPDATA_IMG_URL = "http://114.55.129.5:8081/";

    //    public static String TEST_DEVICE_ID = "BV1234567890";
//    public static String TEST_DEVICE_ID = GApplication.getInstance().userdb.getUserInfo().getDeviceId();
}