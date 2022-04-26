package com.example.hotel.UI.Mine;

import android.util.Log;

import com.example.hotel.Bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MineModel {
    private volatile static MineModel mInstance;

    private MineModel(){

    }

    public static MineModel get(){
        if (mInstance == null) {
            Log.i("MineModel get()", "MineModel没有单例，创建一个");
            synchronized (MineModel.class) {
                if (mInstance == null) {
                    mInstance = new MineModel();
                }
            }
        }
        else {
            Log.i("MineModel get()", "已经有一个MineModel了");
        }
        return mInstance;
    }

    public List<User> getAllUserList(MineContract mineContract){
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereNotEqualTo("username","admin");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    Log.i("TAG", "done: " + list.size());
                } else {
                    Log.e("BMOB", e.toString());
                }

                //回调list，让上一层实现这个接口的方法达成list数据传递回去
                if (mineContract != null){
                    mineContract.getAllUserList(list);
                }
            }
        });
        return null;
    }

    public List<User> getUserByUserName(String username,MineContract mineContract){
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",username);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    Log.i("TAG", "done: " + list.get(0).getUsername());
                    //回调list，让上一层实现这个接口的方法达成list数据传递回去
                    if (mineContract != null){
                        mineContract.getUserByUserName(list.get(0));
                    }
                } else {
                    if (mineContract != null){
                        mineContract.getUserByUserName(null);
                    }
                }


            }
        });
        return null;
    }
}
