package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Data;
import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;

import java.io.IOException;

import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Hotel);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        link();
    }

    private void link() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("haha");
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder()
//                            .url("https://tenapi.cn/wether/?city=广州")
//                            .build();
//                    Response response = client.newCall(request).execute();
//                    System.out.println(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//        Retrofit retrofit;
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://tenapi.cn")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        RoomService roomService = retrofit.create(RoomService.class);
        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
//        roomService.getForecast().enqueue(new Callback<BaseBean<Data>>() {
//            @Override
//            public void onResponse(Call<BaseBean<Data>> call, Response<BaseBean<Data>> response) {
//                Log.i("TAG", "onResponse: " + response.body().getData());
//            }
//
//            @Override
//            public void onFailure(Call<BaseBean<Data>> call, Throwable t) {
//
//            }
//        });
        roomService.getData().subscribe(new Consumer<BaseBean<Data>>() {
            @Override
            public void accept(BaseBean<Data> dataBaseBean) throws Throwable {
                System.out.println(dataBaseBean.toString());
            }
        });
    }
}