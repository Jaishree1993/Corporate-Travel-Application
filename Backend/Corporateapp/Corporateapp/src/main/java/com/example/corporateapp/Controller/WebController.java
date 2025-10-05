package com.example.corporateapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.corporateapp.Service.TravelService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
    private final TravelService travelService;
    public WebController(TravelService travelService) { this.travelService = travelService; }

       @GetMapping("/")
    public String home(HttpSession session) {
        // If using manual session attribute:
        Object user = session.getAttribute("user");
        if (user == null) {
            return "redirect:/loginpage.html";
        }
        return "index";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", travelService.getAllBookings());
        return "bookings";
    }

}
