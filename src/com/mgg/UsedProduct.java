package com.mgg;

/**
 * This class models an used product in the store.
 * 
 * @author jarondavid
 *
 */
public class UsedProduct extends Product {

	private int quantity;
	
	public UsedProduct(String code, String type, String name, double basePrice, int quantity) {
		super(code, type, name, basePrice);
		this.quantity = quantity;
	}
	
	public UsedProduct(String code, String type, String name, double basePrice) {
		this(code, type, name, basePrice, 0);
	}

	public UsedProduct(Item u, int quantity) {
		this(u.getCode(), u.getType(), u.getName(), u.getPrice(), quantity);
	}
	
	@Override
	public double getAmount() {
		return quantity;
	}
	
	@Override
	public double getCost() {
		return Math.round(Math.round(this.getPrice() * 0.8 * 100) / 100.0 * this.quantity * 100) / 100.0;
	}
	
	@Override
	public String toString() {
		String name = this.getName() + "\n";
		String result = String.format(" (Used Item #%s%3.0f@%6.2f/ea)%-27s $%10.2f", this.getCode(), this.getAmount(), this.getPrice()*0.8, "", this.getCost());
		return name + result;
	}

}
