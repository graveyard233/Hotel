package com.example.hotel.UI.Order;

import com.example.hotel.Bean.Order;

import java.util.List;

public interface OrderContract {//回调数据

    void getAllOrders(List<Order> orders);

    void getOrdersById(List<Order> orders);
}
