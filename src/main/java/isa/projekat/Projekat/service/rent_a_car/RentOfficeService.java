package isa.projekat.Projekat.service.rent_a_car;


import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.LocationRepository;
import isa.projekat.Projekat.repository.RentCarRepository;
import isa.projekat.Projekat.repository.RentOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class RentOfficeService {

    @Autowired
    private RentOfficeRepository rentOfficeRepository;

    @Autowired
    private RentCarRepository rentCarRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public Page<RentOffice> findAll(PageRequest pageRequest) { return rentOfficeRepository.findAll(pageRequest);}

    @Transactional(readOnly = true)
    public Page<RentOffice> findAllByRentACarId(Long id, PageRequest pageRequest) { return rentOfficeRepository.findAllByRentACarId(id, pageRequest);}

    @Transactional(readOnly = true)
    public RentOffice findByIdAndRentACarId(Long id, Long idrent){ return rentOfficeRepository.findByIdAndRentACarId(id,idrent);}

    @Transactional(readOnly = true)
    public List<RentOffice> findAllByRentACarIdList(Long id) { return rentOfficeRepository.findAllByRentACarId(id);}



    @Transactional
    public boolean addOffice(RentOffice office,Long id, User user) {

        RentACar rentACar = rentCarRepository.findById(id).get();

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

        locationRepository.save(location);
        rentCarRepository.save(rentACar);
        return true;
    }


    @Transactional
    public boolean editOffice(RentOffice office, User user, Long id){

        Optional<RentOffice> rentOffice = rentOfficeRepository.findById(office.getId());

        Optional<RentACar> rentACar = rentCarRepository.findById(id);

        if (!checkIfPresent(rentACar,rentOffice,user)){
            return false;
        }

        RentOffice fromDatabase = rentOffice.get();

        fromDatabase.getLocation().setCountry(office.getLocation().getCountry());
        fromDatabase.getLocation().setCity(office.getLocation().getCity());
        fromDatabase.getLocation().setLongitude(office.getLocation().getLongitude());
        fromDatabase.getLocation().setLatitude(office.getLocation().getLatitude());
        fromDatabase.getLocation().setAddressName(office.getLocation().getAddressName());

        //fromDatabase.getLocation().setId(office.getLocation().getId()); // id should not be changed, but will leave this in case for future...

        fromDatabase.setName(office.getName());
        locationRepository.save(fromDatabase.getLocation());
        rentOfficeRepository.save(fromDatabase);
        return  true;
    }

    @Transactional
    public boolean removeOffice(Long id, Long idrent, User user){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(idrent);
        Optional<RentOffice> optionalRentOffice = rentOfficeRepository.findById(id);

        if (!checkIfPresent(optionalRentACar,optionalRentOffice,user)){
            return false;
        }

        RentOffice toRemove = optionalRentOffice.get();

        toRemove.setLocation(null); // the location has been saved for all times
        optionalRentACar.get().getCars().remove(toRemove);

        rentOfficeRepository.delete(toRemove);

        return true;
    }

    private boolean checkIfPresent(Optional<RentACar> rentACar, Optional<RentOffice> rentOffice, User user){
        if (rentACar == null || rentOffice == null || user == null){
            return false;
        }else if (!rentACar.isPresent() || !rentOffice.isPresent()) {
            return false;
        }else if (!rentACar.get().getAdmins().contains(user)){
            return false;
        }else {
            return true;
        }
    }




}
