package com.yw.downloadlibrary.expection;

/**
 * 搞一个统一的运行时异常，统一管理异常
 * create by yangwei
 * on 2019-12-23 19:05
 */
public class YwRunTimeException extends Exception {
    public YwRunTimeException() {
        super();
    }

    public YwRunTimeException(String errMsg) {
        super(errMsg);
    }
}
