package com.example.propertyView.dto;

import java.util.List;

public class HotelResponse {

    private Long id;
    private String name;
    private String description;
    private String brand;
    private AddressResponse address;
    private ContactResponse contacts;
    private ArrivalTimeResponse arrivalTime;
    private List<String> amenities;

    public HotelResponse() {
    }

    public HotelResponse(
            Long id,
            String name,
            String description,
            String brand,
            AddressResponse address,
            ContactResponse contacts,
            ArrivalTimeResponse arrivalTime,
            List<String> amenities
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.address = address;
        this.contacts = contacts;
        this.arrivalTime = arrivalTime;
        this.amenities = amenities;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public ContactResponse getContacts() {
        return contacts;
    }

    public ArrivalTimeResponse getArrivalTime() {
        return arrivalTime;
    }

    public List<String> getAmenities() {
        return amenities;
    }
}