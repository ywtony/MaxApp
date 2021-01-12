package com.bohui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.animation.Animation;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.ExAttrs;
import com.bohui.bean.GJPoint;
import com.bohui.bean.MainPage;
import com.bohui.bean.MapBean;
import com.bohui.bean.StatusList;
import com.bohui.bean.ZJAlarmBean;
import com.bohui.bean.ZJPoint;
import com.bohui.config.Constants;
import com.bohui.inter.DrawWhat;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.PointDistantUtils;
import com.bohui.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yw.downloadlibrary.utils.PathUtil;
import com.yw.ziplibrary.utils.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import okhttp3.Headers;

/**
 * 绘制图像数据
 * Created by HaoXiang on 2018/3/26.
 */

public class DrawMapView extends View {
    private final Context mContext;
    private List<MapBean> datas = new ArrayList<>();
    private MainPage mainPage = null;
    private Paint paintBg = new Paint();//主体背景
    private Paint paintSLabel = new Paint();//静态文本
    private Paint paintDLabel = new Paint();//动态文本
    private Paint paintSPicture = new Paint();//静态图片
    private Paint paintDPicture = new Paint();//动态图片
    private Paint paintStatusSwitch = new Paint();//开关按钮
    private Paint paintControlButton = new Paint();//控制按钮
    private int widht = 0;//宽度
    private int height = 0;//高度
    private Bitmap bitmapStaticPicture;
    private Bitmap backgroundPicture;
    private Bitmap controlPictrue;
    private Bitmap dnPictrue;
    private Bitmap switchPicture;
    private Bitmap alarmBitmap;
    private Bitmap videoPicture;
    private int topHeight = 0;
    private List<ZJAlarmBean> zjAlarmBeans = new ArrayList<>();
    private List<GJPoint> points = new ArrayList<>();

    public DrawMapView(Context context) {
        this(context, null);
    }

    public DrawMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int t = 0;
    private int tt = 0;

    @Override
    protected void onDraw(final Canvas canvas) {
        //绘制画布背景
        if (t == 0) {
            t = 1;
            canvas.drawColor(Color.parseColor("#ffffff"));
        }
        DevLog.e("执行开始了");
        //绘制图层开始,主要绘制text的背景颜色
//        Paint paintLayer = new Paint();
//        Rect rect1 = new Rect(100, 100, 400, 400);
//        paintLayer.setColor(Color.RED);
//        paintLayer.setStrokeWidth(5);
//        paintLayer.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(rect1, paintLayer);
//        paintStatusSwitch.setTextSize(40);
//        canvas.drawText("这是一个测试", 100, 100, paintStatusSwitch);
//        drawTest(canvas, paintLayer);
        //绘制图层结束


        widht = getWidth();//画布宽度

        height =
                getHeight();//画布高度
        DevLog.e("width:" + widht + "|height:" + height);
        //尝试一下绘制动画
        if (mainPage != null) {
            int left = 0;
//            int top = -topHeight;
            int top = 0;
            int right = mainPage.getWidth();
            int bottom = mainPage.getHeight();
            RectF rect = new RectF(right, bottom, left, top);
            if (backgroundPicture != null) {
                tt = 0;
                DevLog.e("绘制背景");
                DevLog.e("------width:" + widht + "|height:" + height);
                DevLog.e("bitmapwidth" + backgroundPicture.getWidth() + "|height:" + backgroundPicture.getHeight());
                Matrix matrix = new Matrix();
                //缩放原图
                matrix.postScale(1f, 1f);
                // 向左旋转45度，参数为正则向右旋转
                matrix.postRotate(-180);
//                Bitmap dstbmp = Bitmap.createBitmap(backgroundPicture, 0, 0, backgroundPicture.getWidth(), backgroundPicture.getHeight(),
//                        matrix, true);
//                Rect srcRect = new Rect(0, 0, backgroundPicture.getWidth(), backgroundPicture.getHeight());
//                canvas.drawBitmap(dstbmp, srcRect, rect, paintBg);
                canvas.drawBitmap(backgroundPicture, 0, 0, paintBg);
//                Bitmap roateBitmap = rotateBitmap(backgroundPicture,-180);
//                canvas.drawBitmap(roateBitmap,0,0,paintBg);
//                canvas.drawBitmap(dstbmp, 0,0, paintBg);
            } else {
//                if (tt == 0) {
//                    tt = 1;
                DevLog.e("背景图为空，下载背景");
                Message msg = new Message();
                msg.what = 1;
                msg.obj = mainPage.getBackgroundImage();
                handler.sendMessage(msg);
//                }

            }
        } else {
            DevLog.e("DrawmapViewmainPage为空");
        }

        if (datas != null && datas.size() > 0) {
            DevLog.e("已经开始绘制了");
            for (int i = 0; i < datas.size(); i++) {
                final MapBean mapBean = datas.get(i);
                DevLog.e("我就是需要测试一下这个数据到底是怎亚航产生的：" + mapBean.getAlarmSource());
                if (mapBean.getType().equals(DrawWhat.StaticLabel)) {//绘制静态文本
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    paintSLabel.setTextSize(exAttrs.getFontSize());
                    paintSLabel.setColor(Color.parseColor(exAttrs.getColor()));
//                    paintSLabel.setColor(Color.parseColor("#d81e06"));
                    drawText(canvas, paintSLabel, mapBean.getTop(), mapBean.getLeft(), exAttrs.getCaption(), exAttrs.getBackgroundColor(), exAttrs.isOpacity());
//                    canvas.drawText(exAttrs.getCaption(), mapBean.getLeft(), mapBean.getTop() , paintSLabel);
                } else if (mapBean.getType().equals(DrawWhat.DynamicLabel)) {//绘制动态文本
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    switch (mapBean.getAlarmLevel()) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                    }
                    paintDLabel.setTextSize(exAttrs.getFontSize());
                    paintDLabel.setColor(Color.parseColor(exAttrs.getColor()));
//                    canvas.drawText(mapBean.getCV() + "", mapBean.getLeft(), mapBean.getTop() + topHeight, paintDLabel);
                    drawText(canvas, paintDLabel, mapBean.getTop() + topHeight, mapBean.getLeft(), mapBean.getCV() + "", exAttrs.getBackgroundColor(), exAttrs.isOpacity());
                    //2. 计算文字所在矩形，可以得到宽高
//                    RectF rectF = new RectF();
//                    rectF.left = mapBean.getLeft();
//                    rectF.top = mapBean.getTop() + topHeight;
//                    rectF.right = mapBean.getLeft() + 100;
//                    rectF.bottom = mapBean.getTop() + 100;
//                    Paint rePaint = new Paint();
//                    rePaint.setColor(Color.parseColor("#0066ff"));
//                    canvas.drawRect(rectF, rePaint);
//                    canvas.drawText(mapBean.getCV() + "", mapBean.getLeft(), mapBean.getTop() + topHeight, paintDLabel);
                } else if (mapBean.getType().equals(DrawWhat.StaticPicture)) {//绘制静态图片
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tree);
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    DevLog.e("url:" + Constants.IMAGE_HOST + exAttrs.getImageUrl());
                    int left = mapBean.getLeft();
                    int top = mapBean.getTop() + topHeight;
                    int right = mapBean.getLeft() + mapBean.getWidth();
                    int bottom = top + mapBean.getHeight();
                    Rect rect = new Rect(right, bottom, left, top);

                    if (mapBean.getStaticBitmap() != null) {
                        DevLog.e("静态图片不为空了");
                        Matrix matrix = new Matrix();
                        //缩放原图
                        matrix.postScale(1f, 1f);
                        // 向左旋转45度，参数为正则向右旋转
                        matrix.postRotate(-180);
//                        Bitmap dstbmp = Bitmap.createBitmap(bitmapStaticPicture, 0, 0, bitmapStaticPicture.getWidth(), bitmapStaticPicture.getHeight(),
//                                matrix, true);
//                        Rect srcRect = new Rect(0, 0, bitmapStaticPicture.getWidth(), bitmapStaticPicture.getHeight());
//                        canvas.drawBitmap(dstbmp, srcRect, rect, paintSLabel);
//                        canvas.drawBitmap(dstbmp,left,top,paintSLabel);
//                        Bitmap roateBitmap = rotateBitmap(bitmapStaticPicture,-180);

                        canvas.drawBitmap(mapBean.getStaticBitmap(), left, top, paintSLabel);
                    } else {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.arg1 = i;
                        msg.obj = exAttrs.getImageUrl();
                        handler.sendMessage(msg);
                    }

                } else if (mapBean.getType().equals(DrawWhat.DynamicPicture)) {//绘制动态图片
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.treepage);
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    int cv = (int) mapBean.getCV();
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    List<StatusList> list = exAttrs.getStatusList();
                    String path = null;
                    int alarmLevel = mapBean.getAlarmLevel();
                    if (alarmLevel > 0) {
                        switch (alarmLevel) {
                            case 1:
                                path = exAttrs.getTipUrl();
                                break;
                            case 2:
                                path = exAttrs.getPreAlarmUrl();
                                break;
                            case 3:
                                path = exAttrs.getAlarmUrl();
                                break;
                            case 4:
                                path = exAttrs.getFaultUrl();
                                break;
                        }
                    } else {
                        if (list != null && list.size() > 0) {
                            path = list.get(cv).getPic();
                        } else {
                            path = exAttrs.getNormalUrl();
                        }

                        DevLog.e("cv++++++++:" + cv + "|" + alarmLevel);
                    }

                    int left = mapBean.getLeft();
                    int top = mapBean.getTop() + topHeight;
                    int right = mapBean.getLeft() + mapBean.getWidth();
                    int bottom = top + mapBean.getHeight();
                    Rect rect = new Rect(right, bottom, left, top);
                    if (mapBean.getBitmap() != null) {
//                        Bitmap roateBitmap = rotateBitmap(mapBean.getBitmap(),-180);
                        canvas.drawBitmap(mapBean.getBitmap(), left, top, paintDLabel);
//                        Rect srcRect = new Rect(0, 0, mapBean.getBitmap().getWidth(), mapBean.getBitmap().getHeight());
//                        canvas.drawBitmap(mapBean.getBitmap(), srcRect, rect, paintDLabel);
//                        canvas.drawBitmap(mapBean.getBitmap(), left, top, paintDLabel);
                    }
                    Message msg = new Message();
                    msg.what = 2;
                    msg.arg1 = i;
                    msg.obj = path;
                    handler.sendMessage(msg);
                } else if (mapBean.getType().equals(DrawWhat.ControlButton)) { //绘制控制按钮0红，1绿
                    DevLog.e("绘制controlbutton");
//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hourese);
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    int cv = (int) mapBean.getCV();
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    int left = mapBean.getLeft();
                    int top = mapBean.getTop() + topHeight;
                    int right = mapBean.getLeft() + mapBean.getWidth();
                    int bottom = top + mapBean.getHeight();
                    Rect rect = new Rect(right, bottom, left, top);
                    Bitmap controlPictrue = null;
                    if (exAttrs.getDisplayStyle() == 1) {//lv
                        controlPictrue = BitmapFactory.decodeResource(getResources(), R.mipmap.control_btn_gree);
                    } else {//hong
                        controlPictrue = BitmapFactory.decodeResource(getResources(), R.mipmap.control_btn_red);
                    }
                    Rect srcRect = new Rect(0, 0, controlPictrue.getWidth(), controlPictrue.getHeight());
                    canvas.drawBitmap(controlPictrue, srcRect, rect, paintControlButton);


//                    Bitmap roateBitmap = rotateBitmap(mapBean.getBitmap(),-180);
//                    canvas.drawBitmap(roateBitmap,left,top,paintDLabel);


//                    canvas.drawBitmap(controlPictrue, left, top, paintControlButton);
//                    if (controlPictrue != null) {
//                        canvas.drawBitmap(controlPictrue, null, rect, paint);
//                    } else {
//                        Message msg = new Message();
//                        msg.what = 3;
//                        msg.obj = exAttrs.getImageUrl();
//                        handler.sendMessage(msg);
//                    }

                } else if (mapBean.getType().equals(DrawWhat.StatusSwitch)) {//绘制开关按钮
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    int cv = (int) mapBean.getCV();
                    int left = mapBean.getLeft();
                    int top = mapBean.getTop() + topHeight;
                    int right = mapBean.getLeft() + mapBean.getWidth();
                    int bottom = top + mapBean.getHeight();
                    Rect rect = new Rect(right, bottom, left, top);
                    if (switchPicture != null) {
//                        Rect srcRect = new Rect(0, 0, switchPicture.getWidth(), switchPicture.getHeight());
//                        canvas.drawBitmap(switchPicture, srcRect, rect, paintStatusSwitch);
                        canvas.drawBitmap(switchPicture, left, top, paintStatusSwitch);


//                        Bitmap roateBitmap = rotateBitmap(switchPicture,-180);
//                    canvas.drawBitmap(roateBitmap,left,top,paintStatusSwitch);
                    } else {
                        Message msg = new Message();
                        msg.what = 4;
                        if (cv == 0) {
                            msg.obj = exAttrs.getNormalOffUrl();
                        } else {
                            msg.obj = exAttrs.getNormalOnUrl();
                        }

                        handler.sendMessage(msg);
                    }
                } else if (mapBean.getType().equals(DrawWhat.Distribute)) {//周界安防
                    DevLog.e("获取AlarmSource:" + datas.get(i).getAlarmSource());
                    String json1 = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json1);
                    ExAttrs exAttrs1 = (ExAttrs) JSONUtil.getInstance().getObject(json1, ExAttrs.class);
                    DevLog.e("周界安防完整数据输出：" + json1);
                    Paint linePaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);//画笔
                    linePaint1.setStrokeWidth(exAttrs1.getStrokeWidth());
                    //遍历图片以得到告警图片
                    if (alarmBitmap == null) {
                        if (mapBean.getAlarmType() != null) {
                            List<AlarmType> ats = BoHuiApplication.getInstance().getDatas();
                            DevLog.e("alarmtypesize:" + mapBean.getAlarmType().getID());
                            if (ats != null) {
                                for (AlarmType at : ats) {
                                    //找到与之匹配的数据
                                    if (at.getID().equals(mapBean.getAlarmType().getID()) && at.getObjTypeID() == mapBean.getAlarmType().getObjTypeID()) {
//                                    ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
//                                    String imagePath = configBean.getIp() + "/" + at.getAlarmImage();
//                                    DevLog.e("我匹配到了这些东东:" + imagePath);
//                                    if (at.getBitmap() != null) {
//                                        bitmapAlarm = at.getBitmap();
//                                    }
                                        setAlramTypeBitmap(at);
                                    }

                                }
                            }
                        }
                    }

                    for (int k = 0; k < zjAlarmBeans.size(); k++) {

                        DevLog.e("得到alarmsource就这么难吗？：" + zjAlarmBeans.get(k).getAlarmSource());
//                    linePaint1.setColor(Color.parseColor(exAttrs1.getColor()));
//                        int alarmLevel = 2;
                        int alarmLevel = Integer.parseInt(zjAlarmBeans.get(k).getAlarmSeverity());//告警级别
                        DevLog.e("alarmLevel:" + zjAlarmBeans.get(k).getAlarmSeverity());
                        int alarmSource = 0;
//                        if (mapBean.getAlarmSource() != null && !"".equals(mapBean.getAlarmSource())) {
//                            alarmSource = Integer.parseInt(mapBean.getAlarmSource());
                        alarmSource = Integer.parseInt(zjAlarmBeans.get(k).getAlarmSource());
//                        }
                        if (alarmLevel > 0) {
                            switch (alarmLevel) {//根据告警级别来绘制不同颜色的线段
                                case 1:
                                    linePaint1.setColor(Color.parseColor("#0066ff"));
                                    break;
                                case 2:
                                    linePaint1.setColor(Color.parseColor("#ffff00"));
                                    break;
                                case 3:
                                    linePaint1.setColor(Color.parseColor("#ffae00"));
                                    break;
                                case 4:
                                    linePaint1.setColor(Color.parseColor("#ff0000"));
                                    break;
                            }
                        } else {
                            linePaint1.setColor(Color.parseColor(exAttrs1.getStroke()));
                        }
                        if (exAttrs1.getDeployType() == 0) {//点式加标记

                            List<ZJPoint> points1 = exAttrs1.getPoints();
                            if (points1 != null) {
                                int index = points1.size() - 1;
                                for (int j = 0; j < index; j++) {
                                    DevLog.e("j:" + j + "|" + (j + 1) + "|size:" + points1.size());


                                    int smd = points1.get(j).getSmd();//光纤出来的时候的坐标
                                    int emd = points1.get(j).getEmd();//光纤进来的时候的距离
                                    int smdNext = points1.get(j + 1).getSmd();
                                    int emdNext = points1.get(j + 1).getEmd();
                                    DevLog.e("alarmsource的值是多少：" + alarmSource);

                                    //第一种情况：告警在线上
                                    if (alarmSource >= emd && alarmSource <= smd) {//说明告警点在盘绕上
                                        Paint paint = new Paint();
//                                    paint.setColor(Color.parseColor("#333333"));
                                        //计算坐标点绘制矩形
                                        int left = points1.get(j).getX1() + mapBean.getLeft();
                                        int top = points1.get(j).getY1() + mapBean.getTop();
//                                    int right = left + 50;
//                                    int bottom = top + 50;
//                                    Rect rect = new Rect(right, bottom, left, top);
//                                    canvas.drawRect(rect, paint);
                                        switch (alarmLevel) {//根据告警级别来绘制不同颜色的线段
                                            case 1:
                                                paint.setColor(Color.parseColor("#0066ff"));
                                                break;
                                            case 2:
                                                paint.setColor(Color.parseColor("#ffff00"));
                                                break;
                                            case 3:
                                                paint.setColor(Color.parseColor("#ffae00"));
                                                break;
                                            case 4:
                                                paint.setColor(Color.parseColor("#ff0000"));
                                                break;
                                        }
                                        DevLog.e("周界安防---------------：" + top + "|" + left);
                                        boolean isSame = false;
                                        for (int s = 0; s < points.size(); s++) {
                                            GJPoint gg = points.get(s);
                                            if (gg.getX() == left && gg.getY() == top) {//相同的告警
                                                isSame = true;
                                            }
                                        }
                                        if (!isSame) {
                                            GJPoint gjPoint = new GJPoint(left, top);
                                            gjPoint.setAlarmLevel(zjAlarmBeans.get(k).getAlarmSeverity() + "");
                                            gjPoint.setAlarmType(zjAlarmBeans.get(k).getAlarmType());
                                            gjPoint.setAlarmTime(zjAlarmBeans.get(k).getAlarmTime());
                                            gjPoint.setAlarmId(zjAlarmBeans.get(k).getAlarmId());
                                            points.add(gjPoint);
                                        }
                                        //需要判断绘制一张图片还是绘制一个纯色的点
                                        if (alarmBitmap != null) {
                                            canvas.drawBitmap(alarmBitmap, left, top, paint);
                                        } else {
                                            DevLog.e("绘制圆点了：left:" + left + "|top:" + top);
                                            canvas.drawCircle(left, top, 20, paint);
                                        }

                                    } else if (alarmSource >= smd && alarmSource <= emdNext) {//说明光纤在两点坐标上

                                        Paint paint = new Paint();
//                                    paint.setColor(Color.parseColor("#333333"));
                                        float width1 = (alarmSource - points1.get(j).getSmd());
                                        float width2 = (points1.get(j + 1).getEmd() - points1.get(j).getSmd());
                                        float relRat = width1 / width2;
                                        int x = (int) (points1.get(j).getX1() + (points1.get(j + 1).getX1() - points1.get(j).getX1()) * relRat);
                                        int y = (int) (points1.get(j).getY1() + (points1.get(j + 1).getY1() - points1.get(j).getY1()) * relRat);
                                        int left = x + mapBean.getLeft();
                                        int top = y + mapBean.getTop();
//                                    int right = left + 50;
//                                    int bottom = top + 50;
//                                    Rect rect = new Rect(right, bottom, left, top);
//                                    canvas.drawRect(rect, paint);
                                        switch (alarmLevel) {//根据告警级别来绘制不同颜色的线段
                                            case 1:
                                                paint.setColor(Color.parseColor("#0066ff"));
                                                break;
                                            case 2:
                                                paint.setColor(Color.parseColor("#ffff00"));
                                                break;
                                            case 3:
                                                paint.setColor(Color.parseColor("#ffae00"));
                                                break;
                                            case 4:
                                                paint.setColor(Color.parseColor("#ff0000"));
                                                break;
                                        }
                                        boolean isSame = false;
                                        for (int s = 0; s < points.size(); s++) {
                                            GJPoint gg = points.get(s);
                                            if (gg.getX() == left && gg.getY() == top) {//相同的告警
                                                isSame = true;
                                            }
                                        }
                                        if (!isSame) {
                                            GJPoint gjPoint = new GJPoint(left, top);
                                            gjPoint.setAlarmLevel(zjAlarmBeans.get(k).getAlarmSeverity() + "");
                                            gjPoint.setAlarmType(zjAlarmBeans.get(k).getAlarmType());
                                            gjPoint.setAlarmTime(zjAlarmBeans.get(k).getAlarmTime());
                                            gjPoint.setAlarmId(zjAlarmBeans.get(k).getAlarmId());
                                            points.add(gjPoint);

                                        }
                                        //需要判断绘制一张图片还是绘制一个纯色的点
                                        DevLog.e("周界安防%%%%%%%%%%%：" + points1.get(j).getY1() + "|" + mapBean.getTop());
                                        if (alarmBitmap != null) {
                                            canvas.drawBitmap(alarmBitmap, left - alarmBitmap.getWidth() / 2, top - alarmBitmap.getWidth() / 2, paint);
                                        } else {
                                            canvas.drawCircle(left, top, 20, paint);
                                        }

                                    }


                                    //第二种情况：告警在拐角和盘绕

                                    linePaint1.setColor(Color.parseColor(exAttrs1.getStroke()));

                                    canvas.drawLine(points1.get(j).getX1() + mapBean.getLeft(), points1.get(j).getY1() + mapBean.getTop(), points1.get(j + 1).getX1() + mapBean.getLeft(), points1.get(j + 1).getY1() + mapBean.getTop(), linePaint1);
//                                canvas.drawLine(x+mapBean.getLeft(), y+mapBean.getTop(), points1.get(j + 1).getX1()+mapBean.getLeft(), points1.get(j + 1).getY1()+mapBean.getTop(), linePaint1);
                                }
                            }
                        } else if (exAttrs1.getDeployType() == 1) {//线段、整体改变颜色
                            List<ZJPoint> points1 = exAttrs1.getPoints();
                            if (points1 != null) {
                                int index = points1.size() - 1;
                                for (int j = 0; j < index; j++) {
                                    DevLog.e("j:" + j + "|" + (j + 1) + "|size:" + points1.size());
                                    canvas.drawLine(points1.get(j).getX1(), points1.get(j).getY1(), points1.get(j + 1).getX1(), points1.get(j + 1).getY1(), linePaint1);
                                }
                            }
                        }
                    }

                } else if (mapBean.getType().equals(DrawWhat.NetVideo)) {//视频
                    String json = mapBean.getExAttrs().replaceAll("\\\\", "");
                    DevLog.e(json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    int cv = (int) mapBean.getCV();
                    int left = mapBean.getLeft();
                    int top = mapBean.getTop() + topHeight;
                    int right = mapBean.getLeft() + mapBean.getWidth();
                    int bottom = top + mapBean.getHeight();
                    Rect rect = new Rect(right, bottom, left, top);
                    if (videoPicture != null) {
                        DevLog.e("图片已经获取到了哈哈哈哈");
//                        canvas.drawBitmap(videoPicture, null, rect, paintStatusSwitch);
                        canvas.drawBitmap(videoPicture, left, top, paintStatusSwitch);
                    } else {
                        Message msg = new Message();
                        msg.what = 5;
//                        if (cv == 0) {
//                            msg.obj = exAttrs.getNormalOffUrl();
//                        } else {
//                            msg.obj = exAttrs.getNormalOnUrl();
//                        }
                        msg.obj = exAttrs.getNormalUrl();

                        handler.sendMessage(msg);
                    }
                }
            }
        }


    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
//        origin.recycle();
        return newBM;
    }

    public Bitmap getImage(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void draw(MainPage mainPage, List<MapBean> datas, List<ZJAlarmBean> zjAlarmBeans) {
        this.datas = datas;
        this.mainPage = mainPage;
        this.zjAlarmBeans = zjAlarmBeans;
//        setBackgroundPicture();
        invalidate();
    }

    private int type = 0;

    public void setType(int type) {
        this.type = type;
        invalidate();
    }

    public void setType1(int type) {
        this.type = type;
//        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://当单击屏幕的时候获取屏幕坐标，并与指定的内容作比较
                float newX = event.getX();
                float newY = event.getY();
                checkClickArea(newX, newY);
                break;
        }
        return super.onTouchEvent(event);


    }
    // 处理json字符串中value多余的双引号， 将多余的双引号替换为中文双引号
    private static String toJsonString(String s) {
        char[] tempArr = s.toCharArray();
        int tempLength = tempArr.length;
        for (int i = 0; i < tempLength; i++) {
            if (tempArr[i] == ':' && tempArr[i + 1] == '"') {
                for (int j = i + 2; j < tempLength; j++) {
                    if (tempArr[j] == '"') {
                        if (tempArr[j + 1] != ',' && tempArr[j + 1] != '}') {
                            tempArr[j] = '”'; // 将value中的 双引号替换为中文双引号
                        } else if (tempArr[j + 1] == ',' || tempArr[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(tempArr);
    }
    /**
     * 检测点击区域
     *
     * @param newX
     * @param newY
     */
    private void checkClickArea(float newX, float newY) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                MapBean mapBean = datas.get(i);
//                int left = (int) mapBean.getLeft() - 100;
//                int top = (int) mapBean.getTop() + topHeight + 100;
//                int right = (int) mapBean.getLeft() + 100;
//                int bottom = (int) mapBean.getTop() + topHeight - 100;
                int left = (int) mapBean.getLeft() - 100;
                int top = (int) mapBean.getTop() + topHeight + 100;
                int right = (int) mapBean.getLeft() + mapBean.getWidth();
                int bottom = (int) mapBean.getTop() + topHeight + mapBean.getHeight();
                String exa = mapBean.getExAttrs();
                if (exa != null && !"".equals(exa)) {
                    String json = toJsonString(exa).replaceAll("\\\\", "");
                    DevLog.e("json字符串："+json);
                    ExAttrs exAttrs = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
                    if (mapBean.getType().equals(DrawWhat.StaticLabel)) {//绘制静态文本
//                        if ((left < newX) && (top > newY) && (right > newX) && (bottom < newY)) {
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {
                            if (exAttrs.isPageActived()) {
                                if (pageClickListener != null) {
                                    pageClickListener.clickStaticLabel(mapBean, exAttrs.getPageID() + "");
                                }
                            }

                        }
                    } else if (mapBean.getType().equals(DrawWhat.DynamicLabel)) {//绘制动态文本
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {
                            if (exAttrs.isPageActived()) {
                                if (pageClickListener != null) {
                                    pageClickListener.clickStaticLabel(mapBean, exAttrs.getPageID() + "");
                                }
                            }
                        }
                    } else if (mapBean.getType().equals(DrawWhat.StaticPicture)) {//绘制静态图片
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {

                            if (exAttrs.isPageActived()) {
                                if (pageClickListener != null) {
                                    pageClickListener.clickStaticLabel(mapBean, exAttrs.getPageID() + "");
                                }
                            }
                        }
                    } else if (mapBean.getType().equals(DrawWhat.DynamicPicture)) {//绘制动态图片
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {
                            if (exAttrs.isPageActived()) {
                                if (pageClickListener != null) {
                                    pageClickListener.clickStaticLabel(mapBean, exAttrs.getPageID() + "");
                                }
                            }
                        }
                    } else if (mapBean.getType().equals(DrawWhat.ControlButton)) { //绘制控制按钮
//                        ToastUtil.getInstance().show(mContext, "控制按钮");
                        if ((left < newX) && (top > (newY + topHeight)) && (right > newX) && (bottom < (newY + topHeight))) {
                            if (exAttrs.isPageActived()) {
                                if (pageClickListener != null) {
                                    pageClickListener.clickStaticLabel(mapBean, exAttrs.getPageID() + "");
                                }
                            }
                        }
                    } else if (mapBean.getType().equals(DrawWhat.StatusSwitch)) {
//                        ToastUtil.getInstance().show(mContext, "statusswitch");
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {
//                            ToastUtil.getInstance().show(mContext, "点击");
                            if (pageClickListener != null) {
                                pageClickListener.clickStatusSwitch(mapBean, exAttrs, (int) mapBean.getCV());
                            }
                        }
                    } else if (mapBean.getType().equals(DrawWhat.NetVideo)) {//视频
                        if ((left < newX) && (top < newY) && (right > newX) && (bottom > newY)) {
//                            ToastUtil.getInstance().show(mContext, "点击了视频组件");
                            if (pageClickListener != null) {
                                pageClickListener.clickVideo(mainPage, exAttrs);
                            }
                        }
                    }
                }
            }
        }

        if (points != null) {
            for (int j = 0; j < points.size(); j++) {
                int left = points.get(j).getX() - 50;
                int top = points.get(j).getY() + topHeight + 50;
                int right = points.get(j).getX() + 50;
                int bottom = points.get(j).getY() + topHeight - 50;
                if ((left < newX) && (top > newY) && (right > newX) && (bottom < newY)) {
                    if (pageClickListener != null) {
                        pageClickListener.clickGJ(points.get(j));
                    }
                }
            }
        }

    }

    /**
     * 重置页面内容
     */
    public void resetBitmap() {
//        bitmapStaticPicture = null;
//        backgroundPicture = null;
//        controlPictrue = null;
//        Bitmap dnPictrue;
    }

    /**
     * 页面点击事件的回调接口
     */
    private PageClickListener pageClickListener;

    public void setPageClickListener(PageClickListener pageClickListener) {
        this.pageClickListener = pageClickListener;
    }

    public interface PageClickListener {
        void clickStaticLabel(Object manPage, Object obj);

        void clickDynamicLable(Object manPage, Object obj);

        void clickStaticpicture(Object manPage, Object obj);

        void clickDynamicPicture(Object manPage, Object obj);

        void clickControlButton(Object manPage, Object obj);

        void clickStatusSwitch(Object manPage, Object obj, int cv);

        void clickVideo(Object manPage, Object obj);

        void clickGJ(Object obj);
    }

    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String imagePath = configBean.getIp() + "/images/box/";
            final int position = msg.arg1;
            String type = (String) msg.obj;

//            String alarmImagePath =  Constants.LOCAL_IMAGE_LOCATION+"/alarmImage";
            String boxImgPath = PathUtil.createLocalFilePath(getContext(),"box") + "/BOX/";
            //检测文件夹中是否有指定的文件
            if (FileUtils.getInstance().isExitsFile(boxImgPath, type)) {
                imagePath = "file://" + boxImgPath;
            }
            DevLog.e("ImageRurl:" + imagePath+type);
            switch (msg.what) {
                case 0:
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            datas.get(position).setStaticBitmap(bitmap);
//                            bitmapStaticPicture = bitmap;
                            invalidate();
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
                case 1:
                    String path = (String) msg.obj;
                    DevLog.e("背景图片的路径：" + imagePath + path + "|" + type);
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            backgroundPicture = bitmap;
                            if (backgroundPicture != null) {
                                DevLog.e("得出结论，背景图片不为空");
                            }
                            invalidate();
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
                case 2://动态图片
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            try {
                                datas.get(position).setBitmap(bitmap);
//                            dnPictrue = bitmap;
//                            if (dnPictrue != null) {
//                                DevLog.e("得出结论，背景图片不为空");
//                            }
                                invalidate();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
                case 3://控制按钮
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            controlPictrue = bitmap;
                            if (controlPictrue != null) {
                                DevLog.e("得出结论，背景图片不为空");
                            }
                            invalidate();
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
                case 4:
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            switchPicture = bitmap;
                            if (switchPicture != null) {
                                DevLog.e("得出结论，背景图片不为空");
                            }
                            invalidate();
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
                case 5:
                    ImageLoader.getInstance().loadImage(imagePath + type, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            videoPicture = bitmap;
                            if (videoPicture != null) {
                                DevLog.e("得出结论，背景图片不为空");
                            }
                            invalidate();
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                    break;
            }
        }
    };
    private Animation animation = null;

    private void drawAnimation(Canvas canvas) {
        DevLog.e("开始了绘制");
        int[] res = new int[]{R.mipmap.treepage, R.mipmap.tree, R.mipmap.person, R.mipmap.hourese};
        animation = new Animation(mContext, res, true);
        animation.DrawAnimation(canvas, paintBg, 150, 150);
        DevLog.e("绘制完成了");
    }

    public void setTopHeight(int height) {
        this.topHeight = height;
    }

    private void drawTest(Canvas canvas, Paint mPaint) {
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(50);
        canvas.drawColor(Color.WHITE);
        float baseX = 30;
        float baseY = 200;
//获取FontMetrics对象，可以获得top,bottom,ascent,descent
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float top = baseY + metrics.top;
        float ascent = baseY + metrics.ascent;
        float descent = baseY + metrics.descent;
        float bottom = +baseY + metrics.bottom;

        String text = "Android*java";
        canvas.drawText(text, baseX, baseY, mPaint);
        //计算矩形的宽高
        Rect rectf = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rectf);
        int w = rectf.width();


        Rect rect = new Rect();
        rect.left = (int) (baseX - 15);
        rect.top = (int) top;
        rect.right = (int) (baseX + w + 15);
        rect.bottom = (int) bottom;
        canvas.drawRect(rect, mPaint);
        mPaint.setColor(Color.parseColor("#ffffff"));
        canvas.drawText(text, baseX, baseY, mPaint);

////绘制base
//        canvas.drawLine(0, baseY, getWidth(), baseY, mPaint);
////绘制descent
//        mPaint.setColor(Color.RED);
//        canvas.drawLine(0, descent, getWidth(), descent, mPaint);
////绘制ascent
//        mPaint.setColor(Color.GREEN);
//        canvas.drawLine(0, ascent, getWidth(), ascent, mPaint);
////绘制bottom
//        mPaint.setColor(Color.BLACK);
//        canvas.drawLine(0, bottom, getWidth(), bottom, mPaint);
////绘制top
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0, top, getWidth(), top, mPaint);
    }

    private void drawText(Canvas canvas, Paint mPaint, float baseTop, float baseLeft, String text, String backgroundColor, boolean opacity) {
        float baseX = baseLeft;
        float baseY = baseTop;
//获取FontMetrics对象，可以获得top,bottom,ascent,descent
        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float top = baseY + metrics.top;
        float ascent = baseY + metrics.ascent;
        float descent = baseY + metrics.descent;
        float bottom = +baseY + metrics.bottom;

//        String text = "Android*java";
        canvas.drawText(text, baseX, baseY + topHeight + 15, mPaint);
        //计算矩形的宽高
        Rect rectf = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rectf);
        int w = rectf.width();


        Rect rect = new Rect();
        rect.left = (int) (baseX - 15);
        rect.top = (int) top + 15;
        rect.right = (int) (baseX + w + 15);
        rect.bottom = (int) bottom + 15;
        Paint paint = new Paint();
//        paint.setColor(Color.RED);
        if (!opacity) {
            paint.setColor(Color.parseColor(backgroundColor));
            canvas.drawRect(rect, paint);
        }
        canvas.drawText(text, baseX, baseY + 15, mPaint);
    }

    private void drawPath() {

    }

    /**
     * 异步加载图片后设置在指定的位置
     *
     * @param at
     */
    private void setAlramTypeBitmap(AlarmType at) {
        if (at.getAlarmImage() != null && !"".equals(at.getAlarmImage())) {//如果告警图片不为空，就把告警图片下载下来并存储到本地
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String imagePath = configBean.getIp() + "/";
            ImageLoader.getInstance().loadImage(imagePath + at.getAlarmImage(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    alarmBitmap = bitmap;
                    invalidate();
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    public void setBackgroundPicture() {
        backgroundPicture = null;
    }
}



