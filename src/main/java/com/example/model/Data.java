package com.example.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Data {
	
	@Id
	private String id;
	
	private String indicatorId;
	private String indicatorValue;
	private String countryId;
	private String countryValue;
	private double val;
	private int decim;
	private int year;
	
	public Data() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getIndicatorId() {
		return indicatorId;
	}
	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}
	public String getIndicatorValue() {
		return indicatorValue;
	}
	public void setIndicatorValue(String indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryValue() {
		return countryValue;
	}
	public void setCountryValue(String countryValue) {
		this.countryValue = countryValue;
	}
	public double getValue() {
		return val;
	}
	public void setValue(double value) {
		this.val = value;
	}
	public int getDecimal() {
		return decim;
	}
	public void setDecimal(int decimal) {
		this.decim = decimal;
	}
	public int getDate() {
		return year;
	}
	public void setDate(int date) {
		this.year = date;
	}
		

}
