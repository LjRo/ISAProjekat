package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.hotel.Room;
import isa.projekat.Projekat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Page<Room> findAll(PageRequest pageRequest){
        return roomRepository.findAll(pageRequest);
    }

    public Page<Room> findByHotelId( Long id,PageRequest pageRequest) { return roomRepository.findByHotelId(id,pageRequest);}

}
