package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.config.Constants;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.utils.SHA1SHA256Utils;
import com.bohui.view.IMapDataView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取要绘制的地图数据
 */
public class MapDataModel implements IBaseModel {
    /**
     * 获取地图数据
     *
     * @param context
     * @param iMapDataView
     */
    public void getMapData(Context context, String sessionID,String pageId, final IMapDataView iMapDataView) {
        try {

            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//?type=getBoxList";

            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionID));
            parameters.add(new BasicNameValuePair("type", "getBoxList"));
            parameters.add(new BasicNameValuePair("pageID", pageId));

            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iMapDataView.success(successJson);

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
