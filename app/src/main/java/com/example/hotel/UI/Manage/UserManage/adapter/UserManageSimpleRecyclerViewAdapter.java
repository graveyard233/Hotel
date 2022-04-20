package com.example.hotel.UI.Manage.UserManage.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hotel.Bean.User;
import com.example.hotel.R;

import java.util.List;


public class UserManageSimpleRecyclerViewAdapter extends BaseQuickAdapter<User, BaseViewHolder>{


    public UserManageSimpleRecyclerViewAdapter(@Nullable List<User> data) {
        super(R.layout.user_manage_recycler_simple_item, data);
    }

    public UserManageSimpleRecyclerViewAdapter(){
        super(R.layout.user_manage_recycler_simple_item);
    }


    @Override
    protected void convert(BaseViewHolder helper, User item) {
        helper.setText(R.id.user_manage_simple_item_username,item.getUsername())
                .setText(R.id.user_manage_simple_item_IDcard,item.getObjectId());
    }
}
