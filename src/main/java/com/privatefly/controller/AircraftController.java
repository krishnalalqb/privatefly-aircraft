package com.privatefly.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.privatefly.model.Aircraft;
import com.privatefly.service.AircraftServiceImpl;

@Controller
public class AircraftController {

	private static final Logger logger = Logger.getLogger(AircraftController.class);
	private static final String HOME_PAGE = "index";
	
	@Autowired
	private AircraftServiceImpl aircraftService;

	@RequestMapping("/")
	public String viewAircrafts(ModelMap map) {
		map.addAttribute("aircrafts", aircraftService.findAllAircrafts());
		logger.info("Aircrafts details are fetched!");
		return HOME_PAGE;
	}

	@RequestMapping(value = "/createAircraft", method = RequestMethod.POST)
	public String createAircraft(@RequestParam("airfield") String airfield, @RequestParam("ICAO_code") String ICAO_code,
			@RequestParam("openedDate") String openedDate, @RequestParam("runway_length") String runway_length) {
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = df.parse(openedDate);
		} catch (ParseException e) {
			logger.error(e);
		}
		Aircraft aircraft = new Aircraft(airfield, ICAO_code, date, Double.parseDouble(runway_length));
		aircraftService.saveAircraft(aircraft);
		logger.info("New aircraft is saved!");
		return "redirect:/";
	}

	@RequestMapping(value = "/getAircraft", method = RequestMethod.GET)
	public String sortedAircraft(ModelMap map, @RequestParam("airfield") String airfield) {
		map.addAttribute("aircrafts", aircraftService.findByAirfieldName(airfield));
		logger.info("Aircraft details are fetched!");
		return HOME_PAGE;
	}

	@RequestMapping("/sortedAircrafts")
	public String sortedAircraft(ModelMap map) {
		Iterable<Aircraft> aircrafts = aircraftService.getAllAircraftsSorted();
		map.addAttribute("aircrafts", aircrafts);
		logger.info("Sorted aircrafts details are fetched!");
		return HOME_PAGE;
	}
}
