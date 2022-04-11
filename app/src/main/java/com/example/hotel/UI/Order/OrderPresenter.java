package com.example.hotel.UI.Order;

import com.example.hotel.Bean.Order;

import java.util.List;

public class OrderPresenter {
    private OrderModel orderModel;

    public void getOrderModel(OrderViewInterface viewInterface) {
        orderModel = OrderModel.get();
        orderModel.getAllOrderList(new OrderContract() {
            @Override
            public void getAllOrders(List<Order> orders) {
                if (viewInterface != null){
                    if (orders.size() > 0)
                        viewInterface.getAllOrdersSucceed(orders);
                    else
                        viewInterface.getAllOrderError();
                }
                else {
                    viewInterface.getAllOrderError();
                }
            }

            @Override
            public void getOrderByid(List<Order> orders) {
                if (viewInterface != null){
                    if (orders.size() > 0)
                        viewInterface.getOrderById(orders);
                    else
                        viewInterface.getAllOrderError();

                }
                else {
                    viewInterface.getAllOrderError();
                }
            }
        });
    }
}
