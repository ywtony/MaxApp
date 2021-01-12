package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IGaoJingView;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;

/**
 * 切换页面时获取告警数据
 * @author yangwei
 */
public class GetUnclearedAlarmsByMeasObjIDsModel implements IBaseModel {
    /**
     * @param context
     * @param sessionId
     * @param iGaoJingView
     */
    public void getGetUnclearedAlarmsByMeasObjIDsData(Context context, String sessionId, String measObjIDs, final IGaoJingView iGaoJingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "getUnackedAlarmsByMeasObjIDs"));
            parameters.add(new BasicNameValuePair("measObjIDs", measObjIDs));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法："+successJson);
                        iGaoJingView.success(successJson);

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
