package com.bohui.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * 状态栏工具类
 */
public class StatusBarUtils {
    private StatusBarUtils() {
    }

    private static StatusBarUtils instance;

    public static StatusBarUtils getInstance() {
        if (instance == null) {
            instance = new StatusBarUtils();
        }
        return instance;
    }

    /**
     * 获取手机状态栏的高度
     *
     * @return 状态栏的高度
     */
    public int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
