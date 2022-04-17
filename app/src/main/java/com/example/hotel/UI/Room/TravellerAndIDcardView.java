package com.example.hotel.UI.Room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.hotel.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TravellerAndIDcardView extends LinearLayout {
    private TextInputLayout travellerName;
    private TextInputLayout IDcard;

    public TravellerAndIDcardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.traveller_and_idcard_view,this);

        travellerName = findViewById(R.id.book_the_room_username_layout);

        IDcard = findViewById(R.id.book_the_room_idcard_layout);
    }

    public String getTravellerName(){
        return travellerName.getEditText().getText().toString();
    }

    public void SetTravellerName(String s){
        travellerName.getEditText().setText(s);
    }

    public String getIDcard(){
        return IDcard.getEditText().getText().toString();
    }

    public void SetIDcard(String s){
        IDcard.getEditText().setText(s);
    }

    public Boolean isHasEmpty(){//两个之中有一个没写就返回真
        if (travellerName.getEditText().getText().toString().equals("")
            || IDcard.getEditText().getText().toString().equals(""))
            return true;
        else return false;
    }

    @Override
    public String toString() {
        return "TravellerAndIDcardView{" +
                "travellerName=" + travellerName.getEditText().getText().toString() +
                ", IDcard=" + IDcard.getEditText().getText().toString() +
                '}';
    }
}
