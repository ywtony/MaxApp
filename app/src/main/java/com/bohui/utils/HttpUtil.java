package com.bohui.utils;

import com.bohui.BoHuiApplication;
import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.client.params.ClientPNames;

import java.util.Date;

///*import org.apache.http.client.params.ClientPNames;*/


/**
 * Http工具�?
 *
 * @author tony
 */
public class HttpUtil {
    private HttpUtil() {
    }


    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(2000 * 10);
        // 将重定向设置为false
        client.setEnableRedirects(false);
        client.getHttpClient().getParams()
                .setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.setMaxConnections(20);
    }

    public static AsyncHttpClient getClient() {
        client.addHeader(
                "Accept",
                "image/jpeg, image/png,image/jpg, image/gif, application/json, application/xml,audio/*,*/*");
		client.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        // 添加token

//        String token = BoHuiApplication.getInstance().getAuthConfig().getToken();
//        String token = GApplication.getInstance().getToken();
//        DevLog.e("token:"+token);
//        if (token != null && !"".equals(token)) {
//            client.addHeader("x-auth-token", token);
//            DevLog.e("not null");
//        }else{
//            DevLog.e("nulll");
//        }
//        long timeout = 1000L * 60 * 60 * 24 * 14;
//        if (GApplication.getInstance().getAuthConfig().getLoginTime() > 0) {
//            if ((new Date().getTime() - GApplication.getInstance().getAuthConfig().getLoginTime()) > timeout) {
//                GApplication.getInstance().getAuthConfig().removeLoginTime();
//                GApplication.getInstance().getAuthConfig().removeToken();
////				UserInfoUtils.getInstance().loginOut(GApplication.getInstance());
//            }
//        }
        if (!NetworkUtils.isNetConnected(BoHuiApplication.getInstance())) {
            ToastUtil.getInstance().show(BoHuiApplication.getInstance(), "当前网络不可用");
        }
        return client;
    }
}
