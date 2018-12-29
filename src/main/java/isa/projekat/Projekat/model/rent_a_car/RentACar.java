package isa.projekat.Projekat.model.rent_a_car;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RentServices")
public class RentACar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private Location address;

    @Column(name = "promotionalDescription")
    private String promotionalDescription;

    @OneToMany
    private List<CarOffice> carOffices = new ArrayList<>();

    @OneToMany
    private List<Cars> cars = new ArrayList<>();

    @OneToMany
    private List<Location> branches = new ArrayList<>();

    @Column(name = "fastDiscount", nullable = false)
    private int fastDiscount;

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

    public String getPromotionalDescription() {
        return promotionalDescription;
    }

    public void setPromotionalDescription(String promotionalDescription) {
        this.promotionalDescription = promotionalDescription;
    }

    public List<CarOffice> getCarOffices() {
        return carOffices;
    }

    public void setCarOffices(List<CarOffice> carOffices) {
        this.carOffices = carOffices;
    }

    public List<Cars> getCars() {
        return cars;
    }

    public void setCars(List<Cars> cars) {
        this.cars = cars;
    }

    public List<Location> getBranches() {
        return branches;
    }

    public void setBranches(List<Location> branches) {
        this.branches = branches;
    }

    public int getFastDiscount() {
        return fastDiscount;
    }

    public void setFastDiscount(int fastDiscount) {
        this.fastDiscount = fastDiscount;
    }
}
