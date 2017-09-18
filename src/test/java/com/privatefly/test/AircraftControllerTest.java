package com.privatefly.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.privatefly.PrivateflyBootApplication;
import com.privatefly.dao.AircraftDao;
import com.privatefly.model.Aircraft;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PrivateflyBootApplication.class)
public class AircraftControllerTest {

	@Autowired
    AircraftDao aircraftDAO;
	
	@Before
    public void initialize() throws Exception {
		aircraftDAO.deleteAll();
        Aircraft aircraftOne = new Aircraft("AirfieldNameOne", "AAAA", new Date(), 5555.0);
        Aircraft aircraftTwo = new Aircraft("AirfieldNameTwo", "BBBB", new Date(), 6666.0);
        Aircraft aircraftThree = new Aircraft("AirfieldNameThree", "CCCC", new Date(), 7777.0);
        aircraftDAO.save(aircraftOne);
        aircraftDAO.save(aircraftTwo);
        aircraftDAO.save(aircraftThree);
    }

	@Test
    public void testLoadAllAirecrafts() {
		List<Aircraft> aircrafts = (List<Aircraft>) aircraftDAO.findAll();
		assertEquals("Could not load all Aircrafts!", 3, aircrafts.size());
    }
    @Test
    public void testFindAircraft() throws Exception {
    	Aircraft aircraft = aircraftDAO.findByAirfieldName("AirfieldNameOne");
        assertEquals("Could not find aircraft!", "AirfieldNameOne", aircraft.getAirfieldName());
    }
    @Test
    public void testCreateAircraft() throws Exception {
    	Aircraft aircraftFour = new Aircraft("AirfieldNameFour","DDDD", new Date(), 8888.0);
    	aircraftDAO.save(aircraftFour);
        Aircraft aircraft = aircraftDAO.findByAirfieldName("AirfieldNameFour");
        assertEquals("Aircraft creation failed!", "AirfieldNameFour", aircraft.getAirfieldName());

    }
    
    @Test
    public void testSortedAirecraftsByAirefieldname() {
    	aircraftDAO.deleteAll();
        Aircraft aircraftOne = new Aircraft("C AirfieldNameOne", "AAAA", new Date(), 5555.0);
        Aircraft aircraftTwo = new Aircraft("A AirfieldNameTwo", "BBBB", new Date(), 6666.0);
        Aircraft aircraftThree = new Aircraft("B AirfieldNameThree", "CCCC", new Date(), 7777.0);
        aircraftDAO.save(aircraftOne);
        aircraftDAO.save(aircraftTwo);
        aircraftDAO.save(aircraftThree);
		List<Aircraft> aircrafts = (List<Aircraft>) aircraftDAO.findAllByOrderByAirfieldNameAsc();
		assertEquals("Could not load all Aircrafts!", 3, aircrafts.size());
		assertEquals("Sort failed!", "A AirfieldNameTwo", aircrafts.get(0).getAirfieldName());
		assertEquals("Sort failed!", "B AirfieldNameThree", aircrafts.get(1).getAirfieldName());
		assertEquals("Sort failed!", "C AirfieldNameOne", aircrafts.get(3).getAirfieldName());
    }


}
