package isa.projekat.Projekat.rent.controller;

import isa.projekat.Projekat.TestUtil;
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
public class CarControllerTest {

    private static final String URL_PREFIX = "/api/cars";
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
                new JwtAuthenticationRequest("rent@gmail.com", "123"), UserTokenState.class);
        accessToken = responseEntitySys.getBody().getAccessToken();

    }

    @WithMockUser
    @Test
    public void testGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findByIdAll?page=0&id=1" + DB_ID))
                .andExpect(status().is(200)).andExpect(content().contentType(contentType));
    }
    @WithMockUser
    @Test
    public void getOneWihtoutPaging() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/id=" +DB_ID))
                .andExpect(status().is(401));
    }

    @WithMockUser
    @Test
    public void testAllGetData() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/findAll?page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }



}
