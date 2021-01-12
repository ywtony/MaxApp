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
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.adpter.ImageAdapter;
import com.bohui.bean.ButtomBean;
import com.bohui.bean.CustomGjBean;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.LocalImage;
import com.bohui.dialog.BottomDialog;
import com.bohui.dialog.LoadingDialog;
import com.bohui.model.ClearGaoJIngModel;
import com.bohui.model.CustomGJSubmit;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DataUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.ToastUtil;
import com.bohui.view.IClearGaojingView;
import com.bohui.view.IGaoJingView;
import com.bohui.widget.PhotoPopWin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提交客户自定义的告警信息
 */
public class CustomGJSubmitActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.photoGrdview)
    GridView photoGrdview;
    @BindView(R.id.et_faultDeta)
    EditText et_faultDeta;
    @BindView(R.id.tv_length)
    TextView tv_length;
    @BindView(R.id.tv_header_right)
    TextView tv_header_right;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_latlng)
    TextView tv_latlng;
    @BindView(R.id.tv_zoneid)
    TextView tv_zoneId;
    @BindView(R.id.et_location)
    EditText et_location;
    @BindView(R.id.et_name)
    EditText et_name;
    private ImageAdapter adapter;
    private List<Drawable> list;
    private static int MAX_PHOTO = 5;//最多上传图片数量
    private List<LocalImage> localImages = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    List<CustomGjBean> datas = new ArrayList<>();
    private String zoneid = "";
    private String type = "";
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA, Manifest.permission.WRITE_SETTINGS};
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customgjsumbit_layout);
        ActivityUtil.getInstance().addSmaillActivitys(this);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);

        mContext = this;
        list = DataUtils.createAdd(mContext.getApplicationContext());
        initView();
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.fault_description));
        tv_latlng.setText(BoHuiApplication.getInstance().getLat() + "," + BoHuiApplication.getInstance().getLng());
        loadingDialog = new LoadingDialog(this);
        type = "0";
        tv_type.setText("正常");
        getZoneIds();
    }


    private void initView() {
        tv_header_right.setVisibility(View.VISIBLE);
        tv_header_right.setText(getResources().getString(R.string.propert_submit_feedback));
        tv_header_right.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_zoneId.setOnClickListener(this);
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
                submit();
                break;
            case R.id.tv_type: {
                List<ButtomBean> datas = new ArrayList<>();
                datas.add(new ButtomBean("0", R.string.normal));
                datas.add(new ButtomBean("1", R.string.trouble));
                datas.add(new ButtomBean("2", R.string.risk));
                datas.add(new ButtomBean("3", R.string.danger));
                BottomDialog bottomDialog = new BottomDialog(this, datas,2);
                bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                    @Override
                    public void buttom(ButtomBean bean) {
                        tv_type.setText(bean.getMsg());
                        type = bean.getId();
                    }
                });
                bottomDialog.show();
            }
            break;
            case R.id.tv_zoneid: {
                if (datas != null && datas.size() > 0) {
                    List<ButtomBean> data = new ArrayList<>();
                    for (int i = 0; i < datas.size(); i++) {
                        data.add(new ButtomBean(datas.get(i).getID(), datas.get(i).getID()));
                    }
                    BottomDialog bottomDialog = new BottomDialog(this, data,1);
                    bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                        @Override
                        public void buttom(ButtomBean bean) {
                            tv_zoneId.setText(bean.getMsg());
                            zoneid = bean.getMsg();
                        }
                    });
                    bottomDialog.show();
                } else {
                    getZoneIds();
                }
            }
            break;
        }
    }

    private void getZoneIds() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new CustomGJSubmit().getZoneId(this, id, new IGaoJingView() {
            @Override
            public void success(String msg) {
                try {
                    DevLog.e("zoneid：" + msg);
                    if (!"[]".equals(msg)) {
                        DevLog.e("获取到了");
                        Gson gson = new Gson();
                        List<CustomGjBean> datas = gson.fromJson(msg.toString(),
                                new TypeToken<List<CustomGjBean>>() {
                                }.getType());
                        if (datas != null && datas.size() > 0) {
                            CustomGJSubmitActivity.this.datas = datas;
                            zoneid = datas.get(0).getID();
                            tv_zoneId.setText(datas.get(0).getID());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void showToast(String msg) {

            }
        });
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


    private void submit() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        double lat = BoHuiApplication.getInstance().getLat();
        double lng = BoHuiApplication.getInstance().getLng();
        String zoneId = zoneid;
        String location = et_location.getText().toString();
        String recordType = type;
        String desc = et_faultDeta.getText().toString();
        String name = et_name.getText().toString();

        if (zoneId == null || "".equals(zoneId)) {
            ToastUtil.getInstance().show(this, "请选择zoneid");
            return;
        } else if (location == null || "".equals(location)) {
            ToastUtil.getInstance().show(this, "请数据位置");
            return;
        } else if (recordType == null || "".equals(recordType)) {
            ToastUtil.getInstance().show(this, "请选择类型");
            return;
        } else if (desc == null || "".equals(desc)) {
            ToastUtil.getInstance().show(this, "请输入描述");
            return;
        } else if (name == null || "".equals(name)) {
            ToastUtil.getInstance().show(this, "请输入名称");
            return;
        }
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        new CustomGJSubmit().submitGaoJing(this, id, lat, lng, zoneId + "", location, recordType, desc, name, files, new IClearGaojingView() {
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
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            DevLog.e("清除成功");
            ToastUtil.getInstance().show(CustomGJSubmitActivity.this, "确认告警成功！");
            finish();
        }
    };
}
