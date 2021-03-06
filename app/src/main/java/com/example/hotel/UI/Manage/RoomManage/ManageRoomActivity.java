package com.example.hotel.UI.Manage.RoomManage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import com.google.android.material.textfield.TextInputEditText;
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
    private TextInputLayout roomDiscount;
    private TextInputLayout roomType;

    private TextInputEditText inputDiscount;

    private FloatingActionButton fab;

    private TextInputLayout roomImgUrl;
    private ImageView roomImg;
    private Boolean isImgCanLoad = true;

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
        roomDiscount = findViewById(R.id.manage_room_discount_layout);
        roomType = findViewById(R.id.manage_room_type);
        inputDiscount = findViewById(R.id.manage_room_discount);
        roomImgUrl = findViewById(R.id.manage_room_imgUrl);
        roomImg = findViewById(R.id.manage_room_img);

        fab = findViewById(R.id.floatingActionButton_room_manage);

        roomNumber.getEditText().setText(room_this.getRoomId());
        roomPrice.getEditText().setText(room_this.getPrice().toString());
        roomType.getEditText().setText(room_this.getType());
        roomIsBusy.getEditText().setText(room_this.getIsBusy());
        roomDiscount.getEditText().setText(room_this.getDiscount().toString());
        roomImgUrl.getEditText().setText(room_this.getImgUrl());

        Glide.with(getApplicationContext())
                .load(room_this.getImgUrl())
                .placeholder(R.drawable.ic_bottom_room_24)
                .error(R.drawable.ic_error_25)
                .into(roomImg);
        roomImgUrl.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isImgCanLoad = false;
                Glide.with(getApplicationContext())
                        .load(roomImgUrl.getEditText().getText().toString())
                        .placeholder(R.drawable.ic_bottom_room_24)
                        .addListener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                isImgCanLoad = false;
                                Log.e("TAG", "onLoadFailed: " );
                                roomImg.setImageResource(R.drawable.ic_error_25);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                isImgCanLoad = true;
                                Log.e("TAG", "onResourceReady: ");
                                return false;
                            }
                        })
                        .into(roomImg);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                float max = 1f;
                float min = 0f;
                String pre_s = editable.toString().trim();
                if (pre_s.matches("^[-\\+]?[.\\d]*$")){//????????????
                    int index_point = pre_s.indexOf(".");
                    if (index_point < 0){
                        return;
                    }
                    if (pre_s.length() - index_point - 1 > 2){
                        editable.delete(index_point + 3,index_point + 4);
                    }
                    String s_afterDelete = editable.toString().trim();
                    Double discount_pre = Double.parseDouble(s_afterDelete);
                    if (discount_pre > max){
                        editable.replace(0,editable.length(),String.valueOf(max));
                    } else if (discount_pre < min){
                        editable.replace(0,editable.length(),String.valueOf(min));
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "??????????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                //?????????????????????????????????
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getIsPay() == 3)//?????????????????????
                    {
                        continue;
                    }
                    List<Date> list_temp = new ArrayList<>();
                    list_temp = BmobTimeUtil.getDaysBetween(orders.get(i).getStartTime().getDate(),
                            orders.get(i).getEndTime().getDate());
                    for (int j = 0; j < list_temp.size(); j++) {
                        timeList.add(list_temp.get(j));
                    }
                }
                Date today = new Date();
                for (int i = 0; i < timeList.size(); i++) {
                    if (today.before(timeList.get(i))){//???????????????????????????????????????????????????????????????????????????????????????
                        Log.e("TAG", "getOrderById: "+ timeList.get(i).toString());
                        roomNumber.getEditText().setFocusable(false);
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
                //?????????????????????????????????
                pickerView.init(new Date(), nextWeek.getTime())
                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
                        .withSelectedDate(new Date());
                pickerView.setVisibility(View.VISIBLE);
//                        .inMode(CalendarPickerView.SelectionMode.RANGE);
                pickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
                    @Override
                    public void onInvalidDateSelected(Date date) {
                        Toast.makeText(getApplicationContext(), "???????????????", Toast.LENGTH_SHORT).show();
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
                //?????????????????????????????????
                pickerView.init(new Date(), nextWeek.getTime())
                        .inMode(CalendarPickerView.SelectionMode.SINGLE)
                        .withSelectedDate(new Date());
                pickerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void addOrder(String objId, int i) {

            }

            @Override
            public void cancelOrder(String objId) {

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
                if (checkAll() && isImgCanLoad){
                    room_this.setRoomId(roomNumber.getEditText().getText().toString());
                    room_this.setPrice(Double.parseDouble(roomPrice.getEditText().getText().toString()));
                    room_this.setType(roomType.getEditText().getText().toString());
                    Double discount = Double.valueOf(roomDiscount.getEditText().getText().toString());
                    if (discount < 0 || discount > 1){
                        discount = 1d;
                        Toast.makeText(getApplicationContext(),
                                "???????????????0~1??????",Toast.LENGTH_SHORT).show();
                    }
                    room_this.setDiscount(discount);
                    room_this.setIsBusy(roomIsBusy.getEditText().getText().toString());
                    room_this.setImgUrl(roomImgUrl.getEditText().getText().toString());
                    room_this.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Snackbar.make(findViewById(R.id.manage_room_coord),"????????????",Snackbar.LENGTH_SHORT).show();
                            }
                            else {
                                Snackbar.make(findViewById(R.id.manage_room_coord),"????????????:" + e.getMessage(),Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(findViewById(R.id.manage_room_coord),"????????????????????????",Snackbar.LENGTH_SHORT).show();
                }

        }
    }

    private Boolean checkAll(){
        if (roomNumber.getEditText().getText().toString().equals("") ||
                roomPrice.getEditText().getText().toString().equals("") ||
                roomType.getEditText().getText().toString().equals("") ||
                roomDiscount.getEditText().getText().toString().equals("") ||
                roomImgUrl.getEditText().getText().toString().equals("")  ){
            return false;
        } else {
            return true;
        }
    }
}