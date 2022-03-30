package com.example.hotel;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Forecast;
import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() throws IOException {
//        assertEquals(4, 2 + 2);

        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
        System.out.println("hahaha");
//        Call<ResponseBody> call = roomService.postForm("lyd","1230");
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    Log.i("TAG", "onResponse: " + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

//        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);


//        roomService.getForecast().subscribe(new Consumer<BaseBean>() {
//            @Override
//            public void accept(BaseBean baseBean) throws Throwable {
//                System.out.println(baseBean);
//            }
//        });
    }
}