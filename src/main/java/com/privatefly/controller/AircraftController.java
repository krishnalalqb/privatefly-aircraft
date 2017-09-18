package com.privatefly.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privatefly.model.Aircraft;
import com.privatefly.service.AircraftServiceImpl;

@RestController
public class AircraftController {

	private static final Logger logger = Logger.getLogger(AircraftController.class);

	@Autowired
	private AircraftServiceImpl aircraftService;

	@RequestMapping(value = "/aircrafts", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Aircraft>> listAircrafts() {
		Iterable<Aircraft> aircrafts = aircraftService.findAllAircrafts();
		logger.info("All aircrafts details are fetched!");
		return new ResponseEntity<Iterable<Aircraft>>(aircrafts, HttpStatus.OK);
	}

	@RequestMapping(value = "/aircraft", method = RequestMethod.POST)
	public ResponseEntity<String> createAircraft(@RequestParam("airfieldName") String airfieldName,
			@RequestParam("icaoCode") String icaoCode, @RequestParam("openedDate") String openedDate,
			@RequestParam("runwayLength") String runwayLength) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse(openedDate);
		} catch (ParseException e) {
			logger.error(e);
		}
		Aircraft aircraft = new Aircraft(airfieldName, icaoCode, date, Double.parseDouble(runwayLength));
		aircraftService.saveAircraft(aircraft);
		logger.info("New aircraft is saved!");
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/aircraft/{airfield}", method = RequestMethod.GET)
	public ResponseEntity<Aircraft> searchAircraft(@PathVariable("airfield") String airfield) {
		Aircraft aircraft = aircraftService.findByAirfieldName(airfield);
		logger.info("Aircraft details are fetched!");
		return new ResponseEntity<Aircraft>(aircraft, HttpStatus.OK);
	}

	@RequestMapping(value = "/sortedAircrafts", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Aircraft>> listSortedAircrafts() {
		Iterable<Aircraft> aircrafts = aircraftService.getAllAircraftsSorted();
		logger.info("Sorted aircrafts details are fetched!");
		return new ResponseEntity<Iterable<Aircraft>>(aircrafts, HttpStatus.OK);
	}
}
