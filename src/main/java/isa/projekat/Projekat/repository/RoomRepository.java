package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    Page<Room> findAll(Pageable pageable);

    Page<Room> findByHotelId(Long Hotel, Pageable pageable);
}
