package io.ionic.starter.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;
import com.sdk.AlarmCallBack_PF;
import com.sdk.ExceptionCallBack_PF;
import com.sdk.NETDEV_CLOUD_DEV_LOGIN_S;
import com.sdk.NETDEV_CLOUD_LIMIT_INFO_S;
import com.sdk.NETDEV_CLOUD_MOBILE_INFO_S;
import com.sdk.NETDEV_LOGIN_INFO_S;
import com.sdk.NETDEV_VIDEO_CHL_DETAIL_INFO_S;
import com.sdk.NetDEVSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.ionic.starter.adapter.Demo4Adapter;
import io.ionic.starter.model.AllModel;
import io.ionic.starter.model.JiankongModel;

public class Demo4ListActivity extends Activity implements View.OnClickListener {
    private RelativeLayout rr_back;
    private TextView title;

    private SweetAlertDialog pDialog;
    private RecyclerView recyclerView;
    private Demo4Adapter adapter;
    private List<AllModel> models = new ArrayList<>();
    private JiankongModel keyongmodel;

    private NetDEVSDK oNetSDKDemo;
    private static final int COMPLETED = 0;
    static ArrayList<String> cloudDevNameList = new ArrayList<String>();
    static ArrayList<Long> cloudLpUserIDList = new ArrayList<Long>();
    Map<String, Long> nameLpUserIDMap = new HashMap<String, Long>();
    private static String strCloudDevName = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        keyongmodel = (JiankongModel) getIntent().getSerializableExtra("model");

        initViewPager();
        pDialog = new SweetAlertDialog(Demo4ListActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("请稍后...");
        pDialog.setCancelable(true);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(Demo4ListActivity.this,R.color.theme_color));

//        oNetSDKDemo = new NetDEVSDK();
//        oNetSDKDemo.NETDEV_Init();
//        NetDEVSDK.NETDEV_SetT2UPayLoad(400);
//        if(models.size()>0){
//            recyclerView.removeAllViews();
//        }
        pDialog.show();
        setUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUI() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  1.进行耗时操作
                //  2.传递数据
                qidong();
            }
        }).start();
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            if(msg.what == COMPLETED){
                famhf();
            }
        }
    };

    private void famhf(){
        List<NETDEV_VIDEO_CHL_DETAIL_INFO_S>  chlList = NetDEVSDK.NETDEV_QueryVideoChlDetailList(nameLpUserIDMap.get(strCloudDevName), 64);
        models.clear();
        for (int i=0; i<chlList.size();i++){
            NETDEV_VIDEO_CHL_DETAIL_INFO_S tmp = chlList.get(i);
            AllModel m = new AllModel();
            m.setId(String.valueOf(i+1));
            if(tmp.enStatus == 1){
                m.setStatus("1");
            } else {
                m.setStatus("0");
            }
            if(i == 0){
                m.setName("崔家沟斜井口");
            } else if(i == 1){
                m.setName("崔家沟井底车场");
            } else if(i == 2){
                m.setName("井中150米");
            } else if(i == 3){
                m.setName("井底30米");
            } else if(i == 4){
                m.setName("井口50米");
            } else if(i == 5){
                m.setName("滚筒后面");
            } else if(i == 6){
                m.setName("绞车房");
            } else if(i == 7){
                m.setName("崔家沟绞车房变频器室");
            }
            models.add(m);
        }
        adapter = new Demo4Adapter(this, models,cloudDevNameList,  cloudLpUserIDList, nameLpUserIDMap.get(strCloudDevName));
        recyclerView.setAdapter(adapter);
        pDialog.dismiss();
    }

    private void initViewPager() {
        rr_back = findViewById(R.id.ll_back);
        rr_back.setOnClickListener(this);
        title = findViewById(R.id.title);
        title.setText("视频监控");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager3 = new GridLayoutManager(this, 2,
                LinearLayoutManager.VERTICAL,false);
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager3);
    }


    private void qidong(){
//        String strCloudUserName = keyongmodel.getCloud_username();
//        String strCloudPassword = keyongmodel.getCloud_pwd();
//        String strCloudServerUrl =  keyongmodel.getCloud_url();
//        int  iNoAccountFlag = 0;
//        if ((0 == strCloudUserName.length()) && (0 == strCloudPassword.length())) {
//            iNoAccountFlag = 1;
//        }
//
//        NetDEVSDK.glpcloudID = NetDEVSDK.NETDEV_LoginCloudEx(strCloudServerUrl, strCloudUserName, strCloudPassword, iNoAccountFlag);
//        if(0 != NetDEVSDK.glpcloudID){
            NetDEVSDK.NETDEV_SetClientID("1234567890987654321");          /* ClienID must be Unique! */
            NETDEV_CLOUD_MOBILE_INFO_S stMobileInfo = new NETDEV_CLOUD_MOBILE_INFO_S();
            NETDEV_CLOUD_LIMIT_INFO_S stLimitInfo = new NETDEV_CLOUD_LIMIT_INFO_S();
            stMobileInfo.szMobileModule = "A2017";
            stMobileInfo.szSystemType = "Andriod";
            stMobileInfo.szSystemVersion = "7.0.0.1";
            stMobileInfo.bDonotDisturb = 1;
            stMobileInfo.bPushBuiltFlag = 0;
            stMobileInfo.szAppName = "io.ionic.starter";
            stMobileInfo.szAppLanguage = "zh-cn";
            stMobileInfo.szAppVersion = "V1.0";
            stMobileInfo.bIosEnvir = 1;
            stLimitInfo.udwMaxAppTimeS = 0;
            stLimitInfo.udwMaxDeviceNum = 8;
            stLimitInfo.udwMaxRTSNum = 0;
            int iRet = NetDEVSDK.NETDEV_ReportMobileInfo(NetDEVSDK.glpcloudID, stMobileInfo, stLimitInfo);
            if(0 == iRet) {
                AlertDialog.Builder oBuilder = new  AlertDialog.Builder(Demo4ListActivity.this);
                oBuilder.setTitle("Fail" );
                oBuilder.setMessage("Report Mobile Info Fail." );
                oBuilder.setPositiveButton("OK" ,  null );
                oBuilder.show();
            } else {
                NetDEVSDK.strDevName =keyongmodel.getDev_name();
                NETDEV_CLOUD_DEV_LOGIN_S stCloudDevInfo = new NETDEV_CLOUD_DEV_LOGIN_S();
                stCloudDevInfo.szDeviceName = NetDEVSDK.strDevName;
                stCloudDevInfo.szDevicePassword = "";
                stCloudDevInfo.dwT2UTimeout = 10;

                if (null == stCloudDevInfo.szDeviceName) {
                    AlertDialog.Builder oBuilder = new AlertDialog.Builder(Demo4ListActivity.this);
                    oBuilder.setMessage("当前暂无监控设备");
                    oBuilder.setTitle("");
                    oBuilder.setPositiveButton("确定", null);
                    oBuilder.show();
                } else {
                    if (stCloudDevInfo.szDevicePassword.isEmpty()) {
                        NETDEV_LOGIN_INFO_S stLoginInfo = new NETDEV_LOGIN_INFO_S();
                        stLoginInfo.dwConnectMode = 2;
                        stLoginInfo.LoginType = 1;
                        stLoginInfo.dwT2UTimeout = 15;
                        stLoginInfo.szDeviceName = stCloudDevInfo.szDeviceName;
                        stLoginInfo.mPassword = "";
                        NetDEVSDK.lpUserID = NetDEVSDK.NETDEV_LoginV2(NetDEVSDK.glpcloudID, stLoginInfo);
                        if (0 == NetDEVSDK.lpUserID) {
                            stLoginInfo.dwConnectMode = 3;
                            NetDEVSDK.lpUserID = NetDEVSDK.NETDEV_LoginV2(NetDEVSDK.glpcloudID, stLoginInfo);
                        }
                        if (0 != NetDEVSDK.lpUserID) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    AlarmCallBack_PF pfAlarmCallBack = new AlarmCallBack_PF();
                                    NetDEVSDK.NETDEV_Android_SetAlarmCallBack(NetDEVSDK.lpUserID, pfAlarmCallBack, 0);
                                    ExceptionCallBack_PF pfExceptionCallBack = new ExceptionCallBack_PF();
                                    NetDEVSDK.NETDEV_Android_SetExceptionCallBack(pfExceptionCallBack, 0);
                                }
                            }).start();

//                            if (!cloudDevNameList.contains(stCloudDevInfo.szDeviceName)){
//                                cloudLpUserIDList.add(NetDEVSDK.lpUserID);
//                                cloudDevNameList.add(stCloudDevInfo.szDeviceName);
//                            }

                            cloudLpUserIDList.clear();
                            cloudLpUserIDList.add(NetDEVSDK.lpUserID);
                            cloudDevNameList.add(stCloudDevInfo.szDeviceName);

//                            strCloudDevName = cloudDevNameList.get(cloudDevNameList.size() - 1);
                            strCloudDevName = cloudDevNameList.get(cloudDevNameList.size() - 1);
                            nameLpUserIDMap.put(strCloudDevName, cloudLpUserIDList.get(cloudLpUserIDList.size()-1));
                            Message msg = new Message();
                            msg.what = COMPLETED;
                            handler.sendMessage(msg);
                        }
                    } else {
                        AlertDialog.Builder oBuilder = new AlertDialog.Builder(Demo4ListActivity.this);
                        oBuilder.setMessage("登录失败");
                        oBuilder.setTitle("");
                        oBuilder.setPositiveButton("OK", null);
                        oBuilder.show();
                    }
                }
            }
//        }else{
//            AlertDialog.Builder oBuilder =new  AlertDialog.Builder(Demo4ListActivity.this);
//            oBuilder.setTitle("失败" );
//            oBuilder.setMessage("登录失败，请检查输入是否正确。" );
//            oBuilder.setPositiveButton("OK" ,  null );
//            oBuilder.show();
//        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
}
