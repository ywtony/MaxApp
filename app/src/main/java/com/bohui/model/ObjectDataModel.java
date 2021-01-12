package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.config.Constants;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IMapDataView;
import com.bohui.view.IObjDataView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


/**
 * 获取更新数据的数据模型
 */
public class ObjectDataModel implements IBaseModel {
    /**
     * @param context
     * @param sessionID
     * @param objId
     * @param iObjDataView
     */
    public void getObjDatas(Context context, String sessionID, String objId, final IObjDataView iObjDataView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//?type=getBoxList";
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionID));
            parameters.add(new BasicNameValuePair("type", "searchCurMeasValueList"));
            parameters.add(new BasicNameValuePair("measObjIDs", objId));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iObjDataView.success(successJson);

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

}
