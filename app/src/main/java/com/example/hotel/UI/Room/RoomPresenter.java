package com.example.hotel.UI.Room;

import com.example.hotel.Bean.Room;

import java.util.List;
//P层调用model层获取数据
public class RoomPresenter{

    private RoomModel roomModel;

    public void getRoomsPresenter(RoomViewInterface viewInterface){
        roomModel = new RoomModel();
        roomModel.getRooms(new RoomContract() {
            @Override
            public void getRooms(List<Room> rooms) {
                if (viewInterface != null)
                {
                        viewInterface.getRoomsSucceed(rooms);
                }
            }

            @Override
            public void getRoomsByType(List<Room> rooms) {

            }

            @Override
            public void addNewRoom(int i, String msg) {

            }
        });
    }

    public void getRoomsByType(String type,RoomViewInterface viewInterface){
        roomModel = new RoomModel();
        roomModel.searchRoomByType(type, new RoomContract() {
            @Override
            public void getRooms(List<Room> rooms) {

            }

            @Override
            public void getRoomsByType(List<Room> rooms) {
                if (rooms.size() > 0){
                    viewInterface.getRoomsSucceed(rooms);
                }
                else {
                    viewInterface.getRoomError();
                }
            }

            @Override
            public void addNewRoom(int i, String msg) {

            }
        });
    }

    public void addRoom(Room room,RoomViewInterface viewInterface){
        roomModel = new RoomModel();
        roomModel.addNewRoom(room, new RoomContract() {
            @Override
            public void getRooms(List<Room> rooms) {

            }

            @Override
            public void getRoomsByType(List<Room> rooms) {

            }

            @Override
            public void addNewRoom(int i, String msg) {
                if (i == 1){
                    viewInterface.addRoomSucceed(msg);
                } else {
                    viewInterface.addRoomError();
                }
            }
        });
    }



}
