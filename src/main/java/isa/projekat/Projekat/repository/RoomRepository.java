package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    Page<Room> findAll(Pageable pageable);

    Page<Room> findByHotelId(Long Hotel, Pageable pageable);

    @Query(value = "Select * " +
            "from Rooms ro " +
            "where (ro.room_type_id = ?4 OR  ?4 = 0 )  AND  ro.hotel_id = ?1  " +
            "AND ro.room_type_id IN  (SELECT hpl.room_type_id " +
            "FROM hotel_price_list hpl " +
            "Where hpl.hotel_id = ro.hotel_id AND ?10 * hpl.price >= ?8 AND ?10 * hpl.price <= ?9) " +
            "AND ro.number_of_people >= ?5 " +
            "AND (ro.number_of_rooms = ?6 or ?6 = 0 ) " +
            "AND (ro.number_of_beds = ?7 or ?7 = 0 ) "+
            "AND ro.id NOT IN (Select r.room_id " +
            "from reservation_hotel r " +
            "where " +
            "            (((r.arrival_date >= ?2 AND r.departure_date <=  ?3) OR " +
            "(r.arrival_date >= ?2 AND r.arrival_date <=  ?3) or " +
            "(r.departure_date >= ?2 AND r.departure_date <= ?3 )) AND r.hotel_id = ro.hotel_id ) " +
            ")",nativeQuery = true)
    public Page<Room> returnRoomsThatAreAvailable(Long hotel_id, String arrivalDate, String departureDate, Long type, Integer people, Integer rooms,Integer beds, Integer minPrice, Integer maxPrice,int numberOfDays,Pageable pageable);

    @Query(value ="Select * \n" +
            "from Rooms ro  \n" +
            "where ro.id =?1  AND ro.id NOT IN\n" +
            "(Select r.room_id  \n" +
            "from reservation_hotel r  \n" +
            "where (r.fast is null or r.fast is false) AND  \n" +
            "(((r.arrival_date >= ?2 AND r.departure_date <=  ?3) OR  \n" +
            "(r.arrival_date >= ?2 AND r.arrival_date <=  ?3) or  \n" +
            "(r.departure_date >= ?2 AND r.departure_date <= ?3 )) AND r.hotel_id = ro.hotel_id ))" ,nativeQuery = true)
    public  Room checkIfAvailableStill(Long roomId,String arrivalDate, String departureDate);




 //

}