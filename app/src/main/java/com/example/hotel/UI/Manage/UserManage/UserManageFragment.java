package com.example.hotel.UI.Manage.UserManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Manage.UserManage.adapter.UserManageSimpleRecyclerViewAdapter;
import com.example.hotel.UI.Mine.MinePresenter;
import com.example.hotel.UI.Mine.MineViewInterface;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class UserManageFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private UserManageSimpleRecyclerViewAdapter adapter;
    private MinePresenter presenter = new MinePresenter();

    private FloatingActionButton fab;

    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.user_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_user_manage_24);
        recyclerView = find(R.id.user_manage_recyclerView);

        fab = getActivity().findViewById(R.id.manage_activity_floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("UserManageFragment");
            }
        });
//        List<User> list = new ArrayList<>();
//        User user = new User();
//        user.setUsername("aaa");
//        user.setIDcard("159753");
//        list.add(user);list.add(user);list.add(user);list.add(user);list.add(user);
//

        presenter.getMineModel(new MineViewInterface() {
            @Override
            public void getAllUsersSucceed(List<User> users) {
                adapter = new UserManageSimpleRecyclerViewAdapter(users);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Log.i("TAG", "onItemClick: " + position);
                        Intent to_single_user = new Intent(getActivity(),SingleUserInfoActivity.class);
                        Gson gson = new Gson();
                        String userJson = gson.toJson(adapter.getItem(position));
                        to_single_user.putExtra("userJson",userJson);
                        startActivity(to_single_user);
                    }
                });
            }

            @Override
            public void getAllUsersError() {

            }
        });



    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_manage;
    }



}