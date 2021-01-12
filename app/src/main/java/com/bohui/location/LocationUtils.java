package com.bohui.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bohui.BoHuiApplication;
import com.bohui.model.SubmitLocationModel;
import com.bohui.utils.DevLog;
import com.bohui.view.IClearGaojingView;

/**
 * 获取定位
 */
public class LocationUtils {
    private Context context;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            double lat = aMapLocation.getLatitude();
            double lon = aMapLocation.getLongitude();
            if (lat > 0 && lon > 0) {
                DevLog.e("连续定位：" + lat + "|" + lon);
                String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
                if (id != null && !"".equals(id)) {
                    BoHuiApplication.getInstance().setLat(lat);
                    BoHuiApplication.getInstance().setLng(lon);
                    submitLocation(id, lat, lon);
                }
            }

        }
    };

    public LocationUtils(Context context) {
        this.context = context;
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(30000);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

    }

    /**
     * 提交位置信息
     * @param id 手机的id
     * @param lat 经度
     * @param lng 维度
     */
    private void submitLocation(String id, double lat, double lng) {
        String appid = getSerialNumber(context);
        DevLog.e("手机的唯一ID：" + appid);
        new SubmitLocationModel().submitLocation(context, id, appid, lat, lng, new IClearGaojingView() {
            @Override
            public void success(String msg) {

            }
        });
    }

    private String getSerialNumber(Context context) {
        String SerialNumber = android.os.Build.SERIAL;
        return SerialNumber;
    }
}
