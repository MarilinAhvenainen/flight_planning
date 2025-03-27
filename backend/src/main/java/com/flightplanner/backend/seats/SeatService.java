package com.flightplanner.backend.seats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getAllSeats(){
        List<Seat> seats = new ArrayList<>();

        seatRepository.findAll().forEach(seats::add);
        return seats;
    }

    // Returns seats based on id
    public Optional<Seat> getSeat(Long id){
        return seatRepository.findById(id);
    }

    public void addSeat(Seat seat){
        seatRepository.save(seat);
    }

    public void bookSeat(Long id, Seat updatedSeat){
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));

        if (Boolean.TRUE.equals(seat.getIsBooked())) {
            throw new IllegalStateException("Seat is already booked");
        }
        seat.setIsBooked(updatedSeat.getIsBooked());

        seatRepository.save(seat);

    }

    public List<Seat> getSeatsByFlight(Long flightId) {
        List<Seat> seats = seatRepository.findByFlightId(flightId);
        return seats;
    }
    
}
