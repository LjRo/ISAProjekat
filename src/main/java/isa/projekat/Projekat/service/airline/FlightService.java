package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.ReservationRepository;
import isa.projekat.Projekat.repository.SeatRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Boolean bookFlight(BookingData bd, String email) {
        List<ReservationData> reservations = bd.getAirlineReservations();

        User requester = userRepository.findByUsername(email);

        if(!requester.getId().equals(bd.getAirlineReservations().get(0).getUserId())) {
            return false;
        }

        for (ReservationData resData : reservations) {

            Seat targetSeat = seatRepository.findById(resData.getSeatId()).get();
            User targetUser;

            if(resData.getUserId() == null) {
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

            if(resData.getPassport() == null || resData.getPassport() == "") {
                return false;
            }

            if (resData.getPointsUsed() > 10.0)
                resData.setPointsUsed(10.0);
            if(resData.getPointsUsed() < 0.0)
                resData.setPointsUsed(0.0);

            resData.setTotalCost(targetSeat.getPrice());

            Reservation newRes = new Reservation();
            if(targetUser==null) {

                if(resData.getFirstName() == null || resData.getFirstName() == "") {
                    return false;
                }
                if(resData.getLastName() == null || resData.getLastName() == "") {
                    return false;
                }

                newRes.setName(resData.getFirstName());
                newRes.setLastName(resData.getLastName());
                newRes.setPointsUsed(0.0);
                newRes.setTotalCost(targetSeat.getPrice());
            } else {
                newRes.setName(targetUser.getFirstName());
                newRes.setLastName(targetUser.getLastName());
                newRes.setUser(targetUser);
                BigDecimal priceFactor = new BigDecimal(1);
                if(targetUser.getPoints() >= resData.getPointsUsed()) {
                    targetUser.setPoints(targetUser.getPoints()- resData.getPointsUsed());
                    newRes.setPointsUsed(resData.getPointsUsed());
                    priceFactor = (new BigDecimal(1.0 - (resData.getPointsUsed()/10 * 0.05)));
                } else {
                    newRes.setPointsUsed(0.0);
                }

                newRes.setTotalCost(resData.getTotalCost().multiply(priceFactor));
            }



            newRes.setPassport(resData.getPassport());
            newRes.setSeat(targetSeat);
            targetSeat.setTaken(true);
            reservationRespository.save(newRes);
        }
        return true;
    }
}
