package isa.projekat.Projekat.model.airline;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import isa.projekat.Projekat.model.hotel.ReservationHotel;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany
    @JsonManagedReference("reservations")
    private List<Reservation> reservations;

    @OneToOne
    @JsonManagedReference("rent")
    private RentReservation rentReservation;

    @OneToOne
    @JsonManagedReference("rooms")
    private ReservationHotel reservationHotel;

    @ManyToOne
    @JsonBackReference("placed_orders")
    private User placedOrder;

    @Column
    private Date orderDate;

    @Column
    private Boolean finished;

    public Order() {
    }

    public Order(List<Reservation> reservations, User placedOrder, Date orderDate, Boolean finished) {
        this.reservations = reservations;
        this.placedOrder = placedOrder;
        this.orderDate = orderDate;
        this.finished = finished;
    }

    public RentReservation getRentReservation() {
        return rentReservation;
    }

    public void setRentReservation(RentReservation rentReservation) {
        this.rentReservation = rentReservation;
    }

    public ReservationHotel getReservationHotel() {
        return reservationHotel;
    }

    public void setReservationHotel(ReservationHotel reservationHotel) {
        this.reservationHotel = reservationHotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public User getPlacedOrder() {
        return placedOrder;
    }

    public void setPlacedOrder(User placedOrder) {
        this.placedOrder = placedOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
