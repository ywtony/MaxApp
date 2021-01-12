package com.bohui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;


/**
 * Created by 0 on 2017/3/19.
 */

public class DialogUtil {

    /**
     * 单文字,单按钮
     *
     * @param activity   activity
     * @param text       文字
     * @param buttontext 按钮文字
     */
    public static void showSingleButtonDialog(final Activity activity, String text, String buttontext) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.single_button_dialog, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        TextView button_ok = view.findViewById(R.id.go);
        button_ok.setText(buttontext);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        dialog.show();
    }

    /**
     * 单文字,单按钮
     *
     * @param activity   activity
     * @param text       文字
     * @param buttontext 按钮文字
     */
    public static void showSingleButtonDialog(final Activity activity, String text, String buttontext,boolean cancelable) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(cancelable);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.single_button_dialog, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        TextView button_ok = view.findViewById(R.id.go);
        button_ok.setText(buttontext);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        dialog.show();
    }
    /**
     * 单文字,单按钮
     *
     * @param activity   activity
     * @param text       文字
     * @param buttontext 按钮文字
     */
    public static void showSingleButtonDialog(final Activity activity, String text, String buttontext, boolean cancelable, final OnButtonChooseListner listner) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(cancelable);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.single_button_dialog, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        TextView button_ok = view.findViewById(R.id.go);
        button_ok.setText(buttontext);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onOk();
            }
        });

        dialog.setContentView(view);

        dialog.show();
    }

    public interface OnButtonChooseListner {
        void onOk();
    }

    private OnButtonChooseListner onButtonChooseListner;

    /**
     * 单文字,双按钮
     */
    public static void showTwoButtonDialog(final Activity activity, String text, String buttonOk, String buttonCancel, final OnButtonChooseListner onButtonChooseListner) {
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);

        dialog.setCanceledOnTouchOutside(true);
        RelativeLayout view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.two_button_dialog, null);
        ((TextView) view.findViewById(R.id.text)).setText(text);
        TextView button_ok = view.findViewById(R.id.go);
        button_ok.setText(buttonOk);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onButtonChooseListner != null) {
                    onButtonChooseListner.onOk();
                }

            }
        });
        TextView button_cancel = view.findViewById(R.id.cancel);
        button_cancel.setText(buttonCancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        dialog.show();

    }
}
