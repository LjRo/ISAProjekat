package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.hotel.HotelPriceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface HotelPricesRepository extends JpaRepository<HotelPriceList, Long> {


}
