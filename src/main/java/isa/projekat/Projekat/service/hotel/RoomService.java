package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.HotelRepository;
import isa.projekat.Projekat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;


    public Page<Room> findAll(PageRequest pageRequest){
        return roomRepository.findAll(pageRequest);
    }

    public Page<Room> findByHotelId( Long id,PageRequest pageRequest) { return roomRepository.findByHotelId(id,pageRequest);}

    public Room findById( Long id) {
        Optional<Room> oRoom = roomRepository.findById(id);
        if(oRoom.isPresent())
            return oRoom.get();
        else
            return null;
    }

    @Transactional
    public boolean editRoom(Room room, User user, Long hotelId) {

        if(!checkIfAdminAndCorrectAdmin(hotelId,user))
            return false;
        Room foundRoom = roomRepository.findById(room.getId()).get();
        foundRoom.setNumberOfRooms(room.getNumberOfRooms());
        foundRoom.setRoomType(room.getRoomType());
        foundRoom.setNumberOfPeople(room.getNumberOfPeople());
        foundRoom.setRoomNumber(room.getRoomNumber());
        foundRoom.setFloor(room.getFloor());
        foundRoom.setName(room.getName());
        foundRoom.setNumberOfBeds(room.getNumberOfBeds());
        roomRepository.save(foundRoom);
        return true;
    }



    @Transactional
    public boolean deleteRoom(Room room, User user, Long hotelId) {
        if(!checkIfAdminAndCorrectAdmin(hotelId,user))
            return false;

        Optional<Hotel> foundHotelOpt = hotelRepository.findById(hotelId);
        Optional<Room> foundRoomOpt = roomRepository.findById(room.getId());
        if (!foundRoomOpt.isPresent() || !foundHotelOpt.isPresent())
            return false;

        Room foundRoom = foundRoomOpt.get();
        Hotel hotel = foundHotelOpt.get();
        hotel.getRooms().remove(foundRoom);
        roomRepository.delete(foundRoom);
        return true;
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
