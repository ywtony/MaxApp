package com.bohui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.PathClassBean;
import com.bohui.config.Constants;
import com.bohui.model.LoginModel;
import com.bohui.model.MapDataModel;
import com.bohui.model.ResourceModel;
import com.bohui.presenter.IBasePresenter;
import com.bohui.presenter.IBasePresneterImpl;
import com.bohui.receiver.TagAliasUtil;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.ProgressDialogUtil;
import com.bohui.utils.SHA1SHA256Utils;
import com.bohui.utils.ToastUtil;
import com.bohui.utils.ToastUtils;
import com.bohui.view.IBaseView;
import com.bohui.view.ILoginView;
import com.bohui.view.IMapDataView;
import com.bohui.view.IResourceView;
import com.bohui.widget.ClearEditText;
import com.yw.downloadinguilibrary.DialogConfig;
import com.yw.downloadinguilibrary.ProgressbarDialog;
import com.yw.downloadlibrary.DownLoadManager;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.inter.DownloadCallback;
import com.yw.downloadlibrary.utils.PathUtil;
import com.yw.permissionlibrary.permission.PermissionFail;
import com.yw.permissionlibrary.permission.PermissionHelper;
import com.yw.permissionlibrary.permission.PermissionSuccess;
import com.yw.ziplibrary.ZipManager;
import com.yw.ziplibrary.inter.UnZipCallback;
import com.yw.ziplibrary.utils.FileUtils;
//import com.githang.statusbar.StatusBarCompat;


import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.ionic.starter.activity.Demo1ListActivity;

/**
 * 登录
 */
public class LoginActivity extends Activity {
    private Context mContext;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_login_name)
    ClearEditText et_login_name;
    @BindView(R.id.et_password)
    ClearEditText et_password;
    @BindView(R.id.et_id)
    ClearEditText et_id;
    @BindView(R.id.radio_english)
    RadioButton radio_english;
    @BindView(R.id.radio_chinese)
    RadioButton radio_chinese;
    private String loginName;
    private String loginId;
    private String password;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final String TAG = "LoginActivity";
    private ProgressbarDialog dialog = null;
    private boolean isClick = false;//是否以前登录过
    private static final int WRITE_EXTERNAL_REQUEST_DODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        mContext = this;
        initView();
        initConfig();
//        BoHuiApplication.getInstance().getConfigDB().setLanguage("zh");//默认显示中文
        dialog = new ProgressbarDialog(this);

        dialog.setProgressbarDialogCallback(new ProgressbarDialog.ProgressbarDialogCallback() {
            @Override
            public void finish(Object obj) {
                Log.e(TAG, "下载完成了,做下一步操作");
            }
        });
        isClick = false;

        //权限申请
        PermissionHelper.with(LoginActivity.this).
                requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}).
                requestCode(WRITE_EXTERNAL_REQUEST_DODE).
                request();
    }

    @PermissionSuccess(requestCode = WRITE_EXTERNAL_REQUEST_DODE)
    private void doSuccess() {
        Log.e(TAG, "申请权限成功，开始执行下载操作");
//        downloadResource();

    }

    @PermissionFail(requestCode = WRITE_EXTERNAL_REQUEST_DODE)
    private void doFail() {
        Log.e(TAG, "授权失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private void initConfig() {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        if (configBean != null && !"".equals(configBean)) {
            et_login_name.setText(configBean.getId());
            et_password.setText(configBean.getPwd());
            et_id.setText(configBean.getIp());
        }

    }

    private void initView() {
        tv_title.setText(mContext.getResources().getString(R.string.login));
        iv_header_left.setVisibility(View.GONE);
        btn_login.setOnClickListener(OnClick);
        radio_english.setOnClickListener(OnClick);
        radio_chinese.setOnClickListener(OnClick);
    }

    private View.OnClickListener OnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_login:
                    login();
                    break;
                case R.id.radio_english:
                    DevLog.e("英文");
                    BoHuiApplication.getInstance().getConfigDB().setLanguage("en");//设置英文显示
                    radio_chinese.setChecked(false);
                    radio_english.setChecked(true);
                    shiftLanguage("en");

                    break;
                case R.id.radio_chinese:
                    DevLog.e("中文");
                    BoHuiApplication.getInstance().getConfigDB().setLanguage("zh");//设置中文显示
                    radio_chinese.setChecked(true);
                    radio_english.setChecked(false);
                    shiftLanguage("zh");

                    break;
                default:
                    break;
            }
        }
    };

    private void setTagAlias(String sessionId) {
        DevLog.e("sessionid:"+sessionId);
        TagAliasUtil.TagAliasBean bean = new TagAliasUtil.TagAliasBean();
        bean.action = TagAliasUtil.ACTION_SET;
        bean.isAliasAction = false;
        Set<String> sets = new HashSet<String>();
        sets.add(sessionId);
        bean.tags = sets;
        TagAliasUtil.getInstance().handleAction(this, new Random(10000).nextInt(), bean);
    }
    private void login() {
        //设置登录按钮不可点击
        btn_login.setEnabled(false);
        btn_login.setBackgroundResource(R.drawable.corners_btn_gray);
//        if(!btn_login.isEnabled())return;
        loginName = et_login_name.getText().toString().trim();
        password = et_password.getText().toString().trim();
        loginId = et_id.getText().toString().trim();
        if (validate(loginName, password, loginId)) {
            ConfigBean configBean = null;
            if (configBean == null) {
                configBean = new ConfigBean();
            }
            configBean.setId(loginName);
            configBean.setPwd(password);
            configBean.setIp(loginId);
            BoHuiApplication.getInstance().getConfigDB().setConfig(JSONUtil.getInstance().getString(configBean));


            //新的登录方式
            new LoginModel().getRSAKey(this, loginName, password, new ILoginView() {
                @Override
                public void success(String code) {
                    try {
                        btn_login.setEnabled(true);
                        btn_login.setBackgroundResource(R.drawable.corners_btn_bg);
                        JSONObject obj = new JSONObject(code);
                        JSONObject obj1 = obj.getJSONObject("UserSession");
                        String id = obj1.getString("ID");
                        try{
                            setTagAlias(id);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        BoHuiApplication.getInstance().getAuthConfig().setToken(id);
//                        getResource();
                        isClick = true;
                        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
                        downloadResource(configBean.getIp() + "/images/images.zip");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail() {
                    btn_login.setEnabled(true);
                    btn_login.setBackgroundResource(R.drawable.corners_btn_bg);
                }

                @Override
                public void showToast(String msg) {

                }
            });

        }
    }

    //验证输入框的内容
    public boolean validate(String loginName, String password, String loginId) {

        if (loginName == null || loginName.equals("")) {
            ToastUtils.show(LoginActivity.this, getResources().getString(R.string.account_empty));
            return false;
        }
        if (password == null || password.equals("")) {
            ToastUtils.show(LoginActivity.this, getResources().getString(R.string.password_empty));
            return false;
        }
        if (loginId == null || loginId.equals("")) {
            ToastUtils.show(LoginActivity.this, getResources().getString(R.string.id_empty));
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        if (!configuration.locale.toString().contains("zh")) {
            radio_chinese.setChecked(false);
            radio_english.setChecked(true);
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e("分辨率", "width-display :" + dm.widthPixels + " * " + dm.heightPixels);
    }

    @Override
    public void onPause() {
        super.onPause();
        ProgressDialogUtil.getInstance().dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevLog.e("loginactivity执行了ondestroy方法");
//        ButterKnife.unbind(this);

    }


    public void shiftLanguage(String sta) {
        Configuration configuration = getResources().getConfiguration();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (sta.equals("zh")) {
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        }

        getResources().updateConfiguration(configuration, displayMetrics);
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
        finish();
    }

    private void toListActivity() {
        String service_url = "ezcloud.uniview.com";
        String userName = "15364928555";
        String password = "Hkt0813";
        Intent intent = new Intent(LoginActivity.this, Demo1ListActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * 以前是否登录过
     */
    private boolean isLogined() {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        if (configBean != null) {//如果已经设置过了
            return true;
        }
        return false;
    }

    private boolean isExistingFile() {//是否已经从服务器上同步下来文件了
        String filePath = Environment.getExternalStorageDirectory() + "/box";
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {//如果文件存在且文件是个文件夹
            return true;
        }
        return false;
    }

    private void downloadResource(String path) {
///storage/emulated/0/Android/data/com.bohui/cache/box/BOX/hcdl05_24.gif
        if (FileUtils.getInstance().isDirNull(PathUtil.createLocalFilePath(this,"box") + "/BOX/")) {//如果本地没有就同步
            if (isClick) {
                //直接下载
                download(path);
            }

        } else {
            if (isClick) {
                ActivityUtil.getInstance().leftToRightActivity(LoginActivity.this, MainActivity.class);
                finish();
            }
        }
    }

    private void download(String path) {
        //准备下载数据
        DownLoadManager.getDefault(LoginActivity.this).isPause = false;
        DownloadInfo info = new DownloadInfo();
        info.setFileName("images.zip");
        info.setUrl(path);
        DownLoadManager.getDefault(this).startDownloadTask(new DownloadCallback() {
            @Override
            public void connectSuccess() {
                Log.e(TAG, "连接成功");

            }

            @Override
            public void startDownload() {
                Log.e(TAG, "开始下载");
                dialog.show();
//                DownloadNotification.getDefault(MainActivity.this).startNotify();
            }

            @Override
            public void downloadUpdate(DownloadInfo downloadInfo) {
                int process = (int) (downloadInfo.getProcess() * 100.0 / downloadInfo.getFileSize());
//                DownloadNotification.getDefault(MainActivity.this).updateNotification(process);
                Log.e(TAG, "下载进度：" + downloadInfo.getProcess());
                Message msg = new Message();
                msg.what = DialogConfig.PROCESS;
                msg.arg1 = process;
                dialog.getHandler().sendMessage(msg);
            }

            @Override
            public void downloadSuccess(String filePath) {
                dialog.dismiss();
//                DownloadNotification.getDefault(MainActivity.this).cancelNotify();
                Log.e(TAG, "下载完成查看下载路径：" + filePath);

                ZipManager.getDefault().unZip(filePath, new File(filePath).getParent(), new UnZipCallback() {
                    @Override
                    public void startUnZip(Object obj) {
                        Log.e(TAG, "开始解压");
                    }

                    @Override
                    public void processUnZip(int process, Object obj) {
                        Log.e(TAG, "解压进度：" + process);
                    }

                    @Override
                    public void unZipSuccess(Object obj) {
                        Log.e(TAG, "解压成功：" + (String) obj);
                        if (isClick) {
                            ActivityUtil.getInstance().leftToRightActivity(LoginActivity.this, MainActivity.class);
                            finish();
                        }


                    }

                    @Override
                    public void unZipFail(String outputPath, String errMsg) {
                        Log.e(TAG, "解压失败：" + errMsg);
                        if (isClick) {
                            ActivityUtil.getInstance().leftToRightActivity(LoginActivity.this, MainActivity.class);
                            finish();
                        }
                    }
                });
            }

            @Override
            public void downloadFail(String errMsg) {
                dialog.dismiss();
//                DownloadNotification.getDefault(MainActivity.this).cancelNotify();
                Log.e(TAG, "下载出错：" + errMsg);
            }
        }, info);
    }

    private void getResource() {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ResourceModel().getResource(this, id, new IResourceView() {
            @Override
            public void success(String msg) {
                try {
                    DevLog.e("获取资源链接：" + msg);
                    PathClassBean pathClassBean = (PathClassBean) JSONUtil.getInstance().getObject(msg, PathClassBean.class);
                    if (pathClassBean == null) return;
                    isClick = true;
                    downloadResource(configBean.getIp() + "/images/images.zip");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
