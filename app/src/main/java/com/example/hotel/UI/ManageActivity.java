package com.example.hotel.UI;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.google.android.material.navigation.NavigationView;

import cn.bmob.v3.BmobUser;

public class ManageActivity extends BaseActivity {


    private AppBarConfiguration appBarConfiguration;
    private NavController navController;

    @Override
    protected void initViews() {
        Toolbar toolbar = findViewById(R.id.manage_toolbar);
        setSupportActionBar(toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
//        navController = Navigation.findNavController(this,R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        appBarConfiguration = new AppBarConfiguration
//                .Builder(navController.getGraph())
                .Builder(R.id.userManageFragment,R.id.roomManageFragment,R.id.orderManageFragment,R.id.commentManageFragment,R.id.reportFragment)
                .setDrawerLayout(drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        NavigationView navigationView = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(navigationView,navController);



    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage;
    }
}