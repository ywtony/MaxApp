package com.bohui.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.adpter.AlarmsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class CleraAlarmPopWin extends PopupWindow {

    private Context mContext;

    private View view;

    private TextView pop_cancel, pop_determine;
    private List<String>generalCause;
    private GridView grdview;
    private AlarmsAdapter alarmsAdapter;


    public CleraAlarmPopWin(Context mContext, final List<String> generalCause,final CleraAlarmListener listener) {
        this.generalCause=generalCause;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.clear_alarms_pop, null);
        pop_cancel = (TextView) view.findViewById(R.id.pop_cancel);
        pop_determine = (TextView) view.findViewById(R.id.pop_determine);
        grdview=(GridView)view.findViewById(R.id.grdview);
         alarmsAdapter=new AlarmsAdapter(mContext,generalCause,R.layout.item_clear);
        grdview.setAdapter(alarmsAdapter);
        // 取消按钮
        pop_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        pop_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            listener.onDecideancel(alarmsAdapter.getReason());
            }
        });

        // 设置外部可点击
        this.setOutsideTouchable(false);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(false);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }
    public interface  CleraAlarmListener{
        public void onDecideancel(List<String>reasons);
    }
}
