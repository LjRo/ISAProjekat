package isa.projekat.Projekat.hotel.controller;

import isa.projekat.Projekat.TestUtil;
import isa.projekat.Projekat.model.hotel.Hotel;
import isa.projekat.Projekat.model.rent_a_car.Location;
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
public class HotelControllerTest {

    private static final String URL_PREFIXH = "/api/hotel";
    private static final Long DB_ID = 1L;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private String accessToken;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(filterChainProxy).build();
        ResponseEntity<UserTokenState> responseEntitySys = restTemplate.postForEntity("/auth/login",
                new JwtAuthenticationRequest("hotel@gmail.com", "123"), UserTokenState.class);
        accessToken = responseEntitySys.getBody().getAccessToken();

    }

    @WithMockUser
    @Test
    public void testGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIXH + "/findById=" + DB_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_ID))
                .andExpect(jsonPath("$.name").value("Aleksandar"))
                .andExpect(jsonPath("$.description").value("Hotel with 5 stars in Milano with great locations"));
    }

    @Transactional
    @Test
    public void testaddHotel() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Name");
        hotel.setDescription("Desc");
        hotel.setFastDiscount(5);
        String json = TestUtil.json(hotel);
        this.mockMvc.perform(post(URL_PREFIXH+ "/addHotel").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().is(403));
    }
    @Transactional
    @Test
    public void editHotel() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setId(DB_ID);
        hotel.setName("na");
        hotel.setDescription("d");
        hotel.setFastDiscount(5);
            Location location = new Location();
            location.setAddressName("basd");
            location.setCity("ads");
            location.setLongitude(12.45);
            location.setLatitude(35.45);
            location.setCountry("asda");
        hotel.setAddress(location);

        String json = TestUtil.json(hotel);
        this.mockMvc.perform(post(URL_PREFIXH+ "/editHotel").header("Authorization", "Bearer " + accessToken).contentType(contentType).content(json)).andExpect(status().is(202));
    }
}
