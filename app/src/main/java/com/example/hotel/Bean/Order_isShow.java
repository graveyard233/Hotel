package com.example.hotel.Bean;

public class Order_isShow {
    private Order order;
    private Boolean isShow = false;

    public Order_isShow(){

    }

    public Order_isShow(Order order){
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Boolean isShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "Order_isShow{" +
                "order=" + order +
                ", isShow=" + isShow +
                '}';
    }
}
