package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;

public interface RoomViewInterface {

    void getRoomsSucceed(List<Room> rooms);
    void getRoomError();

}
