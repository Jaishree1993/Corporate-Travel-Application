package com.example.corporateapp.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Arrays;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;


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
    try {
        System.out.println("SEED: starting");
        InputStream is = getClass().getClassLoader().getResourceAsStream("data/flights.json");
        if (is == null) {
            System.out.println("SEED ERROR: flights.json not found in classpath at /data/flights.json");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Flight[] arr = mapper.readValue(is, Flight[].class);
        List<Flight> flights = Arrays.asList(arr);
        System.out.println("SEED: parsed " + flights.size() + " flights");
        flightRepo.saveAll(flights);
        System.out.println("SEED: saved " + flightRepo.count() + " flights in repo");
    } catch (Exception e) {
        System.out.println("SEED EXCEPTION:");
        e.printStackTrace();
    }
}



    /*public void seed() {
        if (flightRepo.count() == 0) {
            flightRepo.save(new Flight(null, "DEL","BOM","Indigo", LocalDate.now().plusDays(7), 5000));
            flightRepo.save(new Flight(null, "DEL","BOM","Air India", LocalDate.now().plusDays(7), 5200));
            flightRepo.save(new Flight(null, "DEL","BLR","Vistara", LocalDate.now().plusDays(10), 7000));
        }
    }*/

    public List<Flight> searchFlights(String from, String to, LocalDate date) {
        
       return flightRepo.findByOriginIgnoreCaseAndDestinationIgnoreCaseAndDate(from, to, date);
    }
     public Booking createBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
    public List<Flight> getAllFlights() {
    return flightRepo.findAll();
}

}
