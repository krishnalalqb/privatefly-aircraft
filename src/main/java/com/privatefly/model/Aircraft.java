package com.privatefly.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aircraft")
public class Aircraft {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	private String airfieldName;
	
	private String ICAOCode;
	
	private Date dateOpened;
	
	private double runwayLength;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Aircraft() {
		super();
	}

	public Aircraft(String airfieldName, String ICAOCode, Date dateOpened, double runwayLength){
		this.setAirfieldName(airfieldName);
		this.setICAOCode(ICAOCode);
		this.setDateOpened(dateOpened);
		this.setRunwayLength(runwayLength);
	}

	public String getAirfieldName() {
		return airfieldName;
	}

	public void setAirfieldName(String airfieldName) {
		this.airfieldName = airfieldName;
	}

	public String getICAOCode() {
		return ICAOCode;
	}

	public void setICAOCode(String iCAOCode) {
		ICAOCode = iCAOCode;
	}

	public Date getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}

	public double getRunwayLength() {
		return runwayLength;
	}

	public void setRunwayLength(double runwayLength) {
		this.runwayLength = runwayLength;
	}

}
