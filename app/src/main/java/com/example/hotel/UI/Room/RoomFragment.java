package com.example.hotel.UI.Room;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Room.adapter.RoomRecyclerViewAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RoomRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "RoomFragment";
    private RoomRecyclerViewAdapter adapter;

    private RoomPresenter roomPresenter = new RoomPresenter();


    private SwipeRefreshLayout swipeRefreshLayout;

    private EditText search_edit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    protected void initViews() {
        swipeRefreshLayout = find(R.id.room_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);


        RecyclerView recyclerView = find(R.id.room_recyclerview);

        List<Room> rooms = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        adapter =
                new RoomRecyclerViewAdapter(recyclerView,getActivity(),rooms);
        adapter.setOnItemClickListener(this);
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

        search_edit = find(R.id.edit_query);
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    System.out.println("search " + textView.getText());

                    roomPresenter.getRoomsByType(textView.getText().toString(), new RoomViewInterface() {
                        @Override
                        public void getRoomsSucceed(List<Room> rooms) {
                            adapter.setRooms(rooms);
                        }

                        @Override
                        public void getRoomError() {
                            Toast.makeText(getActivity(),"搜索错误",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });
    }


    @Override
    public void onRefresh() {
//        roomPresenter.getRooms();
        swipeRefreshLayout.setRefreshing(false);
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
    public void OnItemClick(Room room) {
        Log.i(TAG, "OnItemClick: 房间号是 " + room.getRoomId());
        Intent intent = new Intent(getActivity(),RoomDetailActivity.class);
        Gson gson = new Gson();
        String roomDetail = gson.toJson(room);
        intent.putExtra("roomJson",roomDetail);
        startActivity(intent);
    }
}
