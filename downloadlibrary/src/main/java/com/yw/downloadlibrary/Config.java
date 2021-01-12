package com.yw.downloadlibrary;

import android.os.Environment;

import java.io.File;

/**
 * 全局配置类
 * create by yangwei
 * on 2019-12-23 18:24
 */
public class Config {
    //http相关
//    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory()+"/box";//本地存放路径
    public static final int CONNECT_TIMEOUT = 5000;//连接超时时间设置为5秒
    public static final int READ_TIMEOUT = 10 * 1000;//读取超时时间设置为10秒
    public static final String REQEST_METHOD = "GET";//设置请求方式
    //handler相关
    public static final int DOWNLOAD_FILE = 1;//开启下载任务
    public static final int START_DOWNLOAD = 2;//开始下载
    public static final int UPDATE_DOWNLOAD_PROCESS = 3;//更新下载进度
    public static final int SUCCESS_DOWNLOAD = 4;//下载完成
    public static final int FAIL_DOWNLOAD = 5;//下载失败
    public static final int CONNECT_SUCCESS = 6;//连接成功
    //配置相关
    public static final String CONIFG_NAME = "downloadinfo.db";//sharepreference配置
}
