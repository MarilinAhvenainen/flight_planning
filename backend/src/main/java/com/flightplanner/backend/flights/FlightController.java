package com.flightplanner.backend.flights;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping("/flights")
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @GetMapping("/flights/{id}")
    public Optional<Flight> getFlight(@PathVariable Long id) {
        return flightService.getFlight(id);
    }

    @PostMapping("/flights")
    public void addFlight(@RequestBody Flight flight){
        flightService.addFlight(flight);
    }
}
