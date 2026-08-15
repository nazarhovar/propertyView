package com.example.propertyView.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String brand;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "contacts_id")
    private Contact contacts;

    @OneToOne
    @JoinColumn(name = "arrival_time_id")
    private ArrivalTime arrivalTime;

    @ManyToMany
    @JoinTable(
            name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    public Hotel() {
    }

    public Long getId() {
        return id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Contact getContacts() {
        return contacts;
    }

    public void setContacts(Contact contacts) {
        this.contacts = contacts;
    }

    public ArrivalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }
}