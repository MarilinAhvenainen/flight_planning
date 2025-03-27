package com.flightplanner.backend.seats;

import com.flightplanner.backend.flights.Flight;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="seattable")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(name = "booking_status")
    private Boolean isBooked; // is seat booked or not.

    @Column(name = "seat_number")
    private Integer number; // seat number

    @Column(name = "seat_type")
    private String type; // [A,B,C]  -> A:Window seat, B: middle, C:Isle etc

    @Column(name = "leg_room")
    private Boolean legRoom;

    @Column(name = "near_exit")
    private Boolean nearExit;

    @Column(name = "seat_class")
    private String seatClass; 

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    // Constructors
    public Seat() {}


    public Seat(Long id, Boolean isBooked ,Integer number, String type, Boolean legRoom, Boolean nearExit, String seatClass, Flight flight) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.legRoom = legRoom;
        this.nearExit = nearExit;
        this.seatClass = seatClass;
        this.flight = flight;
        this.isBooked = isBooked;
    }
    public Seat(Boolean isBooked ,Integer number, String type, Boolean legRoom, Boolean nearExit, String seatClass, Flight flight) {
        this.number = number;
        this.type = type;
        this.legRoom = legRoom;
        this.nearExit = nearExit;
        this.seatClass = seatClass;
        this.flight = flight;
        this.isBooked = isBooked;
    }


    // Getters and Setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsBooked() {
        return this.isBooked;
    }

    public void setIsBooked(Boolean isBooked) {
        this.isBooked = isBooked;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isLegRoom() {
        return this.legRoom;
    }

    public Boolean getLegRoom() {
        return this.legRoom;
    }

    public void setLegRoom(Boolean legRoom) {
        this.legRoom = legRoom;
    }

    public Boolean isNearExit() {
        return this.nearExit;
    }

    public Boolean getNearExit() {
        return this.nearExit;
    }

    public void setNearExit(Boolean nearExit) {
        this.nearExit = nearExit;
    }

    public String getSeatClass() {
        return this.seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public Flight getFlight() {
        return this.flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
    
}
