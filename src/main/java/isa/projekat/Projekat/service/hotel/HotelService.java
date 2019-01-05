package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.FloorRepository;
import isa.projekat.Projekat.repository.HotelRepository;
import isa.projekat.Projekat.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private FloorRepository floorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Hotel> findAll() {
        List<Hotel> returning =  hotelRepository.findAll();
        for (Hotel hotel: returning)
            hotel.setAdmins(null);
        return returning;
    }

    public Hotel findHotelById(Long id) {
        Hotel returning = hotelRepository.findById(id).get();
        returning.setAdmins(null);
        return returning;
    }

    @Transactional
    public boolean addRoom(RoomData roomData, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;

        Hotel target = user.getAdministratedHotel();
        RoomType roomType = roomTypeRepository.findById(roomData.getRoomType().getId()).get();

        Room room = new Room();
        room.setHotel(target);
        room.setName(roomData.getName());
        room.setFloor(roomData.getFloor());
        room.setNumberOfBeds(roomData.getNumberOfBeds());
        room.setNumberOfPeople(roomData.getNumberOfPeople());
        room.setNumberOfRooms(roomData.getNumberOfRooms());
        room.setRoomNumber(roomData.getRoomNumber());
        room.setRoomType(roomType);
        target.getRooms().add(room);

        entityManager.persist(room);
        entityManager.persist(target);
        return true;
    }
    @Transactional
    public boolean addRoomType(RoomType roomTypeData, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;
        RoomType roomType = new RoomType();
        roomType.setName(roomTypeData.getName());
        Hotel target = user.getAdministratedHotel();
        target.getRoomTypes().add(roomType);

        entityManager.persist(roomType);
        entityManager.persist(target);

        return true;
    }



    @Transactional
    public boolean addFloorPlan(FloorPlan floorPlan, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;
        Hotel target = user.getAdministratedHotel();

        FloorPlan newFloorPlan = new FloorPlan();
        newFloorPlan.setConfiguration(floorPlan.getConfiguration());
        newFloorPlan.setFloorNumber(floorPlan.getFloorNumber());
        newFloorPlan.setHotel(target);

        target.getFloorPlans().add(newFloorPlan);

        entityManager.persist(newFloorPlan);
        entityManager.persist(target);
        return true;
    }

    @Transactional
    public boolean removeFloorPlan(Long idFloor, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;
        Hotel target = user.getAdministratedHotel();
        FloorPlan selected = floorRepository.findById(idFloor).get();

        target.getFloorPlans().remove(selected);
        floorRepository.delete(selected);

        entityManager.persist(target);
        return true;
    }



    private boolean checkIfAdminAndCorrectAdmin(Long id,User adminToCheck){
        if(adminToCheck.getAdministratedHotel() == null) {
            return false;
        }
        Hotel hotel = hotelRepository.findById(id).get();

        if(!hotel.getAdmins().contains(adminToCheck))
        {
            return false;
        }
        return true;
    }

}
