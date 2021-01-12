package com.yw.downloadlibrary.expection;

/**
 * 自定义异常，当URL为null的时候就抛出这个异常
 * create by yangwei
 * on 2019-12-23 18:43
 */
public class URLNullPointerException extends Exception {
    public URLNullPointerException() {
        super();
    }

    public URLNullPointerException(String errMsg) {
        super(errMsg);
    }
}
