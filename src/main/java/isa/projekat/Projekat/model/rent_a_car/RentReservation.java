package isa.projekat.Projekat.model.rent_a_car;

import com.fasterxml.jackson.annotation.JsonBackReference;
import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.airline.Reservation;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="RentReservation")
public class RentReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //private Reservation airlineReservation;

    @ManyToOne
    @JsonBackReference(value = "rent")
    private Order order;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonBackReference(value = "user_rent_reservation")
    private User user;

    @ManyToOne
    private Cars rentedCar;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @ManyToOne
    private Location startLocation;

    @ManyToOne
    private Location endLocation;

    @Column
    private Integer numberOfPeople;

    // One car can be rented in different periods




    private Boolean fastReservation;


    private BigDecimal price;

    public RentReservation() {
        super();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getFastReservation() {
        return fastReservation;
    }

    public void setFastReservation(Boolean fastReservation) {
        this.fastReservation = fastReservation;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
