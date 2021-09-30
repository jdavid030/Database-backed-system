package com.mgg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class models a store
 * 
 * @author jarondavid
 *
 */
public class Store {

	private String storeCode;
	private Person manager;
	private Address address;
	private List<Sale> sales;
	
	
	public Store(String storeCode, Person manager, Address address, List<Sale> sales) {
		this.storeCode = storeCode;
		this.manager = manager;
		this.address = address;
		this.sales = sales;
	}

	public Store(String storeCode, Person manager, Address address) {
		this(storeCode, manager, address, null);
	}
	
	public Store(Store s, List<Sale> sales) {
		this(s.getStoreCode(), s.getManager(), s.getAddress(), sales);
	}
	
	public String getStoreCode() {
		return storeCode;
	}

	public Person getManager() {
		return manager;
	}

	public Address getAddress() {
		return address;
	}
	
	public List<Sale> getSales() {
		return sales;
	}
	
	/**
	 * This method takes a List of stores and puts the code of a store and the 
	 * instance of a store in a HashMap. 
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, Store> codeToStore(List<Store> input){
		
		Map<String, Store> codeAndStore = new HashMap<>();
		for(Store s : input) {
			codeAndStore.put(s.getStoreCode(), s);
		}
		
		return codeAndStore;
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
