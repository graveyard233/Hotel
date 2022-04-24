package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;

public interface RoomContract {
//    interface IRoomPresenter{
//        void getRooms();
//    }

    void getRooms(List<Room> rooms);

    void getRoomsByType(List<Room> rooms);


}
