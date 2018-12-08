package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Hotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String address;

    @Column(nullable = false)
    private String description;

    @ManyToMany
    private Set<HotelService> hotelServices = new HashSet<HotelService>();

    @OneToMany(mappedBy = "Hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> sobe = new HashSet<Room>();

    //TODO Dodati Ocenu
    //TODO Dodati Rezervaciju Hotela

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Set<HotelService> getHotelServices() {
        return hotelServices;
    }

    public void setHotelServices(Set<HotelService> hotelServices) {
        this.hotelServices = hotelServices;
    }
}
