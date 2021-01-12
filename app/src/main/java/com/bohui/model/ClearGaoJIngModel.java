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
public class ClearGaoJIngModel {
    /**
     * 确认告警
     *
     * @param context
     * @param sessionId
     * @param alarmid
     * @param iClearGaojingView
     */
    public void clearGaojing(Context context, String sessionId, String alarmid, final IClearGaojingView iClearGaojingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "clearAlarm"));
            parameters.add(new BasicNameValuePair("alarmID", alarmid));//id
            parameters.add(new BasicNameValuePair("clearReason", "0"));//value
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

                }
            },false,""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确认告警
     *
     * @param context
     * @param sessionId
     * @param alarmID
     * @param files
     * @param iClearGaojingView
     */
//    public void confirmAlarm(Context context, String sessionId, String alarmID, List<File> files, final IClearGaojingView iClearGaojingView) {
//        try {
//            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
////            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
//            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
//
//
//            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair("sessionID", sessionId));
//            parameters.add(new BasicNameValuePair("type", "confirmAlarm"));
//            parameters.add(new BasicNameValuePair("alarmID", alarmID));//id
//            parameters.add(new BasicNameValuePair("desc", "哈哈哈哈哈哈哈哈"));//id
//
//
////            HttpEntity entity = new UrlEncodedFormEntity(parameters);
////            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
////            if (files != null) {
////                for (File file : files) {
////                    entity.writeTo(new FileOutputStream(file));
////                }
////            }
//
//
//            MultipartEntity entity = new MultipartEntity();
//            entity.addStringPart("sessionID", sessionId);
//            entity.addStringPart("type", "confirmAlarm");
//            entity.addStringPart("alarmID", alarmID);
//            entity.addStringPart("desc", "哈哈哈哈哈哈哈哈");
//
//
////            entity.addPart("sessionID", new StringBody(sessionId, Charset.forName("utf-8")));
////            entity.addPart("type", new StringBody("confirmAlarm", Charset.forName("utf-8")));
////            entity.addPart("alarmID", new StringBody(alarmID, Charset.forName("utf-8")));
////            entity.addPart("desc", new StringBody("您好啊好啊阿啊", Charset.forName("utf-8")));
//
//            if (files != null) {
//                for (File file : files) {
//                    entity.addFilePart("files", file);
//                }
//            }
//
//            RequestParams params = new RequestParams();
//            params.put("sessionID", sessionId);
//            params.put("type", "confirmAlarm");
//            params.put("alarmID", alarmID);
//            params.put("desc", "adjaldsjf");
//            if (files != null) {
//                for (File file : files) {
//                    params.put("Files", file);
//                }
//            }
//
////            HttpUtil.getClient().addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
////            HttpUtil.getClient().post(url, params, new GsonHttpResponseHandler(context, new JsonResponseInter() {
////                @Override
////                public void onSuccess(int statusCode, String successJson) {
////                    try {
////                        DevLog.e("进入了success方法");
////                        iClearGaojingView.success(successJson);
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                @Override
////                public void onFailure(int statusCode, String errJson) {
////
////                }
////            }));
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//
//            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
//                @Override
//                public void onSuccess(int statusCode, String successJson) {
//                    try {
//                        DevLog.e("进入了success方法");
//                        iClearGaojingView.success(successJson);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, String errJson) {
//
//                }
//            }));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void confirmAlarm(Context context, String sessionId, String alarmID, List<File> files, String desc,String ackResult,
                             String alarmLevel,String riskLevel,String vName,final IClearGaojingView iClearGaojingView) {
        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
        String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;

        Map<String, Object> map = new HashMap<>();
        map.put("sessionID", sessionId);
        map.put("type", "confirmAlarm");
        map.put("alarmID", alarmID);
        map.put("desc", desc);
        map.put("ackResult", ackResult);
        map.put("alarmLevel",alarmLevel);
        map.put("riskLevel",riskLevel);
        map.put("confirmer",vName);
        map.put("lat",BoHuiApplication.getInstance().getLat());
        map.put("lng",BoHuiApplication.getInstance().getLng());
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
        // readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
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


}
