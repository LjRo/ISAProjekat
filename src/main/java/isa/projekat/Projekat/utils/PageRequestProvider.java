package isa.projekat.Projekat.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class PageRequestProvider {

    public PageRequest provideRequest(String page){
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (Exception e){
            pageNumber = 0;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber,10);
        return pageRequest;
    }
}
