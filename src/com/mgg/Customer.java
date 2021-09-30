package com.mgg;

import java.util.List;

/** 
 * This class models a regular customer at the store
 * 
 * @author jarondavid
 *
 */

public class Customer extends Person{

	public Customer(String personCode, String type, String lastName, String firstName, Address address,
			List<String> emails) {
		super(personCode, type, lastName, firstName, address, emails);
	}

	@Override
	public double getDiscount() {
		return 0.0;
	}

	@Override
	public List<Sale> getSales() {
		return null;
	}

	@Override
	public double getTotal() {
		return 0;
	}

	@Override
	public int getNumOfSales() {
		return 0;
	}

}
