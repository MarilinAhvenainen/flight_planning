package com.flightplanner.backend.flights;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    
    @Autowired
    private FlightRepository flightRepository;

    //Tagastab kõik lennud
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    //Tagastab lennu vastavalt lennu id'le
    public Optional<Flight> getFlight(Long id){
        return flightRepository.findById(id);
    }

    // Tagastab lennud vastavalt alguspunktile
    public List<Flight> getFlightsByOrigin(String origin){
        return flightRepository.findByOrigin(origin);
    }
    // Tagastab lennud vastavalt sihtpunktile
    public List<Flight> getFlightsByDestination(String destination){
        return flightRepository.findByDestination(destination);
    }
    // Tagastab lennud vastavalt algpunktile ja sihtpunktile
    public List<Flight> getFlightsByOriginAndDestination(String origin, String destination){
        return flightRepository.findByOriginAndDestination(origin, destination);
    }

    // Lisab uue lennu
    public void addFlight(Flight flight){
        flightRepository.save(flight);
    }

    // uuendab lennu infot
    public void updateFlight(Long id, Flight updatedFlight){
        Flight flight = flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found"));

        flight.setOrigin(updatedFlight.getOrigin());
        flight.setDestination(updatedFlight.getDestination());
        flight.setDate(updatedFlight.getDate());
        flight.setTime(updatedFlight.getTime());
        flight.setPrice(updatedFlight.getPrice());

        flightRepository.save(flight);
    }

    //kustutab lennu
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }




}
