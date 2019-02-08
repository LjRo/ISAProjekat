package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.hotel.ReservationHotel;
import isa.projekat.Projekat.model.rent_a_car.RentReservation;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import isa.projekat.Projekat.service.user_auth.EmailService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private EmailService emailService;

    @Autowired
    private RentReservationRepository rentReservationRepository;
    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Transactional(readOnly = true)
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Flight findById(Long id) {
        if(flightRepository.findById(id).isPresent()) {
            return flightRepository.findById(id).get();
        } else {
            return null;
        }
    }

    @Transactional
    public Boolean cancelOrder(Long idOrder, User user){


        Optional<Order> orderOptional = orderRepository.findById(idOrder);

        if (!orderOptional.isPresent() || user == null){
            return false;
        }
        Order order = orderOptional.get();

        // not the user that placed the order
        if (!order.getPlacedOrder().getId().equals(user.getId())|| order.getReservations().size() == 0){
            return  false;
        }
        Date date = order.getReservations().get(0).getFlight().getStartTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -3);
        if (cal.after(new Date())){
            return false; // can't cancel
        }

        for (Reservation r : order.getReservations()) {
            r.setFlight(null);
            r.getSeat().setTaken(false);
            r.getSeat().setReservation(null);

            seatRepository.save(r.getSeat());
            r.setSeat(null);
            reservationRepository.deleteById(r.getId());
        }

        if (order.getRentReservation() != null){
            RentReservation rentReservation = order.getRentReservation();
            rentReservationRepository.deleteById(rentReservation.getId());
        }
        if (order.getReservationHotel() != null){
            ReservationHotel reservationHotel = order.getReservationHotel();
            reservationHotel.setUser(null);
            reservationHotel.setRoom(null);
            hotelReservationRepository.save(reservationHotel);
        }
        reservationRepository.CleanUp();
        orderRepository.deleteById(order.getId());

        return true;
    }


    @SuppressWarnings("Duplicates")
    @Transactional
    public Boolean cancelRent(Long idOrder, User user){
        Optional<Order> orderOptional = orderRepository.findById(idOrder);
        if (!orderOptional.isPresent()){
            return false;
        }
        Order order = orderOptional.get();
        RentReservation rentReservation = order.getRentReservation();
        if (rentReservation == null){
            return false;
        }

        Date date = order.getRentReservation().getStartDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -3);
        if (!cal.after(new Date())){
            return false; // can't cancel
        }

        order.setRentReservation(null);
        rentReservation.setUser(null);
        rentReservation.setEndLocation(null);
        rentReservation.setStartLocation(null);
        rentReservation.setOrder(null);
        rentReservation.setRentedCar(null);

        rentReservationRepository.save(rentReservation);
        rentReservationRepository.deleteById(rentReservation.getId());

        return true;
    }

    @SuppressWarnings("Duplicates")
    @Transactional
    public Boolean cancelHotel(Long id, User user){
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()){
            return false;
        }
        Order order = orderOptional.get();
        ReservationHotel reservationHotel = order.getReservationHotel();
        if (reservationHotel == null){
            return false;
        }

        Date date = order.getReservationHotel().getArrivalDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -3);
        if (!cal.after(new Date())){
            return false; // can't cancel
        }

        return true;
    }

    // For friends to cancel
    @Transactional
    public Boolean declineFlight(Long idReservation, User user){

        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (!optionalReservation.isPresent()){
            return false;
        }

        Reservation reservation = optionalReservation.get();



        Order fromOrder = orderRepository.findByReservationId(idReservation);
        if (fromOrder == null){
            return false;
        }

        Boolean calledUser = reservation.getUser().getId().equals(user.getId());
        Boolean orderUser = fromOrder.getPlacedOrder().getId().equals(user.getId());
        if (!(calledUser || orderUser)){
            return false;
        }


        fromOrder.getReservations().remove(reservation);

        reservation.setUser(null);
        reservation.setFlight(null);
        reservation.setSeat(null);
        reservation.setOrder(null);
        reservationRepository.save(reservation);

        reservationRepository.deleteById(reservation.getId());

        return true;
    }


    @Transactional
    public Boolean confirmFlight(Long idReservation, User user){
        Optional<Reservation> optionalReservation = reservationRepository.findById(idReservation);

        if (!optionalReservation.isPresent()){
            return false;
        }

        Reservation reservation = optionalReservation.get();

        if (!reservation.getUser().getId().equals(user.getId())){
            return false;
        }

        reservation.setConfirmed(true);
        reservationRepository.save(reservation);
        return true;

    }


    @Transactional(readOnly = true)
    public Order findOrderById(Long id){
        Optional<Order> ord = orderRepository.findById(id);
        if (!ord.isPresent())
            return null;
        else
            return ord.get();
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
        List<Order> list = orderRepository.findAllByPlacedOrderId(id);
        if (list == null){
            return null;
        }else {
            return list;
        }
    }

    @Transactional(readOnly = true)
    public List<Reservation> findFutureReservationByUserId(Long userId){
        String date = java.time.LocalDate.now().toString();
        return reservationRepository.returnAllFutureReservationsByUser(userId,date);

    }

    @Transactional(readOnly = true)
    public List<Reservation> findRentReservations(Long userId){
        String date = java.time.LocalDate.now().toString();
        return reservationRepository.returnFutureRentReservationByUser(userId,date);
    }

    @Transactional(readOnly = true)
    public List<Order> findAllFutureOrders(Long userId){
        String date = java.time.LocalDate.now().toString();
        return  orderRepository.findAllFutureOrdersByUserIdAndDate(userId,date);
    }


    @Transactional(readOnly = true)
    public List<Reservation> findReservationsByUserId(Long userId){
        return reservationRepository.getAllByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findAllReservationsByUserId(Long userId){
        return reservationRepository.findAllByUserId(userId);
    }



    @Transactional
    public Boolean finishOrder(Long id,String email) {
        Order target;
        if(orderRepository.findById(id).isPresent()) {
            target = orderRepository.findById(id).get();
        } else {
            return false;
        }
        User req = userRepository.findByUsername(email);
        if(req  == null) {
            return false;
        }

        for(Order o: req.getOrders()) {
            if(o.getId().equals(target.getId())) {
                target.setFinished(true);
                Flight fl = target.getReservations().get(0).getFlight();
                orderRepository.save(target);
                emailService.sendOrderMail(req,target,fl);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Boolean finishLastOrder(String email) {
        User req = userRepository.findByUsername(email);
        if(req  == null) {
            return false;
        }
        List<Order> allOrders = req.getOrders();

        for(int i = allOrders.size()-1; i >=0; i++) {

            if(!allOrders.get(i).getFinished()) {
                Order target = allOrders.get(i);
                Flight fl = target.getReservations().get(0).getFlight();
                target.setFinished(true);
                orderRepository.save(target);
                emailService.sendOrderMail(req, target, fl);
                return true;
            }
        }
        return false;
    }


    @Transactional(readOnly = true)
    public Boolean isOrdering(Long id,String email) {
        User req = userRepository.findByUsername(email);
        if(req == null) {
            return false;
        }

        Order target;
        if(orderRepository.findById(id).isPresent()) {
            target = orderRepository.findById(id).get();
        } else {
            return false;
        }

        for(Order o: req.getOrders()) {
            if(o.getId().equals(target.getId())) {
                return true;
            }
        }
        return false;
    }


    @Transactional
    public Boolean bookFlight(BookingData bd, String email) {
        if(bd == null) {
            return false;
        }

        List<ReservationData> reservations = bd.getAirlineReservations();

        User requester = userRepository.findByUsername(email);

        if (!requester.getId().equals(bd.getAirlineReservations().get(0).getUserId())) {
            return false;
        }
        Order nOrder = new Order(new ArrayList<Reservation>(),requester,new Date(),false );
        orderRepository.save(nOrder);

        for (ReservationData resData : reservations) {

            if(!seatRepository.findById(resData.getSeatId()).isPresent()) {
                return false;
            }

            Seat targetSeat = seatRepository.findById(resData.getSeatId()).get();
            User targetUser;


            if (resData.getUserId() == null) {
                targetUser = null;
            } else {
                if(! userRepository.findById(resData.getUserId()).isPresent()) {
                    return false;
                }

                targetUser = userRepository.findById(resData.getUserId()).get();
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

            if(!flightRepository.findById(resData.getFlight()).isPresent()) {
                return false;
            }

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

                if (targetUser.getId().equals(requester.getId())) {
                    newRes.setConfirmed(true);
                } else {
                    newRes.setConfirmed(false);
                    emailService.sendInviteMail(requester,targetUser);
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
        requester.getOrders().add(nOrder);
        orderRepository.save(nOrder);
        userRepository.save(requester);
        return true;
    }


    @Transactional
    public Boolean quickBookFlight(QuickTicketData qTD, String email) {

        User requester = userRepository.findByUsername(email);

        if (requester == null) {
            return false;
        }

        if(!seatRepository.findById(qTD.getSeatId()).isPresent()) {
            return false;
        }

        Seat targetSeat = seatRepository.findById(qTD.getSeatId()).get();

        if (targetSeat.isTaken()) {
            return false;
        }
        if (qTD.getPassport() == null || qTD.getPassport().equals("")) {
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

