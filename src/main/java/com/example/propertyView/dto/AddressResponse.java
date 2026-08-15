package com.example.propertyView.dto;

public class AddressResponse {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;

    public AddressResponse() {
    }

    public AddressResponse(
            Integer houseNumber,
            String street,
            String city,
            String country,
            String postCode
    ) {
        this.houseNumber = houseNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }
}