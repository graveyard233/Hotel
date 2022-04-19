package com.example.hotel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Start.LoginFragment;
import com.example.hotel.UI.Start.RegisterFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class LoginActivity extends BaseActivity {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private final String[] titles = {
            "登录","注册"
    };
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;

    private ImageView img;

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

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabLayout.performClick();
                Log.i("TAG", "onClick: ");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}