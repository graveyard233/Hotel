package com.example.hotel.UI.Manage.RoomManage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class RoomManageFragment extends BaseFragment implements View.OnClickListener {

    private Button floor1;
    private Button floor2;
    private Button floor3;
    private Button floor4;
    private Button floor5;
    private Button floor6;

    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.room_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_room_manage_24);
        floor1 = find(R.id.floor1);
        floor2 = find(R.id.floor2);
        floor3 = find(R.id.floor3);
        floor4 = find(R.id.floor4);
        floor5 = find(R.id.floor5);
        floor6 = find(R.id.floor6);

        floor1.setOnClickListener(this);
        floor2.setOnClickListener(this);
        floor3.setOnClickListener(this);
        floor4.setOnClickListener(this);
        floor5.setOnClickListener(this);
        floor6.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_manage;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.floor1:
                gotoFloor(1);
                break;
            case R.id.floor2:
                gotoFloor(2);
                break;
            case R.id.floor3:
                gotoFloor(3);
                break;
            case R.id.floor4:
                gotoFloor(4);
                break;
            case R.id.floor5:
                gotoFloor(5);
                break;
            case R.id.floor6:
                gotoFloor(6);
                break;
        }
    }

    private void gotoFloor(int whichFloor){
        Intent intent = new Intent(getActivity(),FloorActivity.class);
        intent.putExtra("FloorNumber",whichFloor);
        startActivity(intent);
        Log.i("TAG", "gotoFloor: " + whichFloor);
    }
}