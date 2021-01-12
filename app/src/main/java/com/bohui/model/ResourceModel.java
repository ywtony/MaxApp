package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IResourceView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取资源文件下载路径
 * create by yangwei
 * on 2019-12-24 16:29
 */
public class ResourceModel {
    public void getResource(Context context,String sessionId, final IResourceView iResourceView) {
        try {
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "getImagesBoxZipInfo"));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iResourceView.success(successJson);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, String errJson) {

                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
