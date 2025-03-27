package com.flightplanner.backend.config;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.flightplanner.backend.flights.Flight;
import com.flightplanner.backend.flights.FlightService;
import com.flightplanner.backend.seats.Seat;
import com.flightplanner.backend.seats.SeatService;

@Component
public class DataSeeder implements CommandLineRunner {

    private final FlightService flightService;
    private final SeatService seatService;

    public DataSeeder(FlightService flightService, SeatService seatService) {
        this.flightService = flightService;
        this.seatService = seatService;
    }

    @Override
    public void run(String... args) {
        if (flightService.getAllFlights().isEmpty()) { // Duplikaatideta
            System.out.println("Seeding flights.");
            
            flightService.addFlight(new Flight("Tallinn","Helsinki",LocalDate.now(),LocalTime.of(14,30),new BigDecimal("50.00")));
            flightService.addFlight(new Flight("Helsinki","Tallinn",LocalDate.now(),LocalTime.of(15,30),new BigDecimal("70.00")));
            
            List<Flight> flights = flightService.getAllFlights();
            
            System.out.println("Seeding seats.");
            
            for (Flight flight: flights){
                Long id = flight.getId();
                seedSeats(flight,id,20);
            }
            System.out.println("Flights and seats seeded!");
        }
    }

    public void seedSeats(Flight flight,Long id, Integer rows){
        Integer seatNumber = 1;
        int exitMidRow = rows / 2;
        String[] seatTypes = {"A", "B", "C", "D", "E", "F"};
        for (int i=1; i<=rows; i++){
            boolean nearExit = (i == exitMidRow || i == 1 || i == rows);
            Random random = new Random();

            for (String type : seatTypes) {
                Seat seat = new Seat(
                    random.nextBoolean(),  // isBooked
                    seatNumber,            // seat number
                    type,                // type: A:Akna all, B: keskel, C:vahekäik
                    random.nextBoolean(),  // legRoom
                    nearExit,              // is near exit
                    "turistiklass",        // class
                    flight                 // flight 
                );
                seatService.addSeat(seat); 
                seatNumber++; 
            }

        }
    }
}