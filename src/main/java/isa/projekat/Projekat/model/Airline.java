package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "Airlines")
public class Airline implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column
    private String description;
    //private ArrayList<Double> grades;
    @ManyToMany
    private ArrayList<Destination> destinations;

    public Airline() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Destination> destinations) {
        this.destinations = destinations;
    }
}
