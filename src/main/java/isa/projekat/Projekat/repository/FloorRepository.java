package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.hotel.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface FloorRepository extends JpaRepository<FloorPlan, Long> {

    Set<FloorPlan> findByHotelIdOrderByFloorNumberAsc(Long id);
    FloorPlan findByIdOrderByFloorNumberAsc(Long id);
}
