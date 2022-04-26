package com.example.hotel.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.hotel.Bean.Order;
import com.example.hotel.MyDraw.LineGraph;
import com.example.hotel.R;
import com.example.hotel.Service.InitRoomIsBusyIntentService;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Manage.ChangeDialog;
import com.example.hotel.UI.Start.LoginFragment;
import com.example.hotel.UI.Start.RegisterFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class LoginActivity extends BaseActivity implements ChangeDialog.OnItemClickListener{

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private final String[] titles = {
            "登录","注册"
    };
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;

    private ImageView img;

    private ChangeDialog changeDialog;

    @Override
    protected void initViews() {
        viewPager = findViewById(R.id.login_viewPager);
        tabLayout = findViewById(R.id.login_tabLayout);
        img = findViewById(R.id.login_img);
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        viewPager.setBackgroundColor(Color.WHITE);

        tabLayout.setViewPager(viewPager,titles,this,fragments);

        Bmob.initialize(getApplicationContext(),"f6017516ea38b947a8214fa98dbec40f");

//        changeDialog = new ChangeDialog(this);
//        changeDialog.setOnItemClickListener((ChangeDialog.OnItemClickListener) this);
//        changeDialog.setOldText("3333");
//        changeDialog.show();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("TAG", "onClick: ");
//                handler.sendEmptyMessage(1);


            }
        });

        //初始化房间状态数据
        Intent intent = new Intent(this, InitRoomIsBusyIntentService.class);
        startService(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                MotionEvent down = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_DOWN,0,1000,0);
                dispatchTouchEvent(down);
                handler.sendMessageDelayed(handler.obtainMessage(2),75);
            }

            if (msg.what == 2){
                MotionEvent move = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_MOVE,1000,1000,0);
                dispatchTouchEvent(move);
                handler.sendMessageDelayed(handler.obtainMessage(3),75);
            }

            if (msg.what == 3){
                MotionEvent up = MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.currentThreadTimeMillis(),
                        MotionEvent.ACTION_UP,50,220,0);
                dispatchTouchEvent(up);
            }
        }
    };


    @Override
    public void OnItemClick(ChangeDialog dialog, View view) {
        switch (view.getId()){
            case R.id.change_dialog_ok:
                System.out.println("ok");
                System.out.println(changeDialog.getInputText());
                changeDialog.dismiss();
                break;
        }
    }
}