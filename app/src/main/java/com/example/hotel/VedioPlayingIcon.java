package com.example.hotel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class VedioPlayingIcon extends View {

    private Paint paint;

    //跳动指针集合
    private List<Pointer> pointers;

    //跳动指针数量
    private int pointerNum = 3;

    private float basePointX;
    private float basePointY;

    //指针间隙
    private float pointerPadding;

    //指针宽度
    private float pointerWidth = 30;

    //指针颜色
    private int pointerColor = Color.WHITE;


    //开始 停止
    private boolean isPlaying;

    private Thread myThread;

    //跳动速度
    private int pointerSpeed = 35;

    public VedioPlayingIcon(Context context) {
        super(context);
        init();
    }



    public VedioPlayingIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VedioPlayingIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化指针与画笔
    private void init() {
        paint = new Paint();

        //抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(pointerColor);
        //半透明画笔
        paint.setAlpha(123);

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(pointerWidth);

        pointers = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取逻辑原点，画布的左下角,要减去padding bottom的距离
        basePointY = getHeight() - getPaddingBottom();
        Random random = new Random();
        if (pointers != null){
            pointers.clear();
        }
        float j = 0.1f;
        for (int i = 0; i < pointerNum; i++) {

            Pointer pointer = new Pointer(
                    (float) ( j * (getHeight() - getPaddingBottom() - getPaddingTop())
                    )
            );
            pointers.add(pointer);
            j += 0.4f;
        }

        //指针之间的间隔
        pointerPadding = (getWidth() - getPaddingLeft() - getPaddingRight() - pointerWidth * pointerNum)
                / (pointerNum - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        basePointX = 0f + getPaddingLeft();
        for (int i = 0; i < pointers.size(); i++) {
//            canvas.drawRect(basePointX,
//                    basePointY - pointers.get(i).getHeight(),
//                    basePointX + pointerWidth,
//                    basePointY,
//                    paint);
            canvas.drawLine(basePointX,
                    basePointY,
                    basePointX,
                    basePointY - pointers.get(i).getHeight(),
                    paint);
            //加上padding 往右移动
            basePointX += (pointerPadding + pointerWidth);
        }
    }

    public void start(){
        if (!isPlaying){
            if (myThread == null){
                myThread = new Thread(new MyRunnable());
                myThread.start();
            }
            isPlaying = true;
        }
    }

    public void stop(){
        isPlaying = false;
        //刷新view
        invalidate();
    }

    //处理子线程发出来的指令 然后刷新view
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    public class MyRunnable implements Runnable{

        @Override
        public void run() {
            for (float i = 0; i < Integer.MAX_VALUE; ) {
                try {
                    for (int j = 0; j < pointers.size(); j++) {
                        //改变指针高度
                        //利用正弦规律获取0到1
                        float rate = (float) Math.abs(Math.cos(i + j));
                        //有规律的改变高度
                        pointers.get(j).setHeight((basePointY - getPaddingTop()) * rate);
                    }
                    Thread.sleep(pointerSpeed);
                    if (isPlaying){
                        //更新布局
                        handler.sendEmptyMessage(0);
                        i+=0.1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Pointer {
        private float height;

        public Pointer(float height){
            this.height = height;
        }

        public float getHeight() {
            return height;
        }

        public void setHeight(float height) {
            this.height = height;
        }
    }
}
