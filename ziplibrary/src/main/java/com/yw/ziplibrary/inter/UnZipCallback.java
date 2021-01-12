package com.yw.ziplibrary.inter;

/**
 * 解压回调函数
 * create by yangwei
 * on 2019-12-24 10:42
 */
public interface UnZipCallback {
    /**
     * 开始解压
     *
     * @param obj
     */
    void startUnZip(Object obj);

    /**
     * 正在解压
     *
     * @param process 解压进度
     * @param obj
     */
    void processUnZip(int process, Object obj);

    /**
     * 解压成功
     *
     * @param obj
     */
    void unZipSuccess(Object obj);

    /**
     * 解压失败
     *
     * @param errMsg
     */
    void unZipFail(String outputFilePath, String errMsg);

}
