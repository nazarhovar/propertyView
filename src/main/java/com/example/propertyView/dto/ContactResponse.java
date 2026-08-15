package com.example.propertyView.dto;

public class ContactResponse {

    private String phone;
    private String email;

    public ContactResponse() {
    }

    public ContactResponse(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}