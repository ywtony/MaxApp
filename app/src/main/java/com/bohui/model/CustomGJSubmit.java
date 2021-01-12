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
import com.bohui.view.IGaoJingView;

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
import top.zibin.luban.Luban;

/**
 * 用户手动提交告警信息
 */
public class CustomGJSubmit {
    /**
     * 手动提交告警信息
     *
     * @param context
     * @param sessionId
     * @param lat
     * @param lng
     * @param iClearGaojingView
     */
    public void submitGaoJing(Context context, String sessionId, double lat, double lng, String zoneID, String location, String recordType, String desc,String name, List<File> files, final IClearGaojingView iClearGaojingView) {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
        Map<String, Object> map = new HashMap<>();
        map.put("sessionID", sessionId);
        map.put("type", "addInsRecord");
        map.put("lat", lat);
        map.put("lng", lng);
        map.put("zoneID", zoneID);
        map.put("location", location);
        map.put("recordType", recordType);
        map.put("desc", desc);
        map.put("Name", name);
        OkHttpClient client = new OkHttpClient();
        // form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null && files.size() > 0) {
            // MediaType.parse() 里面是上传的文件类型。
            for (int i = 0; i < files.size(); i++) {
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), files.get(i));
                String filename = files.get(i).getName();
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                requestBody.addFormDataPart("files", files.get(i).getName(), body);
            }

        }
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                requestBody.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(context).build();
        // readTimeout("请求超时时间" , 时间单位,超时时间设置30分钟);
        client.newBuilder().readTimeout(60000*30, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("success", "onFailure----->");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    iClearGaojingView.success("上传图片成功");
                }
            }
        });
    }

    public void getZoneId(Context context, String sessionId, final IGaoJingView iGaoJingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "searchMeasZone"));

            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iGaoJingView.success(successJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, String errJson) {

                }
            }, false, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
