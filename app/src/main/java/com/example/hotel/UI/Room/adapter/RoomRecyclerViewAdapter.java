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

public class RoomRecyclerViewAdapter extends RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder> {

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

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView typeTextView;
        TextView priceTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.item_MType);
            priceTextView = itemView.findViewById(R.id.item_MPrice);
            imageView = itemView.findViewById(R.id.item_Mimg);
        }
    }




    @NonNull
    @Override
    public RoomRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.room_recycler_text_img,parent,false);

        RoomRecyclerViewAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.priceTextView.setText(rooms.get(position).getPrice().toString());
        holder.typeTextView.setText(rooms.get(position).getType());
        Glide.with(context)
                .load(rooms.get(position).getImgUrl())
                .placeholder(R.drawable.ic_bottom_room_24)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }




}
