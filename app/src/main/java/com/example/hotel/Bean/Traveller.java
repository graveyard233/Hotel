package com.example.hotel.Bean;

import cn.bmob.v3.BmobObject;

public class Traveller extends BmobObject {
    private String travellerName;
    private String IDcard;

    public Traveller(String travellerName, String IDcard) {
        this.travellerName = travellerName;
        this.IDcard = IDcard;
    }

    public String getTravellerName() {
        return travellerName;
    }

    public void setTravellerName(String travellerName) {
        this.travellerName = travellerName;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }
}
