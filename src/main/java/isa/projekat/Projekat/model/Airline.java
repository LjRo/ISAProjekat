package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Airlines")
public class Airline implements Serializable {

    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Long id;

    @Column(name = "A_NAME", nullable = false)
    private String name;

    @Column(name = "A_ADDRESS", nullable = false)
    private String address;

    @Column(name = "A_DESCRIPTION", nullable = false)
    private String description;
    //private ArrayList<Double> grades;
    //private ArrayList<String> destinations;

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


}
