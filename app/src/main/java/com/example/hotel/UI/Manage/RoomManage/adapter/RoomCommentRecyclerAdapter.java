package com.example.hotel.UI.Manage.RoomManage.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hotel.Bean.Order;
import com.example.hotel.R;

import java.util.List;

public class RoomCommentRecyclerAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {
    public RoomCommentRecyclerAdapter(@Nullable List<Order> data) {
        super(R.layout.manage_room_comment_recycler_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {
        helper.setText(R.id.manage_room_recycler_item_username,item.getMsgId())
                .setText(R.id.manage_room_recycler_item_time,item.getCreatedAt())
                .setText(R.id.manage_room_recycler_item_comment,item.getUserMassage());
    }
}
