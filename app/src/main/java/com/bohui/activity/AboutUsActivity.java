package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.utils.ActivityUtil;
//import com.githang.statusbar.StatusBarCompat;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关于我们
 */
public class AboutUsActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_layout);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.about_us));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
        }
    }
}
