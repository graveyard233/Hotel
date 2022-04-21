package com.example.hotel.UI.Manage.UserManage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Order_isShow;
import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Manage.ChangeDialog;
import com.example.hotel.UI.Order.adapter.OrderRecyclerViewAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class SingleUserInfoActivity extends BaseActivity implements View.OnClickListener,ChangeUserInfoDialog.OnDialogItemClickListener {

    private String userJson;
    private User user_this = new User();
    private Gson gson = new Gson();

    private TextView tv_username;
    private TextView tv_objId;
    private TextView tv_gender;
    private TextView tv_IDcard;
    private TextView tv_phone;
    private TextView tv_create;
    private TextView tv_update;

    private ChangeUserInfoDialog changeUserInfoDialog;

    private ImageView img;


    private List<Order_isShow> list_order = new ArrayList<>();
    private Boolean is_gone = false;
    private OrderRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void initViews() {
        userJson = getIntent().getStringExtra("userJson");
        user_this = gson.fromJson(userJson,User.class);
        tv_username = findViewById(R.id.single_user_info_username);
        tv_objId = findViewById(R.id.single_user_info_objID);
        tv_gender = findViewById(R.id.single_user_info_gender);
        tv_IDcard = findViewById(R.id.single_user_info_IDcard);
        tv_phone = findViewById(R.id.single_user_info_phone);
        tv_create = findViewById(R.id.single_user_info_createAt);
        tv_update = findViewById(R.id.single_user_info_updateTime);
        refreshUserInfo();

        changeUserInfoDialog = new ChangeUserInfoDialog(this,user_this);
        changeUserInfoDialog.setDialogOnItemClickListener((ChangeUserInfoDialog.OnDialogItemClickListener)this);
        img = findViewById(R.id.single_user_info_change_img);
        img.setOnClickListener(this);

        recyclerView = findViewById(R.id.single_user_info_recycleView);

        showOrderList();
    }

    private void showOrderList() {
        BmobQuery<Order> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("msgId",user_this.getObjectId());
        bmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> list, BmobException e) {
                if (list.size() > 0){
                    for (int i = 0; i < list.size(); i++) {
                        list_order.add(new Order_isShow(list.get(i)));
                    }
                    adapter = new OrderRecyclerViewAdapter(list_order);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);


                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.i("TAG", "onItemChildClick: ");
                            if (list_order.get(position).isShow()){
                                list_order.get(position).setShow(false);
                            } else {
                                list_order.get(position).setShow(true);
                            }
                            adapter.setNewData(list_order);

                        }
                    });
                } else {
                    Log.e("BMOB", "error");
                }
            }
        });
    }

    private void refreshUserInfo() {
        tv_username.setText(user_this.getUsername());
        tv_objId.setText(user_this.getObjectId());
        tv_gender.setText(user_this.getSex());
        tv_IDcard.setText(user_this.getIDcard());
        tv_phone.setText(user_this.getPhone());
        tv_create.setText(user_this.getCreatedAt());
        tv_update.setText(user_this.getUpdatedAt());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_single_user_info;
    }

    @Override
    public void OnItemClick(ChangeUserInfoDialog dialog, View view) {
        switch (view.getId()){
            case R.id.change_user_info_ok:
                System.out.println("ok");
                user_this = changeUserInfoDialog.getChangeUser();
                updateUserInfo();
                refreshUserInfo();

                changeUserInfoDialog.dismiss();

                break;
            case R.id.change_user_info_cancel:
                System.out.println("cancel");
                changeUserInfoDialog.dismiss();
                break;
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.single_user_info_change_img:
                changeUserInfoDialog.show();
                break;
        }
    }

    private void updateUserInfo() {
        user_this.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("TAG", "done: " + user_this.toString());
                }else{
                    Log.e("TAG", "更新失败," + e.getMessage());
                }
            }
        });
    }
}