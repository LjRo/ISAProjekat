package isa.projekat.Projekat.rent.controller;

import isa.projekat.Projekat.TestUtil;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.rent_a_car.Location;
import isa.projekat.Projekat.model.rent_a_car.RentACar;
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
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentControllerTest {

    private static final String URL_PREFIX = "/api/rentacar";
    private static final Long DB_ID = 1L;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private String accessToken;
    private String accessToken2;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(filterChainProxy).build();
        ResponseEntity<UserTokenState> responseEntitySys = restTemplate.postForEntity("/auth/login",
                new JwtAuthenticationRequest("rent@gmail.com", "123"), UserTokenState.class);
        accessToken = responseEntitySys.getBody().getAccessToken();

        ResponseEntity<UserTokenState> responseEntitySys2 = restTemplate.postForEntity("/auth/login",
                new JwtAuthenticationRequest("admin@gmail.com", "123"), UserTokenState.class);
        accessToken2 = responseEntitySys2.getBody().getAccessToken();

    }

    @WithMockUser
    @Test
    public void testGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findById=" + DB_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_ID))
                .andExpect(jsonPath("$.name").value("RentAleksandar"))
                .andExpect(jsonPath("$.description").value("Cheap cars that will take you where you need to be."));
    }

    @WithMockUser
    @Test
    public void testAllGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @WithMockUser
    @Test
    public void testFilter() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/filtered?type=1&search=Al&start=2019-01-01&end=2019-01-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Transactional
    @Test
    public void testAddRent() throws Exception {
        RentACar rentACar = new RentACar();
        rentACar.setName("Name");
        rentACar.setDescription("Desc");
        rentACar.setFastDiscount(5);
        String json = TestUtil.json(rentACar);
        this.mockMvc.perform(post(URL_PREFIX+ "/add").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().is(403));
    }
    @Transactional
    @Test
    public void editRent() throws Exception {
        RentACar rentACar = new RentACar();
        rentACar.setId(DB_ID);
        rentACar.setName("Name");
        rentACar.setDescription("Desc");
        rentACar.setFastDiscount(5);
        Location location = new Location();
        location.setAddressName("aca");
        location.setCity("Meg");
        location.setLongitude(45.45);
        location.setLatitude(45.45);
        location.setCountry("Niger");
        rentACar.setAddress(location);

        String json = TestUtil.json(rentACar);
        this.mockMvc.perform(post(URL_PREFIX+ "/edit").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().is(202));
    }

}
