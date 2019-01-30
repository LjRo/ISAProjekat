package isa.projekat.Projekat.model.rent_a_car;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import isa.projekat.Projekat.model.airline.Reservation;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="RentReservation")
public class RentReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Reservation airlineReservation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "user_rent_reservation")
    private User user;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Location startLocation;

    @Column
    private Location endLocation;

    @Column
    private Integer numberOfPeople;

    // One car can be rented in different periods
    @ManyToOne
    private Cars rentedCar;

    private Boolean fastReservation;

    public Boolean getFastReservation() {
        return fastReservation;
    }

    public void setFastReservation(Boolean fastReservation) {
        this.fastReservation = fastReservation;
    }

    public RentReservation() {
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reservation getAirlineReservation() {
        return airlineReservation;
    }

    public void setAirlineReservation(Reservation airlineReservation) {
        this.airlineReservation = airlineReservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Cars getRentedCar() {
        return rentedCar;
    }

    public void setRentedCar(Cars rentedCar) {
        this.rentedCar = rentedCar;
    }
}
