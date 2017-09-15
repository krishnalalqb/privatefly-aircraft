package com.privatefly.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.privatefly.model.Aircraft;

@Transactional
public interface AircraftDao extends CrudRepository<Aircraft, Long> {

	public Aircraft findByAirfieldName(String airfieldName);
	
	public Iterable<Aircraft> findAllByOrderByAirfieldNameAsc();
	
}
