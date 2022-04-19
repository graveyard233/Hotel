package com.example.hotel.UI.Start;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterFragment extends BaseFragment {

    private TextInputLayout username_input;
    private TextInputLayout age_input;
    private TextInputLayout IDcard_input;
    private TextInputLayout phone_input;
    private TextInputLayout password_input;

    private RadioGroup radioGroup;

    private String username;
    private String age;
    private String IDcard;
    private String phone;
    private String password;
    private String gender = "男";

    Button btn_reg;


    @Override
    protected void initViews() {
        username_input = find(R.id.register_username_layout);
        age_input = find(R.id.register_age_layout);
        IDcard_input = find(R.id.register_IDcard_layout);
        phone_input = find(R.id.register_phone_layout);
        password_input = find(R.id.register_password_layout);
        btn_reg = find(R.id.login_btn_register);
        radioGroup = find(R.id.reg_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.reg_radioButton1){
                    gender = "男";
                    Log.i("TAG", "initViews: " + gender);
                } else {
                    gender = "女";
                    Log.i("TAG", "initViews: " + gender);
                }
            }
        });



        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.reg_coord),"姓名没有填写",Snackbar.LENGTH_SHORT).show();

                } else if (age_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.reg_coord),"年龄没有填写",Snackbar.LENGTH_SHORT).show();
                } else if (IDcard_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.reg_coord),"身份证没有填写",Snackbar.LENGTH_SHORT).show();
                } else if (phone_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.reg_coord),"电话号码没有填写",Snackbar.LENGTH_SHORT).show();
                } else if (password_input.getEditText().getText().toString().equals("")){
                    Snackbar.make(find(R.id.reg_coord),"密码没有填写",Snackbar.LENGTH_SHORT).show();
                } else {//上面的信息都填了,进行注册操作
                    username = username_input.getEditText().getText().toString();
                    age = age_input.getEditText().getText().toString();
                    IDcard = IDcard_input.getEditText().getText().toString();
                    phone = phone_input.getEditText().getText().toString();
                    password = password_input.getEditText().getText().toString();
                    final User user = new User(IDcard,gender,Integer.parseInt(age),phone);
                    user.setUsername(username);
                    user.setPassword(password);
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                Snackbar.make(view, "注册成功," + user.getUsername(), Snackbar.LENGTH_LONG).show();
                                BmobUser.logOut();//因为默认注册后登录，所以要登出然后让自己登录
                                handler.sendMessageDelayed(handler.obtainMessage(1),1500);
                            } else {
                                Snackbar.make(view, "尚未失败：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                MotionEvent down = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_DOWN,0,1000,0);
                getActivity().dispatchTouchEvent(down);
                handler.sendMessageDelayed(handler.obtainMessage(2),75);
            }

            if (msg.what == 2){
                MotionEvent move = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_MOVE,1000,1000,0);
                getActivity().dispatchTouchEvent(move);
                handler.sendMessageDelayed(handler.obtainMessage(3),75);
            }

            if (msg.what == 3){
                MotionEvent up = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_UP,50,220,0);
                getActivity().dispatchTouchEvent(up);
            }
        }
    };
}