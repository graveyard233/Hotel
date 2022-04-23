package com.example.hotel.MyDraw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class VedioPlayingIcon extends View {

    private static final int MESSAGE_TYPE_EXECUTE = 1;

    private static final String TAG = "VedioPlayingIcon";

    private Paint paint;

    //跳动指针集合
    private List<Pointer> pointers;

    //跳动指针数量
    private int pointerNum = 3;

    private float basePointX;
    private float basePointY;

    //指针间隙 为指针宽度的一半
    private float pointerPadding;

    //指针宽度 基于此控件的宽度
    private float pointerWidth;

    //指针颜色
    private int pointerColor = Color.WHITE;

    //跳动速度  ms
    private int pointerSpeed = 35;

    /**
     * 指针同步率
     *
     * 合适的范围:0.5~0.65
     */
    private float sync = 0.575f;

    //循环计数器，用于变化sin值
    private float add = 0;

    public void setPointerColor(int pointerColor) {
        paint.setColor(pointerColor);
        paint.setAlpha(123);
    }

    /**
     * 设置指针速度
     * @param ms 速度
     * 注: 单位 毫秒，数值越大，指针跳动越慢
     */
    public void setPointerSpeed(int ms) {
        this.pointerSpeed = ms;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //一共三个指针，将宽度分成十份，一个指针占两份，指针间隔占一份
        pointerWidth = w / 5;
        paint.setStrokeWidth(pointerWidth);
    }

    public VedioPlayingIcon(Context context) {
        super(context);
    }

    public VedioPlayingIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TYPE_EXECUTE),pointerSpeed);
    }

    public VedioPlayingIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化指针与画笔
    private void init() {
        paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //假如没有赋予颜色，则默认白色
        if (pointerColor == -1)
            paint.setColor(pointerColor);
        //半透明画笔
        paint.setAlpha(123);
        paint.setStrokeCap(Paint.Cap.ROUND);
        pointers = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("TAG", "onLayout: " + getWidth());
        //获取逻辑原点，画布的左下角,要减去pointerWidth一半的距离
        basePointY = getHeight() - pointerWidth / 2 ;
        Random random = new Random();
        if (pointers != null){
            pointers.clear();
        }
        float j = 0.2f;
        //初始化指针高度
        for (int i = 0; i < pointerNum; i++) {
            Pointer pointer = new Pointer(
                    (float) ( j * (getHeight() - getPaddingBottom() - getPaddingTop())
                    )
            );
            pointers.add(pointer);
            j += 0.3f;
        }
        //间隔为柱子宽度的一半
        pointerPadding = pointerWidth / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        basePointX = (float) (0f + pointerPadding * 2);
        for (int i = 0; i < pointers.size(); i++) {
            canvas.drawLine(basePointX,
                    basePointY,
                    basePointX,
                    basePointY - pointers.get(i).getHeight() ,
                    paint);
            //加上padding 往右移动
            basePointX += (pointerPadding + pointerWidth);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDetachedFromWindow();
    }

    //处理handler发出来的指令 然后刷新view
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_TYPE_EXECUTE){
                try {
                    for (int j = 0; j < pointers.size(); j++) {
                        //改变指针高度
                        //利用正弦规律获取0到1，j需要乘同步率，尽量使指针在同一振幅中
                        float rate = (float) Math.abs(Math.sin(add + j * sync));
                        //有规律的改变高度
                        pointers.get(j).setHeight((basePointY - getPaddingTop()) * rate * 0.8f);
                    }
                    //更新布局
                    add+=0.1;
                    invalidate();
                    handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_TYPE_EXECUTE),pointerSpeed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

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
