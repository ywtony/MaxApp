package com.bohui.apk;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


import com.bohui.utils.DevLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * apk下载工具
 *
 * @author tony
 */
public class DownApkUtils {
    private static final String APK_NAME = "bohui.apk";
    public static final int NOTIFICATION_UPDATE = 2000;// 通知主线程更新notification
    public static final int NOTIFICATION_SUCCESS = 2001;// 通知主线程下载apk成功
    public static final int NOTIFICATION_FAILER = 2002;// 通知主线程下载apk失败
    public static boolean isDownloading = false;
    // 文件的大�?
    public static int length = 0;
    // 累计下载�?
    public int downloaded = 0;

    private static DownApkUtils instance = null;

    public static DownApkUtils getInstance() {
        if (instance == null) {
            instance = new DownApkUtils();
        }
        return instance;
    }

    /**
     * 下载APK文件
     *
     * @param context
     * @param handler
     * @throws MalformedURLException
     * @throws IOException
     */
    public boolean download(Context context, String url, int length,
                            Handler handler, DownloadNotification downloadNotification) {
        DevLog.e("downloadurl:" + url);
        boolean result = false;
        int progress = 0;
        HttpURLConnection conn = null;
        try {
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conn.getInputStream();
                    String path = DownLoadApkFileOption.getinstance().getSavePath();
                    String name = DownLoadApkFileOption.getinstance().getFileName(context);
//                    DevLog.e("path:" + path);
//                    DevLog.e("name:" + name);
                    File file = new File(path, name);
                    if(file.exists()){
//                        DevLog.e("存在");
                    }else{
                        file.createNewFile();
//                        DevLog.e("不存在");
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buff = new byte[1024];
                    int len = 0;
                    int tempProgress = -1;
                    int times = 0;

                    DevLog.e("length:"+length);
                    while ((len = in.read(buff)) != -1) {
                        isDownloading = true;
                        progress = (int) (downloaded * 100.0 / length);
                        DevLog.e("progress:"+len);
                        out.write(buff, 0, len);
                        // 累计下载�?
                        downloaded += len;
                        if (times >= 512 || progress == 100) {
                            times = 0;
                            Message msg = new Message();
                            DownInfo info = new DownInfo();
                            info.setDownloaded(downloaded);
                            info.setLength(length);
                            info.setProgresss(progress);
                            info.setTempProgress(tempProgress);
                            msg.obj = info;
                            msg.what = NOTIFICATION_UPDATE;
                            handler.sendMessage(msg);
                        }
                        times++;
                    }
                    isDownloading = false;
                    progress = 0;
                    downloaded = 0;
                    out.close();
                    in.close();
                    result = true;
                } else {
                }
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
