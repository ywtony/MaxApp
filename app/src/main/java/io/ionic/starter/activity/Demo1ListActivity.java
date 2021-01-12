package io.ionic.starter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sdk.NETDEV_CLOUD_DEV_INFO_S;
import com.sdk.NetDEVSDK;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.ionic.starter.adapter.DemoAdapter;
import io.ionic.starter.app.CeshiApp;
import io.ionic.starter.model.JiankongModel;
import io.ionic.starter.utils.CustomToast;

public class Demo1ListActivity extends Activity implements View.OnClickListener, DemoAdapter.OnOrderClickLister {
    private RelativeLayout rr_back;
    private TextView title;

    private SweetAlertDialog pDialog;

    private TwinklingRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private List<JiankongModel> models = new ArrayList<>();
    private String url;
    private String user;
    private String pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        initViewPager();
        url = getIntent().getStringExtra("url");
        user = getIntent().getStringExtra("user");
        pwd = getIntent().getStringExtra("pwd");
        refreshLayout.startRefresh();


        pDialog = new SweetAlertDialog(Demo1ListActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("请稍后...");
        pDialog.setCancelable(true);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(Demo1ListActivity.this, R.color.theme_color));

        NETDEV_CLOUD_DEV_INFO_S stclouddeviceinfo = new NETDEV_CLOUD_DEV_INFO_S();
        long dwFileHandle = NetDEVSDK.NETDEV_FindCloudDevList(NetDEVSDK.glpcloudID);
        for (int i = 0; i < 10; i++) {
            String strMeg = "";
            int iRet = NetDEVSDK.NETDEV_FindNextCloudDevInfo(dwFileHandle, stclouddeviceinfo);
            if (0 == iRet) {
                break;
            } else {
                JiankongModel m = new JiankongModel();
                m.setStation_name(stclouddeviceinfo.szDevName);
                m.setCloud_url(url);
                m.setCloud_pwd(pwd);
                m.setCloud_username(user);
                m.setDev_name(stclouddeviceinfo.szDevUserName);
                models.add(m);
//                strMeg = "IP:" + stclouddeviceinfo.szIPAddr + "\n";
//                strOut += strMeg;
//                strMeg = "User Name:" + stclouddeviceinfo.szDevUserName + "\n";
//                strOut += strMeg;
//                strMeg = "Serial Num:" + stclouddeviceinfo.szDevSerialNum + "\n";
//                strOut += strMeg;
//                strMeg = "Dev Name:" +  + "\n";
//                strOut += strMeg;
//                strMeg = "Dev Model:" + stclouddeviceinfo.szDevModel + "\n";
//                strOut += strMeg;
//                strMeg = "Dev Port:" + String.valueOf(stclouddeviceinfo.dwDevPort) + "";
//                strOut += strMeg;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initViewPager() {
        rr_back = findViewById(R.id.ll_back);
        rr_back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("视频监控");

        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recyclerView);

        shuaxin();
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager3 = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL, false);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager3);
        adapter = new DemoAdapter(this, models);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        refreshLayout.startRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void shuaxin() {
        /**
         * 刷新加载
         */
        ProgressLayout header = new ProgressLayout(this);
        refreshLayout.setHeaderView(header);
        refreshLayout.setFloatRefresh(true);
        /**
         * 刷新加载圈的颜色
         */
        header.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);
        /**
         * 刷新加载圈是否在顶部
         */
        refreshLayout.setOverScrollRefreshShow(false);
        /**
         * 是否可以加载更多
         */
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout1) {
                refreshLayout.finishRefreshing();
//                getList();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout1) {
                refreshLayout.finishLoadmore();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }




    @Override
    public void onIntent(int position) {
        Intent intent = new Intent(Demo1ListActivity.this, Demo4ListActivity.class);
        intent.putExtra("model", (Serializable) models.get(position));
        startActivity(intent);
    }
}
