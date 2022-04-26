package com.example.hotel.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;
import com.example.hotel.UI.Order.BmobTimeUtil;
import com.example.hotel.UI.Order.OrderPresenter;
import com.example.hotel.UI.Order.OrderViewInterface;
import com.example.hotel.UI.Room.RoomPresenter;
import com.example.hotel.UI.Room.RoomViewInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class InitRoomIsBusyIntentService extends IntentService {

    private static final String TAG = "InitRoomIsBusyIntentService";
    private RoomPresenter roomPresenter;
    private OrderPresenter orderPresenter;

    public InitRoomIsBusyIntentService() {
        super("InitRoomIsBusyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            System.out.println("start intent");
            roomPresenter = new RoomPresenter();
            roomPresenter.getRoomsPresenter(new RoomViewInterface() {
                @Override
                public void getRoomsSucceed(List<Room> rooms) {
                    if (rooms.size() > 0){
                        Date today = new Date();
                        System.out.println(today);
                        orderPresenter = new OrderPresenter();

                        for (int i = 0; i < rooms.size(); i++) {
                            System.out.println(rooms.get(i).getIsBusy());
                            orderPresenter.setRoom(rooms.get(i));
                            int finalI = i;
                            orderPresenter.getOrderModel(new OrderViewInterface() {
                                @Override
                                public void getAllOrdersSucceed(List<Order> orders) {

                                }

                                @Override
                                public void getAllOrderError() {

                                }

                                @Override
                                public void getOrderById(List<Order> orders) {

                                    //拿到这个房间已被预定的时间列表
                                    List<Date> timelistAll = new ArrayList<>();
                                    for (int j = 0; j < orders.size(); j++) {
                                        List<Date> temp_list = new ArrayList<>();
                                        temp_list = BmobTimeUtil.getDaysBetween(orders.get(j).getStartTime().getDate(),
                                                orders.get(j).getEndTime().getDate());
                                        for (int k = 0; k < temp_list.size(); k++) {
                                            timelistAll.add(temp_list.get(k));
                                        }
                                    }

                                    System.out.println(timelistAll);

                                    List<String> slist = new ArrayList<>();
                                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    if (timelistAll.size() <= 0){
                                        Log.e(TAG, "这个房间没被预定过" + orders.get(0).getRoomId());
                                        rooms.get(finalI).setIsBusy("空闲");
                                        rooms.get(finalI).update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null){
                                                    Log.i(TAG, "is busy done: ");
                                                } else {
                                                    Log.e(TAG, "error: " + e.getMessage() );
                                                }
                                            }
                                        });
                                    } else {
                                        //房间被预定过
                                        for (int i = 0; i < timelistAll.size(); i++) {
                                            String temp1 = sdf.format(timelistAll.get(i));
                                            slist.add(temp1);
                                        }
                                        String today_s = sdf.format(today);
                                        Log.i(TAG, "today:" + today_s);
                                        if (slist.contains(today_s)){
                                            Log.i(TAG, rooms.get(finalI).getRoomId() + ": 这个房间今天有被预定");
                                            rooms.get(finalI).setIsBusy("忙");
                                            rooms.get(finalI).update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null){
                                                        Log.i(TAG, "is busy done: ");
                                                    } else {
                                                        Log.e(TAG, "error: " + e.getMessage() );
                                                    }
                                                }
                                            });
                                        } else {
                                            Log.i(TAG, rooms.get(finalI).getRoomId() + ": 这个房间今天没有预定");
                                            rooms.get(finalI).setIsBusy("空闲");
                                            rooms.get(finalI).update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null){
                                                        Log.i(TAG, "is busy done: ");
                                                    } else {
                                                        Log.e(TAG, "error: " + e.getMessage() );
                                                    }
                                                }
                                            });

                                        }
                                    }
                                }

                                @Override
                                public void getOrderByIdError() {
                                    Log.e(TAG, "getOrderByIdError: 这个房间估计没有被预定过" + rooms.get(finalI));
                                    rooms.get(finalI).setIsBusy("空闲");
                                    rooms.get(finalI).update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Log.i(TAG, "is busy done: ");
                                            } else {
                                                Log.e(TAG, "error: " + e.getMessage() );
                                            }
                                        }
                                    });

                                }

                                @Override
                                public void addOrder(String objId, int i) {

                                }

                                @Override
                                public void cancelOrder(String objId) {

                                }
                            });
//                            List<Date> list_choice = new ArrayList<>();
//                            list_choice = BmobTimeUtil.getDaysBetween(order.getStartTime().getDate(),order.getEndTime().getDate());

                        }
                    }
                }

                @Override
                public void getRoomError() {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "service onDestroy: ");
    }
}