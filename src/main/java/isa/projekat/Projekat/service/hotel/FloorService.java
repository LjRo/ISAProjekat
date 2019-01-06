package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FloorService {

    @Autowired
    private FloorRepository floorRepository;

    public FloorPlan findById(Long id) {
        return floorRepository.findById(id).get();
    }
    public Set<FloorPlan> findByHotelIdOrderedByFloorNumberAsc(Long id){
        return floorRepository.findByHotelIdOrderByFloorNumberAsc(id);
    }
}
