package com.example.hotel.UI.Manage.CommentManage.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hotel.Bean.Announcement;
import com.example.hotel.R;

import java.util.List;

public class AnnouncementRecyclerAdapter extends BaseQuickAdapter<Announcement, BaseViewHolder> {
    public AnnouncementRecyclerAdapter(@Nullable List<Announcement> data) {
        super(R.layout.comment_manage_recycler_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Announcement item) {
        helper.setText(R.id.announcement_title,item.getTitle())
                .setText(R.id.announcement_time,item.getCreatedAt())
                .setText(R.id.announcement_text,item.getText());
    }
}
