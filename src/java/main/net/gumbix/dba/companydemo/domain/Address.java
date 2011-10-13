package net.gumbix.dba.companydemo.domain;

/**
 * @project manager  Markus Gumbel (m.gumbel@hs-mannheim.de)
 * 
 * @author Marius Czardybon  (m.czardybon@gmx.net)
 * 
 * */

public class Address {

	private String zip;
	private String city;


	public Address(String zip, String city) {
		this.zip = zip;
		this.city = city;
	}

	public Address() {

	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}





























