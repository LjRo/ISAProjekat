package isa.projekat.Projekat.model.hotel;

import isa.projekat.Projekat.model.Rating;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

// Model for hotel
@Entity
@Table(name = "Hotels")
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    private Set<HotelServices> hotelServices = new HashSet<>();

    //mappedBy = "hotel"
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    @OneToMany
    private Set<Rating> ratings = new HashSet<>();


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

    public Set<HotelServices> getHotelServices() {
        return hotelServices;
    }

    public void setHotelServices(Set<HotelServices> hotelServices) {
        this.hotelServices = hotelServices;
    }
}
