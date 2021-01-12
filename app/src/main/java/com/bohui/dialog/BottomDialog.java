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

import com.bohui.R;
import com.bohui.adpter.BottomAdapter;
import com.bohui.bean.ButtomBean;

import java.util.ArrayList;
import java.util.List;

public class BottomDialog extends Dialog implements AdapterView.OnItemClickListener {

    private Context context;
    private ListView listView;
    private BottomAdapter adapter;
    private List<ButtomBean> datas = new ArrayList<>();
    private int type;//1默认区域 2告警级别

    public BottomDialog(Context context, List<ButtomBean> datas, int type) {
        super(context, R.style.dialog);
        this.context = context;
        this.type = type;
        this.datas = datas;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位�?
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画
        setContentView(R.layout.ssid_dialog);
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
        listView = (ListView) findViewById(R.id.dialog_listview);
        listView.setOnItemClickListener(this);
        adapter = new BottomAdapter(context,type);
        adapter.setList(datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.dismiss();
        if (dialogSSIDListener != null) {
            dialogSSIDListener.buttom(datas.get(i));
        }
    }

    public interface DialogSSIDListener {
        public void buttom(ButtomBean bean);
    }

    private DialogSSIDListener dialogSSIDListener;

    public void setDialogSSIDListener(DialogSSIDListener dialogSSIDListener) {
        this.dialogSSIDListener = dialogSSIDListener;
    }
}