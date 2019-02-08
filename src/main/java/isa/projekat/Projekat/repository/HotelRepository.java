package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.HotelServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelRepository extends JpaRepository<Hotel, Long> {




        @Query(value ="Select * from hotels h " +
            "where h.address_id in " +
            "(select l.id from locations l " +
            "where l.address_name like %?3% OR l.city like %?3%) AND h.name like %?4% " +
            "AND EXISTS  " +
            "(Select * " +
            "from Rooms ro " +
            "where ro.hotel_id = h.id  " +
            "AND ro.id NOT IN (Select r.room_id " +
            "from reservation_hotel r " +
            "where (r.fast is null or r.fast is false) AND " +
            " r.room_id in (select ho.id from rooms ho where ho.hotel_id =ro.hotel_id ) AND" +
            "(((r.arrival_date >= ?1 AND r.departure_date <=  ?2) OR " +
            "(r.arrival_date >= ?1 AND r.arrival_date <=  ?2) or " +
            "(r.departure_date >= ?1 AND r.departure_date <= ?2 )))))",nativeQuery = true)
          public List<Hotel> returnAvailableHotels(String arrivalDate, String departureDate, String location , String name);

    @Query(value ="Select h from hotels h left join hotels_hotel_services hhs on h.id = hhs.id and  hhs.hotel_service_id = ?1",nativeQuery = true)
        Hotel returnHotelServicesForHotel(Long hotelId);
}
