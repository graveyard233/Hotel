package com.example.hotel.Network.Service;

import android.content.Context;

import com.example.hotel.UI.MainActivity;

public class RoomService {
//
//    @GET("wether/?city=广州")
//    Call<BaseBean<Data>> getForecast();



//    @POST("post")
//    @FormUrlEncoded
//    Call<ResponseBody> postForm(@Field("username") String username,@Field("password") String pwd);

//    @GET("wether/?city=广州")
//    Flowable<BaseBean<Data>> getData();
    private Context context;
    private MainActivity activity;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public RoomService(MainActivity activity) {

        this.activity = activity;
    }

    public void getMethod(){
        activity.getMyR();
    }
}
