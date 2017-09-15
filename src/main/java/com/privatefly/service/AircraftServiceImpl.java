package com.privatefly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privatefly.dao.AircraftDao;
import com.privatefly.model.Aircraft;

@Service("aircraftService")
public class AircraftServiceImpl implements AircraftService{
	
	@Autowired
	private AircraftDao aircraftDao;

	public Iterable<Aircraft> findAllAircrafts() {
		return aircraftDao.findAll();
	}
	
	public Aircraft findByAirfieldName(String airfieldName) {
		return aircraftDao.findByAirfieldName(airfieldName);
	}
	
	public void saveAircraft(Aircraft aircraft) {
		aircraftDao.save(aircraft);
	}

	public Iterable<Aircraft> getAllAircraftsSorted() {
		return aircraftDao.findAllByOrderByAirfieldNameAsc();
	}


}
