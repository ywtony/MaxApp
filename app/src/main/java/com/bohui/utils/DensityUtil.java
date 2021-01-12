package com.bohui.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DensityUtil {
	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将1mm值转换为px值
	 */
	public static double mm2px(Context context) {
		return context.getResources().getDisplayMetrics().densityDpi/25.4;
	}



	/**
	 * 各种单位转换
	 * <p>该方法存在于TypedValue
	 */
	public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
		switch (unit) {
			case TypedValue.COMPLEX_UNIT_PX:
				return value;
			case TypedValue.COMPLEX_UNIT_DIP:
				return value * metrics.density;
			case TypedValue.COMPLEX_UNIT_SP:
				return value * metrics.scaledDensity;
			case TypedValue.COMPLEX_UNIT_PT:
				return value * metrics.xdpi * (1.0f / 72);
			case TypedValue.COMPLEX_UNIT_IN:
				return value * metrics.xdpi;
			case TypedValue.COMPLEX_UNIT_MM:
				return value * metrics.xdpi * (1.0f / 25.4f);
		}
		return 0;
	}
}
