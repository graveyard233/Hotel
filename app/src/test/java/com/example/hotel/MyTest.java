package com.example.hotel;

import android.util.Log;
import android.widget.Toast;

import com.example.hotel.Bean.Person;
import com.example.hotel.UI.MainActivity;

import org.junit.Test;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

//import io.reactivex.rxjava3.functions.Consumer;
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class MyTest {

    @Test
    public void test1(){
        Bmob.initialize(Bmob.getApplicationContext(),"f6017516ea38b947a8214fa98dbec40f");
        Person p2 = new Person();
        p2.setName("cjh");
        p2.setAddress("湖南工业大学");
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.i("TAG", "done: " + objectId);
                }else{
                    Log.e("TAG", "error: " + e.getMessage());
                }
            }
        });

    }
}
