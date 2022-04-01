package com.example.hotel.UI.Room;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;

public class RoomFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void initViews() {
        SwipeRefreshLayout swipeRefreshLayout = find(R.id.room_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        RecyclerView recyclerView = find(R.id.room_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
//        new GridLayoutManager.SpanSizeLookup()

    }


    @Override
    public void onRefresh() {

    }
}
