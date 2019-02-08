package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Used to check if already rated
    Rating findByFlightReservation_Id(Long id);
    Integer countByFlightReservation_Id(Long id);
    Rating findByHotelReservation_Id(Long id);
    Integer countByHotelReservation_Id(Long id);
    Rating findByRentReservation_Id(Long id);
    Integer countByRentReservation_Id(Long id);

    @Query(value = "SELECT AVG(r.user_rating) FROM ratings r " +
            "LEFT OUTER JOIN rent_reservation rr ON rr.id = r.rent_reservation_id " +
            "WHERE rr.rented_car_id = ?1 group by rr.rented_car_id",nativeQuery = true)
    Double getAverageCarRatingById(Long id);

    @Query(value = "SELECT AVG(r.user_rating) FROM ratings r" +
            " LEFT OUTER JOIN reservations rr ON rr.id = r.flight_reservation_id" +
            " WHERE rr.flight_id = ?1 group by rr.flight_id",nativeQuery = true)
    Double getAverageFlightRatingById(Long id);

    @Query(value = "SELECT AVG(r.user_rating) FROM ratings r" +
            " LEFT OUTER JOIN reservation_hotel rh ON rh.id = r.hotel_reservation_id" +
            " WHERE rh.room_id = 1 group by rh.room_id",nativeQuery = true)
    Double getAverageRoomRatingById(Long id);



    @Query(value = "SELECT AVG(r.user_rating) FROM ratings r" +
            " LEFT OUTER JOIN reservation_hotel rh ON rh.id = r.hotel_reservation_id" +
            " WHERE rh.hotel_id = ?1 group by rh.hotel_id", nativeQuery = true)
    Double getAverageHotelRatingById(Long id);

    @Query(value = "SELECT AVG(r.user_rating) FROM ratings r" +
            " LEFT OUTER JOIN reservations rr ON rr.id = r.flight_reservation_id\n" +
            " LEFT OUTER JOIN flights f ON rr.flight_id = f.id" +
            " WHERE f.airline_id = 1 group by f.airline_id", nativeQuery = true)
    Double getAverageAirlineRatingById(Long id);


    @Query(value = "Select AVG(r.user_rating) FROM ratings r" +
            " LEFT OUTER JOIN rent_reservation rr ON rr.id = r.rent_reservation_id" +
            " LEFT OUTER JOIN cars c ON c.id = rr.rented_car_id " +
            " WHERE c.rentacar_id = ?1 group by c.rentacar_id", nativeQuery = true)
    Double getAverageRentACarRatingById(Long id);

}
