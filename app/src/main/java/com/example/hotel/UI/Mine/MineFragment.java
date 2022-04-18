package com.example.hotel.UI.Mine;

import android.widget.TextView;

import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;

import cn.bmob.v3.BmobUser;

public class MineFragment extends BaseFragment {
    private User user;
    private TextView username;
    private TextView objId;
    private TextView gender;
    private TextView IDcard;
    private TextView age;
    private TextView createAt;
    @Override
    protected void initViews() {
        user =  BmobUser.getCurrentUser(User.class);
        username = find(R.id.mine_username);
        objId = find(R.id.mine_objId);
        gender = find(R.id.mine_IDcard);
        IDcard = find(R.id.mine_IDcard);
        age = find(R.id.mine_age);
        createAt = find(R.id.mine_createAt);

        username.setText(user.getUsername());
        objId.setText(user.getObjectId());
        gender.setText(user.getSex());
        IDcard.setText(user.getIDcard());
        age.setText(String.valueOf(user.getAge()));
        createAt.setText(user.getCreatedAt());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
