package com.example.hotel.UI.Room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.hotel.R;

public class TravellerAndIDcardView extends LinearLayout {
    public TravellerAndIDcardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.traveller_and_idcard_view,this);
    }
}
