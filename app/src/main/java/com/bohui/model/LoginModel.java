package com.bohui.model;

import android.content.Context;
import android.util.Log;

import com.bohui.BoHuiApplication;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.LoginData;
import com.bohui.bean.LoginParams;
import com.bohui.config.Constants;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.Aes;
import com.bohui.utils.Base64Utils;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.RSAUtils;
import com.bohui.utils.SHA1SHA256Utils;
import com.bohui.utils.ToastUtil;
import com.bohui.view.ILoginView;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录模型
 */
public class LoginModel implements IBaseModel {
    /**
     * 用户登录接口
     *
     * @param context
     * @param username
     * @param password
     * @param loginView
     */
    public void login(final Context context, String username, String password, final ILoginView loginView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();

//            String url = Constants.HOST + "Handler/Security/SecUserHandler.ashx";//?username=" + username + "&password=" + SHA1SHA256Utils.getInstance().shaEncrypt(password)+"&type=login&checked=true&terminalType=1";
            String url = configBean.getIp() + "/Handler/Security/SecUserHandler.ashx";

            DevLog.e("url:" + url);
//            LoginParams loginParams = new LoginParams();
//            loginParams.setUsername(username);
//            loginParams.setPassword(SHA1SHA256Utils.getInstance().shaEncrypt(password));
//            HttpEntity entity = new StringEntity(JSONUtil.getInstance().getString(loginParams));
//            RequestParams params = new RequestParams();
//            params.put("type", "login");
//            params.put("username", username);
//            params.put("password", SHA1SHA256Utils.getInstance().shaEncrypt(password));
//            params.put("checked", 0);
//            params.put("terminalType", 3);
//            params.put("expand", "");


            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("username", username));
            parameters.add(new BasicNameValuePair("password", SHA1SHA256Utils.getInstance().shaEncrypt(password)));
            parameters.add(new BasicNameValuePair("type", "login"));
            parameters.add(new BasicNameValuePair("checked", 0 + ""));
            parameters.add(new BasicNameValuePair("terminalType", 3 + ""));
            parameters.add(new BasicNameValuePair("expand", ""));

            HttpEntity entity = new UrlEncodedFormEntity(parameters);
//            HttpUtil.getClient().addHeader("content-type","text/html");
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
//                        JSONObject obj = new JSONObject(successJson);
//                        final String code = obj.getString("code");
//                        if (code.equals("0")) {
//                            String msg = obj.getString("msg");
//                            String js2 = obj.getString("data");
////                        JSONObject boj2 = new JSONObject(js2);
////                        String token = boj2.getString("token");
////                        BoHuiApplication.getInstance().getAuthConfig().setToken(token);
//                        }
                        loginView.success(successJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, String errJson) {
                    DevLog.e("onFailure:" + errJson + statusCode);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取服务端Ras key，用于加密登录时的数据
     *
     * @param context
     * @param loginView
     */
    public void getRSAKey(final Context context, String userName, String password, final ILoginView loginView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/Security/SecUserHandler.ashx";
            DevLog.e("url:" + url);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("type", "getRSAKey"));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                    ToastUtil.showToast(context,"失败了");
                    loginView.onFail();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    Log.e("看看返回了个啥玩意:",s);
                    login(context, userName, password, s, loginView);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户登录接口
     *
     * @param context
     * @param userName
     * @param password
     * @param loginView
     */
    public void login(final Context context, String userName, String password, String key, final ILoginView loginView) {
        try {
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String url = configBean.getIp() + "/Handler/Security/SecUserHandler.ashx";
            DevLog.e("url:" + url);
            LoginData loginData = new LoginData();
            loginData.setUserName(userName);
            loginData.setPassword(password);
            String loginJsonEncrypt = Base64Utils.encode(RSAUtils.encryptData(loginData.toJson().getBytes(),RSAUtils.loadPublicKey(key)));
            String signature = SHA1SHA256Utils.getInstance().shaEncrypt(loginJsonEncrypt);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("type", "login"));
            parameters.add(new BasicNameValuePair("data", loginJsonEncrypt));
            parameters.add(new BasicNameValuePair("signature", signature));
            HttpEntity entity = new UrlEncodedFormEntity(parameters);
//            HttpUtil.getClient().addHeader("content-type","text/html");
            HttpUtil.getClient().post(context, url, entity, "application/x-www-form-urlencoded; charset=UTF-8", new GsonHttpResponseHandler(context, new JsonResponseInter() {
                @Override
                public void onSuccess(int statusCode, String successJson) {
                    try {
//                        JSONObject obj = new JSONObject(successJson);
//                        final String code = obj.getString("code");
//                        if (code.equals("0")) {
//                            String msg = obj.getString("msg");
//                            String js2 = obj.getString("data");
////                        JSONObject boj2 = new JSONObject(js2);
////                        String token = boj2.getString("token");
////                        BoHuiApplication.getInstance().getAuthConfig().setToken(token);
//                        }
                        loginView.success(successJson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, String errJson) {
                    DevLog.e("onFailure:" + errJson + statusCode);
                    loginView.onFail();
                }
            }));
        } catch (Exception e) {
            loginView.onFail();
            e.printStackTrace();
        }

    }
}
