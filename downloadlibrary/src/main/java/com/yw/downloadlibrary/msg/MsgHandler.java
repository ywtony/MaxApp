package com.yw.downloadlibrary.msg;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.yw.downloadlibrary.Config;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.http.DownloadThread;
import com.yw.downloadlibrary.inter.DownloadCallback;

/**
 * 下载任务中的Handler，用于切换主线程和子线程，更新数据以及返回结果等
 * create by yangwei
 * on 2019-12-23 18:26
 */
public class MsgHandler extends Handler {
    private Context context;

    public MsgHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Config.DOWNLOAD_FILE://开启下载任务的进程,意味着链接成功了
                DownloadInfo info = (DownloadInfo) msg.obj;
                new DownloadThread(context, info).start();//开启下载线程
                if (downloadCallback != null) {
                    downloadCallback.connectSuccess();
                }
                break;
            case Config.UPDATE_DOWNLOAD_PROCESS://更新下载进度
                if (downloadCallback != null) {
                    downloadCallback.downloadUpdate((DownloadInfo) msg.obj);
                }
                break;
            case Config.SUCCESS_DOWNLOAD://下载成功
                if (downloadCallback != null) {
                    downloadCallback.downloadSuccess((String) msg.obj);
                }
                break;
            case Config.FAIL_DOWNLOAD://下载失败
                if (downloadCallback != null) {
                    downloadCallback.downloadFail((String) msg.obj);
                }
                break;
            case Config.START_DOWNLOAD://开始下载
                if (downloadCallback != null) {
                    downloadCallback.startDownload();
                }
                break;
//            case Config.CONNECT_SUCCESS://服务器连接成功
//                if (downloadCallback != null) {
//
//                }
//                break;
        }
    }

    private DownloadCallback downloadCallback;

    public void setDownloadCallback(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }
}
