package com.mgg;

/**
 * An address class that contains the specifics of an address such as
 * street, city, state, zip code, and country.
 * 
 * @author jarondavid
 *
 */
public class Address {

	private String street;
	private String city; 
	private String state; 
	private String zipCode;
	private String country;
	
	public Address(String street, String city, String state, String zipCode, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public String toString() {
		String result = String.format("  %s\n  %s %s %s %s\n", this.getStreet(), this.getCity(), this.getState(), this.getZipCode(), this.getCountry());
		return result;
	}	
	
}
