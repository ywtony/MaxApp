package com.bohui.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 0 on 2017/4/5.
 */

public class RegexUtil {
    public static boolean isEmpty(Context context, String s) {
        return TextUtils.isEmpty(s);
    }

    public static boolean isEmpty(Context context, String s, String noticeStr) {
        if (TextUtils.isEmpty(s)) {
            ToastUtil.showToast(context, noticeStr);
            return true;
        } else {
            return false;
        }
    }

    public static boolean notPhoneNumber(Context context, String phone) {
        Pattern p = Pattern.compile("^[\\d]{11}$");
        Matcher matcher = p.matcher(phone);
        if (matcher.matches()) {
            return false;
        } else {
            ToastUtil.showToast(context, "请输入11位手机号码");
            return true;
        }
    }

    public static boolean notPassword(Context context, String password) {
        Pattern p = Pattern.compile("^[\\w]{6,20}$");
        Matcher matcher = p.matcher(password);
        if (matcher.matches()) {
            return false;
        } else {
            ToastUtil.showToast(context, "请输入6-20位数字,英文密码");
            return true;
        }
    }
}
