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
import cn.bmob.v3.listener.SQLQueryListener;

public class RoomModel {

    public List<Room> mylist = new ArrayList<>();

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what){
                case 10:{
                    System.out.println("yesyesyes from roomModel");
//                    EditText editText = findViewById(R.id.edit_query);
                    List<Room> theList = (List<Room>) message.obj;
                    for (int i = 0; i < theList.size(); i++) {
                        System.out.println(theList.get(i).getType());
                        mylist.add(theList.get(i));
                    }
                }
            }
            return false;
        }
    });

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public Handler getmHandler() {
        return mHandler;
    }
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
        //...
//        try {
//            Thread.sleep(3000);
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (mylist.size() > 0)
//            return mylist;
//        else
//            return null;
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

                if (roomContract != null){
                    roomContract.getRooms(list);
                }
            }


        });
        return null;
    }





}
