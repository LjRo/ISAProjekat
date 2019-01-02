package isa.projekat.Projekat.model.airline;

import com.fasterxml.jackson.annotation.JsonIgnore;
import isa.projekat.Projekat.model.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="Seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private int c_row;
    @Column
    private int c_column;
    @Column
    private BigDecimal price;
    @ManyToOne
    @JsonIgnore
    private User customer;
    @Column
    private boolean taken;
    @Column
    private boolean quick;

    public Seat() {
    }

    public Seat(int row, int column, BigDecimal price, User customer, boolean taken, boolean quick) {
        this.c_row = row;
        this.c_column = column;
        this.price = price;
        this.customer = customer;
        this.taken = taken;
        this.quick = quick;
    }

    public int getRow() {
        return c_row;
    }

    public void setRow(int row) {
        this.c_row = row;
    }

    public int getC_column() {
        return c_column;
    }

    public void setC_column(int c_column) {
        this.c_column = c_column;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isQuick() {
        return quick;
    }

    public void setQuick(boolean quick) {
        this.quick = quick;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
