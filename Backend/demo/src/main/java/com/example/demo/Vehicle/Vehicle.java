package com.example.demo.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;

public class Vehicle {
	
	private int mChargeCost;
	private String mClassType;
	private LocalDate mDateOfRequest;
	private LocalTime mRequestTime;
	private LocalDate mDateOfService;
	private LocalTime mServiceTime;
	
	public Vehicle() {
		
	}
	
	public Vehicle(int iChargeCost,
	               String iClassType,
	               LocalDate iDateOfRequest,
	               LocalTime iRequestTime,
	               LocalDate iDateOfService,
	               LocalTime iServiceTime) {
		
		this.mChargeCost = iChargeCost;
		this.mClassType = iClassType;
		this.mDateOfRequest = iDateOfRequest;
		this.mRequestTime = iRequestTime;
		this.mDateOfService = iDateOfService;
		this.mServiceTime = iServiceTime;
	}
	
	public void setChargeCost(int iChargeCost) {
		this.mChargeCost = iChargeCost;
	}
	
	public int getChargeCost() {
		return mChargeCost;
	}
	
	public void setClassType(String iClassType) {
		this.mClassType = iClassType;
	}
	
	public String getClassType() {
		return mClassType;
	}
	
	
	public void setDateOfRequest(LocalDate iDateOfRequest) {
		this.mDateOfRequest = iDateOfRequest;
	}
	
	public LocalDate getDateOfRequest() {
		return mDateOfRequest;
	}
	
	
	public void setRequestTime(LocalTime iRequestTime) {
		this.mRequestTime = iRequestTime;
	}
	
	public LocalTime getRequestTime() {
		return mRequestTime;
	}
	
	
	public void setDateOfService(LocalDate iDateOfService) {
		this.mDateOfService = iDateOfService;
	}
	
	public LocalDate getDateOfService() {
		return mDateOfService;
	}
	
	
	public void setServiceTime(LocalTime iServiceTime) {
		this.mServiceTime = iServiceTime;
	}
	
	public LocalTime getServiceTime() {
		return mServiceTime;
	}
	
	
	
	

}
