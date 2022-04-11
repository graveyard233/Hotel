package com.example.hotel.UI.Order;

import com.example.hotel.Bean.Order;

import java.util.List;

public interface OrderViewInterface {

    void getAllOrdersSucceed(List<Order> orders);
    void getOrderError();
}
