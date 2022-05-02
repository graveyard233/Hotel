package com.example.hotel.UI.Mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.LoginActivity;
import com.example.hotel.UI.Manage.UserManage.ChangeUserInfoDialog;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class MineFragment extends BaseFragment implements ChangeUserInfoDialog.OnDialogItemClickListener{
    private User user;
    private TextView username;
    private TextView objId;
    private TextView gender;
    private TextView IDcard;
    private TextView age;
    private TextView createAt;
    private TextView phone;
    private Button btn_logout;
    private Button btn_change;

    private ChangeUserInfoDialog changeUserInfoDialog;
    @Override
    protected void initViews() {
        user =  BmobUser.getCurrentUser(User.class);
        username = find(R.id.mine_username);
        objId = find(R.id.mine_objId);
        gender = find(R.id.mine_gender);
        IDcard = find(R.id.mine_IDcard);
        phone = find(R.id.mine_phone);
        age = find(R.id.mine_age);
        createAt = find(R.id.mine_createAt);
        btn_logout = find(R.id.mine_logout);
        btn_change = find(R.id.btn_change_user_info);


        changeUserInfoDialog = new ChangeUserInfoDialog(getActivity(),user);
        changeUserInfoDialog.setDialogOnItemClickListener((ChangeUserInfoDialog.OnDialogItemClickListener)this);

//        username.setText(user.getUsername());
//        objId.setText(user.getObjectId());
//        gender.setText(user.getSex());
//        IDcard.setText(user.getIDcard());
//        age.setText(String.valueOf(user.getAge()));
//        createAt.setText(user.getCreatedAt());
        refreshUserInfo();
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserInfoDialog.show();
            }
        });

    }



    @Override
    public void OnItemClick(ChangeUserInfoDialog dialog, View view) {
        switch (view.getId()){
            case R.id.change_user_info_ok:
                System.out.println("ok");
                changeUserInfoDialog.dismiss();
                user = changeUserInfoDialog.getChangeUser();
                if (user == null){
                    Toast.makeText(getActivity(), "数据没填写完", Toast.LENGTH_SHORT).show();
                } else {
                    updateUserInfo();
                    refreshUserInfo();
                }

                break;
            case R.id.change_user_info_cancel:
                System.out.println("cancel");
                changeUserInfoDialog.dismiss();
                break;

        }
    }

    private void updateUserInfo() {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("TAG", "done: " + user.toString());
                    Toast.makeText(getActivity(), "更新成功，"+ user.toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("TAG", "更新失败," + e.getMessage());
                    Toast.makeText(getActivity(), "更新失败," + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshUserInfo() {
        username.setText(user.getUsername());
        objId.setText(user.getObjectId());
        gender.setText(user.getSex());
        IDcard.setText(user.getIDcard());
        age.setText(String.valueOf(user.getAge()));
        createAt.setText(user.getCreatedAt());
        phone.setText(user.getPhone());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
