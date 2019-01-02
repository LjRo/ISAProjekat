package isa.projekat.Projekat.service.rent_a_car;


import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.repository.RentCarRepository;
import isa.projekat.Projekat.repository.RentOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RentOfficeService {

    @Autowired
    private RentOfficeRepository rentOfficeRepository;

    @Autowired
    private RentCarRepository rentCarRepository;


    public Page<RentOffice> findAll(PageRequest pageRequest) { return rentOfficeRepository.findAll(pageRequest);}

    public Page<RentOffice> findAllByRentACarId(Long id, PageRequest pageRequest) { return rentOfficeRepository.findAllByRentACarId(id, pageRequest);}

}
