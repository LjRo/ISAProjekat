package isa.projekat.Projekat.model.RentACar;

import javax.persistence.*;

@Entity
@Table(name =  "CarType")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name= "name", unique = true, nullable = false)
    private String name;


    public CarType() {
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

}
