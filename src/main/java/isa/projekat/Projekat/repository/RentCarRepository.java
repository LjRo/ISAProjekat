package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.rent_a_car.RentACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RentCarRepository extends JpaRepository<RentACar, Long> {
}
