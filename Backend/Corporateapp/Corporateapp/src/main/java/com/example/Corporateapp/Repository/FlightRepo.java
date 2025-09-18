package com.example.Corporateapp.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Corporateapp.Model.Flight;

public interface FlightRepo extends JpaRepository<Flight,Long> {
    List<Flight> findByFromAirportIgnoreCaseAndToAirportIgnoreCaseAndDepartureDate(
        String fromAirport, String toAirport, LocalDate departureDate);
}
