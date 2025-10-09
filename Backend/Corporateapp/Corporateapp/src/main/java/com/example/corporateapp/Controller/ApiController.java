package com.example.corporateapp.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.corporateapp.Model.Booking;
import com.example.corporateapp.Model.Flight;
import com.example.corporateapp.Repository.BookingRepo;
import com.example.corporateapp.Repository.FlightRepo;
import com.example.corporateapp.Service.TravelService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private BookingRepo bookingRepo;

    private final TravelService travelService;

    public ApiController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/search/flights")
    public List<Flight> searchFlights(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(required = false) Double maxPrice,
        @RequestParam(required = false) Integer stops,
        @RequestParam(required = false) String departureTime // "morning" | "afternoon" | "evening"
    ) {
        System.out.println("DEBUG filters: maxPrice=" + maxPrice + ", stops=" + stops + ", departureTime=" + departureTime);
        return travelService.searchFlights(from, to, date, maxPrice, stops, departureTime);
    }

    @PostMapping("/book")
    @ResponseBody
    public Booking book(@RequestBody Booking booking) {
        booking.setCreatedAt(LocalDateTime.now());

        Flight flight = flightRepo.findById(booking.getFlightId()).orElse(null);
        if (flight != null) {
            booking.setAmount(flight.getPrice());
        } else {
            booking.setAmount(0.0);
        }

        return bookingRepo.save(booking);
    }

    @GetMapping("/bookings")
    public String showBookings(Model model) {
        List<Booking> bookings = travelService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/all")
    public List<Flight> allFlights() {
        return travelService.getAllFlights();
    }
}
