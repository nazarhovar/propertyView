package com.example.propertyView.dto;

import jakarta.validation.constraints.NotBlank;

public class ArrivalTimeRequest {

    @NotBlank(message = "Check-in time is required")
    private String checkIn;

    @NotBlank(message = "Check-out time is required")
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