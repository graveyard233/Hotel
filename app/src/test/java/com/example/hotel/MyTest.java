package com.example.hotel;

import android.util.Log;

import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTest {

    @Test
    public void test1(){
        System.out.println("哈哈哈");
        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
        roomService.getForecast().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.i("TAG", "onResponse: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
