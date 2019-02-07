package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.airline.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    @Query(value ="Select * from reservations r where r.user_id = ?1 AND r.finished is not true ",nativeQuery = true)
    public List<Reservation> getAllByUser(Long userId);

    @Query(value ="Select * from reservations r where r.user_id = ?1 AND  r.flight_id in " +
            "(select f.id from flights f  where f.start_time > ?2 ) ",nativeQuery = true)
    public List<Reservation> returnAllFutureReservationsByUser(Long userId,String todayDate);

    @Query(value = "SELECT * FROM reservations r \n" +
            "\t\t\tWHERE r.user_id = ?1 AND  EXISTS\n" +
            "\t\t\t(SELECT * FROM flights f  WHERE f.start_time >= ?2 AND f.id = r.flight_id) \n" +
            "\t\t\tAND NOT EXISTS (SELECT * FROM rent_reservation rr WHERE rr.user_id = ?1 AND rr.airline_reservation_id = r.id)",nativeQuery = true)
    List<Reservation> returnFutureRentReservationByUser(Long userId, String todayDate);



    List<Reservation> findAllByUserId(Long userId);



}
