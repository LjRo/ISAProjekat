package isa.projekat.Projekat.model.airline;

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

    @ManyToMany
    private Location start;

    @ManyToMany
    private Location finish;

    @ManyToMany
    private List<Location> stops;

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
}
