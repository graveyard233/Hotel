package com.example.hotel.UI.Room;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import com.example.hotel.R;
import com.example.hotel.UI.Order.OrderModel;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleDecorator implements CalendarCellDecorator {

    public volatile static SampleDecorator mInstance;

    List<Date> times = new ArrayList<>();
    List<String> slist = new ArrayList<>();

    public void setTimes(List<Date> times) {
        this.times = times;
    }

    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public SampleDecorator(List<Date> times){
        this.times = times;
    }

    private SampleDecorator(){ }

    public static SampleDecorator get(){
        if (mInstance == null) {
            Log.i("SampleDecorator get()", "SampleDecorator没有单例，创建一个");
            synchronized (SampleDecorator.class) {
                if (mInstance == null) {
                    mInstance = new SampleDecorator();
                }
            }
        }
        else {
            Log.i("SampleDecorator get()", "已经有一个SampleDecorator了");
        }
        return mInstance;
    }


    @Override
    public void decorate(CalendarCellView cellView, Date date) {

        String temp1 = sdf.format(date);


//        String temp2 = sdf.format(times.get(0));
        if (times.size() > 0){
            for (int i = 0; i < times.size(); i++) {
                String temp2 = sdf.format(times.get(i));
                slist.add(temp2);
            }

            if (slist.contains(temp1)){
                cellView.setBackgroundColor(Color.BLACK);
            }else {
                if (cellView.isSelectable()){
                    if (cellView.isSelected()){
                        cellView.setBackgroundColor(Color.parseColor("#2578b5"));
                    }
                    else {
                        cellView.setBackgroundColor(Color.parseColor("#ebe1b2"));
                    }
                } else {//如果为不可选时间则直接设置日期背景
                    cellView.setBackgroundColor(Color.parseColor("#e1dbcd"));
                }
            }
//            if (temp1.equals(temp2)){
//                Log.i("right", "date = date");
//                cellView.setBackgroundColor(Color.BLACK);
//            }
//            else {
//                if (cellView.isSelectable()){
//                    if (cellView.isSelected()){
//                        cellView.setBackgroundColor(Color.parseColor("#2578b5"));
//                    }
//                    else {
//                        cellView.setBackgroundColor(Color.parseColor("#ebe1b2"));
//                    }
//                } else {//如果为不可选时间则直接设置日期背景
//                    cellView.setBackgroundColor(Color.parseColor("#e1dbcd"));
//                }
//            }
        }



        if (cellView.isToday()){
            Log.i("TAG", "decorate: " + date);
            System.out.println("temp: " + temp1);
        }


    }
}
