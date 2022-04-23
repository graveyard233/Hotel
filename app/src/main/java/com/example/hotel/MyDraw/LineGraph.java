package com.example.hotel.MyDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.hotel.Bean.Order;
import com.example.hotel.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LineGraph extends View {
    //原点xy坐标
    private float basePointX;
    private float basePointY;

    //x y轴长度
    private float width_x_axis;
    private float height_y_axis;

    //x y轴坐标间隔
    private float x_pointer_padding;
    private float y_pointer_padding;

    //控件宽高
    private int view_width;
    private int view_height;

    //最大小值
    private int max = 100;
    private int min = 0;

    //自定义文字
    private String text_start = new Date().toString();
    private String text_end = "end";
    //文字style
    private int text_color = getResources().getColor(R.color.深竹月);
    private int text_size = 10;

    //日期宽度
    private int timeWidth;

    //订单数据
    private List<Order> orderList;

    //渐变阴影色
    private int[] shade_color;

    //坐标轴颜色
    private int axis_color = Color.BLACK;
    //应该是坐标轴一格的宽度
    private float axis_width = 3;
    //折现颜色
    private int line_color = getResources().getColor(R.color.深竹月);
    //背景颜色
    private int bg_color = getResources().getColor(R.color.月色白);

    //文字画笔
    private Paint paint_text;
    //坐标轴画笔
    private Paint paint_axis;
    //折线画笔
    private Paint paint_line;
    //折线路径
    private Path path;

    //渐变色画笔和路径
    private Paint paint_shader;
    private Path path_shader;

    //  10dp的间距
    private int margin_10;

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //拿到控件宽高
        view_width = w;
        view_height = h;
        text_size = w / 15;
    }

    public LineGraph(Context context) {
        super(context);
        initPaint();
    }

    public LineGraph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        orderList = new ArrayList<>();

        final float scale = context.getResources().getDisplayMetrics().density;
        margin_10 = (int) (10f + scale + 0.5f);

        shade_color = new int[]{
                Color.argb(100, 255, 86, 86),
                Color.argb(15, 255, 86, 86),
                Color.argb(0, 255, 86, 86)
        };
        initPaint();
    }

    public LineGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        //坐标轴
        paint_axis = new Paint();
        paint_axis.setColor(axis_color);
        paint_axis.setStrokeWidth(axis_width);
        //文字
        paint_text = new Paint();
        paint_text.setStyle(Paint.Style.FILL);
        paint_text.setAntiAlias(true);
        paint_text.setStrokeWidth(text_size);
        paint_text.setColor(text_color);
        paint_text.setTextAlign(Paint.Align.LEFT);

        //折线
        paint_line = new Paint();
        paint_line.setStyle(Paint.Style.STROKE);
        paint_line.setAntiAlias(true);
        //和坐标轴一致
        paint_line.setStrokeWidth(axis_width);
        paint_line.setColor(line_color);

        //折现路径
        path = new Path();
        path_shader = new Path();

        //阴影画笔
        paint_shader = new Paint();
        paint_shader.setAntiAlias(true);
        paint_shader.setStrokeWidth(2f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed){
            timeWidth = (int) paint_text.measureText(text_start);
            basePointX = margin_10;
            basePointY = (view_height - text_size - margin_10);
//            setBackgroundColor(bg_color);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        x_pointer_padding = (view_width - basePointX) / (orderList.size() - 1);
        y_pointer_padding = (max - min) / (basePointY - margin_10);
        drawAxis(canvas);
        drawLine(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        canvas.drawText("100",basePointX + 6,2 * margin_10,paint_text);
        canvas.drawText("0",basePointX + 6,basePointY - 6,paint_text);
        canvas.drawText("50",basePointX + 6,(basePointY + margin_10) / 2,paint_text);

        canvas.drawText(text_start,basePointX,view_height - margin_10,paint_text);
        canvas.drawText(text_end,view_width - timeWidth - margin_10,view_height - margin_10,paint_text);
    }

    private void drawAxis(Canvas canvas) {
        //x
        canvas.drawLine(basePointX,basePointY,view_width - margin_10,basePointY,paint_axis);
        //x mid
        canvas.drawLine(basePointX,basePointY/2,view_width - margin_10,basePointY/2,paint_axis);
        //x top
        canvas.drawLine(basePointX,margin_10,view_width - margin_10,margin_10,paint_axis);
        //y
        canvas.drawLine(basePointX,basePointY,basePointX,margin_10,paint_axis);
        //y right
        canvas.drawLine(view_width - margin_10,margin_10,view_width - margin_10,basePointY,paint_axis);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < orderList.size(); i++) {
            float x= i * x_pointer_padding + basePointX + axis_width;
            if (i == 0){
                path_shader.moveTo(x, (float) (basePointY - (orderList.get(i).getPrice() - min) / y_pointer_padding));
                path.moveTo(x,(float) (basePointY - (orderList.get(i).getPrice() - min) / y_pointer_padding));
            } else {
                path.lineTo(x - margin_10 - axis_width,basePointY - (float) (basePointY - (orderList.get(i).getPrice() - min) / y_pointer_padding));
                path_shader.lineTo(x - margin_10 - axis_width,basePointY - (float) (basePointY - (orderList.get(i).getPrice() - min) / y_pointer_padding));
                if ( i == orderList.size() - 1){
                    path_shader.lineTo(x - margin_10 - axis_width,basePointY);
                    path_shader.lineTo(basePointX,basePointY);
                    path_shader.close();
                }
            }
        }



        //渐变
        Shader shader = new LinearGradient(0,0,0,getHeight(),shade_color,null,Shader.TileMode.CLAMP);
        paint_shader.setShader(shader);

        //渐变阴影
        canvas.drawPath(path_shader,paint_shader);
        canvas.drawPath(path,paint_line);
    }
}
