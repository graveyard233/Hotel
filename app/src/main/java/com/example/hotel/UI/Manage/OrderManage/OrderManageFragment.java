package com.example.hotel.UI.Manage.OrderManage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class OrderManageFragment extends BaseFragment {


    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.order_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_list_manage_24);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_manage;
    }
}