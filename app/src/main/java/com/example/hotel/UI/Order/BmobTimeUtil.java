package com.example.hotel.UI.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
