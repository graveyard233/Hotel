package com.example.hotel.Bean;

import cn.bmob.v3.BmobObject;

public class Announcement extends BmobObject {
    private String title;
    private String text;
    private String expendJson;

    public Announcement() {
    }

    public Announcement(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public Announcement(String title, String text, String expendJson) {
        this.title = title;
        this.text = text;
        this.expendJson = expendJson;
    }

    public String getExpendJson() {
        return expendJson;
    }

    public void setExpendJson(String expendJson) {
        this.expendJson = expendJson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
