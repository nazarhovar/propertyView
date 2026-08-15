package com.example.propertyView.dto;

public class ArrivalTimeRequest {

    private String checkIn;
    private String checkOut;

    public ArrivalTimeRequest() {
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}