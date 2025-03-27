package com.flightplanner.backend.seats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class SeatController {
    
    @Autowired
    private SeatService seatService;

    @GetMapping("/flights/{flightId}/seats")
    public List<Seat> getAllSeatsByFlight(@PathVariable Long flightId){
        return seatService.getSeatsByFlight(flightId);
    }

    @GetMapping("/seats")
    public List<Seat> getAllSeats(){
        return seatService.getAllSeats();
    }
    
    @GetMapping("/flights/{flightId}/seats/{seatId}")
    public Seat getSeat(@PathVariable Long flightId, @PathVariable Long seatId) {
        return seatService.getSeat(seatId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Istekohta (" + seatId + ") ei leitud."
        ));
        }

}
