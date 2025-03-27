package com.flightplanner.backend.seats;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    // getAllSeats()
    // getSeat(Long Id)
    // addSeat(Seat seat)
    // updateSeat(Long id, Seat seat)
    // deleteSeat(Long id)
    List<Seat> findByFlightId(Long flightId);
    
}
