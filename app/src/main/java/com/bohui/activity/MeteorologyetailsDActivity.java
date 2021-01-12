package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.utils.ActivityUtil;
//import com.githang.statusbar.StatusBarCompat;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/19 0019.
 *
 */

public class MeteorologyetailsDActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteorologyetails);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        iv_header_left.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.synopsis));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.btn_submit:
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
