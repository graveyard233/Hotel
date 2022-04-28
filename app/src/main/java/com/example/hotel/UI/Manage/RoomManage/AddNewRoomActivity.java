package com.example.hotel.UI.Manage.RoomManage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
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

    private LinearLayout step2_hide;
    private TextInputLayout roomPrice;
    private TextInputLayout comment1;
    private TextInputLayout comment2;
    private TextInputLayout comment3;

    private LinearLayout step3_hide;
    private TextInputLayout roomImgUrl;
    private ImageView roomImg;

    private Boolean isImgCanLoad = false;

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

        step2_hide = findViewById(R.id.step2_hide);
        roomPrice = findViewById(R.id.step2_roomPrice);
        comment1 = findViewById(R.id.pre_comment1);
        comment2 = findViewById(R.id.pre_comment2);
        comment3 = findViewById(R.id.pre_comment3);

        step3_hide = findViewById(R.id.step3_hide);
        roomImgUrl = findViewById(R.id.step3_imgUrl);
        roomImg = findViewById(R.id.step3_img);



        step1_view.setOnClickListener(this);
        step2_view.setOnClickListener(this);
        step3_view.setOnClickListener(this);

        step1_hide.setVisibility(View.VISIBLE);
        roomImgUrl.getEditText().setText("https://pic.iyingdi.com/post/cover/2022/04/28/12225831/dc529661-fad4-483b-96e7-97b4cd660017_w_443_h_244.jpg?imageMogr2/format/jpg|imageMogr2/quality/60");
        roomImgUrl.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.i("TAG", "onEditorAction: done");
                if (i == EditorInfo.IME_ACTION_DONE){
                    isImgCanLoad = false;//重新加载，要重新判断
                    Glide.with(getApplicationContext())
                            .load(textView.getText().toString())
                            .error(R.drawable.ic_error_25)
                            .placeholder(R.drawable.ic_bottom_room_24)
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    isImgCanLoad = true;
                                    System.out.println("11111111111");
                                    roomImg.setImageDrawable(resource);
                                }
                            });

                }
                return false;
            }
        });
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
            case R.id.add_new_room_step2:
                if (isStep1Complete()) {//完成了第一步才能做第二布
                    if (isStep2Complete()){
                        //已经完成了第二步,允许随意开关view
                        if (step2_hide.getVisibility() == View.VISIBLE){
                            step2_hide.setVisibility(View.GONE);
                        } else {
                            step2_hide.setVisibility(View.VISIBLE);
                        }
                    } else {
                        step2_hide.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.add_new_room_step3:
                if (isStep1Complete()){
                    if (isStep2Complete()){
                        if (isStep3Complete()){
                            if (step3_hide.getVisibility() == View.VISIBLE){
                                step3_hide.setVisibility(View.GONE);
                            } else {
                                step3_hide.setVisibility(View.VISIBLE);
                            }
                        } else {
                            step3_hide.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
        }
    }



    private boolean isStep1Complete() {//没完成返回假，完成了返回真
        if (roomType.getEditText().getText().toString().equals("") ||
            roomId.getEditText().getText().toString().equals("")){
            list_boolean.set(0,false);
            stepView.setList_whichComplete(list_boolean);
            return false;
        } else {
            list_boolean.set(0,true);
            stepView.setList_whichComplete(list_boolean);
            return true;
        }
    }

    private boolean isStep2Complete() {//没完成返回假，完成了返回真
        if (roomPrice.getEditText().getText().toString().equals("")){
            list_boolean.set(1,false);
            stepView.setList_whichComplete(list_boolean);
            return false;
        } else if (!comment1.getEditText().getText().toString().equals("") ||
            !comment2.getEditText().getText().toString().equals("") ||
            !comment3.getEditText().getText().toString().equals("")){
            //三个预制评论必须实现一个
            list_boolean.set(1,true);
            stepView.setList_whichComplete(list_boolean);
            return true;
        } else{
            list_boolean.set(1,false);
            stepView.setList_whichComplete(list_boolean);
            return false;
        }
    }

    private boolean isStep3Complete(){
        if (roomImgUrl.getEditText().getText().toString().equals("")){//uri没写
            list_boolean.set(2,false);
            stepView.setList_whichComplete(list_boolean);
            return false;
        } else if (isImgCanLoad){//uri写了，图片加载了
            list_boolean.set(2,true);
            stepView.setList_whichComplete(list_boolean);
            return true;
        } else {//uri写了，图片加载不来
            list_boolean.set(2,false);
            stepView.setList_whichComplete(list_boolean);
            return false;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_new_room;
    }
}