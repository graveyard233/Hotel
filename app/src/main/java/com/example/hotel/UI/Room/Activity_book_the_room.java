package com.example.hotel.UI.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Spinner;
import android.widget.TextView;


import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity_book_the_room extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Activity_book_the_room";
    private TextView choiceStartTime;
    private TextView choiceEndTime;

    private TextView starTime_text;
    private TextView endTime_text;

    private TravellerAndIDcardView tv_1;
    private TravellerAndIDcardView tv_2;
    private TravellerAndIDcardView tv_3;
    private TravellerAndIDcardView tv_4;
    private TravellerAndIDcardView tv_5;


    private Spinner spinner;

    @Override
    protected void initViews() {
        choiceStartTime = findViewById(R.id.bookTheRoom_choiceStart);
        choiceEndTime = findViewById(R.id.bookTheRoom_choiceEnd);
        spinner = findViewById(R.id.book_the_room_spinner);
        starTime_text = findViewById(R.id.book_the_room_start_time);
        endTime_text = findViewById(R.id.book_the_room_end_time);

        choiceStartTime.setOnClickListener(this);
        choiceEndTime.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_the_room;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bookTheRoom_choiceStart:{
                //设置开始时间显示
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
                            Date startTime = new Date(aLong);
                            Log.i(TAG, "onClick: start_time:" + startTime);
                            starTime_text.setText(BmobTimeUtil.DateToString(startTime) + "时");
                            return null;
                        }).build().show();
                break;
            }

            case R.id.bookTheRoom_choiceEnd:{
                //设置结束时间显示
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
                            Date endTime = new Date(aLong);
                            Log.i(TAG, "onClick: end_time:" + endTime );
                            endTime_text.setText(BmobTimeUtil.DateToString(endTime) + "时");
                            return null;
                        }).build().show();
                break;
            }

            default:break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        tv_1 = findViewById(R.id.traveller_info_1);
        tv_2 = findViewById(R.id.traveller_info_2);
        tv_3 = findViewById(R.id.traveller_info_3);
        tv_4 = findViewById(R.id.traveller_info_4);
        tv_5 = findViewById(R.id.traveller_info_5);
        switch (i){
            case 0:{
                tv_1.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.GONE);
                tv_3.setVisibility(View.GONE);
                tv_4.setVisibility(View.GONE);
                tv_5.setVisibility(View.GONE);
                break;
            }
            case 1:{
                tv_1.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.VISIBLE);
                tv_3.setVisibility(View.GONE);
                tv_4.setVisibility(View.GONE);
                tv_5.setVisibility(View.GONE);
                break;
            }
            case 2:{
                tv_1.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.VISIBLE);
                tv_3.setVisibility(View.VISIBLE);
                tv_4.setVisibility(View.GONE);
                tv_5.setVisibility(View.GONE);
                break;
            }
            case 3:{
                tv_1.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.VISIBLE);
                tv_3.setVisibility(View.VISIBLE);
                tv_4.setVisibility(View.VISIBLE);
                tv_5.setVisibility(View.GONE);
                break;
            }
            case 4:{
                tv_1.setVisibility(View.VISIBLE);
                tv_2.setVisibility(View.VISIBLE);
                tv_3.setVisibility(View.VISIBLE);
                tv_4.setVisibility(View.VISIBLE);
                tv_5.setVisibility(View.VISIBLE);
                break;
            }
            default:break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}