package com.example.hotel.Network.Service;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Forecast;
import com.example.hotel.Bean.Yesterday;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RoomService {

//    @GET("wether/?city=广州")
//    Flowable<BaseBean> getForecast();

    @POST("post")
    @FormUrlEncoded
    Call<ResponseBody> postForm(@Field("username") String username,@Field("password") String pwd);

//    @GET("wether/?city=广州")
//    Flowable<BaseBean<Yesterday>> getYesterday();
}
