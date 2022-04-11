package com.example.hotel.Bean;

import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Order extends BmobObject {
    private User user;
    private String roomId;
    private BmobDate startTime;
    private BmobDate endTime;
    private Double price;
    private int isPay;//0是没付款，1是已付款
    private String userMassage;
    private String msgId;
    private List<Traveller> travellerList;

    public Order(){ }

    public Order(User user, String roomId, BmobDate startTime, BmobDate endTime, Double price, int isPay, String userMassage, String msgId, List<Traveller> travellerList) {
        this.user = user;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.isPay = isPay;
        this.userMassage = userMassage;
        this.msgId = msgId;
        this.travellerList = travellerList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public BmobDate getStartTime() {
        return startTime;
    }

    public void setStartTime(BmobDate startTime) {
        this.startTime = startTime;
    }

    public BmobDate getEndTime() {
        return endTime;
    }

    public void setEndTime(BmobDate endTime) {
        this.endTime = endTime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getUserMassage() {
        return userMassage;
    }

    public void setUserMassage(String userMassage) {
        this.userMassage = userMassage;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public List<Traveller> getTravellerList() {
        return travellerList;
    }

    public void setTravellerList(List<Traveller> travellerList) {
        this.travellerList = travellerList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", roomId='" + roomId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ", isPay=" + isPay +
                ", userMassage='" + userMassage + '\'' +
                ", msgId='" + msgId + '\'' +
                ", travellerList=" + travellerList +
                '}';
    }
}
