package com.example.hotel.UI.Room.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotel.Bean.Announcement;
import com.example.hotel.R;
import com.google.android.material.card.MaterialCardView;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;
import java.util.zip.Inflater;

public class AnnouncementBannerAdapter extends BannerAdapter<Announcement,AnnouncementBannerAdapter.BannerViewHolder> {

    private Context context;

    public AnnouncementBannerAdapter(List<Announcement> datas) {
        super(datas);
    }

    public AnnouncementBannerAdapter(Context context,List<Announcement> datas) {
        super(datas);
        this.context = context;
    }

    @Override
    public AnnouncementBannerAdapter.BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_manage_recycler_item,parent,false);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        return new AnnouncementBannerAdapter.BannerViewHolder(view);
    }

    @Override
    public void onBindView(AnnouncementBannerAdapter.BannerViewHolder holder, Announcement data, int position, int size) {
        holder.title.setText(data.getTitle());
        holder.announcement.setText(data.getText());
        holder.time.setText(data.getCreatedAt());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (holder.announcement.isSingleLine()){
                        holder.announcement.setSingleLine(false);
                    } else {
                        holder.announcement.setSingleLine(true);
                    }
                }
            }
        });
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        TextView announcement;
        MaterialCardView cardView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.announcement_title);
            time = itemView.findViewById(R.id.announcement_time);
            announcement = itemView.findViewById(R.id.announcement_text);
            cardView = itemView.findViewById(R.id.announcement_item);
        }
    }

}
