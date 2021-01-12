package com.bohui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;


/**
 * 通用弹出框，文本可配置
 * Created by yangwei on 2018/3/16.
 */
public class CommonDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private ImageView iv_close;
    private TextView tv_title;
    private TextView tv_toindex;
    private TextView tv_quguanzhu;
    private String title;
    private String title1;
    private String btnLeft;
    private String btnRight;
    private TextView tv_title1;
    private String id = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CommonDialog(Context context, String title, String btnLeft, String btnRight) {
        super(context, R.style.dialog);
        this.context = context;
        this.title = title;
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
    }

    public CommonDialog(Context context, String title1, String title, String btnLeft, String btnRight) {
        super(context, R.style.dialog);
        this.context = context;
        this.title = title;
        this.btnLeft = btnLeft;
        this.btnRight = btnRight;
        this.title1 = title1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_dialog);
        initViews();
    }

    private void initViews() {
        iv_close = (ImageView) findViewById(R.id.dialog_close);
        tv_title = (TextView) findViewById(R.id.dialog_title);
        tv_toindex = (TextView) findViewById(R.id.dialog_qushouye);
        tv_quguanzhu = (TextView) findViewById(R.id.dialog_quguanzhu);
        tv_title1 = (TextView) findViewById(R.id.dialog_tv_title);
        iv_close.setOnClickListener(this);
        tv_toindex.setOnClickListener(this);
        tv_quguanzhu.setOnClickListener(this);
        if (title1 == null || "".equals(title1)) {
            tv_title1.setText("");
            tv_title1.setVisibility(View.GONE);
        } else {
            tv_title1.setText(title1);
            tv_title1.setVisibility(View.VISIBLE);
        }

        tv_title.setText(title);
        tv_toindex.setText(btnLeft);
        tv_quguanzhu.setText(btnRight);
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_close:
                if(dialogCommonListener!=null){
                    dialogCommonListener.close();
                }
                dismiss();
                break;
            case R.id.dialog_qushouye:
                if(dialogCommonListener!=null){
                    dialogCommonListener.close();
                }
                dismiss();
                break;
            case R.id.dialog_quguanzhu:
                if (dialogCommonListener != null) {
                    dialogCommonListener.common(id);
                }
                dismiss();
                break;
        }
    }

    public interface DialogCommonListener {
        void common(String ssid);
        void close();
    }

    private DialogCommonListener dialogCommonListener;

    public void setDialogConnectListener(DialogCommonListener dialogCommonListener) {
        this.dialogCommonListener = dialogCommonListener;
    }
}
