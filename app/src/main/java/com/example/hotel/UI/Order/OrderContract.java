package com.example.hotel.UI.Order;

import com.example.hotel.Bean.Order;

import java.util.List;

public interface OrderContract {

    void getAllOrders(List<Order> orders);

    void getOrderByid(List<Order> orders);
}
