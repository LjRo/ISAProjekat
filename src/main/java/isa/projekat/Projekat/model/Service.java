package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    public  Service(){
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
        if( !(o instanceof Service))
            return false;
        Service service = (Service) o;
        if (service.id.equals(this.id)) {
            return true;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Service" + "[id=" +this.id + ",name="+ this.name + ",price=" + this.price+"]";
    }
}
