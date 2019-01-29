package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.rent_a_car.CarType;
import isa.projekat.Projekat.model.rent_a_car.Cars;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface RentReservationRepository extends JpaRepository<RentReservation, Long> {

    public List<RentReservation> findByUserId(Long id);

    public void removeById(Long id);

    public List<RentReservation> findAllByRentedCarId(Long id);


}
