package isa.projekat.Projekat.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "RentService")
public class RentService {

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
    private ArrayList<CarService> carServices = new ArrayList<>();

    @OneToMany
    private ArrayList<Cars> cars;

    @OneToMany
    private ArrayList<Location> branches = new ArrayList<>();

    @Column(name = "fastDiscount", nullable = false)
    private int fastDiscount;




}
