package com.flightplanner.backend.flights;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;

    //Returns all flights
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        // all flights from database
        flightRepository.findAll().forEach(flights::add); 
        
        return flights;
    }

    // Returns flight based on id
    public Optional<Flight> getFlight(Long id){
        return flightRepository.findById(id);
    }

    // Returns flight based on origin
    public List<Flight> getFlightsByOrigin(String origin){
        return flightRepository.findByOrigin(origin);
    }
    // Returns flight based on destination
    public List<Flight> getFlightsByDestination(String destination){
        return flightRepository.findByDestination(destination);
    }
    // Returns flight based on origin and destination
    public List<Flight> getFlightsByOriginAndDestination(String origin, String destination){
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    // Inserts flight to database
    public void addFlight(Flight flight){
        flightRepository.save(flight);
    }

    // update flights
    public void updateFlight(Long id, Flight updatedFlight){
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setOrigin(updatedFlight.getOrigin());
        flight.setDestination(updatedFlight.getDestination());
        flight.setDate(updatedFlight.getDate());
        flight.setTime(updatedFlight.getTime());
        flight.setPrice(updatedFlight.getPrice());

        flightRepository.save(flight);
    }

    //delete flights
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }




}
