package com.example.hotel.UI.Room;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.hotel.Bean.Person;
import com.example.hotel.Bean.Room;
import com.example.hotel.Network.Service.RoomService;
import com.example.hotel.UI.MainActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class RoomModel implements RoomContract.IRoomModel{

    public void testData() {

        String bql = "select * from Room";
        BmobQuery<Room> bmobQuery = new BmobQuery<Room>();

        bmobQuery.setSQL(bql);
        List<Room> mylist = new ArrayList<>();
        bmobQuery.doSQLQuery(new SQLQueryListener<Room>() {
            @Override
            public void done(BmobQueryResult<Room> bmobQueryResult, BmobException e) {
                if(e ==null){
                    List<Room> list = (List<Room>) bmobQueryResult.getResults();
                    if(list!=null && list.size()>0){
                        for (int i = 0; i < list.size(); i++) {
                            Log.i("search by sql", "name: " + list.get(i).getType() + ",objId = " + list.get(i).getObjectId());
                            if (list.get(i)!=null)
                                mylist.add(list.get(i));
                        }
                    }else{
                        Log.i("smile", "查询成功，无数据返回");
                    }
                }else{
                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                }


            }


        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mylist.size() > 0){
                    for (int i = 0; i < mylist.size(); i++) {
                        Log.i("TAG", "testData: " + mylist.get(i).getRoomId());
                    }
                } else {
                    Log.e("TAG", "testData: 数据没准备好");
                }
            }
        },2000);


    }


    public void getandtest(MainActivity activity){
        RoomService roomService = new RoomService(activity);
        roomService.getMethod();

    }

    //从网络获取数据,在model层操作数据
    @Override
    public List<Room> getRooms() {
        //...

        return null;
//        bmobQuery.doSQLQuery(new SQLQueryListener<Person>() {
//            @Override
//            public void done(BmobQueryResult<Person> bmobQueryResult, BmobException e) {
//                if(e ==null){
//                    List<Person> list = (List<Person>) bmobQueryResult.getResults();
//                    if(list!=null && list.size()>0){
//                        for (int i = 0; i < list.size(); i++) {
//                            Log.i("search by sql", "name: " + list.get(i).getName() + ",objId = " + list.get(i).getObjectId());
//                        }
//                    }else{
//                        Log.i("smile", "查询成功，无数据返回");
//                    }
//                }else{
//                    Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
//                }
//            }
//        });
//        return null;
    }


}
