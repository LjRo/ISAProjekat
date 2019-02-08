package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RentReservationRepository extends JpaRepository<RentReservation, Long> {

    List<RentReservation> findByUserId(Long id);



    List<RentReservation> findAllByRentedCarId(Long id);


    @Query(value = "SELECT rr.* FROM rent_reservation rr left outer join cars c on rr.rented_car_id = c.id\n" +
            "\t\t\tWHERE rr.fast_reservation = true AND c.rentacar_id = ?1 AND rr.end_date >= ?2", nativeQuery = true)
    List<RentReservation> listQuick(Long idrent, String currentDate);

    List<RentReservation> findAllByUserId(Long userId);


    void deleteById(Long id);



   // List<Object> findStatisticsDaily();


}
