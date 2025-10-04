package com.example.corporateapp.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long flight_id;
    public String airline;
    public String origin;
    public String destination;
  @JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate date;


    public String departure_time;
    public String arrival_time;
    public double price;
    public int seats_available;
    
}
