package com.bohui.model;

import android.content.Context;

import com.bohui.config.Constants;
import com.bohui.http.GsonHttpResponseHandler;
import com.bohui.http.JsonResponseInter;
import com.bohui.utils.DevLog;
import com.bohui.utils.HttpUtil;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * 录音文件上传
 * Created by yangwei on 2017/6/28.
 */
public class UploadFileModel {
    /**
     * 多文件上传带宽高
     *
     * @param files
     */
    public void uploadPropertyFiles(Context context, List<String> files, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        for (int i = 0; i < files.size(); i++) {
            DevLog.e("------------------:" + i + ":--" + files.get(i));
            try {
                String fileName = System.currentTimeMillis() + "" + i;
                params.put("file", new File(files.get(i)));
                params.put("fileName",fileName);
                params.put("type" ,"USER");
//                DevLog.e("------------------:" + i + ":--" + files.get(i));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        String url = Config.INTERFACE_URL + "upload?token=" + GApplication.getInstance().getAuthConfig().getToken();
        String url = Constants.HOST + "upload";
        HttpUtil.getClient().post(context, url, params, handler);
    }

    /**
     * 单文件上传
     *
     * @param context
     * @param path
     * @param handler
     */
    public void uploadPropertyFiles(Context context, String path, JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        try {
            params.put("file", new File(path));
//            params.put("type", -1);
//			params.put("file",path);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String url = Constants.HOST + "/jindouyun-server/file/fileUpload.do?userid="+ UserCenter.getInstance().getUser().getUserId(); //+ GApplication.getInstance().getAuthConfig().getToken();
//        DevLog.e(url);
//        HttpUtil.getClient().post(context, url, params, handler);
    }

//    public void uploadPropertyFiles(Context context, String path, final IImgView iImgView) {
//        RequestParams params = new RequestParams();
//        try {
//            params.put("file", new File(path));
//            params.put("type", -1);
////			params.put("file",path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String url = Constants.HOST + "api/upload?token=";// + GApplication.getInstance().getAuthConfig().getToken();
//        HttpUtil.getClient().post(context, url, params, new GsonHttpResponseHandler(context, new JsonResponseInter() {
//            @Override
//            public void onSuccess(int statusCode, String successJson) {
//                try {
//                    Gson gson = new Gson();
//                    ResponseListBeanUtils<Img> bean = gson.fromJson(successJson,
//                            new TypeToken<ResponseListBeanUtils<Img>>() {
//                            }.getType());
//                    if (bean != null) {
////                        if (bean.getData() != null) {
////                            if ("0".equals(bean.getCode())) {
////                                iImgView.img(bean.getData());
////                            }
////                        }
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, String errJson) {
//
//            }
//        }));
//    }
}
