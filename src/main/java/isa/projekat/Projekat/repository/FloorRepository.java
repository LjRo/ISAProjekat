package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.hotel.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface FloorRepository extends JpaRepository<FloorPlan, Long> {

}
