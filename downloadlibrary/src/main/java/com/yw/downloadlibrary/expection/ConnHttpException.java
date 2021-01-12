package com.yw.downloadlibrary.expection;

/**
 * 链接服务端的自定义异常
 * create by yangwei
 * on 2019-12-23 19:02
 */
public class ConnHttpException extends Exception {
    public ConnHttpException() {
        super();
    }

    public ConnHttpException(String errMsg) {
        super(errMsg);
    }
}
