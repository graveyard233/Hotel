package com.example.hotel.UI.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BmobTimeUtil {
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
