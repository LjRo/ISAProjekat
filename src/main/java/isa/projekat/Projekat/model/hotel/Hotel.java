package isa.projekat.Projekat.model.hotel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isa.projekat.Projekat.model.Rating;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @ManyToOne
    private Location address;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int fastDiscount;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference(value="hotel_prices")
    private Set<HotelPriceList> hotelPriceList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<HotelServices> hotelServices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hotel")
    @JsonManagedReference(value="hotel_rooms")
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "floors")
    private Set<FloorPlan> floorPlans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private Set<RoomType> roomTypes = new HashSet<>();

    @OneToMany(mappedBy = "administratedHotel", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel_admins")
    private List<User> admins = new ArrayList<>();


    public Hotel() {
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

    public int getFastDiscount() {
        return fastDiscount;
    }

    public void setFastDiscount(int fastDiscount) {
        this.fastDiscount = fastDiscount;
    }

    public Set<HotelPriceList> getHotelPriceList() {
        return hotelPriceList;
    }

    public void setHotelPriceList(Set<HotelPriceList> hotelPriceList) {
        this.hotelPriceList = hotelPriceList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
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

    public Location getAddress() {
        return address;
    }

    public void setAddress(Location address) {
        this.address = address;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<FloorPlan> getFloorPlans() {
        return floorPlans;
    }

    public void setFloorPlans(Set<FloorPlan> floorPlans) {
        this.floorPlans = floorPlans;
    }

    public Set<RoomType> getRoomTypes() {
        return roomTypes;
    }

    public void setRoomTypes(Set<RoomType> roomTypes) {
        this.roomTypes = roomTypes;
    }
}
