package com.example.hotel.MyDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class StepView extends View {

    private float basePointX;
    private float basePointY;

    private Paint paint_startLine;
    private float startLine_width = 5;
    private int startLine_color;

    private Paint paint_passLine;
    private float passLine_width;
    private int passLine_color;

    private Paint paint_whitePoint;
    private float whitePoint_r;
    private int whitePoint_color;

    private Paint paint_passPoint;
    private float passPoint_r;
    private int passPoint_color;

    private float line_chang;

    private float view_height;
    private float view_width;


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
    }

    private void init(){
        paint_startLine = new Paint();
        paint_startLine.setAntiAlias(true);
        paint_startLine.setColor(Color.RED);
        paint_startLine.setStrokeWidth(startLine_width);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        basePointX = getWidth() / 8;
        basePointY = getHeight() /2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(basePointX,
                basePointY,
                basePointX * 7,
                basePointY,
                paint_startLine);
    }
}
