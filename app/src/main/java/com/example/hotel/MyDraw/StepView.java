package com.example.hotel.MyDraw;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class StepView extends View {

    private float basePointX;
    private float basePointY;

    private Paint paint_startLine;
    private float startLine_width;
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


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        line_chang = w / 4 * 3;
    }

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
