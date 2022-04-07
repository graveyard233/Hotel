package com.example.hotel.UI.Room;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Room.adapter.RoomRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RoomRecyclerViewAdapter adapter;

    private RoomPresenter roomPresenter = new RoomPresenter();

    private RoomModel roomModel = new RoomModel();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void initViews() {
        SwipeRefreshLayout swipeRefreshLayout = find(R.id.room_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        RecyclerView recyclerView = find(R.id.room_recyclerview);

        List<Room> rooms = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        adapter =
                new RoomRecyclerViewAdapter(getActivity(),rooms);
        recyclerView.setAdapter(adapter);

//        roomPresenter = new RoomPresenter();
        roomPresenter.getRoomsPresenter(new RoomViewInterface() {
            @Override
            public void getRoomsSucceed(List<Room> rooms) {
                adapter.setRooms(rooms);

            }

            @Override
            public void getRoomError() {

            }
        });
    }


    @Override
    public void onRefresh() {
//        roomPresenter.getRooms();
        roomPresenter.getRoomsPresenter(new RoomViewInterface() {
            @Override
            public void getRoomsSucceed(List<Room> rooms) {

                adapter.setRooms(rooms);
            }

            @Override
            public void getRoomError() {

            }
        });
    }
}
