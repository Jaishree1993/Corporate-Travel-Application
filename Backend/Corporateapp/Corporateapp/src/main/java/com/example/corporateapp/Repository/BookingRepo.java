package com.example.corporateapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.corporateapp.Model.Booking;

public interface BookingRepo extends JpaRepository<Booking,Long> {
    
}
