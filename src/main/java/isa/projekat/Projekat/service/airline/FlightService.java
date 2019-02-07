package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private OrderRepository orderRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Flight findById(Long id) {
        return flightRepository.findById(id).get();
    }


    @Transactional(readOnly = true)
    public SeatData findSeatDataById(Long id) {
        SeatData result = new SeatData();
        Flight target = findById(id);

        result.setSegments(target.getSegments());
        result.setColumns(target.getC_columns());
        result.setRows(target.getC_rows());
        result.setSeats(target.getSeats());

        return result;
    }

    @Transactional(readOnly = true)
    public List<Order> findAllNotFinished(Long id){
        return orderRepository.findAllByPlacedOrderIdAndFinishedIsFalse(id);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllOrders(Long id){
        return orderRepository.findAllByPlacedOrderIdAndFinishedIsFalse(id);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findFutureReservationByUserId(Long userId){
        String date = java.time.LocalDate.now().toString();
        List<Reservation> reservations = reservationRepository.returnAllFutureReservationsByUser(userId,date);
        return reservations;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findRentReservations(Long userId){
        String date = java.time.LocalDate.now().toString();
        List<Reservation> reservations = reservationRepository.returnFutureRentReservationByUser(userId,date);
        return reservations;
    }



    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByUserId(Long userId){
        List<Reservation> reservations = reservationRepository.getAllByUser(userId);
        return reservations;
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservationsByUserId(Long userId){
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);
        return reservations;
    }

    @Transactional
    public Boolean finishOrder(Long id,String email) {
        Order target = orderRepository.findById(id).get();
        User req = userRepository.findByUsername(email);

        for(Order o: req.getOrders()) {
            if(o.getId().equals(target.getId())) {
                target.setFinished(true);
                orderRepository.save(target);
                return true;
            }
        }
        return false;
    }


    @Transactional(readOnly = true)
    public Boolean isOrdering(Long id,String email) {
        Order target = orderRepository.findById(id).get();
        User req = userRepository.findByUsername(email);

        for(Order o: req.getOrders()) {
            if(o.getId().equals(target.getId())) {
                return true;
            }
        }
        return false;
    }


    @Transactional
    public Boolean bookFlight(BookingData bd, String email) {
        List<ReservationData> reservations = bd.getAirlineReservations();

        User requester = userRepository.findByUsername(email);

        if (!requester.getId().equals(bd.getAirlineReservations().get(0).getUserId())) {
            return false;
        }
        Order nOrder = new Order(new ArrayList<Reservation>(),requester,new Date(),false );
        orderRepository.save(nOrder);

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

            if (resData.getPassport() == null || resData.getPassport().equals("")) {
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

                if (resData.getFirstName() == null || resData.getFirstName().equals("")) {
                    return false;
                }
                if (resData.getLastName() == null || resData.getLastName().equals("")) {
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
            nOrder.getReservations().add(newRes);
            newRes.setOrder(nOrder);
            reservationRepository.save(newRes);

        }
        orderRepository.save(nOrder);
        return true;
    }


    @Transactional
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
        newRes.setTotalCost(targetSeat.getPrice().multiply(new BigDecimal("0.95")));
        newRes.setConfirmed(true);
        newRes.setUser(requester);

        newRes.setPassport(qTD.getPassport());
        newRes.setSeat(targetSeat);
        targetSeat.setTaken(true);
        targetSeat.setReservation(newRes);
        reservationRepository.save(newRes);

        return true;
    }

    @Transactional(readOnly = true)
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
                        if (fmt.format(fl.getLandTime()).equals(fmt.format(data.getLandDate())) && current.isBefore(fl.getStartTime().getTime())) {
                            res.add(formatData(fl));
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

