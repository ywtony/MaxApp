package com.bohui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.Fault;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DataUtils;
import com.bohui.utils.ToastUtils;
import com.bohui.widget.CleraAlarmPopWin;
import com.bohui.widget.PhotoPopWin;
import com.wq.photo.util.StatusBarCompat;
//import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/19 0019.
 * 故障详情
 */

public class FaultetailsActivity extends Activity implements View.OnClickListener{
    private Context mContext;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_faultTime)
    TextView tv_faultTime;
    @BindView(R.id.tv_lever)
    TextView tv_lever;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_faultData)
    TextView tv_faultData;
    @BindView(R.id.tv_determine)
    TextView tv_determine;
    @BindView(R.id.tv_clear)
    TextView tv_clear;
    private Fault fault;
    CleraAlarmPopWin cleraAlarmPopWin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_tetails);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        mContext=this;
        fault=(Fault) getIntent().getSerializableExtra("fault");
        initView();
    }
    private void initView(){
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.fault_details));
        tv_faultTime.setText(fault.getFaultTime());
        tv_faultData.setText(fault.getDescribe());
        tv_lever.setText(fault.getFaultLevel());
        tv_address.setText(fault.getAddress());
        tv_clear.setOnClickListener(this);
        tv_determine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_clear:
                showPopFormBottom();
                break;
            case R.id.tv_determine:
                ToastUtils.show(mContext,"警告已确定");
                finish();
                break;
        }
    }

    public void showPopFormBottom(){


         cleraAlarmPopWin=new CleraAlarmPopWin(mContext, DataUtils.createFaultReasons(), new CleraAlarmPopWin.CleraAlarmListener() {
            @Override
            public void onDecideancel(List<String> reasonss) {
                cleraAlarmPopWin.dismiss();
                for(String s:reasonss){
                    Log.i("jackFire","reasonss  "+s);
                }

            }
        });
        cleraAlarmPopWin.showAtLocation(tv_determine, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//    ButterKnife.unbind(this);
    }
}