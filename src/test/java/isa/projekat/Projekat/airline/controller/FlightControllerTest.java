package isa.projekat.Projekat.airline.controller;

import isa.projekat.Projekat.TestUtil;
import isa.projekat.Projekat.model.airline.FlightSearchData;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightControllerTest {

    private static final String URL_PREFIX = "/api/flight";
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

    @WithMockUser(roles = {"USER"})
    @Test
    public void getFlightTest() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/" + DB_ID+"/")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(DB_ID));
    }

    @WithMockUser(roles = {"USER"})
    @Test
    @Transactional
    public void confirmTest() throws Exception {
        mockMvc.perform(post("/api/order" + "/" + DB_ID+"/confirm")).andExpect(status().is5xxServerError());
    }

    @WithMockUser(roles = {"USER"})
    @Test
    public void isOrderingTest() throws Exception {
        mockMvc.perform(get("/api/order" + "/" + DB_ID+"/isOrdering")).andExpect(status().is5xxServerError());
    }

    @WithMockUser(roles = {"USER"})
    @Test
    public void testSearch() throws Exception {
        FlightSearchData fSD = new FlightSearchData();
        fSD.setCityFrom("Beograd");
        fSD.setCityTo("Beograd");
        LocalDate localDate = LocalDate.parse("2019-02-15");
        fSD.setStartDate(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));


        String json = TestUtil.json(fSD);
        this.mockMvc.perform(post(URL_PREFIX+ "/search").contentType(contentType).content(json)).andExpect(status().isOk());
    }

}
