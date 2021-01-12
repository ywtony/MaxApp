package com.bohui.model;

import android.content.Context;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.view.IGaoJingView;
import com.bohui.view.IMainPageView;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取告警列表
 */
public class GaoJingModel implements IBaseModel {
    /**
     * 获取告警列表
     *
     * @param context
     * @param sessionId
     * @param pageNo
     * @param pageSize
     * @param iGaoJingView
     */
    public void getGaoJingList(Context context, String sessionId, int pageNo, int pageSize, final IGaoJingView iGaoJingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "searchAllUnackedAlarm"));
            parameters.add(new BasicNameValuePair("curpagenum", pageNo + ""));//当前页
            parameters.add(new BasicNameValuePair("pagedatanum", pageSize + ""));//每页多少条

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
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取历史告警信息
     * @param context
     * @param sessionId sessionid
     * @param alarmLevel 告警等级
     * @param riskLevel 风险等级
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNo 第几页
     * @param pageSize 每页显示多少行
     * @param iGaoJingView 回调函数
     */
    public void getHistoryAlarmList(Context context, String sessionId,String alarmLevel,String riskLevel,String startTime,String endTime, int pageNo, int pageSize, final IGaoJingView iGaoJingView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//            String url = Constants.HOST + "Handler/App/AppHandler.ashx";//?type=getBoxList";
            String url = configBean.getIp() + "/Handler/App/AppHandler.ashx";//type=getBoxList&sessionID="+sessionId;
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("sessionID", sessionId));
            parameters.add(new BasicNameValuePair("type", "searchAlarmForDataPageEx"));
            parameters.add(new BasicNameValuePair("alarmLevel", alarmLevel));
            parameters.add(new BasicNameValuePair("riskLevel", riskLevel));
            parameters.add(new BasicNameValuePair("startTime", startTime));
            parameters.add(new BasicNameValuePair("endTime", endTime));
            parameters.add(new BasicNameValuePair("curpagenum", pageNo + ""));//当前页
            parameters.add(new BasicNameValuePair("pagedatanum", pageSize + ""));//每页多少条

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
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
