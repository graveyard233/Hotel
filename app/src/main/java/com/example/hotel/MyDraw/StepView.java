package com.example.hotel.MyDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StepView extends View {

    private static final int MESSAGE_ADD = 1;
    private static final int MESSAGE_SUBTRACT = 2;

    private float basePointX;
    private float basePointY;

    private Paint paint_startLine;
    private float startLine_width = 25;
    private int startLine_color = Color.parseColor("#ab96c5");

    private Paint paint_passLine;
    private float passLine_width = 30;
    private int passLine_color = Color.parseColor("#d15d5e");

    private Paint paint_whitePoint;
    private float whitePoint_r = 35;
    private int whitePoint_color = Color.parseColor("#ab96c5");

    private Paint paint_passPoint;
    private float passPoint_r = 35;
    private int passPoint_color = Color.parseColor("#d15d5e");

    private Paint paint_smallPoint;
    private float smallPoint_r;
    private int smallPoint_color = Color.WHITE;

    private Paint paint_breathePoint;
    private float breathePoint_r;
    private int breathePoint_color = Color.parseColor("#d15d5e");

    private float line_chang;

    private float view_height;
    private float view_width;

    private int step ;
    final int[] list_step = {1,4,7};

    RectF rectF = new RectF();

    private int alpha = 125;

    private int changeSpeed = 10;

    private RectF rectF1;
    private RectF rectF2;
    private RectF rectF3;
    private List<RectF> list_rectF = new ArrayList<>();

    public void setStep(int step) {
        this.step = step;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        view_width = w;
        view_height = h;
        line_chang = w / 4 * 3;
    }

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_ADD),changeSpeed);
    }

    private void init(){
        paint_startLine = new Paint();
        paint_startLine.setAntiAlias(true);
        paint_startLine.setColor(startLine_color);
        paint_startLine.setStrokeWidth(startLine_width);

        paint_passLine = new Paint();
        paint_passLine.setAntiAlias(true);
        paint_passLine.setColor(passLine_color);
        paint_passLine.setStrokeWidth(passLine_width);

        paint_passPoint = new Paint();
        paint_passPoint.setAntiAlias(true);
        paint_passPoint.setStrokeWidth(passPoint_r);
        paint_passPoint.setColor(passPoint_color);


        paint_whitePoint = new Paint();
        paint_whitePoint.setStrokeWidth(whitePoint_r);
        paint_whitePoint.setAntiAlias(true);
//        paint_whitePoint.setStyle(Paint.Style.STROKE);
        paint_whitePoint.setColor(whitePoint_color);

        paint_smallPoint = new Paint();
        smallPoint_r = whitePoint_r /  2;
        paint_smallPoint.setStrokeWidth(whitePoint_r /  2);
        paint_smallPoint.setAntiAlias(true);
        paint_smallPoint.setColor(smallPoint_color);

        paint_breathePoint = new Paint();
        breathePoint_r = passPoint_r * 1.5f;
        paint_breathePoint.setColor(breathePoint_color);
        paint_breathePoint.setAntiAlias(true);
        paint_breathePoint.setStrokeWidth(breathePoint_r);

        rectF1 = new RectF();
        rectF2 = new RectF();
        rectF3 = new RectF();
        list_rectF.add(rectF1);
        list_rectF.add(rectF2);
        list_rectF.add(rectF3);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        basePointX = getWidth() / 8;
        basePointY = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStartLine(canvas);
        drawPassLine(canvas);
        drawWhitePoint(canvas);
        drawPassPoint(canvas);
        drawBreathePoint(canvas);
    }

    protected void drawBreathePoint(Canvas canvas) {
        paint_breathePoint.setAlpha(alpha);
//        Log.i("alpha", "drawBreathePoint: " + alpha);
        canvas.drawCircle(basePointX * list_step[step],
                basePointY,
                breathePoint_r,
                paint_breathePoint);
    }

    protected void drawWhitePoint(Canvas canvas) {
        float rectF_temp = 1.5f;
        for (int i = 0 ; i <= 2 ; i++) {
            canvas.drawCircle(basePointX * list_step[i],
                    basePointY,
                    whitePoint_r,
                    paint_whitePoint);
            canvas.drawCircle(basePointX * list_step[i],
                    basePointY,
                    smallPoint_r,
                    paint_smallPoint);
            list_rectF.get(i).set(basePointX * list_step[i] - breathePoint_r * rectF_temp,
                    basePointY + breathePoint_r * rectF_temp,
                    basePointX * list_step[i] + breathePoint_r * rectF_temp,
                    basePointY - breathePoint_r * rectF_temp);
//            canvas.drawRect(list_rectF.get(i),paint_startLine);
        }
    }

    protected void drawPassPoint(Canvas canvas) {
        for (int i = 0; i <= step; i++) {
            canvas.drawCircle(basePointX * list_step[i],
                    basePointY,
                    passPoint_r,
                    paint_passPoint);

        }

    }

    protected void drawPassLine(Canvas canvas) {
        canvas.drawLine(basePointX,
                basePointY,
                basePointX * list_step[step],
                basePointY,
                paint_passLine);
    }

    protected void drawStartLine(Canvas canvas){
        canvas.drawLine(basePointX,
                basePointY,
                basePointX * 7,
                basePointY,
                paint_startLine);
    }

    public void setStepDoNotComplete(int which){
        // TODO: 2022/4/27  
    }

    @Override
    protected void onDetachedFromWindow() {
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDetachedFromWindow();
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MESSAGE_ADD){
                try {
                    alpha+=2;
                    invalidate();
                    if (alpha > 190){
                        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_SUBTRACT),changeSpeed);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_ADD),changeSpeed);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (msg.what == MESSAGE_SUBTRACT){
                try {
                    alpha-=2;
                    invalidate();
                    if (alpha <= 10){
                        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_ADD),changeSpeed);
                    } else {
                        handler.sendMessageDelayed(handler.obtainMessage(MESSAGE_SUBTRACT),changeSpeed);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x =  event.getX();
                float y =  event.getY();
                Log.i("down", "onTouchEvent: " + x + "," + y);
                if (x > rectF1.left && x < rectF1.right && y < rectF1.top && y > rectF1.bottom){
                    step = 0;
                }
                if (x > rectF2.left && x < rectF2.right && y < rectF2.top && y > rectF2.bottom){
                    step = 1;
                }
                if (x > rectF3.left && x < rectF3.right && y < rectF3.top && y > rectF3.bottom){
                    step = 2;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
