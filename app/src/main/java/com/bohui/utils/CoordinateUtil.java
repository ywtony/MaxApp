package com.bohui.utils;

import android.content.Context;
import android.graphics.PointF;

public class CoordinateUtil {
    /**
     * 坐标点进行转化
     */
    public static PointF transformCoordinate(Context context, PointF pointF) {
        int newX = (int) ((pointF.x / 1000) * DensityUtil.mm2px(context));
        int newY = (int) ((pointF.y / 1000) * DensityUtil.mm2px(context));
        return new PointF(newX, newY);
    }

    /**
     * 转换回去
     */
    public static PointF reTransformCoordinate(Context context, PointF pointF) {
        int newX = (int) ((pointF.x * 1000) / DensityUtil.mm2px(context));
        int newY = (int) ((pointF.y * 1000) / DensityUtil.mm2px(context));
        return new PointF(newX, newY);
    }
}
