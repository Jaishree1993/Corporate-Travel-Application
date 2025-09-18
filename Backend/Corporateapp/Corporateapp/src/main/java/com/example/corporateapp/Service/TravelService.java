package com.example.corporateapp.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.corporateapp.Model.Booking;
import com.example.corporateapp.Model.Flight;
import com.example.corporateapp.Repository.BookingRepo;
import com.example.corporateapp.Repository.FlightRepo;

import jakarta.annotation.PostConstruct;

@Service
public class TravelService {
    private final FlightRepo flightRepo;
    private final BookingRepo bookingRepo;

    public TravelService(FlightRepo flightRepo, BookingRepo bookingRepo) {
        this.flightRepo = flightRepo;
        this.bookingRepo = bookingRepo;
    }
    @PostConstruct
    public void seed() {
        if (flightRepo.count() == 0) {
            flightRepo.save(new Flight(null, "DEL","BOM","Indigo", LocalDate.now().plusDays(7), 5000));
            flightRepo.save(new Flight(null, "DEL","BOM","Air India", LocalDate.now().plusDays(7), 5200));
            flightRepo.save(new Flight(null, "DEL","BLR","Vistara", LocalDate.now().plusDays(10), 7000));
        }
    }

    public List<Flight> searchFlights(String from, String to, LocalDate date) {
        
        return flightRepo.findByFromAirportIgnoreCaseAndToAirportIgnoreCaseAndDepartureDate(from, to, date);
    }
     public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
}
