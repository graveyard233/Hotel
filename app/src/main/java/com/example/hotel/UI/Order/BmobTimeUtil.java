package com.example.hotel.UI.Order;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.datatype.BmobDate;

public class BmobTimeUtil {

    public static Date[] getAllDateThisMonth(String year,String month){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        int maxDate = 0;
        Date first = null;



        try {
            Calendar cal = Calendar.getInstance();
            first = sdf.parse(year + month);
            cal.setTime(first);
            maxDate = cal.getMaximum(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date[] dates =new Date[maxDate];
        for (int i = 1; i <=  maxDate; i++) {
            dates[i - 1] = new Date(first.getTime());
            first.setDate(first.getDate() + 1);
        }



        return dates;
    }

    public static Date getDateAfterMonth(int how_many){
        Calendar next = Calendar.getInstance();
        next.add(Calendar.MONTH,how_many);

        return  next.getTime();
    }

    public static Date StringToDate(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat
                ("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = simpleDateFormat.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String DateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        String s = format.format(date);
        return s;
    }

    public static List<Date> getDaysBetween(String startTime, String endTime){
        List<Date> days = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE,0);// 不知道为什么我不需要 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(tempStart.getTime());
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


}
