package com.example.hotel.UI.Room.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.List;

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater layoutInflater;

    private final Context context;
    private final List<Room> rooms;

    public RoomRecyclerViewAdapter(Context context, List<Room> rooms){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.rooms = rooms;
    }

    public void setRooms(List<Room> rooms){
        this.rooms.clear();
        this.rooms.addAll(rooms);
        notifyDataSetChanged();//更新整个view
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(viewType, parent, false);
        if (viewType == R.layout.room_recycler_text_img){
            return new MultiViewHolder(view);
        } else
            return new SingleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Room room = rooms.get(position);

        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case R.layout.room_recycler_banner:

                ((Banner)holder.itemView).setAdapter(new BannerImageAdapter<String>(room.getCommentList()) {
                    @Override
                    public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                        //图片加载自己实现
                        Glide.with(holder.itemView)
                                .load("https://s3.bmp.ovh/imgs/2022/04/02/0601ae530f5c0119.png")
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                })
                        .addBannerLifecycleObserver((LifecycleOwner) context)//添加生命周期观察者
                        .setIndicator(new CircleIndicator(context));
                //更多使用方法仔细阅读文档，或者查看demo

                break;
            case R.layout.room_recycler_text_img:
                MultiViewHolder multiViewHolder = (MultiViewHolder) holder;
                multiViewHolder.textView.setText(room.getType() + "价格 :" + room.getPrice() + "元");
                Glide.with(holder.itemView)
                        .load("https://moetu.org/image/7bIp5")
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(multiViewHolder.imageView);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        Room room = rooms.get(position);
        if (room.getType().equals("豪华套房")){//是豪华套房
            //豪华套房banner图
            return R.layout.room_recycler_banner;
        }else {
            //显示文字加图片
            return R.layout.room_recycler_text_img;
        }

    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder {

        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        public MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_Mtext);
            imageView = itemView.findViewById(R.id.item_Mimg);
        }
    }
}
