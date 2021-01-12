package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.ICongrolView;
import com.bohui.view.IGaoJingView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制按钮
 */
public class ControlModel implements IBaseModel {
    /**
     *
     * @param context
     * @param sessionId
     * @param measObjId
     * @param measValue
     * @param iCongrolView
     */
    public void postControl(Context context, String sessionId, String measObjId, String measValue, final ICongrolView iCongrolView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "updateSwitchMeasValue"));
            parameters.add(new BasicNameValuePair("measObjId",  measObjId));//id
            parameters.add(new BasicNameValuePair("measValue",  measValue));//value

            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iCongrolView.success(successJson);

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
