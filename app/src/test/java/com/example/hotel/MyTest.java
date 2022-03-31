package com.example.hotel;

import android.util.Log;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Data;
import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTest {

    @Test
    public void test1(){
        System.out.println("哈哈哈");
        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
        roomService.getData().subscribe(new Consumer<BaseBean<Data>>() {
            @Override
            public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
                System.out.println(dataBaseBean.toString());
            }
        });

    }
}
