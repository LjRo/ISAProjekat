package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.ReservationRepository;
import isa.projekat.Projekat.repository.SeatRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRespository;

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id).get();
    }


    public SeatData findSeatDataById(Long id) {
        SeatData result = new SeatData();
        Flight target = findById(id);

        result.setSegments(target.getSegments());
        result.setColumns(target.getC_columns());
        result.setRows(target.getC_rows());
        result.setSeats(target.getSeats());

        return result;
    }

    public List<Reservation> findFutureReservationByUserId(Long userId){
        String date = java.time.LocalDate.now().toString();
        List<Reservation> reservations = reservationRespository.returnAllFutureReservationsByUser(userId,date);
        return reservations;
    }



    public List<Reservation> findReservationsByUserId(Long userId){
        List<Reservation> reservations = reservationRespository.getAllByUser(userId);
        return reservations;
    }


    public Boolean bookFlight(BookingData bd, String email) {
        List<ReservationData> reservations = bd.getAirlineReservations();

        User requester = userRepository.findByUsername(email);

        if (!requester.getId().equals(bd.getAirlineReservations().get(0).getUserId())) {
            return false;
        }

        for (ReservationData resData : reservations) {

            Seat targetSeat = seatRepository.findById(resData.getSeatId()).get();
            User targetUser;

            if (resData.getUserId() == null) {
                targetUser = null;
            } else {
                targetUser = userRepository.findById(resData.getUserId()).get();
            }

            if (targetSeat == null) {
                return false;
            }
            if (targetSeat.isTaken()) {
                return false;
            }

            if (resData.getPassport() == null || resData.getPassport() == "") {
                return false;
            }

            if (resData.getPointsUsed() > 10.0)
                resData.setPointsUsed(10.0);
            if (resData.getPointsUsed() < 0.0)
                resData.setPointsUsed(0.0);

            resData.setTotalCost(targetSeat.getPrice());

            Reservation newRes = new Reservation();
            Flight flight = flightRepository.findById(resData.getFlight()).get();
            newRes.setFlight(flight);
            if (targetUser == null) {

                if (resData.getFirstName() == null || resData.getFirstName() == "") {
                    return false;
                }
                if (resData.getLastName() == null || resData.getLastName() == "") {
                    return false;
                }
                newRes.setName(resData.getFirstName());
                newRes.setLastName(resData.getLastName());
                newRes.setPointsUsed(0.0);
                newRes.setTotalCost(targetSeat.getPrice());
                newRes.setConfirmed(true);
            } else {

                if (targetUser.getId() == requester.getId()) {
                    newRes.setConfirmed(true);
                } else {
                    newRes.setConfirmed(false);
                }

                newRes.setName(targetUser.getFirstName());
                newRes.setLastName(targetUser.getLastName());
                newRes.setUser(targetUser);
                BigDecimal priceFactor = new BigDecimal(1);
                if (targetUser.getPoints() >= resData.getPointsUsed()) {
                    targetUser.setPoints(targetUser.getPoints() - resData.getPointsUsed());
                    newRes.setPointsUsed(resData.getPointsUsed());
                    priceFactor = (new BigDecimal(1.0 - (resData.getPointsUsed() / 10 * 0.05)));
                    targetUser.setPoints(targetUser.getPoints()+flight.getDistance()/1000);
                } else {
                    newRes.setPointsUsed(0.0);
                }

                newRes.setTotalCost(resData.getTotalCost().multiply(priceFactor));
            }


            newRes.setPassport(resData.getPassport());
            newRes.setSeat(targetSeat);
            targetSeat.setReservation(newRes);
            targetSeat.setTaken(true);
            reservationRespository.save(newRes);
        }
        return true;
    }

    public Boolean quickBookFlight(QuickTicketData qTD, String email) {

        User requester = userRepository.findByUsername(email);

        if (requester == null) {
            return false;
        }

        Seat targetSeat = seatRepository.findById(qTD.getSeatId()).get();

        if (targetSeat == null || targetSeat.isTaken()) {
            return false;
        }
        if (qTD.getPassport() == null || qTD.getPassport() == "") {
            return false;
        }
        if(!targetSeat.isQuick()) {
            return false;
        }

        Reservation newRes = new Reservation();

        newRes.setName(requester.getFirstName());
        newRes.setLastName(requester.getLastName());
        newRes.setPointsUsed(0.0);
        newRes.setTotalCost(targetSeat.getPrice().multiply(new BigDecimal(0.95)));
        newRes.setConfirmed(true);
        newRes.setUser(requester);

        newRes.setPassport(qTD.getPassport());
        newRes.setSeat(targetSeat);
        targetSeat.setTaken(true);
        targetSeat.setReservation(newRes);
        reservationRespository.save(newRes);

        return true;
    }

    public List<FlightSearchResultData> searchFlights(FlightSearchData data) {
        ArrayList<FlightSearchResultData> res = new ArrayList<>();
        //List<Flight> locFlights = flightRepository.findFlights(data.getCityFrom(),data.getCityTo());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        List<Flight> locFlights = flightRepository.findAll();
        DateTime current = new DateTime();

        for(Flight fl : locFlights) {
                if(!fl.getStart().getCity().equals(data.getCityFrom())) {
                    continue;
                }
                if(!fl.getFinish().getCity().equals(data.getCityTo())) {
                    continue;
                }
                if(fmt.format(fl.getStartTime()).equals(fmt.format(data.getStartDate()))) {
                    if (data.getLandDate() != null) {
                        if (fmt.format(fl.getLandTime()).equals(fmt.format(data.getLandDate()))) {
                            if(current.isBefore(fl.getStartTime().getTime())) {
                                res.add(formatData(fl));
                            }
                        }
                    } else {
                        if(current.isBefore(fl.getStartTime().getTime())) {
                            res.add(formatData(fl));
                        }
                    }
                }
            }


        return res;
    }

    private FlightSearchResultData formatData(Flight fl) {

        FlightSearchResultData nD = new FlightSearchResultData();
        nD.setFlight(fl);
        nD.setHasExtraLuggage(fl.getAirline().getHasExtraLuggage());
        nD.setHasFood(fl.getAirline().getHasFood());
        nD.setHasOtherServices(fl.getAirline().getHasOtherServices());

        return nD;
    }
}

