package com.example.hotel.UI.Manage.CommentManage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hotel.Bean.Announcement;
import com.example.hotel.R;
import com.example.hotel.UI.Announcement.AnnouncementPresenter;
import com.example.hotel.UI.Announcement.AnnouncementViewInterface;
import com.example.hotel.UI.Base.BaseFragment;
import com.example.hotel.UI.Manage.CommentManage.adapter.AnnouncementRecyclerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class CommentManageFragment extends BaseFragment implements View.OnClickListener, OnRefreshListener {


    private AnnouncementPresenter ap = new AnnouncementPresenter();

    private List<Announcement> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private AnnouncementRecyclerAdapter adapter;

    private FloatingActionButton fab;

    private RefreshLayout refreshLayout;

    @Override
    protected void initViews() {
        ((CollapsingToolbarLayout) requireActivity().findViewById(R.id.collapsingToolBarLayout))
                .setTitle(getString(R.string.comment_manage_fragment_title));
        ((ImageView) requireActivity().findViewById(R.id.toolbarIconImg))
                .setImageResource(R.drawable.ic_comment_manage_24);


        fab = getActivity().findViewById(R.id.manage_activity_floatingActionButton);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(this);

        refreshLayout = find(R.id.refreshLayout_comment_manage);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setOnRefreshListener(this);


//        initRecyclerView();
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
            case R.id.manage_activity_floatingActionButton:
                Intent to_edit = new Intent(getActivity(),EditeAnnouncementActivity.class);
                startActivity(to_edit);
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

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
                refreshLayout.finishRefresh(1500);

                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        TextView t = view.findViewById(R.id.announcement_text);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            if (t.isSingleLine()){
                                t.setSingleLine(false);
                            } else {
                                t.setSingleLine(true);
                            }
                        }

                    }
                });

            }

            @Override
            public void getAllAnnouncementError() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh(refreshLayout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_manage;
    }


}