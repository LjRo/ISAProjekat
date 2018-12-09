package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "HotelServices")
public class HotelService implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    public HotelService(){
        super();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if( !(o instanceof HotelService))
            return false;
        HotelService hotelService = (HotelService) o;
        return (hotelService.id.equals(this.id));

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HotelService" + "[id=" +this.id + ",name="+ this.name + ",price=" + this.price+"]";
    }
}
