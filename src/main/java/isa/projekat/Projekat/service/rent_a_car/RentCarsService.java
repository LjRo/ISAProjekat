package isa.projekat.Projekat.service.rent_a_car;

import isa.projekat.Projekat.model.rent_a_car.RentACar;
import isa.projekat.Projekat.model.rent_a_car.RentOffice;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.RentCarRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class RentCarsService {

    @Autowired
    private RentCarRepository rentCarRepository;

    public List<RentACar> findAll(){
        return rentCarRepository.findAll();
    }

    public RentACar findById(Long id) {
        return  rentCarRepository.findById(id).get();
    }

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

    public List<RentACar> findAllByName(String name, String startDate, String endDate){return rentCarRepository.findAllByName(name,startDate,endDate);}

    public List<RentACar> findAllByLocation(String city, String startDate, String endDate){return  rentCarRepository.findAllByCity(city,startDate,endDate);}

}
