package isa.projekat.Projekat.model.rent_a_car;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RentACar")
public class RentACar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Location address;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RentOffice> rentOffices = new ArrayList<>();

    @OneToMany
    @JsonManagedReference
    private List<Cars> cars = new ArrayList<>();

   // @OneToMany(cascade = CascadeType.ALL, mappedBy = "administratedRent")
    @OneToMany(mappedBy = "administratedRent", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<User> admins = new ArrayList<>();

    //private  User admin;

    //@OneToMany moved to car offices
    //private List<Location> branches = new ArrayList<>();

    @Column
    private int bronzeDiscount = 0;

    @Column
    private int silverDiscount = 0;

    @Column
    private int goldDiscount = 0;

    @Column(nullable = false)
    private int fastDiscount;

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
    }

    public int getBronzeDiscount() {
        return bronzeDiscount;
    }

    public void setBronzeDiscount(int bronzeDiscount) {
        this.bronzeDiscount = bronzeDiscount;
    }

    public int getSilverDiscount() {
        return silverDiscount;
    }

    public void setSilverDiscount(int silverDiscount) {
        this.silverDiscount = silverDiscount;
    }

    public int getGoldDiscount() {
        return goldDiscount;
    }

    public void setGoldDiscount(int goldDiscount) {
        this.goldDiscount = goldDiscount;
    }

    public RentACar() {
        super();
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

    public List<RentOffice> getRentOffices() {
        return rentOffices;
    }

    public void setRentOffices(List<RentOffice> rentOffices) {
        this.rentOffices = rentOffices;
    }

    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }

    public int getFastDiscount() {
        return fastDiscount;
    }

    public void setFastDiscount(int fastDiscount) {
        this.fastDiscount = fastDiscount;
    }
}
