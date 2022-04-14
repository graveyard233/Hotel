package com.example.hotel.UI.Order;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.VedioPlayingIcon;

public class OrderFragment extends BaseFragment {

    private VedioPlayingIcon icon;
    @Override
    protected void initViews() {
        icon = find(R.id.my_icon);
        icon.start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }
}
