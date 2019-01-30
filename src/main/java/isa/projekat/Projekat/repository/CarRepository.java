package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public interface CarRepository extends JpaRepository<Cars, Long> {
    @Override
    Page<Cars> findAll(Pageable pageable);



    //Page<Cars> findAllById(Long id, Pageable pageable);
    Page<Cars> findByRentACarId(Long RentACar, Pageable pageable);

    List<Cars> findByRentACarId(Long RentACar);


    @Query(value = "SELECT *" +
            " FROM cars c WHERE (?1 = 0 OR c.type_id = ?1)  AND c.rentACar_id = ?5 AND \n" +
            "         c.max_passengers >= ?2 AND c.daily_price >= ?6 AND c.daily_price <= ?7 AND c.id NOT IN\n" +
            "         (SELECT r.rented_car_id FROM rent_reservation r \n" +
            "         WHERE r.rented_car_id = c.id AND ((r.start_date >= ?3 AND r.end_date <= ?4) OR\n" +
            "          (r.start_date >= ?3 AND r.start_date <= ?4) OR\n" +
            "          (r.end_date >= ?3 AND r.end_date <= ?4) )) \nGROUP BY c.id",
            countQuery = "SELECT COUNT(c.id)" +
                    " FROM cars c WHERE (?1 = 0 OR c.type_id = ?1) AND c.rentACar_id = ?5 AND \n" +
                    "         c.max_passengers >= ?2 AND c.daily_price >= ?6 AND c.daily_price <= ?7 AND c.id NOT IN\n" +
                    "         (SELECT r.rented_car_id FROM rent_reservation r \n" +
                    "         WHERE r.rented_car_id = c.id AND ((r.start_date >= ?3 AND r.end_date <= ?4) OR\n" +
                    "          (r.start_date >= ?3 AND r.start_date <= ?4) OR\n" +
                    "          (r.end_date >= ?3 AND r.end_date <= ?4) )) \nGROUP BY c.id",
            nativeQuery = true)
    Page<Cars> filterCars(Long type, int passengers, String pickUp, String  dropOff, Long idrent, BigDecimal minCen, BigDecimal maxCena, Pageable pageable);


    @Query(value = "SELECT * FROM cars c WHERE c.rentacar_id = ?1 AND c.id = ?2 AND c.id NOT IN" +
            "(SELECT r.rented_car_id FROM rent_reservation r \n" +
            "         WHERE r.rented_car_id = c.id AND ((r.start_date >= ?3 AND r.end_date <= ?4) OR\n" +
            "          (r.start_date >= ?3 AND r.start_date <= ?4) OR\n" +
            "          (r.end_date >= ?3 AND r.end_date <= ?4) )) \nGROUP BY c.id"
            ,nativeQuery = true)
    Cars isTheCarRentedInTheFuture(Long idrent, Long id, String start, String end);



}
