package com.flightplanner.backend.flights;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class FlightController {
    @Autowired
    private FlightService flightService;
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @GetMapping("/flights/all")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public List<Flight> getAllFlights() {
        List<Flight> allFlights = flightService.getAllFlights();
        logger.info("Retrieved {} total flights", allFlights.size());
        return allFlights;
    }

    // Lennud alguspunkti ja sihtpunkti järgi
    @GetMapping("/flights")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public List<Flight> searchFlights(
        @RequestParam(required = false) String origin,
        @RequestParam(required = false) String destination
    ) {
        logger.info("Search Params - Origin: " + origin + 
                           ", Destination: " + destination);

        // Kas alguspunkt ja lõpppunkt on tühjad.
        boolean hasOrigin = origin != null && !origin.isEmpty();
        boolean hasDestination = destination != null && !destination.isEmpty();
        List<Flight> flights;
        if (hasOrigin && !hasDestination){
            flights = flightService.getFlightsByOrigin(origin);
        }else if (!hasOrigin && hasDestination){
            flights = flightService.getFlightsByDestination(destination);
        }else if (hasOrigin && hasDestination){
            flights = flightService.getFlightsByOriginAndDestination(origin, destination);
        }else{
            flights = flightService.getAllFlights(); // kui mõlemad on tühjad siis tagastame kõik lennud.
        }

        logger.info("Found {} flights", flights.size());
        return flights;
    }

    @GetMapping("/flights/{id}")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public Optional<Flight> getFlight(@PathVariable Long id) {
        return flightService.getFlight(id);
    }

    @PostMapping("/flights")
    public void addFlight(@RequestBody Flight flight){
        flightService.addFlight(flight);
    }
}
