package com.example.hotel.UI.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hotel.Bean.Order;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.loper7.date_time_picker.DateTimeConfig;
import com.loper7.date_time_picker.dialog.CardDatePickerDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class Activity_book_the_room extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "Activity_book_the_room";
    private TextView choiceStartTime;
    private TextView choiceEndTime;

    private TextView starTime_text;
    private TextView endTime_text;

    private Date startTime;
    private Date endTime;

    private TravellerAndIDcardView tv_1;
    private TravellerAndIDcardView tv_2;
    private TravellerAndIDcardView tv_3;
    private TravellerAndIDcardView tv_4;
    private TravellerAndIDcardView tv_5;


    private Spinner spinner;

    Order order = new Order();

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

    @SuppressLint("ResourceType")
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
                        .setMinTime(new Date().getTime())
                        .setMaxTime(BmobTimeUtil.getDateAfterMonth(2).getTime())
                        .setThemeColor(getResources().getColor(R.color.深竹月_浅色))
                        .setDisplayType(displayList)
                        .setWrapSelectorWheel(false)
                        .setOnChoose("确定",aLong -> {
                            startTime = new Date(aLong);
                            Log.i(TAG, "onClick: start_time:" + startTime);
                            if (order.getEndTime() == null){//还没选退房时间
                                starTime_text.setTextColor(getResources().getColor(R.color.time_show));
                                starTime_text.setText(BmobTimeUtil.DateToString(startTime));
                                order.setStartTime(new BmobDate(startTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//小于退房时间
                                    starTime_text.setTextColor(getResources().getColor(R.color.time_show));
                                    starTime_text.setText(BmobTimeUtil.DateToString(startTime));
                                    order.setStartTime(new BmobDate(startTime));
                                }
                                else {//大于退房时间
                                    Toast.makeText(this,"入住时间大于退房时间",Toast.LENGTH_SHORT).show();
                                    return null;
                                }
                            }
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
                        .setMinTime(new Date().getTime())
                        .setMaxTime(BmobTimeUtil.getDateAfterMonth(2).getTime())
                        .setThemeColor(getResources().getColor(R.color.深竹月_浅色))
                        .setDisplayType(displayList)
                        .setWrapSelectorWheel(false)
                        .setOnChoose("确定",aLong -> {
                            endTime = new Date(aLong);
                            Log.i(TAG, "onClick: end_time:" + endTime );
                            if (order.getStartTime() == null){//还没选入住时间
                                endTime_text.setTextColor(getResources().getColor(R.color.time_show));
                                endTime_text.setText(BmobTimeUtil.DateToString(startTime));
                                order.setEndTime(new BmobDate(endTime));
                                return null;
                            } else {
                                //已经选择了退房时间
                                if (startTime.getTime() < endTime.getTime()){//大于入住时间
                                    endTime_text.setTextColor(getResources().getColor(R.color.time_show));
                                    endTime_text.setText(BmobTimeUtil.DateToString(startTime));
                                    order.setEndTime(new BmobDate(endTime));
                                }
                                else {//小于入住时间
                                    Toast.makeText(this,"退房时间小于入住时间",Toast.LENGTH_SHORT).show();
                                    return null;
                                }
                            }
                            return null;
                        }).build().show();
                break;
            }

            default:break;
        }
    }


    //显示输入多少人的信息
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