package com.example.hotel.UI.Order;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.VedioPlayingIcon;

public class OrderFragment extends BaseFragment {



    private TextView textView;
    private LinearLayout view;
    private ConstraintLayout layout;
    @Override
    protected void initViews() {
//        icon = find(R.id.my_icon);
//        icon2 = find(R.id.my_icon2);

        view = find(R.id.mytest);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("TAG", "onClick: 2");
//            }
//        });
        textView = find(R.id.textView);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("TAG", "onClick: 1");
//            }
//        });

        layout = find(R.id.maxlayout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("TAG", "onTouch: x = " + motionEvent.getX() + ",y = " + motionEvent.getY());
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

}
