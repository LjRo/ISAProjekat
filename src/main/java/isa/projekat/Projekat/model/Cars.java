package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "CARS")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private CarType type;

    @Column(name = "registrationNumber", unique = true, nullable = false)
    private String registrationNumber;

    @Column(name = "numberOfBags", nullable = false)
    private int numberOfBags;

    @Column(name = "maxPassengers", nullable = false)
    private int maxPassengers;

    @Column(name = "numberOfDoors", nullable = false)
    private int numberOfDoors;

    @Column(name = "dailyPrice", nullable = false)
    private BigDecimal dailyPrice;

    //TODO SET LIST OF REVIEWS


    public Cars() {
    }

    public Cars(CarType type, String registrationNumber, int numberOfBags, int maxPassengers, int numberOfDoors, BigDecimal dailyPrice) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.numberOfBags = numberOfBags;
        this.maxPassengers = maxPassengers;
        this.numberOfDoors = numberOfDoors;
        this.dailyPrice = dailyPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public BigDecimal getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(BigDecimal dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
}
