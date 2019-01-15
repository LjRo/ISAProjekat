package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.hotel.HotelServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HotelServicesRepository extends JpaRepository<HotelServices, Long> {


}
