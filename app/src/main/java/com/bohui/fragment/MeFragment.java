package com.bohui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.activity.AboutUsActivity;
import com.bohui.activity.FaultDescriptionActivity;
import com.bohui.activity.FeedbackActivity;
import com.bohui.activity.LoginActivity;
import com.bohui.activity.SynopsisActivity;
import com.bohui.activity.TemperDetectActivity;
import com.bohui.activity.TestActivity;
import com.bohui.apk.UpgradeAPK;
import com.bohui.apk.VersionsInfo;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.MainPage;
import com.bohui.bean.PathClassBean;
import com.bohui.dialog.CommonDialog;
import com.bohui.model.CheckVersionModel;
import com.bohui.model.LoginOutModel;
import com.bohui.model.ResourceModel;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.AppInfoUtil;
import com.bohui.utils.BKBMUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.ImageLoaderUtils;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.ToastUtil;
import com.bohui.view.IClearGaojingView;
import com.bohui.view.IPageView;
import com.bohui.view.IResourceView;
import com.bohui.widget.CircleImageView;
import com.bohui.widget.dialog.BottomToTopDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yw.downloadinguilibrary.DialogConfig;
import com.yw.downloadinguilibrary.ProgressbarDialog;
import com.yw.downloadlibrary.DownLoadManager;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.inter.DownloadCallback;
import com.yw.ziplibrary.ZipManager;
import com.yw.ziplibrary.inter.UnZipCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.ionic.starter.utils.PreferenceUtils;

/**
 * Created by Administrator on 2018/4/18 0018.
 */

public class MeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeFragment";
    private Context mContext;
    private View view;
    @BindView(R.id.rl_syn)
    RelativeLayout rl_syn;
    @BindView(R.id.rl_feedback)
    RelativeLayout rl_feedback;
    @BindView(R.id.rl_aboutus)
    RelativeLayout rl_aboutus;
    @BindView(R.id.rl_caching)
    RelativeLayout rl_caching;
    @BindView(R.id.rl_updating)
    RelativeLayout rl_updating;
    @BindView(R.id.rl_downloadresource)
    RelativeLayout rel_updateResource;
    @BindView(R.id.btn_exit_logon)
    Button btn_exit_logon;
    @BindView(R.id.iv_header)
    CircleImageView iv_header;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_current_version)
    TextView tv_current_version;
    @BindView(R.id.tv_cache)
    TextView tv_cache;
    @BindView(R.id.tv_current_deviceid)
    TextView tv_deviceId;
    private CommonDialog dialog_memory;
    private ProgressbarDialog dialog = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, view);
        initView();

        if (Build.VERSION.SDK_INT >= 29) {
            tv_deviceId.setText(new PreferenceUtils(getActivity()).getUUID());
        }else{
            String SerialNumber = android.os.Build.SERIAL;
            tv_deviceId.setText(SerialNumber);
        }
        dialog = new ProgressbarDialog(getActivity());

        dialog.setProgressbarDialogCallback(new ProgressbarDialog.ProgressbarDialogCallback() {
            @Override
            public void finish(Object obj) {
                Log.e(TAG, "下载完成了,做下一步操作");
            }
        });
        return view;
    }

    private void initView() {
        iv_header_left.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.menu_me));
        rl_syn.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_aboutus.setOnClickListener(this);
        rl_caching.setOnClickListener(this);
        rl_updating.setOnClickListener(this);
        btn_exit_logon.setOnClickListener(this);
        iv_header.setOnClickListener(this);
        rel_updateResource.setOnClickListener(this);
        tv_current_version.setText(getVersioninfo());
        dialog_memory = new CommonDialog(getActivity(), "确认要清除缓存吗？", "取消", "确认");
        dialog_memory.setDialogConnectListener(new CommonDialog.DialogCommonListener() {
            @Override
            public void common(String ssid) {
                dialog_memory.dismiss();
                ImageLoaderUtils.getInstance().clearCache();
                tv_cache.setText("0b");
            }

            @Override
            public void close() {

            }
        });
        tv_cache.setText(BKBMUtils.getInstance().getPrintSize(ImageLoaderUtils.getInstance().getCache()));
    }

    private String getVersioninfo() {
        return "v" + AppInfoUtil.getInstance().getInfo(getActivity()).getVersionName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_syn:
                startActivity(new Intent(getActivity(), SynopsisActivity.class));
                break;
            case R.id.rl_feedback:

//                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            case R.id.rl_aboutus:
//                startActivity(new Intent(getActivity(), TestActivity.class));
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.rl_caching:
                if (dialog_memory != null && !dialog_memory.isShowing()) {
                    dialog_memory.show();
                }
                break;
            case R.id.rl_updating:
                checkVersion();
//                ActivityUtil.getInstance().leftToRightActivity(getActivity(), TemperDetectActivity.class);
                break;
            case R.id.btn_exit_logon:
                logout();
//                BottomToTopDialog bottomToTopDialog = new BottomToTopDialog(getContext(),39.9037448095,116.3980007172,"北京天安门");
//                bottomToTopDialog.show();
                break;
            case R.id.iv_header:
                break;
            case R.id.rl_downloadresource://更新资源文件
                getResource();
//                ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//                download(configBean.getIp() + "/images/images.zip");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//    ButterKnife.unbind(this);
    }

    private void checkVersion() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        String versionCode = AppInfoUtil.getInstance().getInfo(getActivity()).getVersionName() + "";
        new CheckVersionModel().checkVersion(getActivity(), id, "0", versionCode, new IPageView() {
            @Override
            public void success(String msg) {
                Gson gson = new Gson();
                VersionsInfo bean = gson.fromJson(msg,
                        new TypeToken<VersionsInfo>() {
                        }.getType());
                if (bean != null) {
                    if (bean.isNeedUpdate()) {

                        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
                        String url = configBean.getIp();// + "/Handler/App/AppHandler.ashx";//type=getBoxList&se
                        DevLog.e("文件下载路径：" + url + "/" + bean.getPath() + "|" + bean.getSize());
                        new UpgradeAPK(getActivity(), url + "/" + bean.getPath(), bean.getSize(), "发现新版本，确定要升级吗？", false);
                    } else {
                        ToastUtil.getInstance().show(getActivity(), "当前已是最新版本了");
                    }
                } else {
                    ToastUtil.getInstance().show(getActivity(), "当前已是最新版本了");
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void logout() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new LoginOutModel().logout(getActivity(), id, new IClearGaojingView() {
            @Override
            public void success(String msg) {
                BoHuiApplication.getInstance().getAuthConfig().removeToken();
                startActivity(new Intent(getActivity(), LoginActivity.class));
//                ActivityUtil.getInstance().finishSmaillActivitys();
                BoHuiApplication.getInstance().setMainHome(false);
                BoHuiApplication.getInstance().setHomeCreate(false);
                BoHuiApplication.getInstance().setClickHome(false);
                BoHuiApplication.getInstance().setFragmentType(0);
                BoHuiApplication.getInstance().setLng(0);
                BoHuiApplication.getInstance().setLat(0);
                BoHuiApplication.getInstance().setDatas(new ArrayList<AlarmType>());
                BoHuiApplication.getInstance().setCount(0);
                getActivity().finish();

            }
        });
    }

    private void download(String path) {
        //准备下载数据
        DownLoadManager.getDefault(getActivity()).isPause = false;
        DownloadInfo info = new DownloadInfo();
        info.setFileName("images.zip");
        info.setUrl(path);
        DownLoadManager.getDefault(getActivity()).startDownloadTask(new DownloadCallback() {
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
                ToastUtil.getInstance().show(getActivity(), "资源文件更新已完成");
                //删除原来的资源文件
                //删除压缩包
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


                    }

                    @Override
                    public void unZipFail(String outputPath, String errMsg) {
                        Log.e(TAG, "解压失败：" + errMsg);
                    }
                });
            }

            @Override
            public void downloadFail(String errMsg) {
                dialog.dismiss();
//                DownloadNotification.getDefault(MainActivity.this).cancelNotify();
                ToastUtil.getInstance().show(getActivity(), "下载出错，请重新下载！");
            }
        }, info);
    }

    private void getResource() {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ResourceModel().getResource(getActivity(), id, new IResourceView() {
            @Override
            public void success(String msg) {
                try {
                    DevLog.e("获取资源链接：" + msg);
                    PathClassBean pathClassBean = (PathClassBean) JSONUtil.getInstance().getObject(msg, PathClassBean.class);
                    if (pathClassBean == null) return;
                    download(configBean.getIp() + "/" + pathClassBean.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
