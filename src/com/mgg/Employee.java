package com.mgg;

import java.util.List;

/** 
 * This class models an employee at the store
 * 
 * @author jarondavid
 *
 */
public class Employee extends Person{
	
	private List<Sale> sales;

	public Employee(String personCode, String type, String lastName, String firstName, Address address, List<String> emails, List<Sale> sales) {
		super(personCode, type, lastName, firstName, address, emails);
		this.sales = sales;
	}
	
	public Employee(String personCode, String type, String lastName, String firstName, Address address, List<String> emails) {
		this(personCode, type, lastName, firstName, address, emails, null);
	}
	
	public Employee(Person p, List<Sale> sales) {
		this(p.getPersonCode(), p.getType(), p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmails(), sales);
	}

	@Override
	public double getDiscount() {
		return 0.15;
	}

	@Override
	public List<Sale> getSales() {
		return sales;
	}
	
	/**
	 * gets the total value of a sale made in each sale
	 * 
	 * @return
	 */
	public double getTotal() {
		double result = 0.0;
		for(Sale s : sales) {
			result += s.getGrandTotal();
		}
		return result;
	}
	
	/**
	 * Gets the number of items sold by an employee in a sale
	 * 
	 * @return
	 */
	public int getNumOfSales() {
		return this.getSales().size();
	}
}
