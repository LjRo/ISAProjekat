package isa.projekat.Projekat.model.airline;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Airlines")
public class Airline implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Location address;

    @Column
    private String description;
    //private ArrayList<Double> grades;

    @Column
    private String pricing;

    @ManyToMany
    private List<Location> destinations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "airline")
    @JsonManagedReference(value = "list_flights")
    private List<Flight> flights;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "administratedAirline")
    @JsonManagedReference(value = "airline_admins")
    private List<User> admins;

    public Airline() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Location> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Location> destinations) {
        this.destinations = destinations;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }
}
