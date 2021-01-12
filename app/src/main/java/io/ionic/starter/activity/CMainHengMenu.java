package io.ionic.starter.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bohui.R;
import com.sdk.NETDEV_PREVIEWINFO_S;
import com.sdk.NetDEVSDK;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.ionic.starter.model.AllModel;
import io.ionic.starter.utils.CPlayView;

public class CMainHengMenu extends FragmentActivity {
    public CPlayView m_oPlayer;

    private Spinner m_oChannelList;
    private int m_dwChannelID;
    private ArrayAdapter<String> m_oChnAdapter;
    private RelativeLayout ll_back;
    private TextView title;
    private SweetAlertDialog pDialog;
    private List<AllModel> models;
    private List<AllModel> newmodels = new ArrayList<>();
    private long lpUerID;
    private Button m_oStartLiveViewBtn;
    private NetDEVSDK oNetSDKDemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu3);

        pDialog = new SweetAlertDialog(CMainHengMenu.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("请稍后...");
        pDialog.setCancelable(true);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(CMainHengMenu.this,R.color.theme_color));


        oNetSDKDemo = new NetDEVSDK();
        oNetSDKDemo.NETDEV_Init();
        NetDEVSDK.NETDEV_SetT2UPayLoad(400);

        m_dwChannelID = Integer.valueOf(getIntent().getStringExtra("m_dwChannelID"));
        models = (ArrayList<AllModel>) getIntent().getSerializableExtra("models");
        lpUerID = getIntent().getLongExtra("lpUerID", 0);
        initView();
    }

    private void initView() {
        m_oStartLiveViewBtn = (Button) findViewById(R.id.startLiveView);
        m_oStartLiveViewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        m_oChannelList = (Spinner) findViewById(R.id.channelList);
        m_oPlayer =  findViewById(R.id.liveview_View1);
        m_oChnAdapter = new ArrayAdapter<String>(CMainHengMenu.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
        for (int i = 0; i < models.size(); i++) {
//            m_oChnAdapter.add(models.get(i).getName() + ": Status -" + ((models.get(i).getStatus().equals("1")) ? "在线" : "不在线") );
            if(models.get(i).getStatus().equals("1")){
                m_oChnAdapter.add(models.get(i).getName()+ ": Status - 在线" );
                newmodels.add(models.get(i));
            }
        }
        m_oChannelList.setAdapter(m_oChnAdapter);
        for (int i = 0; i < newmodels.size(); i++) {
            if(m_dwChannelID == Integer.valueOf(newmodels.get(i).getId())){
                m_oChannelList.setSelection(i,true);
            }
        }

        m_oPlayer.m_bCanDrawFrame = false;
        m_oPlayer.m_dwWinIndex = 1;
        NetDEVSDK.gdwWinIndex = 1;


        LiveView();
    }

    NETDEV_PREVIEWINFO_S stPreviewInfo = new NETDEV_PREVIEWINFO_S();
    float scaleRatio[] = null;
    long lpLiveViewHandle[]= null;
    private void LiveView() {
        scaleRatio = new float[newmodels.size()];
        lpLiveViewHandle = new long[newmodels.size()];
        for (int i=0; i< newmodels.size(); i++){
            scaleRatio[i] = 1.0f;
        }

        stPreviewInfo.dwLinkMode = 1;
        stPreviewInfo.dwStreamIndex = 0;


        stPreviewInfo.dwChannelID = m_dwChannelID;
        m_oPlayer.m_bCanDrawFrame = true;
        NetDEVSDK.NETDEV_StopRealPlay(lpLiveViewHandle[m_oPlayer.m_dwWinIndex - 1], m_oPlayer.m_dwWinIndex);

        lpLiveViewHandle[NetDEVSDK.gdwWinIndex - 1] = NetDEVSDK.NETDEV_RealPlay(lpUerID, stPreviewInfo, NetDEVSDK.gdwWinIndex);
        scaleRatio[NetDEVSDK.gdwWinIndex - 1] = 1.0f;
        NetDEVSDK.Scale(scaleRatio[NetDEVSDK.gdwWinIndex - 1], 0, 0, NetDEVSDK.gdwWinIndex);

        m_oChannelList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                m_dwChannelID = Integer.valueOf(newmodels.get(arg2).getId());
                stPreviewInfo.dwChannelID = m_dwChannelID;


                m_oPlayer.m_bCanDrawFrame = true;
                NetDEVSDK.NETDEV_StopRealPlay(lpLiveViewHandle[m_oPlayer.m_dwWinIndex - 1], m_oPlayer.m_dwWinIndex);

                lpLiveViewHandle[NetDEVSDK.gdwWinIndex - 1] = NetDEVSDK.NETDEV_RealPlay(lpUerID, stPreviewInfo, NetDEVSDK.gdwWinIndex);
                scaleRatio[NetDEVSDK.gdwWinIndex - 1] = 1.0f;
                NetDEVSDK.Scale(scaleRatio[NetDEVSDK.gdwWinIndex - 1], 0, 0, NetDEVSDK.gdwWinIndex);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        int iRet = NetDEVSDK.NETDEV_StopRealPlay(lpLiveViewHandle[0], 1);
        lpLiveViewHandle[0] = 0;
        System.out.println(iRet);
    }
}
