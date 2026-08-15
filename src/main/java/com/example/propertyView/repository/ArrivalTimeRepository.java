package com.example.propertyView.repository;

import com.example.propertyView.entity.ArrivalTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArrivalTimeRepository extends JpaRepository<ArrivalTime, Long> {
}