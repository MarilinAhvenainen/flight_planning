package com.flightplanner.backend.flights;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    // getAllFlights()
    // getFlight(Long Id)
    // addFlight(Flight flight)
    // updateFlight(Long id, Flight flight)
    // deleteFlight(Long id)
    List<Flight> findByOriginAndDestination(String origin, String destination);
    List<Flight> findByOrigin(String origin);
    List<Flight> findByDestination(String destination);
}
