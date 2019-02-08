package isa.projekat.Projekat.model;

import isa.projekat.Projekat.model.airline.Reservation;
import isa.projekat.Projekat.model.hotel.ReservationHotel;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Ratings")
public class Rating implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int userRating;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false) //1- cars , 2 - flight , 3- rooms  4-rentacar , 5 - hotel , 6 - airlines
    private int type;

    @ManyToOne(targetEntity = Reservation.class)
    private Reservation flightReservation;

    @ManyToOne(targetEntity = ReservationHotel.class)
    private ReservationHotel hotelReservation;

    @ManyToOne(targetEntity = RentReservation.class)
    private RentReservation rentReservation;


    public Rating() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Reservation getFlightReservation() {
        return flightReservation;
    }

    public void setFlightReservation(Reservation flightReservation) {
        this.flightReservation = flightReservation;
    }

    public ReservationHotel getHotelReservation() {
        return hotelReservation;
    }

    public void setHotelReservation(ReservationHotel hotelReservation) {
        this.hotelReservation = hotelReservation;
    }

    public RentReservation getRentReservation() {
        return rentReservation;
    }

    public void setRentReservation(RentReservation rentReservation) {
        this.rentReservation = rentReservation;
    }
}
