package com.example.hotel.UI.Manage.RoomManage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Manage.RoomManage.adapter.FloorRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FloorActivity extends BaseActivity {
    private int whichFloor;
    private TextView floor_number;
    private RecyclerView recyclerView;
    private FloorRecyclerViewAdapter adapter;

    private List<Room> rooms = new ArrayList<>();

    @Override
    protected void initViews() {
        whichFloor = getIntent().getIntExtra("FloorNumber",0);
        floor_number = findViewById(R.id.activity_floor_number);
        floor_number.setText("第" + whichFloor + "楼");
        recyclerView = findViewById(R.id.activity_floor_recycler);
        BmobQuery<Room> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                for (Room room:
                     list) {
                    if (whichFloor * 100 <= Integer.parseInt(room.getRoomId())
                            && Integer.parseInt(room.getRoomId()) < (whichFloor + 1) * 100){
                        rooms.add(room);
                    }

                }
                System.out.println(rooms.size());
                adapter = new FloorRecyclerViewAdapter(rooms);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_floor;
    }
}