package isa.projekat.Projekat.service.rent_a_car;


import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.RentCarRepository;
import isa.projekat.Projekat.repository.RentOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import isa.projekat.Projekat.model.rent_a_car.Location;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Service
public class RentOfficeService {

    @Autowired
    private RentOfficeRepository rentOfficeRepository;

    @Autowired
    private RentCarRepository rentCarRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Page<RentOffice> findAll(PageRequest pageRequest) { return rentOfficeRepository.findAll(pageRequest);}

    public Page<RentOffice> findAllByRentACarId(Long id, PageRequest pageRequest) { return rentOfficeRepository.findAllByRentACarId(id, pageRequest);}

    public RentOffice findByIdAndRentACarId(Long id, Long idrent){ return rentOfficeRepository.findByIdAndRentACarId(id,idrent);}

    @Transactional
    public boolean addOffice(RentOffice office,Long id, User user) {

        RentACar rentACar = rentCarRepository.findById(id).get();

      /*  if (!rentACar.getAdmin().equals(user)){
            return  false; // not a administrator on this particular rent a car
        }
        */
        if (!rentACar.getAdmins().contains(user))
            return false;
        RentOffice rentOffice = new RentOffice();

        rentOffice.setName(office.getName());
        rentOffice.setRentACar(rentACar);

        Location location = new Location();

        location.setAddressName(office.getLocation().getAddressName());
        location.setLatitude(office.getLocation().getLatitude());
        location.setLongitude(office.getLocation().getLongitude());
        location.setCity(office.getLocation().getCity());
        location.setCountry(office.getLocation().getCountry());

        rentOffice.setLocation(location);

        rentACar.getRentOffices().add(rentOffice);

        entityManager.persist(location);
        entityManager.persist(rentACar);

        return true;

    }
}
