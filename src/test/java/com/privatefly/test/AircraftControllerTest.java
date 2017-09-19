package com.privatefly.test;

import com.privatefly.PrivateflyBootApplication;
import com.privatefly.controller.AircraftController;
import com.privatefly.model.Aircraft;
import com.privatefly.service.AircraftService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PrivateflyBootApplication.class})
public class AircraftControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(aircraftController)
                .build();
    }

    // Get All Aircrafts
    @Test
    public void test_get_all_success() throws Exception {
        List<Aircraft> aircrafts = Arrays.asList(
                new Aircraft("AirfieldNameOne", "AAAA", new Date(), 5555.0),
                new Aircraft("AirfieldNameTwo", "BBBB", new Date(), 6666.0));

        when(aircraftService.findAllAircrafts()).thenReturn(aircrafts);

        mockMvc.perform(get("/aircrafts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        
        verify(aircraftService, times(1)).findAllAircrafts();
        verifyNoMoreInteractions(aircraftService);
    }

	// Get Aircraft by Airfield name
    @Test
    public void test_get_by_id_success() throws Exception {
        Aircraft aircraft = new Aircraft("AirfieldNameOne", "AAAA", new Date(), 5555.0);

        when(aircraftService.findByAirfieldName("AirfieldNameOne")).thenReturn(aircraft);

        mockMvc.perform(get("/aircraft/{airfield}", "AirfieldNameOne"))
                .andExpect(status().isOk());

        verify(aircraftService, times(1)).findByAirfieldName("AirfieldNameOne");
        verifyNoMoreInteractions(aircraftService);
    }


    // Create New Aircraft
    @Test
    public void test_create_aircraft_success() throws Exception {
    	Date dateCreated = new Date();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Aircraft aircraft = new Aircraft("AirfieldNameFive", "EEEE", dateCreated, 9999.0);

        when(aircraftService.exists(aircraft)).thenReturn(false);
        doNothing().when(aircraftService).saveAircraft(aircraft);

        mockMvc.perform(
                post("/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("airfieldName", "AirfieldNameFive")
                        .param("icaoCode", "EEEE")
                        .param("openedDate", df.format(dateCreated))
                        .param("runwayLength", "9999.0"))
                .andExpect(status().isCreated());
    }

}