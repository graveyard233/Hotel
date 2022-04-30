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

    public static Boolean checkTime(List<Date> list_choice,List<Date> list_ordered){
        List<String> slist = new ArrayList<>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (list_ordered.size() <= 0){
            return true;
        } else {
            for (int i = 0; i < list_ordered.size(); i++) {
                String temp1 = sdf.format(list_ordered.get(i));
                slist.add(temp1);
            }
        }

        for (int i = 0; i < list_choice.size(); i++) {
            String temp2 = sdf.format(list_choice.get(i));
            if (slist.contains(temp2)){
                return false;
            }
        }
        return true;
    }

    //今天在这个list中的化返回true 否则返回false
    public static Boolean checkTimeTodayIsInDateList(List<Date> list_ordered){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        List<String> slist = new ArrayList<>();
        if (list_ordered.size() <= 0){
            return false;
        } else {
            for (int i = 0; i < list_ordered.size(); i++) {
                String temp1 = sdf.format(list_ordered.get(i));
                slist.add(temp1);
            }
        }

        if (slist.contains(today)){
            return true;
        } else {
            return false;
        }
    }


}
