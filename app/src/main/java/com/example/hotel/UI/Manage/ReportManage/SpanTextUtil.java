package com.example.hotel.UI.Manage.ReportManage;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

public class SpanTextUtil {
    public static SpannableString changeText(String text, int color){
        final SpannableString string = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        string.setSpan(colorSpan,0,string.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return string;
    }
}
