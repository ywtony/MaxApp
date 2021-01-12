package io.ionic.starter.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bohui.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sdk.NETDEV_CLOUD_LIMIT_INFO_S;
import com.sdk.NETDEV_CLOUD_MOBILE_INFO_S;
import com.sdk.NetDEVSDK;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.ionic.starter.app.CeshiApp;
import io.ionic.starter.model.UserModel;
import io.ionic.starter.utils.CustomToast;
import io.ionic.starter.utils.PreferenceUtils;

/**
 *  Created by Administrator on 2017/7/18.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText username, password, edt_idaddress;
    private Button login_btn;
    private ImageView showpwd;
    private PreferenceUtils preferencesService;
    private SweetAlertDialog pDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final NetDEVSDK oNetSDKDemo = new NetDEVSDK();
        oNetSDKDemo.NETDEV_Init();
        NetDEVSDK.NETDEV_SetT2UPayLoad(800);


        initview();
        onclick();

    }

    private void initview() {
        edt_idaddress  = (EditText) findViewById(R.id.edt_idaddress);
        username = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        login_btn = (Button) findViewById(R.id.login_btn);
        showpwd = (ImageView) findViewById(R.id.showpwd);

        pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("请稍后...");
        pDialog.setCancelable(true);
        pDialog.getProgressHelper().setBarColor(ContextCompat.getColor(LoginActivity.this,R.color.theme_color));
    }

    private void onclick() {
        login_btn.setOnClickListener(this);
        showpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                String strCloudUserName = username.getText().toString();
                String strCloudPassword = password.getText().toString();
                String strCloudServerUrl = edt_idaddress.getText().toString();
                int  iNoAccountFlag = 0;
                if ((0 == strCloudUserName.length()) && (0 == strCloudPassword.length()))  {
                    iNoAccountFlag = 1;
                }
                NetDEVSDK.glpcloudID = NetDEVSDK.NETDEV_LoginCloudEx(strCloudServerUrl, strCloudUserName, strCloudPassword, iNoAccountFlag);
                if(0 != NetDEVSDK.glpcloudID) {
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
                    stMobileInfo.szAppVersion = "V0.0.0.1";
                    stMobileInfo.bIosEnvir = 1;
                    stLimitInfo.udwMaxAppTimeS = 0;
                    stLimitInfo.udwMaxDeviceNum = 0;
                    stLimitInfo.udwMaxRTSNum = 0;
                    int iRet = NetDEVSDK.NETDEV_ReportMobileInfo(NetDEVSDK.glpcloudID, stMobileInfo, stLimitInfo);
                    if(0 == iRet) {
                        AlertDialog.Builder oBuilder = new  AlertDialog.Builder(LoginActivity.this);
                        oBuilder.setTitle("Fail" );
                        oBuilder.setMessage("Report Mobile Info Fail." );
                        oBuilder.setPositiveButton("OK" ,  null );
                        oBuilder.show();
                    } else {
                        Intent intent=new Intent(LoginActivity.this,Demo1ListActivity.class);
                        startActivity(intent);
//                        finish();
                    }
                }else {
                    AlertDialog.Builder oBuilder =new  AlertDialog.Builder(LoginActivity.this);
                    oBuilder.setTitle("Fail" );
                    oBuilder.setMessage("Login failed, please check if the input is correct." );
                    oBuilder.setPositiveButton("OK" ,  null );
                    oBuilder.show();
                }

                break;
        }
    }


    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK){

            return exitBy2Click();
        }
        return super.dispatchKeyEvent(event);
    }

    private boolean exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }


        return true;
    }
}
