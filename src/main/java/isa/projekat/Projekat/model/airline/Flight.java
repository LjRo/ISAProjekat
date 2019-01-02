package isa.projekat.Projekat.model.airline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isa.projekat.Projekat.model.rent_a_car.Location;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Airline airline;

    @Column (nullable = false)
    private Date startTime;

    @Column (nullable = false)
    private Date landTime;

    @Column (nullable = false)
    private Double duration;

    @Column (nullable = false)
    private Double distance;

    @Column (nullable = false)
    private Integer numberOfStops;

    @Column
    private int segments;

    @Column
    private int c_columns;

    @Column
    private int c_rows;

    @ManyToOne
    private Location start;

    @ManyToOne
    private Location finish;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Location> stops;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;

    @Column (nullable = false)
    private BigDecimal price;

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

    public List<Location> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Location> stops) {
        this.stops = stops;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public void setStops(List<Location> stops) {
        this.stops = stops;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public int getC_columns() {
        return c_columns;
    }

    public void setC_columns(int c_columns) {
        this.c_columns = c_columns;
    }

    public int getC_rows() {
        return c_rows;
    }

    public void setC_rows(int c_rows) {
        this.c_rows = c_rows;
    }
}
