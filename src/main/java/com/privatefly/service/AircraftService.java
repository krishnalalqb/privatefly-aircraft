package com.privatefly.service;


import com.privatefly.model.Aircraft;

public interface AircraftService {
	
	Aircraft findByAirfieldName(String airfieldName);
	
	void saveAircraft(Aircraft aircraft);
	
	Iterable<Aircraft> findAllAircrafts();
	
	Iterable<Aircraft> getAllAircraftsSorted();
}
