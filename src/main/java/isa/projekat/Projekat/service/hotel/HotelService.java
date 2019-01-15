package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private HotelPricesRepository hotelPricesRepository;

    @Autowired
    private HotelServicesRepository hotelServicesRepository;

    @PersistenceContext
    private EntityManager entityManager;



    public List<Hotel> findAll() {
        List<Hotel> returning =  hotelRepository.findAll();
        for (Hotel hotel: returning)
            hotel.setAdmins(null);
        return returning;
    }

    public Hotel findHotelById(Long id) {
        Optional<Hotel> item = hotelRepository.findById(id);
        if(!item.isPresent())
            return null;
        Hotel returning = item.get();
        returning.setAdmins(null);
        return returning;
    }

    public HotelServices findHotelServiceById(Long id){
        Optional<HotelServices> item = hotelServicesRepository.findById(id);
        if(!item.isPresent())
        {
            return  null;
        }
        HotelServices hotelServices = item.get();
        hotelServices.getHotel().setAdmins(null);
        return hotelServices;
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

        HotelPriceList hotelPriceList = new HotelPriceList();
        hotelPriceList.setPrice(new BigDecimal(0));
        hotelPriceList.setHotel(target);
        hotelPriceList.setRoomType(roomType);
        hotelPriceList.setStarts(new Date());

        hotelPricesRepository.save(hotelPriceList);
        target.getHotelPriceList().add(hotelPriceList);

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

    @Transactional
    public boolean editHotel(Hotel hotel, User user) {
        if(!checkIfAdminAndCorrectAdmin(hotel.getId(),user))
            return false;
        Hotel foundHotel = hotelRepository.findById(hotel.getId()).get();
        foundHotel.setAddress(hotel.getAddress());
        foundHotel.setFastDiscount(hotel.getFastDiscount());
        foundHotel.setDescription(hotel.getDescription());
        foundHotel.setName(hotel.getName());


        hotelRepository.save(foundHotel);
       // entityManager.persist(foundHotel);
        return true;
    }

    @Transactional
    public boolean editHotelList(HotelPriceList hotelPriceList, User user) {
        if(!checkIfAdminAndCorrectAdmin(hotelPriceList.getHotel().getId(),user))
            return false;
        Optional<HotelPriceList> foundHotelPrice = hotelPricesRepository.findById(hotelPriceList.getId());
        if(!foundHotelPrice.isPresent())
            return false;
        HotelPriceList exact = foundHotelPrice.get();
        exact.setStarts(new Date());
        exact.setPrice(hotelPriceList.getPrice());
        // entityManager.persist(foundHotel);
        return true;
    }

    @Transactional
    public boolean addHotelServices(HotelServices hotelServices, Long id, User user) {

        if(!checkIfAdminAndCorrectAdmin(id,user))
            return false;
        Hotel target = user.getAdministratedHotel();
        HotelServices newHotelService = new HotelServices();
        newHotelService.setName(hotelServices.getName());
        newHotelService.setPrice(hotelServices.getPrice());
        target.getHotelServices().add(newHotelService);
        hotelServicesRepository.save(newHotelService);
        //entityManager.persist(newFloorPlan);
        //entityManager.persist(target);
        return true;
    }

    @Transactional
    public boolean editHotelServices(HotelServices hotelServices, User user) {
        if(!checkIfAdminAndCorrectAdmin(hotelServices.getHotel().getId(),user))
            return false;
        Optional<HotelServices> foundHotelServices = hotelServicesRepository.findById(hotelServices.getId());
        if(foundHotelServices.isPresent())
        {
            HotelServices edited = foundHotelServices.get();
            edited.setName(hotelServices.getName());
            edited.setPrice(hotelServices.getPrice());
            hotelServicesRepository.save(edited);
            return true;
        }
        // entityManager.persist(foundHotel);
        return false;
    }

    @Transactional
public boolean removeHotelService(HotelServices hotelServices, User user) {
        if(!checkIfAdminAndCorrectAdmin(hotelServices.getHotel().getId(),user))
            return false;
        Optional<HotelServices> foundHotelServices = hotelServicesRepository.findById(hotelServices.getId());
        if(foundHotelServices.isPresent())
        {
            HotelServices removing = foundHotelServices.get();
            Hotel target = hotelRepository.findById(hotelServices.getHotel().getId()).get();
            target.getHotelServices().remove(removing);
            removing.setHotel(null);
            hotelServicesRepository.delete(removing);
            return true;
        }
        // entityManager.persist(foundHotel);
        return false;
    }


    @SuppressWarnings("Duplicates")
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
