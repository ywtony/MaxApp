package com.bohui.http;

import android.app.ProgressDialog;
import android.content.Context;

import com.bohui.dialog.LoadingDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * 自定义ResponseHandler
 * 
 * @author tony
 * 
 */
public class GsonHttpResponseHandler extends JsonHttpResponseHandler {
	private JsonResponseInter inter;
	private Context context;
	private ProgressDialog dialog = null;
	private boolean isShowDialog = true;
	private LoadingDialog loadingdialog;

	/**
	 * 构�?函数时创建监听对�?isShowDialog:是否显示dialog ture显示，false不显�?
	 *
	 * @param context
	 * @param inter
	 */
	public GsonHttpResponseHandler(Context context, JsonResponseInter inter) {
		this.inter = inter;
		this.context = context;
		if (isShowDialog) {

			// dialog = new ProgressDialog(context);
			loadingdialog = new LoadingDialog(context);

			/*
			 * dialog.setTitle("葩旅�?); dialog.setMessage("玩命加载�?...");
			 */
		}

	}
	/**
	 * @param context
	 * @param inter
	 * @param isShowDialog
	 *            是否显示
	 * @param msg
	 *            显示内容
	 */
	public GsonHttpResponseHandler(Context context, JsonResponseInter inter,
			boolean isShowDialog, String msg) {
		this.inter = inter;
		this.context = context;
		if (isShowDialog) {
			loadingdialog = new LoadingDialog(context);
		}

	}

	/**
	 * @param context
	 *            上下�?
	 * @param inter
	 * @param isShowDialog
	 *            是否显示
	 * @param msg
	 *            显示内容
	 * @param iscanel
	 *            是否支持自动取消
	 */
	public GsonHttpResponseHandler(Context context, JsonResponseInter inter,
			boolean isShowDialog, String msg, boolean iscanel) {
		this.inter = inter;
		this.context = context;
		if (isShowDialog) {

			loadingdialog = new LoadingDialog(context);
			if (iscanel == false) {
				loadingdialog.setCancelable(false);
			}

		}

	}



	@Override
	public void onStart() {
		super.onStart();
		if (isShowDialog) {
			if (loadingdialog != null && !loadingdialog.isShowing()) {
				try {
					loadingdialog.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
		super.onSuccess(statusCode, headers, response);
		String successJson = null;
		if (response != null) {
			successJson = response.toString();
		}

		if (inter != null) {

			inter.onSuccess(statusCode, successJson);
		}
	}



	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		super.onSuccess(statusCode, headers, response);
		String successJson = null;
		if (response != null) {
			successJson = response.toString();
		}
		if (inter != null) {
			inter.onSuccess(statusCode, successJson);
		}
	}

//	@Override
//	public void onSuccess(int statusCode, Header[] headers, String responseString) {
//		super.onSuccess(statusCode, headers, responseString);
//		if (inter != null) {
//			inter.onSuccess(statusCode, responseString);
//		}
//	}

	@Override
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, JSONObject errorResponse) {
		super.onFailure(statusCode, headers, throwable, errorResponse);
		String errorJson = null;
		if (errorResponse != null) {
			errorJson = errorResponse.toString();
		}
		if (inter != null) {
			inter.onFailure(statusCode, errorJson + "");
		}
	}

	@Override
	public void onFinish() {
		super.onFinish();
		if (isShowDialog) {
			// java.lang.IllegalArgumentException: View not
			// attached to window manager
			if (loadingdialog != null && loadingdialog.isShowing()) {
				try {
					loadingdialog.dismiss();
					loadingdialog.setCancelable(false);
					loadingdialog = null;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
