package com.bohui.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 视图工具
 * Created by yangwei on 2018/3/23.
 */
public class ViewUtils {
    private ViewUtils() {
    }

    private static ViewUtils instance = null;

    public static ViewUtils getInstance() {
        if (instance == null) {
            instance = new ViewUtils();
        }
        return instance;
    }



    /**
     * 设置EditText drawableleft 的图标
     * @param context
     * @param left
     * @param editText
     */
    public void setEditTextDrawableLeft(Context context,int left,EditText editText){
        Drawable drawableLeft = context.getResources().getDrawable(left);
        editText.setCompoundDrawables(drawableLeft, null, null, null);
    }

    /**
     * 设置EditTExt drawable图片大小
     * @param context
     * @param left
     * @param width
     * @param height
     * @param editText
     */
    public void setEditTextDrawableLeftSize(Context context,int left,int width,int height,EditText editText){
        Drawable drawableLeft = context.getResources().getDrawable(left);
        drawableLeft.setBounds(0, 0, width, height);
        editText.setCompoundDrawables(drawableLeft, null, null, null);
    }

    /**
     * 设置TextView的drawable图片
     * @param context
     * @param left
     * @param textView
     */
    public void setTextViewDrawableLeft(Context context,int left,TextView textView){
        Drawable drawableLeft = context.getResources().getDrawable(left);
        textView.setCompoundDrawables(drawableLeft, null, null, null);
    }

    /**
     * 设置TextView的drawable图片大小
     * @param context
     * @param left
     * @param width
     * @param height
     * @param textView
     */
    public void setTextViewDrawableLeftSize(Context context,int left,int width,int height,TextView textView){
        Drawable drawableLeft = context.getResources().getDrawable(left);
        drawableLeft.setBounds(0, 0, width, height);
        textView.setCompoundDrawables(drawableLeft, null, null, null);
    }
}
