package com.example.hotel;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.hotel.Bean.BaseBean;
import com.example.hotel.Bean.Forecast;
import com.example.hotel.Network.RetrofitClient;
import com.example.hotel.Network.Service.RoomService;

import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        RoomService roomService = RetrofitClient.getmInstance().getService(RoomService.class);
        roomService.getForecast().subscribe(new Consumer<BaseBean>() {
            @Override
            public void accept(BaseBean baseBean) throws Throwable {
                System.out.println(baseBean);
            }
        });
    }
}