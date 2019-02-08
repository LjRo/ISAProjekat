package isa.projekat.Projekat.service;

import isa.projekat.Projekat.model.Rating;
import isa.projekat.Projekat.model.airline.Reservation;
import isa.projekat.Projekat.model.hotel.ReservationHotel;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.repository.HotelReservationRepository;
import isa.projekat.Projekat.repository.RatingRepository;
import isa.projekat.Projekat.repository.RentReservationRepository;
import isa.projekat.Projekat.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RentReservationRepository rentReservationRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /*Type : 1- cars , 2 - flight , 3- rooms  // only for searches  => 4-rentacar  , 5 - hotel , 6 - airlines */
    @Transactional(readOnly = true)
    public Double getRatingForTypeAndId(Integer type, Long id){
        Double rating = 0.0;
        switch (type){
            case 1:
                rating =  ratingRepository.getAverageCarRatingById(id);
                break;
            case 2:
                rating = ratingRepository.getAverageFlightRatingById(id);
                break;
            case 3:
                rating = ratingRepository.getAverageRoomRatingById(id);
                break;
            case 4:
                rating = ratingRepository.getAverageRentACarRatingById(id);
               break;
            case 5:
                rating = ratingRepository.getAverageHotelRatingById(id);
                break;
            case 6:
                rating = ratingRepository.getAverageAirlineRatingById(id);
                break;
            default:
                break;
        }
        if (rating == null)
            rating = 0.0;
        return rating;
    }

    /* Type : 1- cars , 2 - flight , 3- rooms */
    @Transactional
    public Boolean addNewRating(Rating rating){
        Date date = new Date();
        if (rating.getType() == 0){
            return false;
        }
        boolean alreadyVoted = true;
        boolean moreInfo = true;
        boolean rightTime = true;

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();



        switch (rating.getType()){
            case 1:
                if (rating.getRentReservation() != null){
                    alreadyVoted = ratingRepository.countByRentReservation_Id(rating.getRentReservation().getId()) == 1;
                    if (rating.getFlightReservation() != null || rating.getHotelReservation() != null){
                        moreInfo = false;

                        RentReservation rent = rentReservationRepository.getOne(rating.getRentReservation().getId());

                        if (rent != null){
                            cal1.setTime(rent.getEndDate());
                            cal2.setTime(date);
                            if (sameDay(cal1,cal2) || date.after(rent.getEndDate())){
                                rightTime = false;
                            }
                        }

                    }
                }
                break;
            case 2:
                if (rating.getFlightReservation() != null){
                    alreadyVoted = ratingRepository.countByFlightReservation_Id(rating.getRentReservation().getId()) == 1;
                    if (rating.getRentReservation() != null || rating.getHotelReservation() != null){
                        moreInfo = false;

                        Reservation flight = reservationRepository.getOne(rating.getFlightReservation().getId());
                        if (flight != null && flight.getFlight() != null){
                            cal1.setTime(flight.getFlight().getLandTime());
                            cal2.setTime(date);
                            if (sameDay(cal1,cal2) || date.after(flight.getFlight().getLandTime())){
                                rightTime = false;
                            }
                        }
                    }
                }
                break;
            case 3:
                if (rating.getHotelReservation() != null){
                    alreadyVoted = ratingRepository.countByHotelReservation_Id(rating.getHotelReservation().getId()) == 1;
                    if (rating.getRentReservation() != null || rating.getFlightReservation() != null){
                        moreInfo = false;

                        ReservationHotel hotel = hotelReservationRepository.getOne(rating.getHotelReservation().getId());
                        if (hotel != null){
                            cal1.setTime(hotel.getDepartureDate());
                            cal2.setTime(date);

                            if (sameDay(cal1,cal2) || date.after(hotel.getDepartureDate())){
                                rightTime = false;
                            }

                        }
                    }
                }
                break;
            default:
                return false;
        }
        // does not save if it has more references, already voted on the reservation or is not after the end date
        if (alreadyVoted || moreInfo || rightTime)
            return false;

        ratingRepository.save(rating);
        return true;
    }

    private boolean sameDay(Calendar cal1, Calendar cal2){
        return  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

}
