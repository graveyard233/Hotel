package com.example.hotel.Bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Room extends BmobObject {
    private String roomId;
    private String type;
    private Double price;
    private String isBusy;//0是没人，1是有人
    private Double discount;
    private String imgUrl;
    private List<String> commentList;

    public Room(){

    }

    public Room(String roomId, String type, Double price, String isBusy, Double discount, String imgUrl, List<String> commentList) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.isBusy = isBusy;
        this.discount = discount;
        this.imgUrl = imgUrl;
        this.commentList = commentList;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(String isBusy) {
        this.isBusy = isBusy;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<String> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", isBusy='" + isBusy + '\'' +
                ", discount=" + discount +
                ", imgUrl='" + imgUrl + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
