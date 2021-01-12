package com.bohui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bohui.BoHuiApplication;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ConfigBean;
import com.bohui.model.SearchAllAlarmTypeModel;
import com.bohui.view.IGaoJingView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * 告警类别工具类
 */
public class AlarmTypeUtils {
    private AlarmTypeUtils() {
    }

    private static AlarmTypeUtils instance;

    public static AlarmTypeUtils getInstance() {
        if (instance == null) {
            instance = new AlarmTypeUtils();
        }
        return instance;
    }

    /**
     * 设置告警数据
     * @param context
     */
    public void setAlarmTypeData(Context context) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new SearchAllAlarmTypeModel().getAllAlarmTypes(context, id, new IGaoJingView() {
            @Override
            public void success(String msg) {
                if (!"[]".equals(msg)) {
                    Gson gson = new Gson();
                    final List<AlarmType> datas = gson.fromJson(msg.toString(),
                            new TypeToken<List<AlarmType>>() {
                            }.getType());
                    if (datas != null) {
                        BoHuiApplication.getInstance().setDatas(datas);
//                        for (int i = 0; i < datas.size(); i++) {//告警类型列表
//                            AlarmType at = datas.get(i);
//                            setAlramTypeBitmap(at, i);
//                        }
                    }
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    /**
     * 异步加载图片后设置在指定的位置
     *
     * @param at
     * @param position
     */
    private void setAlramTypeBitmap(AlarmType at, final int position) {
        if (at.getAlarmImage() != null && !"".equals(at.getAlarmImage())) {//如果告警图片不为空，就把告警图片下载下来并存储到本地
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String imagePath = configBean.getIp() + "/";
            ImageLoader.getInstance().loadImage(imagePath + at.getAlarmImage(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (bitmap != null) {
                        BoHuiApplication.getInstance().getDatas().get(position).setBitmap(bitmap);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }
}
