package com.example.hotel.UI.Order;

import android.util.Log;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class OrderModel {

    public List<Order> mylist = new ArrayList<>();

    private volatile static OrderModel mInstance;

    private Room room;

    public void setRoom(Room room) {
        this.room = room;
    }

    private OrderModel(){}

    public static OrderModel get(){
        if (mInstance == null) {
            Log.i("OrderModel get()", "OrderModel没有单例，创建一个");
            synchronized (OrderModel.class) {
                if (mInstance == null) {
                    mInstance = new OrderModel();
                }
            }
        }
        else {
            Log.i("OrderModel get()", "已经有一个OrderModel了");
        }
        return mInstance;
    }

    public List<Order> getAllOrderList(OrderContract orderContract) {
        String bql = "select * from Order";
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();

        bmobQuery.setSQL(bql);
        bmobQuery.doSQLQuery(new SQLQueryListener<Order>() {
            @Override
            public void done(BmobQueryResult<Order> bmobQueryResult, BmobException e) {
                List<Order> list = null;
                if (e == null){
                    list = (List<Order>) bmobQueryResult.getResults();
                    if (list != null && list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("search order by sql", "done: " +
                                    list.get(i).toString());
                        }
                    }else {
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else {
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }

                //回调list，让上一层实现这个接口的方法达成list数据传递回去
                if (orderContract != null){
                    orderContract.getAllOrders(list);
                }
            }
        });
        return null;
    }

    public List<Order> getOrderById(OrderContract orderContract){
        String bql = "select * from Order where roomId = " + "'" + room.getRoomId() + "'";
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();

        bmobQuery.setSQL(bql);
        bmobQuery.doSQLQuery(new SQLQueryListener<Order>() {
            @Override
            public void done(BmobQueryResult<Order> bmobQueryResult, BmobException e) {
                List<Order> list = null;
                if (e == null){
                    list = (List<Order>) bmobQueryResult.getResults();
                    if (list != null && list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("search order by Id and sql", "done: " +
                                    list.get(i).toString());
                        }
                    }else {
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else {
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }

                //回调list，让上一层实现这个接口的方法达成list数据传递回去
                if (orderContract != null){
                    orderContract.getOrdersById(list);
                }
            }
        });
        return null;
    }

    public List<Order> getAllOrderList(int i,OrderContract orderContract) {
        String bql = "select * from Order";
        BmobQuery<Order> bmobQuery = new BmobQuery<Order>();

        bmobQuery.setSQL(bql);
        bmobQuery.doSQLQuery(new SQLQueryListener<Order>() {
            @Override
            public void done(BmobQueryResult<Order> bmobQueryResult, BmobException e) {
                List<Order> list = null;
                if (e == null){
                    list = (List<Order>) bmobQueryResult.getResults();
                    if (list != null && list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("search order by sql", "done: " +
                                    list.get(i).toString());
                        }
                    }else {
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else {
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }

                //回调list，让上一层实现这个接口的方法达成list数据传递回去
                if (orderContract != null){
                    orderContract.getOrdersById(list,2);
                }
            }
        });
        return null;
    }

}
