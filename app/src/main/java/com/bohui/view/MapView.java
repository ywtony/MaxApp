package com.bohui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.bohui.utils.DevLog;

/**
 * 绘制地图的View
 */
public class MapView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder = null;
    private DrawThread drawThread;

    public MapView(Context context) {
        super(context);
//        init();
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);//保持屏幕长亮
        drawThread = new DrawThread(surfaceHolder);
    }

    //以下是surfaceholder.callback的回调函数
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawThread.setRun(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        drawThread.setRun(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void Draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawRGB(0, 0, 0); // 把画布填充为黑色
        canvas.drawCircle(50, 50, 10, paint); // 画一个圆
    }

    public class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private boolean isRunning = false;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRun(boolean isRunning) {
            this.isRunning = isRunning;
        }

        @Override
        public void run() {
            super.run();
            DevLog.e("执行了run方法");
            int count = 0;
            while (true) {
                Canvas canvas = null;
                synchronized (surfaceHolder) {
                    try {
                        canvas = surfaceHolder.lockCanvas();//创建画布
                        canvas.drawColor(Color.WHITE);
                        Paint p = new Paint();
                        p.setColor(Color.BLACK);
                        DevLog.e("正在绘制view");
                        Rect r = new Rect(100, 50, 300, 250);
                        canvas.drawRect(r, p);
                        canvas.drawText("这是第" + (count++) + "秒", 100, 310, p);
//                        draw(canvas);
                        Thread.sleep(1000);// 睡眠时间为1秒
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != canvas) {
                            surfaceHolder.unlockCanvasAndPost(canvas);//解锁画布
                        }
                    }
                }
            }
        }
    }
}
