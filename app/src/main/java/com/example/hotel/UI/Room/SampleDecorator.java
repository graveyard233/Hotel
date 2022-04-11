package com.example.hotel.UI.Room;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;

import com.example.hotel.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;

import java.util.Date;
import java.util.List;

public class SampleDecorator implements CalendarCellDecorator {

//    List<>


    @Override
    public void decorate(CalendarCellView cellView, Date date) {

        if (cellView.isToday()){
            Log.i("TAG", "decorate: " + date.toString());
        }

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
}
