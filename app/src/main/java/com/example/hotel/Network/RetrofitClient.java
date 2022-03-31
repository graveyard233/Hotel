package com.example.hotel.Network;

//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    private static volatile RetrofitClient mInstance;
//
//    private static final String BASE_URL = "https://tenapi.cn/";
////    private static final String BASE_URL = "https://www.httpbin.org/";
//
//    private Retrofit retrofit;//
//
//    private RetrofitClient(){
//
//    }
//
//    public static RetrofitClient getmInstance() {
//        if (mInstance == null){
//            synchronized (RetrofitClient.class){
//                if (mInstance == null){
//                    mInstance = new RetrofitClient();
//                }
//            }//尝试更新
//        }
//        return mInstance;
//    }
//
//    public <T> T getService(Class<T> cls){
//        return getRetrofit().create(cls);//传递实例对象，retrotifit会帮我生成对应的接口代理对象
//    }
//
//    private synchronized Retrofit getRetrofit() {
//        if (retrofit == null){//通过接口代理对象来访问网络
//            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                    .build();
//        }
//
//        return retrofit;
//    }


}
