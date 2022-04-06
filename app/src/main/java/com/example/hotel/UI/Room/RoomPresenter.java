package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;
//P层调用model层获取数据
public class RoomPresenter{

    public void getRoomsPresenter(RoomViewInterface viewInterface){
        RoomModel roomModel = new RoomModel();
        roomModel.getRooms(new RoomContract() {
            @Override
            public void getRooms(List<Room> rooms) {
                if (viewInterface != null){
                    viewInterface.getRoomsSucceed(rooms);
                }
            }
        });
    }

}
