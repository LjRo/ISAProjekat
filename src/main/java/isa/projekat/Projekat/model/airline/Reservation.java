package isa.projekat.Projekat.model.airline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;

@Entity
@Table(name="Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonBackReference(value = "user_reservation")
    private User user;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String passport;

    @OneToOne
    @JsonBackReference(value = "seat_reservation")
    private Seat seat;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
