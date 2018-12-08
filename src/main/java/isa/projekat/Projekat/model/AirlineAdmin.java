package isa.projekat.Projekat.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AirlineAdmin extends User {

    @ManyToOne
    private Airline administratedAirline;

    public Airline getAdministratedAirline() {
        return administratedAirline;
    }

    public void setAdministratedAirline(Airline administratedAirline) {
        this.administratedAirline = administratedAirline;
    }
}
