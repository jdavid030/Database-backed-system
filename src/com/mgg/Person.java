package com.mgg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * A person class that outlines the specifics of a person related to the store,
 * each person has a person code, type, name, address, and email address.
 * 
 * @author jarondavid
 *
 */
public abstract class Person implements Comparable<Person>{

	private String personCode;
	private String type;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> emails;
	
	
	public Person(String personCode, String type, String lastName, String firstName, Address address, List<String> emails) {
		this.personCode = personCode;
		this.type = type;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.emails = emails;
	}


	public String getPersonCode() {
		return personCode;
	}

	public List<String> getEmails() {
		return emails;
	}

	public String getType() {
		return type;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAddress() {
		return address;
	}
	
	public String getName() {
		return this.lastName + ", " + this.firstName; 
	}
	
	/**
	 * This method returns the percent discount a customer gets
	 * based on their status in the store.
	 * 
	 * @return
	 */
	public abstract double getDiscount();
	
	/**
	 * This method returns a list of sales that an employee has made,
	 * not applicable to customers. 
	 * 
	 * @return
	 */
	public abstract List<Sale> getSales();
	
	/**
	 * gets the total value of a sale made by the employee in each sale
	 * 
	 * @return
	 */
	public abstract double getTotal();
	
	/**
	 * Gets the number of items sold by an employee in a sale
	 * 
	 * @return
	 */
	public abstract int getNumOfSales();
	
	/**
	 * This method takes a List of persons and puts the code of a person and the 
	 * instance of a Person in a HashMap. 
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, Person> codeToPerson(List<Person> input){
		
		Map<String, Person> codeAndPerson = new HashMap<>();
		for(Person p : input) {
			codeAndPerson.put(p.getPersonCode(), p);
		}
		
		return codeAndPerson;
	}
	
	
	@Override
	public String toString() {
		return this.getName() + " (" + this.getEmails() + ")" + "\n" + this.getAddress(); 
	}

	@Override
	public int compareTo(Person o) {
		return this.getLastName().compareTo(o.getLastName());
	}
	
	
}
