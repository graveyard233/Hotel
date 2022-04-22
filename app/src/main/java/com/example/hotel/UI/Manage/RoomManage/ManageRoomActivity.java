package com.example.hotel.UI.Manage.RoomManage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Manage.RoomManage.adapter.RoomCommentRecyclerAdapter;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.example.hotel.UI.Room.SampleDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ManageRoomActivity extends BaseActivity implements View.OnClickListener {

    private Room room_this;

    private OrderPresenter orderPresenter = new OrderPresenter();
    private List<Date> timeList = new ArrayList<>();
    private SampleDecorator decorator;
    private CalendarPickerView pickerView;

    private RecyclerView recyclerView;
    private RoomCommentRecyclerAdapter adapter;


    private TextInputLayout roomNumber;
    private TextInputLayout roomPrice;
    private TextInputLayout roomIsBusy;
    private TextInputLayout roomType;

    private FloatingActionButton fab;

    @Override
    protected void initViews() {
        String roomJson = getIntent().getStringExtra("roomJson");
        Gson gson = new Gson();
        room_this = gson.fromJson(roomJson,Room.class);
        orderPresenter.setRoom(room_this);
        pickerView = (CalendarPickerView) findViewById(R.id.manage_room_caldendar);
        recyclerView = findViewById(R.id.manage_room_comment_recycler);

        roomNumber = findViewById(R.id.manage_room_number);
        roomPrice = findViewById(R.id.manage_room_price);
        roomIsBusy = findViewById(R.id.manage_room_is_busy);
        roomType = findViewById(R.id.manage_room_type);

        fab = findViewById(R.id.floatingActionButton_room_manage);

        roomNumber.getEditText().setText(room_this.getRoomId());
        roomPrice.getEditText().setText(room_this.getPrice().toString());
        roomType.getEditText().setText(room_this.getType());
        roomIsBusy.getEditText().setText(room_this.getIsBusy());

        fab.setOnClickListener(this);

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
                for (int i = 0; i < orders.size(); i++) {
                    List<Date> list_temp = new ArrayList<>();
                    list_temp = BmobTimeUtil.getDaysBetween(orders.get(i).getStartTime().getDate(),
                            orders.get(i).getEndTime().getDate());
                    for (int j = 0; j < list_temp.size(); j++) {
                        timeList.add(list_temp.get(j));
                    }
                }

                System.out.println(timeList);
                decorator = new SampleDecorator();
                decorator.setTimes(timeList);


                List<CalendarCellDecorator> d = new ArrayList<>();
                d.add(decorator);
                pickerView.setDecorators(d);
                final Calendar nextWeek = Calendar.getInstance();
                nextWeek.add(Calendar.MONTH, 2);
                //只能看之后两个月的日程
                pickerView.init(new Date(), nextWeek.getTime())
                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
                        .withSelectedDate(new Date());
                pickerView.setVisibility(View.VISIBLE);
//                        .inMode(CalendarPickerView.SelectionMode.RANGE);
                pickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
                    @Override
                    public void onInvalidDateSelected(Date date) {
                        Toast.makeText(getApplicationContext(), "非法的日期", Toast.LENGTH_SHORT).show();
                    }
                });

                adapter = new RoomCommentRecyclerAdapter(orders);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void getOrderByIdError() {
                final Calendar nextWeek = Calendar.getInstance();
                nextWeek.add(Calendar.MONTH, 2);
                //只能看之后两个月的日程
                pickerView.init(new Date(), nextWeek.getTime())
                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
                        .withSelectedDate(new Date());
                pickerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void addOrder(String objId, int i) {

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_room;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floatingActionButton_room_manage:
                room_this.setRoomId(roomNumber.getEditText().getText().toString());
                room_this.setPrice(Double.parseDouble(roomPrice.getEditText().getText().toString()));
                room_this.setType(roomType.getEditText().getText().toString());
                room_this.setIsBusy(roomIsBusy.getEditText().getText().toString());

                room_this.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            Snackbar.make(findViewById(R.id.manage_room_coord),"更新成功",Snackbar.LENGTH_SHORT).show();
                        }
                        else {
                            Snackbar.make(findViewById(R.id.manage_room_coord),"更新失败:" + e.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}