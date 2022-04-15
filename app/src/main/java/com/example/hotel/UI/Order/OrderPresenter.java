package com.example.hotel.UI.Order;

import com.example.hotel.Bean.Order;
import com.example.hotel.Bean.Room;

import java.util.List;

public class OrderPresenter {
    private OrderModel orderModel;

    private Room room;

    public void setRoom(Room room) {
        this.room = room;
    }

    public void getOrderModel(OrderViewInterface viewInterface) {
        orderModel = OrderModel.get();
        orderModel.setRoom(room);
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
            public void getOrdersById(List<Order> orders) {

            }

            @Override
            public void getOrdersById(List<Order> orders, int i) {

            }


        });

        orderModel.getOrderById(new OrderContract() {
            @Override
            public void getAllOrders(List<Order> orders) {

            }

            @Override
            public void getOrdersById(List<Order> orders) {
                if (viewInterface != null){
                    if (orders.size() > 0)
                        viewInterface.getOrderById(orders);
                    else
                        viewInterface.getOrderByIdError();

                }
                else {
                    viewInterface.getOrderByIdError();
                }
            }

            @Override
            public void getOrdersById(List<Order> orders, int i) {

            }
        });
    }

    public void getOrderModel(int i,OrderViewInterface viewInterface) {
        orderModel = OrderModel.get();
        orderModel.setRoom(room);
        orderModel.getAllOrderList(i,new OrderContract() {
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
            public void getOrdersById(List<Order> orders) {

            }

            @Override
            public void getOrdersById(List<Order> orders, int i) {

            }


        });

        orderModel.getOrderById(new OrderContract() {
            @Override
            public void getAllOrders(List<Order> orders) {

            }

            @Override
            public void getOrdersById(List<Order> orders) {
                if (viewInterface != null){
                    if (orders.size() > 0)
                        viewInterface.getOrderById(orders);
                    else
                        viewInterface.getOrderByIdError();

                }
                else {
                    viewInterface.getOrderByIdError();
                }
            }

            @Override
            public void getOrdersById(List<Order> orders, int i) {
                viewInterface.getOrderById(orders,i);
            }
        });
    }
}
