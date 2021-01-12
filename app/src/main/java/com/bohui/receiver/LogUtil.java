package com.bohui.receiver;

import android.util.Log;

/**
 * create by yangwei
 * on 2020/12/21 22:33
 */
class LogUtil {

    private static final String TAG = "INSPECTION_APP";

    /**
     * 打印日志
     */
    public static void log(String log) {
        Log.e(TAG, log);
    }

    public static void log(int log) {
        Log.e(TAG, log+"");
    }

    public static void log(long log) {
        Log.e(TAG, log+"");
    }

    public static void log(double log) {
        Log.e(TAG, log+"");
    }
}
