package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IObjDataView;
import com.bohui.view.IPageListView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PageListModel implements IBaseModel {
    /**
     * 获取Page列表
     *
     * @param context
     * @param sessionID
     * @param iPageListView
     */
    public void getPageList(Context context, String sessionID, final IPageListView iPageListView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//?type=getBoxList";
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionID));
            parameters.add(new BasicNameValuePair("type", "getPageList"));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iPageListView.success(successJson);

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
