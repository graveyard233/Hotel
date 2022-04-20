package com.example.hotel.UI.Manage;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class UserManageFragment extends BaseFragment {

    private TextView textView;

    private int i = 0;





    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.user_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_user_manage_24);
        textView = find(R.id.text_logout);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_manage", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        i = Integer.parseInt(sharedPreferences.getString("i","-1"));
        Log.i("TAG", "initViews: " + i);

        textView.setText(String.valueOf(i));




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(String.valueOf(i++));
                Log.i("TAG", "onClick: i = " + i);
                SharedPreferences.Editor editor_this = sharedPreferences.edit();

                editor_this.putString("i",String.valueOf(i));

                editor_this.commit();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_manage;
    }



}