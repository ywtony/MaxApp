package io.ionic.starter.app;

import android.app.Application;

import io.ionic.starter.model.UserModel;


/**
 * Created by Administrator on 2017/7/18.
 */

public class CeshiApp extends Application {
    private static CeshiApp mcontext;
    private static UserModel user;
    private static String WEB_SERVICE;

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = this;
    }

    public static CeshiApp getInstance() {
        return mcontext;
    }

    public static UserModel getUser() {
        return user;
    }

    public static void setUser(UserModel user) {
        CeshiApp.user = user;
    }

    public static String getWebService() {
        return WEB_SERVICE;
    }

    public static void setWebService(String webService) {
        WEB_SERVICE = webService;
    }
}
