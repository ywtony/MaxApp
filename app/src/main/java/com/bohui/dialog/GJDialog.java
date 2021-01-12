package com.bohui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.adpter.BottomAdapter;
import com.bohui.bean.ButtomBean;
import com.bohui.bean.GJPoint;

import java.util.ArrayList;
import java.util.List;

public class GJDialog extends Dialog {

    private Context context;
    private ListView listView;
    private BottomAdapter adapter;
    private GJPoint gjPoint;
    private TextView tv_type;
    private TextView tv_level;
    private TextView tv_time;
    private TextView tv_clear;

    public GJDialog(Context context, GJPoint gjPoint) {
        super(context, R.style.dialog);
        this.context = context;
        this.gjPoint = gjPoint;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位�?
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画
        setContentView(R.layout.gj_dialog);
        // 宽度全屏
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        getWindow().setAttributes(lp);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);
//        setContentView(R.layout.ssid_dialog);
        initViews();
    }

    private void initViews() {
        tv_type = findViewById(R.id.alarm_tv_type);
        tv_level = findViewById(R.id.alarm_tv_level);
        tv_time = findViewById(R.id.alarm_tv_time);
        tv_clear = findViewById(R.id.tv_clear_gaojing);
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogGJListener != null) {
                    dialogGJListener.clearGj(gjPoint);
                }
            }
        });
        if (gjPoint != null) {
            tv_type.setText("告警类别：" + gjPoint.getAlarmType());
            int level = Integer.parseInt(gjPoint.getAlarmLevel());
            switch (level) {
                case 1:
                    tv_level.setText("告警级别：提示");
                    break;
                case 2:
                    tv_level.setText("告警级别：一般");
                    break;
                case 3:
                    tv_level.setText("告警级别：严重");
                    break;
                case 4:
                    tv_level.setText("告警级别：危急");
                    break;
            }

            tv_time.setText("告警时间：" + gjPoint.getAlarmTime().replace("T", ""));
        }
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }

    public interface DialogGJListener {
        public void clearGj(GJPoint bean);
    }

    private DialogGJListener dialogGJListener;

    public void setDialogSSIDListener(DialogGJListener dialogGJListener) {
        this.dialogGJListener = dialogGJListener;
    }

}