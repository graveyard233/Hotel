package com.example.hotel.UI.Room;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.google.gson.Gson;
//implements View.OnClickListener
public class RoomDetailActivity extends BaseActivity  {


    private static final String TAG = "RoomDetailActivity";

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
