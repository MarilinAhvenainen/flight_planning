package com.flightplanner.backend.seats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class SeatController {
    
    @Autowired
    private SeatService seatService;

    // Konkreetse lennu istekohad
    @GetMapping("/flights/{flightId}/seats")
    public List<Seat> getAllSeatsByFlight(@PathVariable Long flightId){
        return seatService.getSeatsByFlight(flightId);
    }

    // Kõik istekohad
    @GetMapping("/seats")
    public List<Seat> getAllSeats(){
        return seatService.getAllSeats();
    }
    
    // konktreetse istekoha leidmine istekoha id järgi.
    @GetMapping("/flights/{flightId}/seats/{seatId}")
    public Seat getSeat(@PathVariable Long flightId, @PathVariable Long seatId) {
        return seatService.getSeat(seatId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Istekohta (" + seatId + ") ei leitud."
        ));
        }

    @PostMapping("/flights/{flightId}/seats/{seatId}")
    public void bookSeat(@PathVariable Long flightId, @PathVariable Long seatId) {
            seatService.bookSeat(seatId);
    }

}
