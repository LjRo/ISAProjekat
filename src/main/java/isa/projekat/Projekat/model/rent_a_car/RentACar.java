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
    @JsonManagedReference(value = "list_offices")
    private List<RentOffice> rentOffices = new ArrayList<>();

    @OneToMany
    @JsonManagedReference(value = "list_cars")
    private List<Cars> cars = new ArrayList<>();

    @OneToMany(mappedBy = "administratedRent", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "rent_admins")
    private List<User> admins = new ArrayList<>();

    @Column(nullable = false)
    private int fastDiscount;

    public List<User> getAdmins() {
        return admins;
    }

    public void setAdmins(List<User> admins) {
        this.admins = admins;
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
