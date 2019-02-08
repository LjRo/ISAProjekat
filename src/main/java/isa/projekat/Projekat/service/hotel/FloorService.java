package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.FloorPlan;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.FloorRepository;
import isa.projekat.Projekat.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class FloorService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private FloorRepository floorRepository;

    public FloorPlan findById(Long id) {
        return floorRepository.findById(id).get();
    }
    public Set<FloorPlan> findByHotelIdOrderedByFloorNumberAsc(Long id){
        return floorRepository.findByHotelIdOrderByFloorNumberAsc(id);
    }


    @Transactional
    public boolean editFloorPlan(FloorPlan floorPlan, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;
        Hotel target = user.getAdministratedHotel();
        Optional<FloorPlan> optionalFloorPlan = floorRepository.findById(floorPlan.getId());
        if(target == null || !optionalFloorPlan.isPresent())
        {
            return false;
        }


        FloorPlan updating = optionalFloorPlan.get();
        updating.setConfiguration(floorPlan.getConfiguration());

        floorRepository.save(updating);
        return true;
    }

    @SuppressWarnings("Duplicates")
    private boolean checkIfAdminAndCorrectAdmin(Long id,User adminToCheck){
        if(adminToCheck.getAdministratedHotel() == null) {
            return false;
        }
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);

        if(!optionalHotel.isPresent())
            return false;
        Hotel hotel = optionalHotel.get();

        if(!hotel.getAdmins().contains(adminToCheck))
        {
            return false;
        }
        return true;
    }

}
