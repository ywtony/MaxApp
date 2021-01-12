package com.yw.downloadinguilibrary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;


/**
 * 下载进度条
 * create by yangwei
 * on 2019-12-24 14:28
 */
public class ProgressbarDialog extends Dialog {
    private ProgressBar bar;
    private Context context;

    public ProgressbarDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbardialog_layout);
        setCanceledOnTouchOutside(false);
        initViews();

    }

    private void initViews() {
        bar = findViewById(R.id.downLoadProgress);
    }

    /**
     * 主线程中更新ui
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DialogConfig.PROCESS://更新中
                    if (bar != null) {
                        bar.setProgress(msg.arg1);
                    }
                    break;
                case DialogConfig.FINISH://更新完成
                    if (progressbarDialogCallback != null) {
                        progressbarDialogCallback.finish(null);
                    }
                    dismiss();
                    break;
            }
        }
    };

    /**
     * 获取handler
     *
     * @return
     */
    public Handler getHandler() {
        return handler;
    }

    private ProgressbarDialogCallback progressbarDialogCallback;

    public void setProgressbarDialogCallback(ProgressbarDialogCallback progressbarDialogCallback) {
        this.progressbarDialogCallback = progressbarDialogCallback;
    }

    public interface ProgressbarDialogCallback {
        void finish(Object obj);
    }

}