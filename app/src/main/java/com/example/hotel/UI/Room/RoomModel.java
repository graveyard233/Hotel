package com.example.hotel.UI.Room;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotel.Bean.Room;
import com.example.hotel.R;
import com.example.hotel.UI.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RoomModel {

    public List<Room> mylist = new ArrayList<>();


//    public void dosomething(MainActivity mainActivity){
//        mainActivity.getMyR();
//    }

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("start to print");
                    for (int i = 0; i < mylist.size() - 1; i++) {
                        System.out.println("mylist:" + mylist.get(i).getRoomId());

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    //从网络获取数据,在model层操作数据
//    @Override
    public List<Room> getRooms(RoomContract roomContract) {

        String bql = "select * from Room";
        BmobQuery<Room> bmobQuery = new BmobQuery<Room>();

        bmobQuery.setSQL(bql);
        List<Room> mylist = new ArrayList<>();
        bmobQuery.doSQLQuery(new SQLQueryListener<Room>() {
            @Override
            public void done(BmobQueryResult<Room> bmobQueryResult, BmobException e) {
                List<Room> list = null;
                if(e ==null){
                    list = (List<Room>) bmobQueryResult.getResults();
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

                //回调list，让上一层实现这个接口的方法达成list数据传递回去
                if (roomContract != null){
                    roomContract.getRooms(list);
                }
            }


        });
        return null;
    }


    public List<Room> searchRoomByType(String type,RoomContract roomContract){
        BmobQuery<Room> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("type",type);
        bmobQuery.findObjects(new FindListener<Room>() {
            @Override
            public void done(List<Room> list, BmobException e) {
                if (e == null){
                    if (list.size() > 0){
                        Log.i("TAG", "search done: ");
                        if (roomContract != null){
                            roomContract.getRoomsByType(list);
                        }
                    } else {
                        Log.i("TAG", "done: but list is null");
                        roomContract.getRoomsByType(list);
                    }

                } else {
                    Log.e("TAG", "search error: " + e.getMessage());
                    roomContract.getRoomsByType(list);
                }
            }
        });
        return null;
    }

    public void addNewRoom(Room room,RoomContract roomContract){
        room.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    if (roomContract != null)
                        roomContract.addNewRoom(1,s);
                } else {
                    if (roomContract != null)
                        roomContract.addNewRoom(2,e.getMessage());
                }
            }
        });
    }





}
