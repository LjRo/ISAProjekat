package isa.projekat.Projekat.repository;

import isa.projekat.Projekat.model.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
