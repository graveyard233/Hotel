package com.example.hotel.UI.Start;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.MainActivity;
import com.example.hotel.UI.ManageActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class LoginFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";
    private Button btn_login;

    private String username;
    private String password;

    private TextInputLayout username_input;
    private TextInputLayout password_input;

    @Override
    protected void initViews() {
//        BmobUser.logOut();
        username_input = find(R.id.login_username_layout);
        password_input = find(R.id.login_password_layout);
        username_input.getEditText().setText("admin");
        password_input.getEditText().setText("admin");
        btn_login = find(R.id.login_btn_login);
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            Snackbar.make(find(R.id.login_coord),  user.getUsername() + "已经登录" , Snackbar.LENGTH_LONG).show();
            if (user.getUsername().equals("admin")){//是管理员
                Intent to_Manage = new Intent(getActivity(), ManageActivity.class);
//                                    to_Manage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(to_Manage);
            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);//去用户界面
//                startActivity(intent);
            }

        }
        else {
            Snackbar.make(find(R.id.login_coord), "尚未登录" , Snackbar.LENGTH_LONG).show();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                username_input = find(R.id.login_username_layout);
//                password_input = find(R.id.login_password_layout);
                if (password_input.getEditText().getText().toString().equals("") ||
                username_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.login_coord),"登录信息不完善",Snackbar.LENGTH_SHORT).show();
                } else {//输入不为空，进行登录操作
                    username = username_input.getEditText().getText().toString();
                    password = password_input.getEditText().getText().toString();

                    BmobUser.loginByAccount(username, password, new LogInListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                Log.i("TAG", "登录成功:" + user.getIDcard());
                                Snackbar.make(find(R.id.login_coord), "登录成功：" + user.getUsername(), Snackbar.LENGTH_LONG).show();
                                if (user.getUsername().equals("admin")){//是管理员
                                    Intent to_Manage = new Intent(getActivity(), ManageActivity.class);
//                                    to_Manage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(to_Manage);
                                } else {//为普通用户
                                    Intent to_user = new Intent(getActivity(),MainActivity.class);
                                    to_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(to_user);
                                }

                            }else {
                                Snackbar.make(find(R.id.login_coord), "登录失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
//                Snackbar.make(find(R.id.coordinatorLayout),"login",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }
}