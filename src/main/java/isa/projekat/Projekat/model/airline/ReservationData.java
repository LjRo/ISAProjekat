package isa.projekat.Projekat.model.airline;

import java.math.BigDecimal;

public class ReservationData {

    private Long userId;
    private String firstName;
    private String lastName;
    private String passport;
    private Long seatId;
    private Double pointsUsed;
    private BigDecimal totalCost;

    public ReservationData() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Double getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(Double pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
