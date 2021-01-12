package com.bohui.model;

import android.content.Context;
import android.util.Log;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IClearGaojingView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 清除和确认告警
 */
public class SubmitLocationModel {
    /**
     * 提交位置信息
     *
     * @param context
     * @param sessionId
     * @param appID
     * @param lat
     * @param lng
     * @param iClearGaojingView
     */
    public void submitLocation(Context context, String sessionId, String appID, double lat, double lng, final IClearGaojingView iClearGaojingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "updateLocation"));
            parameters.add(new BasicNameValuePair("appID", appID));//id
            parameters.add(new BasicNameValuePair("lat", lat + ""));//value
            parameters.add(new BasicNameValuePair("lng", lng + ""));//value
            HttpEntity entity = new UrlEncodedFormEntity(parameters);


            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iClearGaojingView.success(successJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, String errJson) {
                    DevLog.e("errjson:"+errJson);
                }
            }, false, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
