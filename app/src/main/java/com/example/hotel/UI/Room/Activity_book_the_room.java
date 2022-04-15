package com.example.hotel.UI.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hotel.Bean.Order;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.google.gson.Gson;
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
    private List<Date> timeList;
    private OrderPresenter orderPresenter;

    private TravellerAndIDcardView tv_1;
    private TravellerAndIDcardView tv_2;
    private TravellerAndIDcardView tv_3;
    private TravellerAndIDcardView tv_4;
    private TravellerAndIDcardView tv_5;

    private Spinner spinner;

    private Button button_ok;
    private Button button_cancel;

    private TextView textView;

    Gson gson = new Gson();
    Order order = new Order();

    @Override
    protected void initViews() {
        choiceStartTime = findViewById(R.id.bookTheRoom_choiceStart);
        choiceEndTime = findViewById(R.id.bookTheRoom_choiceEnd);
        spinner = findViewById(R.id.book_the_room_spinner);
        starTime_text = findViewById(R.id.book_the_room_start_time);
        endTime_text = findViewById(R.id.book_the_room_end_time);
        button_ok = findViewById(R.id.book_ok);
        button_cancel = findViewById(R.id.book_cancel);
        textView = findViewById(R.id.user);

        choiceStartTime.setOnClickListener(this);
        choiceEndTime.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        button_ok.setOnClickListener(this);
        button_cancel.setOnClickListener(this);

//        String timeListJson = getIntent().getStringExtra("timeList");
//        if (timeListJson != null){
//            List<Date> timeList = gson.fromJson(timeListJson,ArrayList.class);
//            Log.i(TAG, "initViews: " + timeList.size());
//        }


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
                //访问数据库获取可选时间
                orderPresenter.getOrderModel(new OrderViewInterface() {
                    @Override
                    public void getAllOrdersSucceed(List<Order> orders) {
                    }

                    @Override
                    public void getAllOrderError() {
                    }

                    @Override
                    public void getOrderById(List<Order> orders) {
                        //拿到已被预定的时间列表
                        timeList = BmobTimeUtil.getDaysBetween(orders.get(0).getStartTime().getDate(),
                                orders.get(0).getEndTime().getDate());

                    }

                    @Override
                    public void getOrderByIdError() {

                    }

                    @Override
                    public void getOrderById(List<Order> orders, int i) {

                    }
                });





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

            case R.id.book_ok:{
                Intent intent = new Intent();
                intent.putExtra("data_return","book_ok!");
                setResult(RESULT_OK,intent);

                finish();
                break;
            }
            case R.id.book_cancel:{
                Intent intent = new Intent();
                intent.putExtra("data_return","book_cancel!");
                setResult(RESULT_CANCELED,intent);

                finish();
                break;
            }

            case R.id.user:{
                orderPresenter.getOrderModel(0,new OrderViewInterface() {
                    @Override
                    public void getAllOrdersSucceed(List<Order> orders) {

                    }

                    @Override
                    public void getAllOrderError() {

                    }

                    @Override
                    public void getOrderById(List<Order> orders) {

                    }

                    @Override
                    public void getOrderByIdError() {

                    }

                    @Override
                    public void getOrderById(List<Order> orders, int i) {

                    }
                });
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