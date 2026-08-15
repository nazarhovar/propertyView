package com.example.propertyView.dto;

public class ArrivalTimeResponse {

    private String checkIn;
    private String checkOut;

    public ArrivalTimeResponse() {
    }

    public ArrivalTimeResponse(String checkIn, String checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
}