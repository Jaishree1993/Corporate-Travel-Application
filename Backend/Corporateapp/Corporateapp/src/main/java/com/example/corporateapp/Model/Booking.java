package com.example.corporateapp.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @JsonProperty("booking_id") 
    private Long booking_id;
    private Long flightId;
    private String firstName;
    private String lastName;
    private double amount;
    private LocalDateTime createdAt;
}
