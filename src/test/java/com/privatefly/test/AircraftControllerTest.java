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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PrivateflyBootApplication.class })
public class AircraftControllerTest {

	private MockMvc mockMvc;

	@Mock
	private AircraftService aircraftService;

	@InjectMocks
	private AircraftController aircraftController;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(aircraftController).build();
	}

	// Get All Aircrafts
	@Test
	public void test_get_all() throws Exception {
		Date openedDate = new Date();
		List<Aircraft> aircrafts = Arrays.asList(new Aircraft("AirfieldNameOne", "AAAA", openedDate, 5555.0),
				new Aircraft("AirfieldNameTwo", "BBBB", openedDate, 6666.0));

		when(aircraftService.findAllAircrafts()).thenReturn(aircrafts);

		mockMvc.perform(get("/aircrafts")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].airfieldName", is("AirfieldNameOne")))
				.andExpect(jsonPath("$[0].icaocode", is("AAAA")))
				.andExpect(jsonPath("$[0].dateOpened", is(openedDate.getTime())))
				.andExpect(jsonPath("$[0].runwayLength", is(5555.0)))
				.andExpect(jsonPath("$[1].airfieldName", is("AirfieldNameTwo")))
				.andExpect(jsonPath("$[1].icaocode", is("BBBB")))
				.andExpect(jsonPath("$[1].dateOpened", is(openedDate.getTime())))
				.andExpect(jsonPath("$[1].runwayLength", is(6666.0)));

		verify(aircraftService, times(1)).findAllAircrafts();
		verifyNoMoreInteractions(aircraftService);
	}

	// Get Aircraft by Airfield name
	@Test
	public void test_get_by_airfieldname() throws Exception {
		Date openedDate = new Date();
		Aircraft aircraft = new Aircraft("AirfieldNameOne", "AAAA", openedDate, 5555.0);

		when(aircraftService.findByAirfieldName("AirfieldNameOne")).thenReturn(aircraft);

		mockMvc.perform(get("/aircraft/{airfield}", "AirfieldNameOne")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.airfieldName", is("AirfieldNameOne")))
				.andExpect(jsonPath("$.dateOpened", is(openedDate.getTime())))
				.andExpect(jsonPath("$.icaocode", is("AAAA")))
				.andExpect(jsonPath("$.runwayLength", is(5555.0)));

		verify(aircraftService, times(1)).findByAirfieldName("AirfieldNameOne");
		verifyNoMoreInteractions(aircraftService);
	}

	// Create New Aircraft
	@Test
	public void test_create_aircraft() throws Exception {
		Date openedDate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Aircraft aircraft = new Aircraft("AirfieldNameFive", "EEEE", openedDate, 9999.0);

		when(aircraftService.exists(aircraft)).thenReturn(false);
		doNothing().when(aircraftService).saveAircraft(aircraft);

		mockMvc.perform(post("/aircraft").contentType(MediaType.APPLICATION_JSON)
				.param("airfieldName", "AirfieldNameFive")
				.param("icaoCode", "EEEE")
				.param("openedDate", df.format(openedDate))
				.param("runwayLength", "9999.0"))
				.andExpect(status().isCreated());
	}
}