package com.bohui.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

/**
 * toast工具
 * 
 * @author tony
 * 
 */
public class ToastUtil {
	public static final int TOAST_SUCESS = 0;
	public static final int TOAST_WARING = 1;
	public static final int TOAST_FELIA = 2;
	public static final int TOAST_SMILE = 3;
	public static final int TOAST_REC = 5;
	private static final String TAG = "ToastUtil";
	private static TipsToast tipsToast;

	private static String oldMsg;
	protected static Toast mToast  = null;
	private static   long  oneTime = 0;
	private static   long  twoTime = 0;

	private ToastUtil() {
	}

	private Toast toast;
	private static ToastUtil instance = new ToastUtil();

	public static ToastUtil getInstance() {
		return instance;
	}

	/**
	 * 显示toast
	 * 
	 * @param context
	 * @param text
	 */
	@SuppressLint("ShowToast")
	public void show(Context context, String text) {
		if (context != null && text != null) {
			try {
				if (mToast == null) {
					mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
				} else {
					mToast.setText(text);
					mToast.setDuration(Toast.LENGTH_SHORT);
				}
				mToast.show();

			} catch (NullPointerException e) {
				Log.e(TAG, "toast内容为空");
			}
		}

	}

	/**
	 * 自定义toast
	 * 
	 * @param
	 *
	 * 
	 *             = 0;(成功) TOAST_WARING = 1(警告); TOAST_FELIA =
	 *            2(失败); TOAST_SMILE=3;(微笑))
	 * @param
	 *
	 * @param tips
	 */
	public void showTips(Context context, int iconType, String tips) {
		if (context != null && tips != null) {

			if (tipsToast != null) {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
					tipsToast.cancel();
				}
			} else {
				tipsToast = TipsToast.makeText(context, tips, TipsToast.LENGTH_SHORT);
			}
			tipsToast.show();
			switch (iconType) {
			case TOAST_SUCESS:
				// tipsToast.setIcon(R.drawable.trip_tips_success);
				break;
			case TOAST_SMILE:
				// tipsToast.setIcon(R.drawable.trip_tips_smile);
				break;
			case TOAST_WARING:
				// tipsToast.setIcon(R.drawable.trip_tips_warning);
				break;
			case TOAST_FELIA:
				// tipsToast.setIcon(R.drawable.trip_tips_error);
				break;
			case TOAST_REC:
				// tipsToast.setIcon(R.drawable.trip_tips_rec_voice);

			default:
				break;
			}

			tipsToast.setText(tips);
		}
	}

	public static void showToast(Context context, String s) {
		if (mToast == null) {
			mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			mToast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					mToast.show();
				}
			} else {
				oldMsg = s;
				mToast.setText(s);
				mToast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showToast(Context context, int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
			mToast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (context.getString(resId).equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					mToast.show();
				}
			} else {
				oldMsg = context.getString(resId);
				mToast.setText(resId);
				mToast.show();
			}
		}
		oneTime = twoTime;
	}

	public static void showToastUi(final Activity context, final String msg) {
		if ("main".equals(Thread.currentThread().getName())) {
			showToast(context, msg);
		} else {
			context.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showToast(context, msg);
				}
			});
		}
	}
}
