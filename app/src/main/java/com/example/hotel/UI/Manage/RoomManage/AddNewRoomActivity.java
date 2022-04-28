package com.example.hotel.UI.Manage.RoomManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hotel.MyDraw.StepView;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddNewRoomActivity extends BaseActivity implements View.OnClickListener {

    private StepView stepView;
    private MaterialCardView step1_view;
    private MaterialCardView step2_view;
    private MaterialCardView step3_view;

    private List<Boolean> list_boolean = new ArrayList<>();

    private LinearLayout step1_hide;
    private TextInputLayout roomType;
    private TextInputLayout roomId;

    @Override
    protected void initViews() {
        stepView = findViewById(R.id.add_step);

        step1_view = findViewById(R.id.add_new_room_step1);
        step2_view = findViewById(R.id.add_new_room_step2);
        step3_view = findViewById(R.id.add_new_room_step3);
        list_boolean.add(false);list_boolean.add(false);list_boolean.add(false);
        stepView.setList_whichComplete(list_boolean);

        step1_hide = findViewById(R.id.step1_hide);
        roomType = findViewById(R.id.step1_roomType);
        roomId = findViewById(R.id.step1_roomId);

        step1_view.setOnClickListener(this);

        step1_hide.setVisibility(View.VISIBLE);
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_new_room_step1:
                if (isStep1Complete()){
                    //已经完成了第一步,允许随意开关view
                    if (step1_hide.getVisibility() == View.VISIBLE){
                        step1_hide.setVisibility(View.GONE);
                    } else {
                        step1_hide.setVisibility(View.VISIBLE);
                    }
                } else {
                    step1_hide.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private boolean isStep1Complete() {//没完成返回假，完成了返回真

        if (roomType.getEditText().getText().toString().equals("") ||
            roomId.getEditText().getText().toString().equals("")){
            return false;
        } else {
            list_boolean.set(0,true);
            stepView.setList_whichComplete(list_boolean);
            return true;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_room;
    }
}