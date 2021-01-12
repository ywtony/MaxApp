package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IGaoJingView;
import com.bohui.view.IIconView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表业务模型
 */
public class IconModel implements IBaseModel {
    /**
     * 获取图表的曲线数据
     *
     * @param context
     * @param sessionId
     * @param measObjID
     * @param oneHourSmooth true
     * @param oneDaySmooth true
     * @param startP 0
     * @param endP 0
     * @param iIconView
     */
    public void getIconData(Context context, String sessionId, String measObjID, String oneHourSmooth, String oneDaySmooth, String startP, String endP, final IIconView iIconView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "getCurDistriMeasValue"));
            parameters.add(new BasicNameValuePair("measObjID", measObjID));//当前页
            parameters.add(new BasicNameValuePair("oneHourSmooth", oneHourSmooth));//每页多少条
            parameters.add(new BasicNameValuePair("oneDaySmooth", oneDaySmooth));//每页多少条
            parameters.add(new BasicNameValuePair("startP", startP));//每页多少条
            parameters.add(new BasicNameValuePair("endP", endP));//每页多少条

            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
                        DevLog.e("进入了success方法");
                        iIconView.success(successJson);

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
