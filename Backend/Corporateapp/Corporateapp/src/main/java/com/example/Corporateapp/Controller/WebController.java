package com.example.Corporateapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Corporateapp.Service.TravelService;

@Controller
public class WebController {
    private final TravelService travelService;
    public WebController(TravelService travelService) { this.travelService = travelService; }

    @GetMapping("/")
    public String home() { return "index"; }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", travelService.getAllBookings());
        return "bookings";
    }

}
