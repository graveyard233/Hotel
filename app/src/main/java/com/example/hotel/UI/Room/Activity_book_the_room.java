package com.example.hotel.UI.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class Activity_book_the_room extends BaseActivity implements View.OnClickListener {

    private TextView choiceStartTime;
    private TextView choiceEndTime;

    @Override
    protected void initViews() {
        choiceStartTime = findViewById(R.id.bookTheRoom_choiceStart);
        choiceStartTime.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_the_room;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bookTheRoom_choiceStart:{
                //设置时间显示
                List<Integer> displayList = new ArrayList<>();
                displayList.add(DateTimeConfig.YEAR);
                displayList.add(DateTimeConfig.MONTH);
                displayList.add(DateTimeConfig.DAY);
                displayList.add(DateTimeConfig.HOUR);

                new CardDatePickerDialog.Builder(this)
                        .setTitle("title")
                        .setThemeColor(getResources().getColor(R.color.深竹月_浅色))
                        .setDisplayType(displayList)
                        .setOnChoose("确定",aLong -> {
                            return null;
                        }).build().show();
                break;
            }
            default:break;
        }
    }
}