package isa.projekat.Projekat.service.hotel;

import isa.projekat.Projekat.model.airline.Order;
import isa.projekat.Projekat.model.hotel.*;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelReservationRepository hotelReservationRepository;

    @Autowired
    private HotelServicesRepository hotelServicesRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Page<Room> findAll(PageRequest pageRequest){
        return roomRepository.findAll(pageRequest);
    }

    public Page<Room> findByHotelId( Long id,PageRequest pageRequest) { return roomRepository.findByHotelId(id,pageRequest);}

    //(roomSearchData.getHotelId(),roomSearchData.getArrivalDate(),roomSearchData.getDepartureDate(),roomSearchData.getType(),pageRequestProvider.provideRequest(roomSearchData.getPage()));
    public Page<Room> findAvailableByHotelId(RoomSearchData roomSearchData, PageRequest pageRequest) {
            return roomRepository.returnRoomsThatAreAvailable(roomSearchData.getHotelId(),roomSearchData.getArrivalDate(),roomSearchData.getDepartureDate(),roomSearchData.getType(),roomSearchData.getNumberOfPeople(),roomSearchData.getNumberOfRooms(),roomSearchData.getNumberOfBeds(),roomSearchData.getMinPrice(),roomSearchData.getMaxPrice(), findDaysInBetween(roomSearchData.getArrivalDate(),roomSearchData.getDepartureDate()),pageRequest);

    }

    private int findDaysInBetween(String arrivalString,String departureString){
        Date arrival;
        Date departure;
        try {
            arrival=new SimpleDateFormat("yyyy-MM-dd").parse(arrivalString);
            departure=new SimpleDateFormat("yyyy-MM-dd").parse(departureString);
        } catch (Exception e){
            return -1;
        }
        long diff = Math.abs(departure.getTime() - arrival.getTime());
        long days = (diff / (1000*60*60*24));
        int idays = (int) days;
        return (idays==0)?1:idays;
    }

    public Room findById( Long id) {
        Optional<Room> oRoom = roomRepository.findById(id);
        if(oRoom.isPresent())
            return oRoom.get();
        else
            return null;
    }

    @Transactional
    public boolean editRoom(Room room, User user, Long hotelId) {

        if(!checkIfAdminAndCorrectAdmin(hotelId,user))
            return false;
        Room foundRoom = roomRepository.findById(room.getId()).get();
        foundRoom.setNumberOfRooms(room.getNumberOfRooms());
        foundRoom.setRoomType(room.getRoomType());
        foundRoom.setNumberOfPeople(room.getNumberOfPeople());
        foundRoom.setRoomNumber(room.getRoomNumber());
        foundRoom.setFloor(room.getFloor());
        foundRoom.setName(room.getName());
        foundRoom.setNumberOfBeds(room.getNumberOfBeds());
        roomRepository.save(foundRoom);
        return true;
    }

    @Transactional
    public int reserveRoom(ReservationHotelData reservationHotelData, User user) {
        ReservationHotel reservationHotel = hotelReservationRepository.findByUserOrder(reservationHotelData.getReservationId());
        if(reservationHotel != null)
            return 2;
        Room room = roomRepository.checkIfAvailableStill(reservationHotelData.getRoomId(),reservationHotelData.getArrivalDate(),reservationHotelData.getDepartureDate());
        Optional<Room> exists  = roomRepository.findById(reservationHotelData.getRoomId());
        Optional<Order> reservation = orderRepository.findById(reservationHotelData.getReservationId());
        Date arrival;
        Date departure;
        try {
            arrival=new SimpleDateFormat("yyyy-MM-dd").parse(reservationHotelData.getArrivalDate());
            departure=new SimpleDateFormat("yyyy-MM-dd").parse(reservationHotelData.getDepartureDate());
        } catch (Exception e){
            return -1;
        }
        long diff = Math.abs(departure.getTime() - arrival.getTime());
        long days = (diff / (1000*60*60*24));

        String services = reservationHotelData.getServices();
        List<HotelServices> servicesList = new ArrayList<>();
        if(services!="")
        {
            if(services.contains(","))
            {
                String[] list = services.split(",");
                for (int i=0; i < list.length ; ++i)
                {
                    Long tmp = Long.parseLong(list[i]);
                    Optional<HotelServices> optional = hotelServicesRepository.findById(tmp);
                    if(optional.isPresent())
                        servicesList.add(optional.get());
                }
            }
            else {
                Long tmp = Long.parseLong(services);
                Optional<HotelServices> optional = hotelServicesRepository.findById(tmp);
                if(optional.isPresent())
                    servicesList.add(optional.get());
            }
        }


        if(!exists.isPresent() || !reservation.isPresent())
            return 3;
        if(room != null)
        {
            Order pendingOrder = reservation.get();
            Room roomFound = exists.get();
            ReservationHotel newReservation = new ReservationHotel();
            newReservation.setNightsStaying((int)days);
            newReservation.setHotel(roomFound.getHotel());
            newReservation.setUserOrder(pendingOrder);
            newReservation.setArrivalDate(arrival);
            newReservation.setPeople(roomFound.getNumberOfPeople());
            newReservation.setDepartureDate(departure);
            newReservation.setServices(servicesList);
            newReservation.setReservationDate(new Date());
            newReservation.setUser(user);
            newReservation.setRoom(roomFound);
            pendingOrder.setReservationHotel(newReservation);
            hotelReservationRepository.save(newReservation);
            orderRepository.save(pendingOrder);
            return 0;
        }
        else
            return 4;
    }


    @Transactional
    public boolean deleteRoom(Room room, User user, Long hotelId) {
        if(!checkIfAdminAndCorrectAdmin(hotelId,user))
            return false;

        Optional<Hotel> foundHotelOpt = hotelRepository.findById(hotelId);
        Optional<Room> foundRoomOpt = roomRepository.findById(room.getId());
        if (!foundRoomOpt.isPresent() || !foundHotelOpt.isPresent())
            return false;

        Room foundRoom = foundRoomOpt.get();
        Hotel hotel = foundHotelOpt.get();
        hotel.getRooms().remove(foundRoom);
        roomRepository.delete(foundRoom);
        return true;
    }


    @SuppressWarnings("Duplicates")
    private boolean checkIfAdminAndCorrectAdmin(Long id,User adminToCheck){
        if(adminToCheck.getAdministratedHotel() == null) {
            return false;
        }
        Hotel hotel = hotelRepository.findById(id).get();

        if(!hotel.getAdmins().contains(adminToCheck))
        {
            return false;
        }
        return true;
    }
}
