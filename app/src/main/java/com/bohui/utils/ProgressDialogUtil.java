package com.bohui.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;


public class ProgressDialogUtil {
    private ProgressDialog progressDialog;
    private static ProgressDialogUtil instance = null;

    public static ProgressDialogUtil getInstance() {
        if (instance == null) {
            synchronized (ProgressDialogUtil.class) {
                if (instance == null) {
                    instance = new ProgressDialogUtil();
                }
            }
        }
        return instance;
    }

    public void showProgressDialog(Context context, String mess) {
        if (context != null || progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context);
            if (progressDialog != null) {
                progressDialog.setMessage(mess);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            progressDialog.dismiss();
                        }
                        return false;
                    }
                });
            }
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
