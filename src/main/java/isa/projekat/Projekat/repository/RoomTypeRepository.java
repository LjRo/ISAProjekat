package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

}
