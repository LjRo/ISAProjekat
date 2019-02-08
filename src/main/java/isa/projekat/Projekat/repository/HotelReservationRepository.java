package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.ReservationHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelReservationRepository extends JpaRepository<ReservationHotel, Long> {


    public ReservationHotel findByUserOrder_Id(Long id);

    @Query(value = "SELECT rh.* FROM reservation_hotel rh left outer join rooms r on rh.room_id = r.id " +
            " WHERE rh.fast = true AND r.hotel_id = ?1 AND rh.departure_date >= ?2 AND rh.user_id is null ", nativeQuery = true)
    public List<ReservationHotel> listQuick(Long idHotel, String curhentDate);
}
