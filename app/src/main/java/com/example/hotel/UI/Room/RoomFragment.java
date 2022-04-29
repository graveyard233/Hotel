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

import com.example.hotel.Bean.Announcement;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.Announcement.AnnouncementPresenter;
import com.example.hotel.UI.Announcement.AnnouncementViewInterface;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Room.adapter.AnnouncementBannerAdapter;
import com.example.hotel.UI.Room.adapter.RoomRecyclerViewAdapter;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RoomRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "RoomFragment";
    private RoomRecyclerViewAdapter adapter;

    private RoomPresenter roomPresenter = new RoomPresenter();


    private SwipeRefreshLayout swipeRefreshLayout;

    private EditText search_edit;

    private Banner banner;
    private List<Announcement> announcements = new ArrayList<>();
    private AnnouncementPresenter announcementPresenter = new AnnouncementPresenter();


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

            @Override
            public void addRoomSucceed(String objId) {

            }

            @Override
            public void addRoomError() {

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

                        @Override
                        public void addRoomSucceed(String objId) {

                        }

                        @Override
                        public void addRoomError() {

                        }
                    });
                }
                return false;
            }
        });

        // TODO: 2022/4/29  
        announcementPresenter.getAllAnnouncement(2, new AnnouncementViewInterface() {
            @Override
            public void addAnnouncement(String objId, int i) {

            }

            @Override
            public void getAllAnnouncementSucceed(List<Announcement> list) {
                for (int i = 0; i < list.size(); i++) {

                    if (i == 3){
                        break;
                    }
                    announcements.add(list.get(list.size() - i - 1));
                }
                AnnouncementBannerAdapter adapter = new AnnouncementBannerAdapter(getActivity(),announcements);
                banner = find(R.id.announcement_banner);
                banner.setAdapter(adapter)
                        .addBannerLifecycleObserver(getViewLifecycleOwner())
                        .setIndicator(new CircleIndicator(getActivity()))
                        .setLoopTime(8000)
                        .setIndicatorSelectedColorRes(R.color.深竹月)
                        .setIndicatorNormalColorRes(R.color.月色白)
                        ;
            }

            @Override
            public void getAllAnnouncementError() {
                Announcement a = new Announcement();
                a.setTitle("加载错误");
                announcements.add(a);
                AnnouncementBannerAdapter adapter = new AnnouncementBannerAdapter(getActivity(),announcements);
                banner = find(R.id.announcement_banner);
                banner.setAdapter(adapter)
                        .addBannerLifecycleObserver(getViewLifecycleOwner())
                        .setIndicator(new CircleIndicator(getActivity()))
                        .setOnBannerListener((data, position) -> {
//                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
//                    LogUtils.d("position：" + position);
                            Log.i(TAG, "initViews: position" + position);
                        });
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

            @Override
            public void addRoomSucceed(String objId) {

            }

            @Override
            public void addRoomError() {

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
