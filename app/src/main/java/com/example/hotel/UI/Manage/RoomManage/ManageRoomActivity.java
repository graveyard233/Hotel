package com.example.hotel.UI.Manage.RoomManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.google.gson.Gson;

public class ManageRoomActivity extends BaseActivity {

    private Room room_this;

    @Override
    protected void initViews() {
        String roomJson = getIntent().getStringExtra("roomJson");
        Gson gson = new Gson();
        room_this = gson.fromJson(roomJson,Room.class);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_room;
    }
}