package com.mgg;

/**
 * This class models a specific type of item 
 * a product in the store.  
 * 
 * @author jarondavid
 *
 */
public abstract class Product extends Item{

	private double basePrice;
	
	public Product(String code, String type, String name, double basePrice) {
		super(code, type, name);
		this.basePrice = basePrice;
	}
	

	public abstract double getCost();
	
	public abstract double getAmount();

	@Override
	public double getTaxRate() {
		return 0.0725;
	}

	@Override
	public double getPrice() {
		return this.basePrice;
	}
}
