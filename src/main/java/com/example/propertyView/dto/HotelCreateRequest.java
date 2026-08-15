package com.example.propertyView.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HotelCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;
    private String description;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotNull(message = "Address is required")
    @Valid
    private AddressRequest address;
    @NotNull(message = "Contacts are required")
    @Valid
    private ContactRequest contacts;
    @Valid
    private ArrivalTimeRequest arrivalTime;

    public HotelCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public ContactRequest getContacts() {
        return contacts;
    }

    public void setContacts(ContactRequest contacts) {
        this.contacts = contacts;
    }

    public ArrivalTimeRequest getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTimeRequest arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}