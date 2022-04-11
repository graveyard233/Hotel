package com.example.hotel.UI.Room;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//implements View.OnClickListener
public class RoomDetailActivity extends BaseActivity  {


    private static final String TAG = "RoomDetailActivity";

    private OrderPresenter orderPresenter = new OrderPresenter();

    private SampleDecorator decorator = SampleDecorator.get();

    @Override
    protected void initViews() {

//        Toolbar toolbar = findViewById(R.id.room_detail_toolBar);
//        toolbar.setNavigationOnClickListener(this);
        String roomJson = getIntent().getStringExtra("roomJson");
        Gson gson= new Gson();
        Room this_room = gson.fromJson(roomJson,Room.class);
        Log.i(TAG, "initViews: " + this_room.toString());

        ImageView img = findViewById(R.id.room_detail_header_img);
        Glide.with(getApplicationContext())
                .load(this_room.getImgUrl())
                .placeholder(R.drawable.ic_bottom_room_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(img);

        TextView title = findViewById(R.id.room_detail_header_title);
        title.setText(this_room.getType());

        TextView price = findViewById(R.id.room_detail_header_price);
        double priceNow = this_room.getPrice() * this_room.getDiscount();
        price.setText(String.valueOf(priceNow) + "元");

        TextView isBusy = findViewById(R.id.room_detail_header_isBusy);
        if (this_room.getIsBusy().equals("空闲"))
            isBusy.setText("仍有空房");
        else
            isBusy.setText("已满，这天的生意真是火呢");

        TextView discount = findViewById(R.id.room_detail_header_discount);
        discount.append(this_room.getDiscount().toString());

        LinearLayout timeLinearLayout = findViewById(R.id.select_time_Linear);
        TextView et_date = findViewById(R.id.et_date);



        orderPresenter.getOrderModel(new OrderViewInterface() {
            @Override
            public void getAllOrdersSucceed(List<Order> orders) {
                List<Date> timeList = new ArrayList<>();
                Log.i(TAG, "getAllOrdersSucceed: " + orders.get(0).toString());
                System.out.println(orders.get(0).getStartTime().getDate() + "!");
                System.out.println("date:" + BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
                timeList.add(BmobTimeUtil.StringToDate(orders.get(0).getStartTime().getDate()));
                //数据准备好了再能点击,因为要把date塞进去
                timeLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        decorator.setTimes(timeList);
                        CalendarDialogUtil.showChooseDateDialog(decorator,
                                RoomDetailActivity.this,
                                "请选择日期",
                                "确定", "取消",
                                new CalendarDialog.ClickCallBack() {
                            @Override
                            public void onOk(CalendarDialog dlg) {
                                dlg.dismissDlg();
                            }
                            @Override
                            public void onCancel(CalendarDialog dlg) {
                                dlg.dismissDlg();
                            }
                        },et_date);
                    }
                });
            }

            @Override
            public void getOrderError() {
                Log.i(TAG, "getOrderError: ");
            }
        });


        //初始化日历
//        Calendar nextYear = Calendar.getInstance();
//        nextYear.add(Calendar.YEAR,1);

//        CalendarPickerView calendarPickerView = findViewById(R.id.myCalendar);
//        Date today = new Date();
//        calendarPickerView.init(today,nextYear.getTime())
//                .withSelectedDate(today)
//                .inMode(CalendarPickerView.SelectionMode.RANGE);
//        摘记
//        https://www.jianshu.com/p/252b355be7ca

//        https://blog.csdn.net/Small_Lee/article/details/51114600
//        https://blog.csdn.net/liubo253/article/details/54927574

//        https://blog.csdn.net/xinluqishi123/article/details/70494854

//        https://blog.csdn.net/huanghaibin_dev/article/details/79040147
//        toolbar.setTitle(this_room.getType());

//        toolbar.setTitle(roomId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_detail;
    }

//    @Override
//    public void onClick(View view) {
//        finish();
//    }
}
