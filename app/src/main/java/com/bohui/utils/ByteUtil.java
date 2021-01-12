package com.bohui.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtil {
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static byte[] shortToByte(short value) {
        int temp = value;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (temp & 0xff);// 将最低位保存在最低位  
            temp = temp >> 8;// 向右移8位  
            System.out.println(b[i]);
        }
        return b;
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }


    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位  
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


    public static byte[] intToByte(int value) {
        int temp = value;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (temp & 0xff);
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static int byteToInt(byte[] b) {
        int a1 = b[0] & 0xff;
        int a2 = b[1] & 0xff;
        int a3 = b[2] & 0xff;
        int a4 = b[3] & 0xff;
        a4 = a4 << 24;
        a3 = a3 << 16;
        a2 = a2 << 8;
        return a4 | a3 | a2 | a1;
    }

    /**
     * 少了的字节补0
     *
     * @param hexStr
     * @param length
     * @return
     */
    public static byte[] add02Byte(String hexStr, int length) {

        //        hexStr.length()
        for (int i = hexStr.length(); i < length; i++) {
            hexStr = hexStr.concat("0");
        }

        return toBytes(hexStr);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            //            stringBuilder.append(i + ":");

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    public static byte[] objectToByte(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        byte[] b = bos.toByteArray();
        oos.close();
        bos.close();
        return b;
    }

    /*public static byte[] objectToByte(byte[] bytes) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(bytes);
        byte[] b = bos.toByteArray();
        oos.close();
        bos.close();
        return b;
    }*/

    public static Object byteToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        Object obj;
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    public static String hex2Str(byte[] bytes) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public static int check_buf(byte[] buf) {
        int i = 0;
        int sum;
        int c = 0;
        int dataLen = buf.length - 1;
        while (i < dataLen) {
            c += (char) buf[i];
            i++;
        }
        c = c & 0x00ff;
        sum = c;

        return sum;
    }

    /**
     * 判断字符串的编码
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode[] = new String[]{
                "UTF-8",
                "ISO-8859-1",
                "GB2312",
                "GBK",
                "GB18030",
                "Big5",
                "Unicode",
                "ASCII"
        };
        for (int i = 0; i < encode.length; i++) {
            try {
                if (str.equals(new String(str.getBytes(encode[i]), encode[i]))) {
                    return encode[i];
                }
            } catch (Exception ex) {
            }
        }

        return "";
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {

        return hexStringToString(s, "UTF-8");
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s, String encode) {

        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, encode);
            // new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转化成为16进制字符串
     *
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static void main(String[] args) {

        /*String str = "98d9a8d9ad111000a1.0.0";
        String hexStr = ByteUtil.strTo16(str);
        System.out.println(hexStr);
        String strNormal = ByteUtil.hexStringToString(hexStr);
        System.out.println(strNormal);*/

        int index = ByteUtil.hexStringToByte("aa55.11V1122").length;
        System.out.println(index);

    }

    /**
     * hex字符串补0
     */
    public static String bu0(String s, int length) {
        while (true) {
            if (s.length() < length * 2) {
                s += 0;
            } else {
                return s;
            }
        }
    }

    /**
     * hex字符串补0
     */
    public static String bu0front(String s, int length) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (s.length() < length * 2) {
                s = 0 + s;

            } else {
                return s;
            }
        }
    }

    /**
     * byte 数组截取
     *
     * @param src           源数组
     * @param startPosition 截取起始位置byte单位
     * @param length        截取字节长度
     */
    public static byte[] subBytes(byte[] src, int startPosition, int length) {
        byte[] b = new byte[length];
        System.arraycopy(src, startPosition, b, 0, length);
        return b;
    }

    /**
     * byte转int
     */
    public static int bytes2Int(byte[] b) {
        int a1 = b[0] & 0xff;
        int a2 = b[1] & 0xff;
        int a3 = b[2] & 0xff;
        int a4 = b[3] & 0xff;
        a4 = a4 << 24;
        a3 = a3 << 16;
        a2 = a2 << 8;
        return a4 | a3 | a2 | a1;
    }

    /**
     * int转byte数组，低八位
     */
    public static byte[] int2ByteH(int a) {
        byte[] b = new byte[4];
        b[3] = (byte) (a >> 24);
        b[2] = (byte) (a >> 16);
        b[1] = (byte) (a >> 8);
        b[0] = (byte) (a);

        return b;
    }
}
