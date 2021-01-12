package com.bohui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bohui.R;
import com.bohui.utils.MapUtil;
import com.bohui.utils.Screen;

import androidx.annotation.NonNull;

/**
 * 选择地图的他弹框
 * 从底部到底部
 * create by yangwei
 * on 2020/6/21 08:27
 */
public class BottomToTopDialog extends Dialog implements View.OnClickListener {
    private Button btnGaoDeMap, btnBaiduMap, btnTencentMap, btnCancel;
    private double latx, laty;
    private String address;
    private RelativeLayout relContent;
    public BottomToTopDialog(@NonNull Context context, double latx, double laty, String address) {
        super(context);
        this.latx = latx;
        this.laty = laty;
        this.address = address;
        //手指触碰到外界取消
        setCanceledOnTouchOutside(true);
        //可取消 为true
        setCancelable(true);
        // 得到dialog的窗体
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        relContent = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.bottomtotop_dialog,null);
        setContentView(relContent);
        initViews();
    }

    private void initViews() {
        btnGaoDeMap = (Button) findViewById(R.id.gaode_map);
        btnBaiduMap = (Button) findViewById(R.id.baidu_map);
        btnTencentMap = (Button) findViewById(R.id.tencent_map);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnGaoDeMap.setOnClickListener(this);
        btnBaiduMap.setOnClickListener(this);
        btnTencentMap.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gaode_map:
                if (MapUtil.isGdMapInstalled()) {
                    MapUtil.openGaoDeNavi(getContext(), 0, 0, null, latx, laty, address);
                } else {
                    //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                    Toast.makeText(getContext(), "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.baidu_map:
                if (MapUtil.isBaiduMapInstalled()) {
                    MapUtil.openBaiDuNavi(getContext(), 0, 0, null, latx, laty, address);
                } else {
                    Toast.makeText(getContext(), "尚未安装百度地图", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tencent_map:
                if (MapUtil.isTencentMapInstalled()) {
                    MapUtil.openTencentMap(getContext(), 0, 0, null, latx, laty, address);
                } else {
                    Toast.makeText(getContext(), "尚未安装腾讯地图", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        relContent.post(()->{
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.height = btnBaiduMap.getMeasuredHeight()+btnCancel.getMeasuredHeight()+btnTencentMap.getMeasuredHeight()+btnGaoDeMap.getMeasuredHeight()+400;
//            params.height = relContent.getMeasuredHeight();
            this.getWindow().setAttributes(params);
        });
        super.show();

    }
}
