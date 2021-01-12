package com.yw.downloadlibrary.http;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.yw.downloadlibrary.Config;
import com.yw.downloadlibrary.DownLoadManager;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.db.DownloadCacheConfig;
import com.yw.downloadlibrary.utils.JSONUtil;
import com.yw.downloadlibrary.utils.PathUtil;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 单任务下载线程，用于下载指定的文件
 * create by yangwei
 * on 2019-12-23 18:29
 */
public class DownloadThread extends Thread {
    private DownloadInfo downloadInfo;//与下载相关的一些信息
    private Context context;

    public DownloadThread(Context context, DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
        this.context = context;
    }

    @Override
    public void run() {
        super.run();
        try {
            downloadConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void downloadConfig() {
        DownloadInfo di = DownloadCacheConfig.getDefault(context).getDownloadInfo();
        if (di != null && di.getFileSize() > 0) {//使用缓存数据进行下载
            this.downloadInfo = di;
            Log.e("下载的开始位置：","不空");
        }else{
            Log.e("下载的开始位置：","空");
        }
        download();
    }

    private void download() {
        //如果检测到以前并没有下载则初始化一个位置
        HttpURLConnection conn = null;//http链接类
        RandomAccessFile raf = null;//文件存储类，此类可以指定从那个位置读取，那个位置下载等
        InputStream in = null;//输入流
        File file = null;//文件存放路径
        try {
            URL url = new URL(downloadInfo.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(Config.READ_TIMEOUT);
            conn.setRequestMethod(Config.REQEST_METHOD);
            //设置开始下载位置
            int startLocation = downloadInfo.getProcess();
            Log.e("下载的开始位置：",startLocation+"");
            /**
             * Range: bytes=0-499 下载第0-499字节范围的内容
             * Range: bytes=500-999 下载第500-999字节范围的内容
             * Range: bytes=-500 下载最后500字节的内容
             * Range: bytes=500- 下载从第500字节开始到文件结束部分的内容
             */
            conn.setRequestProperty("Range", "bytes=" + startLocation + "-" + downloadInfo.getFileSize());
            //设置文件写入位置
            file =  new File(PathUtil.createLocalFilePath(context,"box"), downloadInfo.getFileName());
            raf = new RandomAccessFile(file, "rwd");
            //设置文件写入位置
            raf.seek(startLocation);
            //开始下载
            Message msgs = new Message();
            msgs.what = Config.START_DOWNLOAD;
            DownLoadManager.getDefault(context).getHandler().sendMessage(msgs);
            //初始化下载进度
            int process = downloadInfo.getProcess();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                //读取数据
                in = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = in.read(buffer)) != -1) {
                    //写入文件
                    raf.write(buffer, 0, len);
                    //计算下载进度
                    process += len;
                    //发送消息给Handler更新进度
                    Message msg = new Message();
                    msg.what = Config.UPDATE_DOWNLOAD_PROCESS;
                    downloadInfo.setProcess(process);
                    msg.obj = downloadInfo;
                    DownLoadManager.getDefault(context).getHandler().sendMessage(msg);
                    DownloadInfo info = DownloadCacheConfig.getDefault(context).getDownloadInfo();
                    if (info == null) {
                        info = new DownloadInfo();
                    }
                    info.setProcess(process);
                    info.setStartLocation(process);
                    info.setFileSize(downloadInfo.getFileSize());
                    info.setFileName(downloadInfo.getFileName());
                    info.setUrl(downloadInfo.getUrl());
                    DownloadCacheConfig.getDefault(context).setDownloadInfo(JSONUtil.getInstance().getString(info));
                    //暂停下载，更新下载进度信息
                    //设置一下暂停需要干的事情
                    if (DownLoadManager.getDefault(context).isPause) {
                        return;
                    }
                }
                //下载完成后删除这次的下载信息
                Message msg = new Message();
                msg.what = Config.SUCCESS_DOWNLOAD;
                msg.obj = file.getPath();//存放的本地路径
                DownLoadManager.getDefault(context).getHandler().sendMessage(msg);
                DownloadCacheConfig.getDefault(context).removeDownloadInfo();
            }
        } catch (Exception e) {
            Message msg = new Message();
            msg.what = Config.FAIL_DOWNLOAD;
            msg.obj = e.getMessage();
            DownLoadManager.getDefault(context).getHandler().sendMessage(msg);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
                if (raf != null) {
                    raf.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
