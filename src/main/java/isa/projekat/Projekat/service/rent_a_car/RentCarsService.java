package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.LocationRepository;
import isa.projekat.Projekat.repository.RentCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RentCarsService {

    @Autowired
    private RentCarRepository rentCarRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public List<RentACar> findAll(){
        return rentCarRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RentACar findById(Long id) {
        Optional<RentACar> rentACar = rentCarRepository.findById(id);
        if (!rentACar.isPresent()){
            return null;
        }else {
            return rentACar.get();
        }


    }

    @Transactional
    public boolean editRentACar(RentACar rentACar, User user){

        Optional<RentACar> optionalRentACar = rentCarRepository.findById(rentACar.getId());

        if (!optionalRentACar.isPresent())
            return  false;
        RentACar rent = optionalRentACar.get();

        if (!rent.getAdmins().contains(user))
            return  false;

        rent.setFastDiscount(rentACar.getFastDiscount());
       /* rent.setBronzeDiscount(rentACar.getBronzeDiscount());
        rent.setSilverDiscount(rentACar.getSilverDiscount());
        rent.setGoldDiscount(rentACar.getGoldDiscount());
        */
        rent.getAddress().setAddressName(rentACar.getAddress().getAddressName());
        rent.getAddress().setLatitude(rentACar.getAddress().getLatitude());
        rent.getAddress().setLongitude(rentACar.getAddress().getLongitude());
        rent.getAddress().setCity(rentACar.getAddress().getCity());
        rent.getAddress().setCountry(rentACar.getAddress().getCountry());

        rent.setDescription(rentACar.getDescription());
        rent.setName(rentACar.getName());

        rentCarRepository.save(rent);

        //entityManager.persist(rent.getAddress());
        //entityManager.persist(rent);

        return  true;
    }

    @Transactional
    public boolean addRentacar(RentACar rentACar){
        RentACar rent = new RentACar();
        rent.setFastDiscount(rentACar.getFastDiscount());

        Location location = new Location();
        location.setAddressName(rentACar.getAddress().getAddressName());
        location.setCity(rentACar.getAddress().getCity());
        location.setLongitude(rentACar.getAddress().getLongitude());
        location.setLatitude(rentACar.getAddress().getLatitude());
        location.setCountry(rentACar.getAddress().getCountry());

        locationRepository.save(location);
        rent.setDescription(rentACar.getDescription());
        rent.setName(rentACar.getName());
        rentCarRepository.save(rent);
        return  true;
    }


    @Transactional(readOnly = true)
    public List<RentACar> findAllByName(String name, String startDate, String endDate){
        List<RentACar> list = rentCarRepository.findAllByName(name,startDate,endDate);

        for (RentACar a: list) {
            a.setAdmins(null);
        }

        return list;
    }

    @Transactional(readOnly = true)
    public List<RentACar> findAllByLocation(String city, String startDate, String endDate){
        List<RentACar> list =  rentCarRepository.findAllByCity(city,startDate,endDate);

        for (RentACar a: list) {
            a.setAdmins(null);
        }

        return list;
    }

}
