package com.yw.ziplibrary.inter;

/**
 * 压缩回调函数
 * create by yangwei
 * on 2019-12-24 10:02
 */
public interface ZipCallback {
    /**
     * 开始压缩
     *
     * @param obj
     */
    void startZip(Object obj);

    /**
     * 压缩进度
     *
     * @param process
     * @param obj
     */
    void processZip(int process, Object obj);

    /**
     * 压缩成功
     *
     * @param obj
     */
    void zipSuccess(Object obj);

    /**
     * 压缩失败
     *
     * @param obj
     */
    void zipFail(Object obj);
}
