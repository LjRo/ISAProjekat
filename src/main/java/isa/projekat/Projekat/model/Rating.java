package isa.projekat.Projekat.model;

import isa.projekat.Projekat.model.airline.Airline;
import isa.projekat.Projekat.model.airline.Flight;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
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

    @ManyToOne
    private User user;

    @Column(nullable = false) //1- airlines , 2 - flight , 3- hotel , 4-rooms , 5 - rentacar , 6 - cars
    private int type;

    @ManyToOne
    private Airline airline;

    @ManyToOne
    private Flight flight;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private Room room;

    @ManyToOne
    private RentACar rentACar;

    @ManyToOne
    private Cars cars;


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

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }
}
