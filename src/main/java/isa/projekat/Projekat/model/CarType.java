package isa.projekat.Projekat.model;

import javax.persistence.*;

@Entity
@Table(name =  "CARTYPE")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name= "name", unique = true, nullable = false)
    private String name;

}
