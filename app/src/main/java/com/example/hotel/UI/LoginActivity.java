package com.example.hotel.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.example.hotel.UI.Start.LoginFragment;
import com.example.hotel.UI.Start.RegisterFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private final String[] titles = {
            "登录","注册"
    };
    private ViewPager viewPager;
    private SlidingTabLayout tabLayout;

    @Override
    protected void initViews() {
        viewPager = findViewById(R.id.login_viewPager);
        tabLayout = findViewById(R.id.login_tabLayout);
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());

        tabLayout.setViewPager(viewPager,titles,this,fragments);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
}