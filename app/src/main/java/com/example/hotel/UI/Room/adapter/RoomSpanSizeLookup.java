package com.example.hotel.UI.Room.adapter;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.hotel.Bean.Person;

import java.util.List;

public class RoomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

//    private final List<Person>

    @Override
    public int getSpanSize(int position) {
        return 0;
    }
}
