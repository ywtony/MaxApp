package com.bohui.dialog;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.bohui.R;


/**
 * 加载中Dialog
 * 
 * @author HerotCulb
 * 
 * @E-mail herotculb@live.com
 * 
 * @Createtime 2014-5-10 上午9:14:34
 * 
 */
public class LoadingDialog extends Dialog {

	private TextView tips_loading_msg;

	private String message = null;
	
	public LoadingDialog(Context context) {
		super(context);
		message = getContext().getResources().getString(R.string.msg_load_ing);
	}

	public LoadingDialog(Context context, String message) {
		super(context, R.style.dialog);
		this.message = message;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	public LoadingDialog(Context context, int theme, String message) {
		super(context, R.style.dialog);
		this.message = message;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_tips_loading);
//		tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
//		tips_loading_msg.setText(this.message);
	}

	public void setText(String message) {
		this.message = message;
		tips_loading_msg.setText(this.message);
	}

	public void setText(int resId) {
//		setText(getContext().getResources().getString(resId));
	}

}
