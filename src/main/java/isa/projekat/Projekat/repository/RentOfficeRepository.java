package isa.projekat.Projekat.repository;


import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface RentOfficeRepository  extends JpaRepository<RentOffice, Long> {

    @Override
    Page<RentOffice> findAll(Pageable pageable);

    //Page<RentOffice> findAllById(Long id, Pageable pageable);

    Page<RentOffice> findAllByRentACarId(Long id, Pageable pageable);


    RentOffice findByIdAndRentACarId(Long id, Long rentacar);

}
