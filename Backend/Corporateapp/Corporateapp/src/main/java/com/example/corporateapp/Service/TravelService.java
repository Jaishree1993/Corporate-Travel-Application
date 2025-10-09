package com.example.corporateapp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Arrays;
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

    // Flexible search by route, then filter in Java
    public List<Flight> searchFlights(String from, String to, LocalDate date,
                                      Double maxPrice, Integer stops, String departureTime) {
        List<Flight> flights = flightRepo.findByOriginIgnoreCaseAndDestinationIgnoreCase(from, to);

        return flights.stream()
            .filter(f -> f.getDate().equals(date))
            .filter(f -> maxPrice == null || f.getPrice() <= maxPrice)
            .filter(f -> stops == null || f.getStops() == stops)
            .filter(f -> {
                if (departureTime == null) return true;
                int hour = f.getDeparture_time().getHour();
                switch (departureTime.toLowerCase()) {
                    case "morning": return hour >= 5 && hour < 12;
                    case "afternoon": return hour >= 12 && hour < 17;
                    case "evening": return hour >= 17 && hour < 23;
                    default: return true;
                }
            })
            .toList();
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