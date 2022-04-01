package com.example.hotel.Bean;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private String IDcard;

    private String gender;
    private int age;
    private String phone;

    public User(){

    }

    public User(String IDcard, String gender, int age, String phone) {
        this.IDcard = IDcard;

        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }



    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }



    public String getSex() {
        return gender;
    }

    public void setSex(String sex) {
        this.gender = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + super.getUsername() + '\'' +
                ", IDcard='" + IDcard + '\'' +
                ", sex='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
