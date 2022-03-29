package com.example.hotel.Network.Service;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Forecast;
import com.example.hotel.Bean.Yesterday;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;

public interface RoomService {

    @GET("wether/?city=广州")
    Flowable<BaseBean> getForecast();

//    @GET("wether/?city=广州")
//    Flowable<BaseBean<Yesterday>> getYesterday();
}
