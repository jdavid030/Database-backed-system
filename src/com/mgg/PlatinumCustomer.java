package com.mgg;

import java.util.List;

/**
 * This class models a platinum customer at the store
 * 
 * @author jarondavid
 *
 */
public class PlatinumCustomer extends Person{

	public PlatinumCustomer(String personCode, String type, String lastName, String firstName, Address address,
			List<String> emails) {
		super(personCode, type, lastName, firstName, address, emails);
	}

	@Override
	public double getDiscount() {
		return 0.10;
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
