package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.rent_a_car.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CarTypeRepository extends JpaRepository<CarType,Long> {

}
