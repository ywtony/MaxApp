package com.bohui.utils;

import android.graphics.PointF;

public class PointDistantUtils {
    private PointDistantUtils() {
    }

    private static PointDistantUtils instance;

    public static PointDistantUtils getInstance() {
        if (instance == null) {
            instance = new PointDistantUtils();
        }
        return instance;
    }

    /**
     * 计算两个坐标点之间的距离
     *
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @return
     */
    public double getDistance(int x, int y, int x1, int y1) {
        double xw = Math.abs(x - x1);
        double yw = Math.abs(y - y1);
        return Math.sqrt(xw * xw + yw * yw);
    }
}
