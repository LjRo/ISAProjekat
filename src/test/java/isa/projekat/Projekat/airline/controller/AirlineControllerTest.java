package isa.projekat.Projekat.airline.controller;

import isa.projekat.Projekat.TestUtil;
import isa.projekat.Projekat.model.airline.AirlineEditData;
import isa.projekat.Projekat.model.airline.FlightData;
import isa.projekat.Projekat.model.user.UserTokenState;
import isa.projekat.Projekat.security.auth.JwtAuthenticationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AirlineControllerTest {

    private static final String URL_PREFIX = "/api/airline";
    private static final Long DB_ID = new Long(1);

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /*@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }*/

    @Autowired
    private FilterChainProxy filterChainProxy;

    private String accessToken;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(filterChainProxy).build();
        ResponseEntity<UserTokenState> responseEntitySys = restTemplate.postForEntity("/auth/login",
                new JwtAuthenticationRequest("airline@gmail.com", "123"), UserTokenState.class);
        accessToken = responseEntitySys.getBody().getAccessToken();

    }


    @WithMockUser
    @Test
    public void testGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_ID))
                .andExpect(jsonPath("$.name").value("AirSerbia"))
                .andExpect(jsonPath("$.description").value("Aeroport Nikole Tesle Belgrade"));
    }


    @Transactional
    @Test
    public void testEditAirline() throws Exception {
        AirlineEditData aED = new AirlineEditData();

        aED.setName("Airline");
        aED.setDescription("Generic");
        aED.setCity("City");
        aED.setCountry("The Best");
        aED.setAddress("Near The Border");
        aED.setLongitude(20.0);
        aED.setLatitude(2.01);
        aED.setHasFood(true);
        aED.setHasLuggage(true);
        aED.setHasOther(true);
        aED.setFoodPrice(new BigDecimal("210.2"));
        aED.setLuggagePrice(new BigDecimal("2104"));

        String json = TestUtil.json(aED);
        this.mockMvc.perform(put(URL_PREFIX+ "/" + DB_ID+"/edit").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Transactional
    @Test
    public void testAddFlight() throws Exception {
        FlightData fd = new FlightData();

        fd.setColumns(2);
        fd.setRows(2);
        fd.setSegments(2);
        fd.setPrice(new BigDecimal("20.0"));
        fd.setLength(100.0);
        fd.setStopCount(0);
        fd.setStartID(DB_ID);
        fd.setDestID(DB_ID);
        fd.setDistance(2.0);
        fd.setFinishDate(new Date());
        fd.setStartDate(new Date());

        String json = TestUtil.json(fd);
        this.mockMvc.perform(post(URL_PREFIX+ "/" + DB_ID+"/addFlight").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().isOk());
    }

    @Transactional
    @Test
    public void deleteLocation() throws Exception {
        this.mockMvc.perform(post("/api/location/" + DB_ID+"/delete").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk());
    }


    @Test
    public void testGetDestinations() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/destinations")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testLastSeatData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/lastSeatData").header("Authorization", "Bearer " + accessToken)).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.segments").value(2))
                .andExpect(jsonPath("$.columns").value(2))
                .andExpect(jsonPath("$.rows").value(2));
    }

}
