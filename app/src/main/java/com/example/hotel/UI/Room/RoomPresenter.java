package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;
//P层调用model层获取数据
public class RoomPresenter implements RoomContract.IRoomPresenter{

    private final RoomContract.IRoomModel roomModel;

    public RoomPresenter(){
        roomModel = new RoomModel();
    }

    @Override
    public void getRooms() {
        roomModel.getRooms();
    }
}
