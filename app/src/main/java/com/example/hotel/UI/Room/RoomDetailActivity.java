package com.example.hotel.UI.Room;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;

public class RoomDetailActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.room_detail_toolBar);
        toolbar.setNavigationOnClickListener(this);
        String roomJson = getIntent().getStringExtra("roomJson");
        System.out.println(roomJson);

//        toolbar.setTitle(roomId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_detail;
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
