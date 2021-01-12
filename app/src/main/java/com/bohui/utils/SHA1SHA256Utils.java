package com.bohui.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 散列加密
 * Created by yangwei on 2018/4/20.
 */
public class SHA1SHA256Utils {
    private SHA1SHA256Utils() {
    }

    private static SHA1SHA256Utils instance;

    public static SHA1SHA256Utils getInstance() {
        if (instance == null) {
            instance = new SHA1SHA256Utils();
        }
        return instance;
    }
    /**
     * SHA加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */
    public  String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384、SHA-256等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
