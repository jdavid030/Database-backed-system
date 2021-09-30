package com.mgg;

import java.util.List;

/**
 * This class models a sale that is made in the store
 * 
 * @author jarondavid
 *
 */
public class Sale {
	
	private String salesCode;
	private Store store;
	private Person customer;
	private Person salesPerson;
	private List<Item> items;
	
	public Sale(String salesCode, Store store, Person customer, Person salesPerson, List<Item> items) {
		this.salesCode = salesCode;
		this.store = store;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.items = items;
	}
	
	public String getSalesCode() {
		return salesCode;
	}

	public Store getStore() {
		return store;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public List<Item> getItems() {
		return items;
	}
	
	/** 
	 * This method calculates the total made in one sale including tax. 
	 * 
	 * @return
	 */
	public double getTotalSales() {
		double total = 0.0;
		for(Item i: items) {
			total += i.getTotalCost();
		}
		
		return Math.round(total * 100)/ 100.0;
	}
	
	/**
	 * This method calculates the total made in one sale without tax.
	 * 
	 * @return
	 */
	public double getTotalCost() {
		double total = 0.0;
		for(Item i: items) {
			total += i.getCost();
		}
		
		return total;
	}
	
	/**
	 * This method calculates the total tax amount in one sale.
	 * 
	 * @return
	 */
	public double getTotalTax() {
		double total = 0.0;
		for(Item i: items) {
			total += i.getTax();
		}
		return total;
	}
	
	/**
	 * This method calculates the discount that each customer receives based on their
	 * status at the store. 
	 * 
	 * @return
	 */
	public double getDiscount() {
		
		double total = 0.0;
		total += this.getTotalSales();
		return Math.round(total * customer.getDiscount() * 100) / 100.0; 
	}
	
	/**
	 * This method gets the grand total of a sale, which includes the sub total, tax, and any discounts
	 *
	 * @return
	 */
	public double getGrandTotal() {
		return Math.round((this.getTotalSales() - this.getDiscount()) * 100) / 100.0;
	}
}
