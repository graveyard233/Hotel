package com.example.hotel.UI.Room.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.hotel.Bean.Person;
import com.example.hotel.Bean.Room;

import java.util.List;

public class RoomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private final List<Room> rooms;

    public RoomSpanSizeLookup(List<Room> rooms){
        this.rooms = rooms;
    }

    @Override
    public int getSpanSize(int position) {
        return rooms.get(position).getSpanSize();
    }
}
