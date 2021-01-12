package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.Gasentitiy;
import com.bohui.utils.ActivityUtil;
//import com.githang.statusbar.StatusBarCompat;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/19 0019.
 * 气体详情
 */

public class GasDetailsActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_close)
    Button btn_close;
    @BindView(R.id.tv_checkName)
    TextView tv_checkName;
    @BindView(R.id.tv_checkTime)
    TextView tv_checkTime;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_statusValue)
    TextView tv_statusValue;
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_other)
    TextView tv_other;
    private Gasentitiy gasentitiy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasdetails);
        ActivityUtil.getInstance().addSmaillActivitys(this);
        ButterKnife.bind(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        gasentitiy=(Gasentitiy) getIntent().getSerializableExtra(MainActivity.WEATHER_INTENT_NAME);
        initView();
        iv_header_left.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.gas_details));
    }
    private void initView(){
        tv_checkName.setText(gasentitiy.getDetectionName());
        tv_checkTime.setText(gasentitiy.getDetectionTime());
        tv_status.setText(gasentitiy.getDetectionStatus());
        tv_statusValue.setText(gasentitiy.getStatusValue());
        tv_number.setText(gasentitiy.getNumber());
        tv_other.setText(gasentitiy.getOther());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_left:
                finish();
            case R.id.btn_close:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//    ButterKnife.unbind(this);
    }
}
