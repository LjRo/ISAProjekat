package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.ReservationHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HotelReservationRepository extends JpaRepository<ReservationHotel, Long> {


    public ReservationHotel findByAirlineReservation_Id(Long id);
}
