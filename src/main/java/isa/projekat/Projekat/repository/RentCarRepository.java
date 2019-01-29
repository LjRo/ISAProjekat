package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RentCarRepository extends JpaRepository<RentACar, Long> {

    @Query(value = "SELECT * FROM rentacar rc \n" +
            "\t\tWHERE EXISTS (SELECT * FROM rent_office ro left outer join locations l on ro.location_id = l.id WHERE (l.address_name LIKE CONCAT('%',?1,'%') OR l.city LIKE CONCAT('%',?1,'%')) AND ro.rentacar_id = rc.id) \n" +
            "        AND EXISTS\t(SELECT * FROM cars as c WHERE  c.rentACar_id = rc.id AND c.id NOT IN\n" +
            "\t\t\t\t\t(SELECT r.rented_car_id \n" +
            "\t\t\t\t\t FROM rent_reservation as r \n" +
            "\t\t\t\t\t WHERE r.rented_car_id = c.id AND r.rented_car_id = c.id AND (r.start_date >= '2019-01-01' AND r.end_date <= '2019-01-02') OR\n" +
            "\t\t\t\t\t  (r.start_date >= ?2 AND r.start_date <= ?3) OR\n" +
            "\t\t\t\t\t  (r.end_date >= ?2 AND r.end_date <= ?3)))", nativeQuery = true)
    List<RentACar> findAllByCity(String city, String startDate, String endDate);

    @Query(value = "SELECT * FROM rentacar rc WHERE rc.name like CONCAT('%',?1,'%') AND EXISTS\t(SELECT * FROM cars as c WHERE  c.rentACar_id = rc.id AND c.id NOT IN\n" +
            "\t\t\t\t\t(SELECT r.rented_car_id \n" +
            "\t\t\t\t\t FROM rent_reservation as r \n" +
            "\t\t\t\t\t WHERE r.rented_car_id = c.id AND r.rented_car_id = c.id AND (r.start_date >= ?2 AND r.end_date <= ?3) OR\n" +
            "\t\t\t\t\t  (r.start_date >= ?2 AND r.start_date <= ?3) OR\n" +
            "\t\t\t\t\t  (r.end_date >= ?2 AND r.end_date <= ?3)));", nativeQuery = true)
    List<RentACar> findAllByName(String name, String startDate, String endDate);

}
