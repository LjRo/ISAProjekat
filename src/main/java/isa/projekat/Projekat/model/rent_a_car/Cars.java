package isa.projekat.Projekat.model.rent_a_car;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "Cars")
public class Cars implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private CarType type;


    @Column(name = "name")
    private String name;

    @Column(name = "mark", nullable = false)
    private String mark;

    @Column(name = "model", nullable =  false)
    private String model;

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

    @Column(name = "isFastReserved", nullable = false)
    private Boolean isFastReserved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "list_cars")
    private RentACar rentACar;


    //TODO SET LIST OF REVIEWS


    public Cars() {
    }

    public Cars(CarType type, String name, String mark, String model, String registrationNumber, int numberOfBags, int maxPassengers, int numberOfDoors, BigDecimal dailyPrice, Boolean isFastReserved, RentACar rentACar) {
        this.type = type;
        this.name = name;
        this.mark = mark;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.numberOfBags = numberOfBags;
        this.maxPassengers = maxPassengers;
        this.numberOfDoors = numberOfDoors;
        this.dailyPrice = dailyPrice;
        this.isFastReserved = isFastReserved;
        this.rentACar = rentACar;
    }

    public RentACar getRentACar() {
        return rentACar;
    }

    public void setRentACar(RentACar rentACar) {
        this.rentACar = rentACar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getFastReserved() {
        return isFastReserved;
    }

    public void setFastReserved(Boolean fastReserved) {
        isFastReserved = fastReserved;
    }




}
