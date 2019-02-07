package isa.projekat.Projekat.service.user_auth;

import isa.projekat.Projekat.model.Prices;
import isa.projekat.Projekat.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PricesService {

    @Autowired
    private PriceRepository priceRepository;

    @Transactional(readOnly = true)
    public Float getPrice(String name) {
        Optional<Prices> item = priceRepository.findByPriceName(name);
        if(!item.isPresent())
            {
                if(name.equals("Coupon"))
                {
                    Prices a = new Prices();
                    a.setDiscount(5f);
                    a.setPriceName("Coupon");
                    priceRepository.save(a);
                    return a.getDiscount();
                }
                return 0.0f;
            }
        return item.get().getDiscount();
    }

    @Transactional
    public boolean setPrice(Prices prices) {
        Optional<Prices> item = priceRepository.findByPriceName(prices.getPriceName());
        if(!item.isPresent())
            {
                Prices a = new Prices();
                a.setDiscount(prices.getDiscount());
                a.setPriceName(prices.getPriceName());
                priceRepository.save(a);
            }
        else {
            Prices found= item.get();
            found.setDiscount(prices.getDiscount());
            priceRepository.save(found);
        }
        return true;
    }

}
