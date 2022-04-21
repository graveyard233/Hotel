package com.example.hotel.UI.Manage.RoomManage.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.VedioPlayingIcon;

import java.util.List;

public class FloorRecyclerViewAdapter extends BaseQuickAdapter<Room, BaseViewHolder> {

    public FloorRecyclerViewAdapter(@Nullable List<Room> data) {
        super(R.layout.floor_recycler_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Room item) {
        helper.setText(R.id.floor_room_number,item.getRoomId())
                .setText(R.id.floor_room_type,item.getType());
        VedioPlayingIcon vedioPlayingIcon = helper.getView(R.id.floor_room_is_busy);
        ImageView imageView = helper.getView(R.id.floor_room_is_empty);
        if (item.getIsBusy().equals("空闲")){
            vedioPlayingIcon.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            vedioPlayingIcon.setVisibility(View.VISIBLE);
            vedioPlayingIcon.setPointerColor(Color.parseColor("#2578b5"));
            imageView.setVisibility(View.GONE);
        }
    }
}
