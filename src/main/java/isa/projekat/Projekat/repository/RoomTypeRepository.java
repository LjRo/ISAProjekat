package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    public Optional<RoomType> findByName(String name);
}
