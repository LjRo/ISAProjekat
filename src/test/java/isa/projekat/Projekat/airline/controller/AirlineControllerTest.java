package isa.projekat.Projekat.airline.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


/*

    private String accessToken;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    FilterChainProxy springSecurityFilterChain;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
                .build();

        UserData ud = new UserData();
        ud.setEmail("airline@gmail.com");
        ud.setPassword("123");


        ResponseEntity<String> responseEntityRent = restTemplate.postForEntity("/api/login", ud, String.class);
        accessToken = responseEntityRent.getBody();

        accessToken = accessToken.substring(1, accessToken.length()-1);


    }*/


    @WithMockUser
    @Test
    public void testGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/profile")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_ID))
                .andExpect(jsonPath("$.name").value("AirSerbia"))
                .andExpect(jsonPath("$.description").value("Aeroport Nikole Tesle Belgrade"));
    }

    /*
    @WithMockUser(roles = {"ROLE_ADMIN_AIRLINE"}, username = "airline@gmail.com", password = "pwd")
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
        this.mockMvc.perform(put(URL_PREFIX+ "/" + DB_ID+"/edit").contentType(contentType).content(json)).andExpect(status().isOk());
    }*/


    @Test
    public void testGetDestinations() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/destinations")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testLastSeatData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/lastSeatData")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.segments").value(2))
                .andExpect(jsonPath("$.columns").value(2))
                .andExpect(jsonPath("$.rows").value(2));
    }

}
