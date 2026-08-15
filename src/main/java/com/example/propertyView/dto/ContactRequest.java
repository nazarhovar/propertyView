package com.example.propertyView.dto;

import jakarta.validation.constraints.NotBlank;

public class ContactRequest {

    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Email is required")
    private String email;

    public ContactRequest() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}