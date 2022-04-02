package com.example.hotel.Network;

import cn.bmob.v3.Bmob;

public class MyBmob {
    private static volatile MyBmob mInstance;

    private MyBmob(){

    }

    public static MyBmob getInstance(){
        if (mInstance == null){
            synchronized (RetrofitClient.class){
                if (mInstance == null){
                    mInstance = new MyBmob();
                }
            }//尝试更新
        }
        return mInstance;
    }


}
