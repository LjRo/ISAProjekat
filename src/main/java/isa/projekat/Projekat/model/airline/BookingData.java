package isa.projekat.Projekat.model.airline;

import java.util.List;

public class BookingData {
    private List<ReservationData> airlineReservations;
    //TODO: add Hotel and Car data

    public BookingData(){
        super();
    }

    public List<ReservationData> getAirlineReservations() {
        return airlineReservations;
    }

    public void setAirlineReservations(List<ReservationData> airlineReservations) {
        this.airlineReservations = airlineReservations;
    }
}
