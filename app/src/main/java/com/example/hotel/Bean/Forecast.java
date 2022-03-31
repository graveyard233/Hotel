package com.example.hotel.Bean;

public class Forecast {
    private String date;
    private String high;
    private String fengli;
    private String low;
    private String fengxiang;
    private String type;
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setHigh(String high) {
        this.high = high;
    }
    public String getHigh() {
        return high;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }
    public String getFengli() {
        return fengli;
    }

    public void setLow(String low) {
        this.low = low;
    }
    public String getLow() {
        return low;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }
    public String getFengxiang() {
        return fengxiang;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", high='" + high + '\'' +
                ", fengli='" + fengli + '\'' +
                ", low='" + low + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
