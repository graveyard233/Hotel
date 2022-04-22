package com.example.hotel.UI.Manage.CommentManage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hotel.Bean.Announcement;
import com.example.hotel.R;
import com.example.hotel.UI.Announcement.AnnouncementPresenter;
import com.example.hotel.UI.Announcement.AnnouncementViewInterface;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Manage.CommentManage.adapter.AnnouncementRecyclerAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;


public class CommentManageFragment extends BaseFragment implements View.OnClickListener {

    private FloatingActionButton fab_add;
    private FloatingActionButton fab_delete;

    private AnnouncementPresenter ap = new AnnouncementPresenter();

    private List<Announcement> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnnouncementRecyclerAdapter adapter;


    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.comment_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_comment_manage_24);
        fab_add = find(R.id.fab_comment_add);
        fab_delete = find(R.id.fab_comment_delete);

        fab_add.setOnClickListener(this);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = find(R.id.comment_manage_recycler);
        ap.getAllAnnouncement(1, new AnnouncementViewInterface() {
            @Override
            public void addAnnouncement(String objId, int i) {

            }

            @Override
            public void getAllAnnouncementSucceed(List<Announcement> list) {
                adapter = new AnnouncementRecyclerAdapter(list);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void getAllAnnouncementError() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_comment_add:
                Announcement a = new Announcement();
                a.setTitle("test");
                a.setText("abcdefghijklmnopqrstuvwxyz");
                a.setExpendJson("json");
                ap.addAnnouncement(a, new AnnouncementViewInterface() {
                    @Override
                    public void addAnnouncement(String objId, int i) {
                        if (i == 1){
                            System.out.println(objId);
                        }
                        else if (i == 2){
                            System.out.println(objId);
                        } else {
                            System.out.println("ffff");
                        }
                    }

                    @Override
                    public void getAllAnnouncementSucceed(List<Announcement> list) {

                    }

                    @Override
                    public void getAllAnnouncementError() {

                    }
                });
                break;
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_manage;
    }


}