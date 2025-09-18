package com.example.Corporateapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Corporateapp.Model.Booking;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    
}
