package com.example.hotel.UI.Room;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Room.adapter.RoomRecyclerViewAdapter;
import com.example.hotel.UI.Room.adapter.RoomSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RoomRecyclerViewAdapter adapter;

    private RoomPresenter roomPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void initViews() {
        SwipeRefreshLayout swipeRefreshLayout = find(R.id.room_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        RecyclerView recyclerView = find(R.id.room_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),
                4);
        List<Room> rooms = new ArrayList<>();
        gridLayoutManager.setSpanSizeLookup(new RoomSpanSizeLookup(rooms));

        adapter =
                new RoomRecyclerViewAdapter(getActivity(),rooms);
        recyclerView.setAdapter(adapter);

        roomPresenter = new RoomPresenter();
//        roomPresenter.getRooms();
    }


    @Override
    public void onRefresh() {
//        roomPresenter.getRooms();
    }
}
