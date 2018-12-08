package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CarService")
public class CarService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    private RentService rentService;

    public CarService() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RentService getRentService() {
        return rentService;
    }

    public void setRentService(RentService rentService) {
        this.rentService = rentService;
    }
}
