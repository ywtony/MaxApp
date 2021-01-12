package com.bohui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.adpter.Faultdapter;
import com.bohui.entity.Fault;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DataUtils;
//import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/18 0018.
 *
 */

public class FaultListActivity extends Activity  implements View.OnClickListener{
    private Context mContext;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listView)
    ListView listView;
    private List<Fault> list;
    private Faultdapter faultdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faultlist);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        mContext=this;
        list=DataUtils.createFault();
        initView();

    }

    private void initView(){
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.fault_list));
        faultdapter=new Faultdapter(mContext,list,R.layout.item_fault);
        listView.setAdapter(faultdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(FaultListActivity.this,FaultetailsActivity.class);
                intent.putExtra("fault",list.get(i));
                startActivity(intent);
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_left:
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

