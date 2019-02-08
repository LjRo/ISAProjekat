package isa.projekat.Projekat.model.airline;

import isa.projekat.Projekat.model.rent_a_car.Location;

import java.math.BigDecimal;
import java.util.Date;

public class QuickTicketData {

    private Long id;
    private Date startTime;
    private Date landTime;
    private Double duration;
    private Double distance;
    private Integer numberOfStops;
    private int c_column;
    private int c_row;
    private Location start;
    private Location finish;
    private BigDecimal price;
    private String passport;
    private Long seatId;

    public QuickTicketData() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLandTime() {
        return landTime;
    }

    public void setLandTime(Date landTime) {
        this.landTime = landTime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public int getC_column() {
        return c_column;
    }

    public void setC_column(int c_column) {
        this.c_column = c_column;
    }

    public int getC_row() {
        return c_row;
    }

    public void setC_row(int c_row) {
        this.c_row = c_row;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getFinish() {
        return finish;
    }

    public void setFinish(Location finish) {
        this.finish = finish;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
