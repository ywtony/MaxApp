package com.bohui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.adpter.ImageAdapter;
import com.bohui.adpter.PhotoAdapter;
import com.bohui.bean.ButtomBean;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaojingBean;
import com.bohui.bean.LocalImage;
import com.bohui.bean.MessageEvent;
import com.bohui.dialog.BottomDialog;
import com.bohui.dialog.LoadingDialog;
import com.bohui.model.ClearGaoJIngModel;
import com.bohui.model.UploadFileModel;
import com.bohui.presenter.IBasePresenter;
import com.bohui.presenter.IBasePresneterImpl;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DataUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.ToastUtil;
import com.bohui.utils.ToastUtils;
import com.bohui.view.IBaseView;
import com.bohui.view.IClearGaojingView;
import com.bohui.widget.NoScrollGridView;
import com.bohui.widget.PhotoPopWin;
//import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;

/**
 * Created by Administrator on 2018/4/19 0019.
 * 故障描述
 */

public class FaultDescriptionActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private Context mContext;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.photoGrdview)
    NoScrollGridView photoGrdview;
    @BindView(R.id.et_faultDeta)
    EditText et_faultDeta;
    @BindView(R.id.tv_length)
    TextView tv_length;
    @BindView(R.id.tv_header_right)
    TextView tv_header_right;
    @BindView(R.id.radio_group)
    RadioGroup rg;
    //告警等级
    @BindView(R.id.tvAlarmLevel)
    TextView tvAlarmLevel;
    //风险等级
    @BindView(R.id.tvRiskLevel)
    TextView tvRiskLevel;
    //核实人
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_latlng)
    TextView tv_latlng;
    private String num = "11";
    private ImageAdapter adapter;
    private List<Drawable> list;
    private static int MAX_PHOTO = 5;//最多上传图片数量
    private GaoJingDataList bean = null;
    private List<LocalImage> localImages = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA, Manifest.permission.WRITE_SETTINGS};
    private LoadingDialog loadingDialog;
    private String alarmLevel;//告警等级
    private String riskLevel;//风险等级

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_description);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        bean = (GaoJingDataList) getIntent().getSerializableExtra("bean");

        mContext = this;
        list = DataUtils.createAdd(mContext.getApplicationContext());
        initView();
        iv_header_left.setOnClickListener(this);

        String language = BoHuiApplication.getInstance().getConfigDB().getLanguage();
        if ("zh".equals(language)) {
            tv_title.setText(getResources().getString(R.string.clear_alarm));
        } else {
            tv_title.setText(getResources().getString(R.string.clear_alarm));
        }

        loadingDialog = new LoadingDialog(this);
        tv_latlng.setText(BoHuiApplication.getInstance().getLat() + "," + BoHuiApplication.getInstance().getLng());
    }


    private void initView() {
        tvAlarmLevel.setOnClickListener(this);
        tvRiskLevel.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        tv_header_right.setVisibility(View.VISIBLE);
        tv_header_right.setText(getResources().getString(R.string.propert_submit_feedback));
        tv_header_right.setOnClickListener(this);
        et_faultDeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = et_faultDeta.getText() == null ? 0 : et_faultDeta.getText().length();
                String etSiaze = String.format(getResources().getString(R.string.text_size), length < 200 ? length + "" : "200");
                tv_length.setText(etSiaze);
            }
        });
        LocalImage li = new LocalImage();
        li.setLocalUrl("camera");
        localImages.add(li);
        adapter = new ImageAdapter(mContext);
        adapter.setList(localImages);
        photoGrdview.setAdapter(adapter);
        photoGrdview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LocalImage localImage = localImages.get(i);
                if (localImage.getLocalUrl().equals("camera")) {
                    updateIcon();
                }
            }
        });
        adapter.setDelCallback(new ImageAdapter.DelCallback() {
            @Override
            public void callback(int position) {
                files.remove(position);
                localImages.remove(position);
                adapter.setList(localImages);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
            case R.id.tv_header_right:
//                ToastUtil.getInstance().show(this, "您点击了提交");
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    loadingDialog.show();
                }
                if (files != null && files.size() > 0) {
                    compressFile();
                } else {
                    confirmAlarm();
                }

//                clearAlarm();
                break;
            case R.id.tvAlarmLevel: {
                List<ButtomBean> datas = new ArrayList<>();
                datas.add(new ButtomBean("1", R.string.level1));
                datas.add(new ButtomBean("2", R.string.level2));
                BottomDialog bottomDialog = new BottomDialog(this, datas, 2);
                bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                    @Override
                    public void buttom(ButtomBean bean) {
//                        DevLog.e("输出了Msg:"+bean.getMsg()+bean.getEnMsg());
                        tvAlarmLevel.setText(bean.getContentId());
                        alarmLevel = bean.getId();
                    }
                });
                bottomDialog.show();
            }
            break;
            case R.id.tvRiskLevel:
                List<ButtomBean> datas = new ArrayList<>();
                datas.add(new ButtomBean("A", R.string.aLevel));
                datas.add(new ButtomBean("B", R.string.bLevel));
                datas.add(new ButtomBean("C", R.string.cLevel));
                BottomDialog bottomDialog = new BottomDialog(this, datas, 2);
                bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                    @Override
                    public void buttom(ButtomBean bean) {
//                        DevLog.e("输出了Msg:"+bean.getMsg()+bean.getEnMsg());
                        tvRiskLevel.setText(bean.getContentId());
                        riskLevel = bean.getId();
                    }
                });
                bottomDialog.show();
                break;
        }
    }

    public void showPopFormBottom() {
        PhotoPopWin takePhotoPopWin = new PhotoPopWin(this, new PhotoPopWin.PhotoListener() {
            @Override
            public void onTakePhoto() {

            }

            @Override
            public void onPickPhoto() {

            }
        });
        takePhotoPopWin.showAtLocation(photoGrdview, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }

    /***
     * 以下为上传头像相关的内容
     ****/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            toph(false, true, true, 1, 4);
        }

    }

    /*以下是更改头像相关的*/
    private void updateIcon() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请CAMERA权限
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS_STORAGE, 1000);
        } else {
            toph(false, true, true, 1, 4);
        }
    }

    /**
     * @param isSingle 是否单选
     * @param isCamera 是否需要相机
     * @param isCrop   是否裁剪
     * @param max      最多可以选择多少张
     * @param count    一行显示几张
     */
    private void toph(boolean isSingle, boolean isCamera, boolean isCrop, int max, int count) {
        // 多选
//        int chose_mode = PickConfig.MODE_MULTIP_PICK;
        int chose_mode = PickConfig.MODE_SINGLE_PICK;
        // PickConfig.MODE_SINGLE_PICK单选
        // : PickConfig.MODE_MULTIP_PICK;
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // options.setCompressionQuality(3);
        new PickConfig.Builder(this).isneedcrop(isSingle).actionBarcolor(Color.parseColor("#40c4ff"))
                .statusBarcolor(Color.parseColor("#0094cc")).isneedcamera(isCamera).isSqureCrop(isCrop)
                .setUropOptions(options).maxPickSize(max).spanCount(count).pickMode(chose_mode).build();

    }

    private String filePath = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 最新的
        // 最新的
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            // 在data中返回 选择的图片列表
            List<String> paths = data.getStringArrayListExtra("data");
            if (paths != null && paths.size() > 0) {
                for (String path : paths) {
                    localImages.add((localImages.size() - 1), new LocalImage(path));
                    files.add(new File(path));
                }
            }
            adapter.setList(localImages);
            photoGrdview.setAdapter(adapter);
//            if (loadingDialog != null && !loadingDialog.isShowing()) {
//                loadingDialog.show();
//            }
//            //            ToastUtil.getInstance().show(this, "返回成功" + filePath);
//            uploadFile(paths);
            //            mPresenter.modifyImg(getActivity(), filePath);
            //            uploadImage(filePath);
        }

    }


    private void clearAlarm() {
        if (bean == null) {
            return;
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ClearGaoJIngModel().clearGaojing(this, id, bean.getAlarmID() + "", new IClearGaojingView() {
            @Override
            public void success(String msg) {

            }
        });
    }

    private void confirmAlarm() {
        if (bean == null) {
            return;
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ClearGaoJIngModel().confirmAlarm(this, id, bean.getAlarmID() + "", files, et_faultDeta.getText().toString(), num,
                alarmLevel, riskLevel, etName.getText().toString(), new IClearGaojingView() {
                    @Override
                    public void success(String msg) {
                        handler.sendEmptyMessage(1000);

                    }
                });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if (loadingDialog != null && loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                    DevLog.e("清除成功");
                    //提交成功，清除数据
                    localImages.clear();
                    LocalImage li = new LocalImage();
                    li.setLocalUrl("camera");
                    localImages.add(li);
                    adapter.notifyDataSetChanged();
                    et_faultDeta.setText("");
                    files.clear();
                    File f = new File(targetPath);
                    if (f.exists()) {
                        deleteDir(targetPath);
                    }
                    ToastUtil.getInstance().show(FaultDescriptionActivity.this, "确认告警成功！");
                    MessageEvent event = new MessageEvent();
                    event.what = MessageEvent.REFRESH_GJLIST;
                    EventBus.getDefault().post(event);
                    finish();
                    break;
                case 2000:
                    confirmAlarm();
                    break;
            }

        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.ll_type1:
                num = "11";
                break;
            case R.id.ll_type2:
                num = "12";
                break;
            case R.id.ll_type3:
                num = "13";
                break;
        }
    }

    //图片压缩后的文件目录
    private String targetPath = Environment.getExternalStorageDirectory() + "/box/cache";

    /**
     * 压缩图片
     */
    private void compressFile() {
        new Thread() {
            @Override
            public void run() {
                try {


                    File f = new File(targetPath);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    List<String> paths = new ArrayList<>();
                    for (File file : files) {
                        paths.add(file.getAbsolutePath());
                    }
                    DevLog.e("压缩前的文件个数：" + paths.size());

                    try {
                        files.clear();
                        files = Luban.with(FaultDescriptionActivity.this).load(paths)//需要压缩的图片列表
                                .ignoreBy(100)//不到此大小就忽略
                                .setTargetDir(targetPath).get();//设置压缩后的文件位置.get();
                        DevLog.e("压缩后的文件个数：" + files.size());
                        handler.sendEmptyMessage(2000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }


    //删除文件夹和文件夹里面的文件
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
