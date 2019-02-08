package isa.projekat.Projekat.service.airline;

import isa.projekat.Projekat.model.airline.*;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.repository.AirlineRepository;
import isa.projekat.Projekat.repository.FlightRepository;
import isa.projekat.Projekat.repository.LocationRepository;
import isa.projekat.Projekat.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class AirlineService {
    @Autowired
    private AirlineRepository airlineRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Transactional(readOnly = true)
    public List<Airline> findAll(){
        return airlineRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Airline findById(Long id) {
        if(airlineRepository.findById(id).isPresent()) {
            return airlineRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Location findLocationById(Long id, Long locId) {
        if(!airlineRepository.findById(id).isPresent()) {
            return null;
        }

        List<Location> locs = airlineRepository.findById(id).get().getDestinations();
        for (Location loc : locs) {
            if(loc.getId().equals(locId)) {
                return loc;
            }
        }
        return null;
    }

    @Transactional
    public boolean addAirline(Airline ad) {
        if(ad.getAddress() == null || ad.getDescription().equals("") || ad.getName().equals("")){
            return false;
        }
        Location location = new Location(ad.getAddress().getAddressName(),ad.getAddress().getCountry(),ad.getAddress().getCity(),ad.getAddress().getLatitude(),ad.getAddress().getLongitude());
        locationRepository.save(location);
        Airline target = new Airline();
        target.setAddress(location);
        target.setName(ad.getName());
        target.setDescription(ad.getDescription());
        airlineRepository.save(target);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Location> findAirlineDestinations(Long id) {

        if(!airlineRepository.findById(id).isPresent()) {
            return null;
        }

        List<Location> locations = airlineRepository.findById(id).get().getDestinations();
        ArrayList<Location> res = new ArrayList<>();
        for(Location loc : locations ) {
            if(loc.getActive()) {
                res.add(loc);
            }
        }
        return res;
    }

    @Transactional
    public boolean addFlight(FlightData flightData, String email) {
        User admin = userRepository.findByUsername(email);

        if(admin.getAdministratedAirline() == null) {
            return false;
        }

        Airline target = admin.getAdministratedAirline();

        Flight fl = new Flight();
        fl.setStart(findLocationById(target.getId(),flightData.getStartID()));
        fl.setFinish(findLocationById(target.getId(),flightData.getDestID()));
        fl.setStartTime(flightData.getStartDate());
        fl.setLandTime(flightData.getFinishDate());
        fl.setDuration(flightData.getLength());
        fl.setDistance(flightData.getDistance());
        fl.setPrice(flightData.getPrice());
        fl.setSegments(flightData.getSegments());
        fl.setC_columns(flightData.getColumns());
        fl.setC_rows(flightData.getRows());
        fl.setNumberOfStops(flightData.getStopCount());
        fl.setAirline(target);

        if(fl.getStart() == null || fl.getFinish() == null) {
            return false;
        }

        if(fl.getPrice() == null) {
            return false;
        }


        ArrayList<Location> stops = new ArrayList<>();

        ArrayList<Seat> seats = new ArrayList<>();

        for(int j = 0; j < fl.getSegments()*fl.getC_columns(); j++) {
            for(int k = 0; k< fl.getC_rows(); k++) {
                Seat st = new Seat(k,j,fl.getPrice(),null,false,false);
                //entityManager.persist(st);
                seats.add(st);
            }
        }

        for(int i = 0; i < flightData.getStopCount(); i++) {
            stops.add(findLocationById(target.getId(),flightData.getStopIDs().get(i)));
        }
        fl.setStops(stops);
        fl.setSeats(seats);

        if(target.getAdmins().contains(admin)) {

            target.getFlights().add(fl);
            airlineRepository.save(target);
            return true;

        }

        return false;
    }

    @Transactional(readOnly = true)
    public List<Flight> findAllActiveFlights(Long id) {

        if(!airlineRepository.findById(id).isPresent()) {
            return null;
        }

        List<Flight> allFlights = airlineRepository.findById(id).get().getFlights();
        ArrayList<Flight> result = new ArrayList<>();
        DateTime current = new DateTime();
        for(Flight fl : allFlights) {
            if(current.isBefore(fl.getStartTime().getTime())) {
                result.add(fl);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<QuickTicketData> findAllActiveFlightsWithQuickReservation(Long id) {
        List<Flight> allFlights = airlineRepository.findById(id).get().getFlights();
        ArrayList<QuickTicketData> result = new ArrayList<>();
        DateTime current = new DateTime();
        for(Flight fl : allFlights) {
            if(current.isBefore(fl.getStartTime().getTime())) {
                for(Seat st : fl.getSeats()) {
                    if(st.isQuick() && !st.isTaken()) {
                        QuickTicketData qTicket = new QuickTicketData();
                        qTicket.setId(fl.getId());
                        qTicket.setStartTime(fl.getStartTime());
                        qTicket.setLandTime(fl.getLandTime());
                        qTicket.setDuration(fl.getDuration());
                        qTicket.setDistance(fl.getDistance());
                        qTicket.setNumberOfStops(fl.getNumberOfStops());
                        qTicket.setC_column(st.getC_column());
                        qTicket.setC_row(st.getC_row());
                        qTicket.setPrice(st.getPrice());
                        qTicket.setStart(fl.getStart());
                        qTicket.setFinish(fl.getFinish());
                        qTicket.setSeatId(st.getId());
                        result.add(qTicket);
                    }
                }
            }
        }
        return result;
    }

    @Transactional
    public Boolean deleteLocation(Long locationId, String email) {

        User aAdmin = userRepository.findByUsername(email);
        Airline target = aAdmin.getAdministratedAirline();

        if(!locationRepository.findById(locationId).isPresent()) {
            return false;
        }

        Location loc = locationRepository.findById(locationId).get();

        if(!target.getDestinations().contains(loc)) {
            return false;
        }


        if(loc.getActive()) {
            loc.setActive(false);
            locationRepository.save(loc);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Boolean addLocation (LocationData loc, Long id, String email) {
        User aAdmin = userRepository.findByUsername(email);
        Airline target = aAdmin.getAdministratedAirline();

        if(loc.getCity() != null && loc.getCountry() != null && loc.getAddressName() != null ) {

            if(!airlineRepository.findById(id).isPresent()) {
                return false;
            }

            Airline air = airlineRepository.findById(id).get();
            if(!target.equals(air)) {
                return false;
            }
            Location nLoc = new Location();
            nLoc.setAddressName(loc.getAddressName());
            nLoc.setCity(loc.getCity());
            nLoc.setCountry(loc.getCountry());
            nLoc.setLatitude(loc.getLatitude());
            nLoc.setLongitude(loc.getLongitude());
            nLoc.setActive(true);
            locationRepository.save(nLoc);
            air.getDestinations().add(nLoc);
            airlineRepository.save(air);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public AirlineEditData getEditData(Long id) {
        AirlineEditData res = new AirlineEditData();
        if(!airlineRepository.findById(id).isPresent()) {
            return null;
        }
        Airline target = airlineRepository.findById(id).get();


        res.setName(target.getName());
        res.setDescription(target.getDescription());
        res.setCity(target.getAddress().getCity());
        res.setCountry(target.getAddress().getCountry());
        res.setAddress(target.getAddress().getAddressName());
        res.setLongitude(target.getAddress().getLongitude());
        res.setLatitude(target.getAddress().getLatitude());
        res.setHasFood(target.getHasFood());
        res.setHasLuggage(target.getHasExtraLuggage());
        res.setHasOther(target.getHasOtherServices());
        res.setFoodPrice(target.getFoodPrice());
        res.setLuggagePrice(target.getLuggagePrice());

        return res;
    }

    @Transactional
    public boolean editAirline(AirlineEditData data, Long id) {
        if(!airlineRepository.findById(id).isPresent()) {
            return false;
        }
        Airline res = airlineRepository.findById(id).get();

        if(data.getName().equals("") || data.getDescription().equals("") || data.getAddress().equals("") || data.getCountry().equals("") || data.getCity().equals("")) {
           return false;
        }

        res.setName(data.getName());
        res.setDescription(data.getDescription());
        res.getAddress().setCity(data.getCity());
        res.getAddress().setCountry(data.getCountry());
        res.getAddress().setAddressName(data.getAddress());
        res.getAddress().setLongitude(data.getLongitude());
        res.getAddress().setLatitude(data.getLatitude());
        res.setHasFood(data.isHasFood());
        res.setHasExtraLuggage(data.isHasLuggage());
        res.setHasOtherServices(data.isHasOther());
        res.setFoodPrice(data.getFoodPrice());
        res.setLuggagePrice(data.getLuggagePrice());

        airlineRepository.save(res);

        return true;
    }

    @Transactional(readOnly = true)
    public Long findLastSeat(Long id) {

        if(!airlineRepository.findById(id).isPresent()) {
            return null;
        }

        Airline target = airlineRepository.findById(id).get();
        int val = target.getFlights().size() - 1;
        if(val == -1) {
            return null;
        }
        return target.getFlights().get(val).getId();

    }

    @Transactional(readOnly = true)
    public Map<LocalDate, Integer> countYearlySales(Long airlineId) {
        HashMap<LocalDate,Integer> result = new HashMap<>();

        List<Flight> flights = flightRepository.getAllFlightsThisYear(airlineId);

        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        LocalDate lastDay = now.with(lastDayOfYear());

        return countDateRange(result,firstDay,lastDay,flights);
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, Integer> countMonthlySales(Long airlineId) {
        HashMap<LocalDate,Integer> result = new HashMap<>();

        List<Flight> flights = flightRepository.getAllFlightsThisMonth(airlineId);

        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());

        return countDateRange(result,firstDay,lastDay,flights);

    }

    @Transactional(readOnly = true)
    public Map<LocalDate, Integer> countWeeklySales(Long airlineId) {
        HashMap<LocalDate,Integer> result = new HashMap<>();
        List<Flight> flights = flightRepository.getAllFlightsThisWeek(airlineId);

        LocalDate now = LocalDate.now();
        List<LocalDate> days = datesOfWeekDate(now);
        LocalDate firstDay = days.get(0);
        LocalDate lastDay = days.get(6);

        return countDateRange(result,firstDay,lastDay,flights);
    }

    @Transactional(readOnly = true)
    public Map<LocalDate, BigDecimal> calculateIntervalProfit(Long airlineId, LocalDate firstDay, LocalDate lastDay) {
        HashMap<LocalDate,BigDecimal> result = new HashMap<>();
        List<Object[]> data = flightRepository.getProfitFromRange(airlineId,firstDay,lastDay);

        for (LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1))
        {
            result.put(date, new BigDecimal("0"));
        }
        result.put(lastDay, new BigDecimal("0"));

        for(Object[] prof : data) {
            LocalDate tDate = ((Timestamp)prof[0]).toLocalDateTime().toLocalDate();
            result.put(tDate,result.get(tDate).add(getBigDecimal(prof[1])));
        }

        return result;
    }

    private Map<LocalDate, Integer> countDateRange(Map<LocalDate,Integer> result, LocalDate firstDay, LocalDate lastDay, List<Flight> flights ) {

        for (LocalDate date = firstDay; date.isBefore(lastDay); date = date.plusDays(1))
        {
            result.put(date,0);
        }
        result.put(lastDay,0);

        for(Flight fl : flights) {
            LocalDate sDate = convertToLocalDateViaMillisecond(fl.getStartTime());
            for(Seat st : fl.getSeats()) {
                if(st.isTaken()) {
                    result.put(sDate, result.get(sDate) + 1);
                }
            }
        }

        return result;
    }

    private LocalDate convertToLocalDateViaMillisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private static List<LocalDate> datesOfWeekDate(LocalDate date) {
        LocalDate monday = date
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        return IntStream.range(0, 7).mapToObj(monday::plusDays).collect(Collectors.toList());
    }

    private static BigDecimal getBigDecimal( Object value ) {
        BigDecimal ret = null;
        if( value != null ) {
            if( value instanceof BigDecimal ) {
                ret = (BigDecimal) value;
            } else if( value instanceof String ) {
                ret = new BigDecimal( (String) value );
            } else if( value instanceof BigInteger) {
                ret = new BigDecimal( (BigInteger) value );
            } else if( value instanceof Number ) {
                ret =  BigDecimal.valueOf(((Number)value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");
            }
        }
        return ret;
    }


}

