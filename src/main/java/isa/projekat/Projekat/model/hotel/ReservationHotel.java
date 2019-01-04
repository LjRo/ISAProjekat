package isa.projekat.Projekat.model.hotel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
public class ReservationHotel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date arrivalDate;

    @Column
    private Date reservationDate;

    @Column
    private int nigthsStayimg;

    @Column
    private int People;

    @Column
    private boolean fast;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Hotel hotel;

    //TODO Zavrsiti
    //cena?
    //sobe?





}
