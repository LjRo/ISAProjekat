package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.rent_a_car.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CarRepository extends JpaRepository<Cars, Long> {
}
