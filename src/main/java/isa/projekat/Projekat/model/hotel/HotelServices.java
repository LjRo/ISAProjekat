package isa.projekat.Projekat.model.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

// Model for Services which the hotel does provide
@Entity
@Table(name = "HotelServices")
public class HotelServices implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;



    public HotelServices() {
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

    public void setName(String newString) {
        this.name = newString;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal newPrice) {
        this.price = newPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HotelServices))
            return false;
        HotelServices hotelServices = (HotelServices) o;
        return (hotelServices.id.equals(this.id));

    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HotelServices" + "[id=" + this.id + ",name=" + this.name + ",price=" + this.price + "]";
    }
}
