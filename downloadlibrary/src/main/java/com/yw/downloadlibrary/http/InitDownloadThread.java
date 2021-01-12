package com.yw.downloadlibrary.http;

import android.content.Context;
import android.nfc.Tag;
import android.os.Message;
import android.util.Log;

import com.yw.downloadlibrary.Config;
import com.yw.downloadlibrary.DownLoadManager;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.expection.ConnHttpException;
import com.yw.downloadlibrary.expection.ExceptionMsg;
import com.yw.downloadlibrary.expection.URLNullPointerException;
import com.yw.downloadlibrary.expection.YwRunTimeException;
import com.yw.downloadlibrary.utils.PathUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 初始化下载任务线程
 * create by yangwei
 * on 2019-12-23 18:38
 */
public class InitDownloadThread extends Thread {
    private DownloadInfo downloadInfo;
    private Context context;

    public InitDownloadThread(Context context, DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        int length = -1;//初始化文件大小
        int connectCount = 0;//连接次数
        try {
            //链接网络
            URL url = new URL(downloadInfo.getUrl());
            if (url == null) {
                throw new URLNullPointerException(ExceptionMsg.URL_NULLPOINTER);
            }
            conn = (HttpURLConnection) url.openConnection();
            //设置链接超时时间
            conn.setConnectTimeout(Config.CONNECT_TIMEOUT);
            //设置请求方式
            conn.setRequestMethod(Config.REQEST_METHOD);
            //获取服务端返回的状态吗
            int statusCode = conn.getResponseCode();
            //回调链接成功和连接失败
            while (connectCount < 3) {
                try {
                    if (statusCode == 200) {
                        //表明链接成功了
                        //获取文件大小
                        length = conn.getContentLength();
                        if (length < 0) {
                            throw new YwRunTimeException(ExceptionMsg.FILE_IS_NULL);
                        }
                        //创建目录
                        File dir = new File(PathUtil.createLocalFilePath(context,"box"));
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        //创建一个指定大小的文件
                        File file = new File(dir, downloadInfo.getFileName());
                        raf = new RandomAccessFile(file, "rwd");
                        //设置文件长度
                        raf.setLength(length);
                        downloadInfo.setFileSize(length);
                        //启动下载任务
                        Message msg = new Message();
                        msg.what = Config.DOWNLOAD_FILE;
                        msg.obj = downloadInfo;
                        DownLoadManager.getDefault(context).getHandler().sendMessage(msg);
                        break;
                    }
                } catch (Exception e) {
                    connectCount++;
                    if (connectCount == 2) {//尝试链接三次，如果三次都链接不上则视为链接失败
                        throw new ConnHttpException(ExceptionMsg.HTTP_CONNECTION);
                    }
                    continue;
                }

            }
        } catch (Exception e) {
            Log.e("初始化异常：",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
