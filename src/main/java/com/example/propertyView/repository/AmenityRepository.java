package com.example.propertyView.repository;

import com.example.propertyView.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    Optional<Amenity> findByNameIgnoreCase(String name);
}