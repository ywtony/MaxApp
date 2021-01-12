package io.ionic.starter.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bohui.R;
import com.sdk.NETDEV_PREVIEWINFO_S;
import com.sdk.NetDEVSDK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import io.ionic.starter.utils.CPlayView;

public class Demo3Activity extends FragmentActivity {
    public CPlayView m_oPlayer;
    private int m_dwChannelID;
    private ArrayList<String> szDevNameList ;
    private ArrayList<Long> cloudLpUserIDList;
    float scaleRatio[] = new float[8];
    long lpLiveViewHandle[] = new long[8];
    private NetDEVSDK oNetSDKDemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        oNetSDKDemo = new NetDEVSDK();
        oNetSDKDemo.NETDEV_Init();
        NetDEVSDK.NETDEV_SetT2UPayLoad(800);

        m_dwChannelID = getIntent().getIntExtra("m_dwChannelID", 0);
        szDevNameList = (ArrayList<String>) getIntent().getSerializableExtra("szDevNameList");
        cloudLpUserIDList = (ArrayList<Long>) getIntent().getSerializableExtra("cloudLpUserIDList");
        initView();
    }

    private void initView() {
        m_oPlayer =  findViewById(R.id.liveview_View1);

        lpLiveViewHandle[0] = 0;
        lpLiveViewHandle[1] = 0;
        lpLiveViewHandle[2] = 0;
        lpLiveViewHandle[3] = 0;
        lpLiveViewHandle[4] = 0;
        lpLiveViewHandle[5] = 0;
        lpLiveViewHandle[6] = 0;
        lpLiveViewHandle[7] = 0;

        scaleRatio[0] = 1.0f;
        scaleRatio[1] = 1.0f;
        scaleRatio[2] = 1.0f;
        scaleRatio[3] = 1.0f;
        scaleRatio[4] = 1.0f;
        scaleRatio[5] = 1.0f;
        scaleRatio[6] = 1.0f;
        scaleRatio[7] = 1.0f;


        String strCloudDevName = szDevNameList.get(szDevNameList.size() - 1);
        Long value = cloudLpUserIDList.get(cloudLpUserIDList.size()-1);
        Map<String, Long> nameLpUserIDMap = new HashMap<String, Long>();
        nameLpUserIDMap.put(strCloudDevName, value);
        m_oPlayer.m_dwWinIndex = 1;

        NetDEVSDK.gdwWinIndex = 1;
        NETDEV_PREVIEWINFO_S stPreviewInfo = new NETDEV_PREVIEWINFO_S();
        stPreviewInfo.dwChannelID = m_dwChannelID;
        stPreviewInfo.dwLinkMode = 1;
        stPreviewInfo.dwStreamIndex = 0;

        m_oPlayer.m_bCanDrawFrame = true;
        NetDEVSDK.NETDEV_StopRealPlay(lpLiveViewHandle[m_oPlayer.m_dwWinIndex - 1], m_oPlayer.m_dwWinIndex);

        lpLiveViewHandle[NetDEVSDK.gdwWinIndex - 1] = NetDEVSDK.NETDEV_RealPlay(nameLpUserIDMap.get(strCloudDevName), stPreviewInfo, NetDEVSDK.gdwWinIndex);
        scaleRatio[NetDEVSDK.gdwWinIndex - 1] = 1.0f;
        NetDEVSDK.Scale(scaleRatio[NetDEVSDK.gdwWinIndex - 1], 0, 0, NetDEVSDK.gdwWinIndex);

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
