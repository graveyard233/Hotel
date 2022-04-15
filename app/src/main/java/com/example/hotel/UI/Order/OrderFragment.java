package com.example.hotel.UI.Order;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.VedioPlayingIcon;

public class OrderFragment extends BaseFragment {

    private VedioPlayingIcon icon;
    private VedioPlayingIcon icon2;

    private TextView textView;
    @Override
    protected void initViews() {
        icon = find(R.id.my_icon);
        icon2 = find(R.id.my_icon2);
        icon.start();
        icon2.start();
        textView = find(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: 1");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }
}
