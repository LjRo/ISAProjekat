package isa.projekat.Projekat.model.hotel;

import isa.projekat.Projekat.model.airline.Reservation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "ReservationHotel")
public class ReservationHotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date arrivalDate;

    @Column(nullable = false)
    private Date departureDate;

    @Column(nullable = false)
    private Date reservationDate;

    @Column(nullable = false)
    private int nightsStaying;

    @Column(nullable = false)
    private int People;

    @Column
    private boolean fast;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Reservation airlineReservation;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Room room;

    public ReservationHotel(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNightsStaying() {
        return nightsStaying;
    }

    public void setNightsStaying(int nightsStaying) {
        this.nightsStaying = nightsStaying;
    }

    public int getPeople() {
        return People;
    }

    public void setPeople(int people) {
        People = people;
    }

    public boolean isFast() {
        return fast;
    }

    public void setFast(boolean fast) {
        this.fast = fast;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Reservation getAirlineReservation() {
        return airlineReservation;
    }

    public void setAirlineReservation(Reservation airlineReservation) {
        this.airlineReservation = airlineReservation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
