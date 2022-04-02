package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;

public interface RoomContract {
    public interface IRoomPresenter{
        void getRooms();
    }

    interface IRoomModel{
        List<Room> getRooms();
    }

    interface IRoomView{

    }
}
