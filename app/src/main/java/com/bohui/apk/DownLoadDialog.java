package com.bohui.apk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.ToastUtil;

import java.io.File;

/**
 * 下载弹框Dialog
 *
 * @author tony
 */
public class DownLoadDialog extends Dialog implements
        View.OnClickListener {
    private TextView tv_content;
    private TextView tv_noupdate;
    private TextView tv_yesupdate;
    private ProgressBar bar;
    private Context context;
    private DownloadNotification downloadNotification = null;
    private String url;// 下载路径
    private int length; // 文件大小
    private String content;// 更新内容
    private boolean isUpdate;

    public DownLoadDialog(Context context, String url, int length,
                          String content, boolean isUpdate) {
        super(context, R.style.dialog);
        this.context = context;
        this.url = url;
        this.length = length;
        this.content = content;
        this.isUpdate = isUpdate;
        if (downloadNotification == null) {
            downloadNotification = new DownloadNotification(context);
            downloadNotification.initNotification();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);
        initViews();
    }

    private void initViews() {
        tv_content = (TextView) findViewById(R.id.dialog_tv_content);
        tv_noupdate = (TextView) findViewById(R.id.dialog_tv_noupdate);
        tv_yesupdate = (TextView) findViewById(R.id.dialog_tv_yesupdate);
        bar = (ProgressBar) findViewById(R.id.downLoadProgress);
        tv_noupdate.setOnClickListener(this);
        tv_yesupdate.setOnClickListener(this);
        tv_content.setText(content);
        if (isUpdate) {
            this.setCancelable(false);
            bar.setVisibility(View.VISIBLE);
        } else {
            this.setCancelable(true);
            bar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_tv_noupdate:
                if (isUpdate) {//如果是，强制更新
                    //关闭整个
                    dismiss();
                    ActivityUtil.getInstance().finishSmaillActivitys();
                    android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                    System.exit(0);
                } else {
                    this.dismiss();
                }
                break;
            case R.id.dialog_tv_yesupdate:
                ToastUtil.getInstance().show(context,"安装包已在后台下载，如要查看下载进度，请查看通知通知栏...");
                if (isUpdate) {
                    bar.setProgress(5);
                    //对话框一直保�?
                    download();
                } else {
                    this.dismiss();
                    download();
                }
                break;
        }
    }

    private void updateApkToBackground() {
        new Thread() {
            public void run() {
                downloadNotification.showNotication();
                boolean result = DownApkUtils.getInstance().download(context,
                        url, length, handler, downloadNotification);
                if (result) {
                    handler.sendEmptyMessage(DownApkUtils.NOTIFICATION_SUCCESS);
                } else {
                    handler.sendEmptyMessage(DownApkUtils.NOTIFICATION_FAILER);
                }
            }
        }.start();

    }

    /**
     * 主线程中更新ui
     */
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DownApkUtils.NOTIFICATION_UPDATE:
                    DownInfo info = (DownInfo) msg.obj;
                    downloadNotification.updateNotification(info.getDownloaded(),
                            info.getLength(), info.getTempProgress(),
                            info.getProgresss());
                    bar.setProgress(info.getProgresss());
                    break;
                case DownApkUtils.NOTIFICATION_SUCCESS:// 下载成功
                    File file = new File(DownLoadApkFileOption.getinstance()
                            .getSavePath(), DownLoadApkFileOption.getinstance()
                            .getFileName(context));
                    downloadNotification.downLoadSuccess(file);
                    break;
                case DownApkUtils.NOTIFICATION_FAILER:// 下载失败
                    downloadNotification.downFailer();
                    break;
            }
        }

        ;
    };

    /*
     * 1.判断apk是否正在下载中，不要重复下载�?2.判断apk是否已下载了，则直接安装�?.未下载，也未在下载中
     */
    public void download() {
        if (DownApkUtils.isDownloading) {// 提示不要重复下载
            ToastUtil.getInstance().show(context, "文件正在下载,请不要重复下");
        } else if (DownLoadFileUtil.getInstance().isFileExists(context)) {// 如果�?��版本的apk已经下载，则直接进行安装
            // 判断是否下载完成，如果下载已完成，则直接安装�?
            // 如果下载未完成，删除后直接下�?
//			if (DownLoadFileUtil.getInstance().isFileFull(context)) {
//				InstallApkUtil.getInstance().install(
//						context,
//						new File(DownLoadApkFileOption.getinstance()
//								.getSavePath(), DownLoadApkFileOption
//								.getinstance().getFileName(context)));
//			} else {
            // 将文件删除后再次进行下载
            if (DownLoadFileUtil.getInstance().deleteFile(context)) {
                updateApkToBackground();
            }
//			}
        } else {// 正常下载
            updateApkToBackground();
        }
    }
}
