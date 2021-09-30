package com.mgg;

/**
 * This class models a new product in the store. 
 * 
 * @author jarondavid
 *
 */
public class NewProduct extends Product{
	
	private int quantity;

	public NewProduct(String code, String type, String name, double basePrice, int quantity) {
		super(code, type, name, basePrice);
		this.quantity = quantity;
	}
	
	public NewProduct(String code, String type, String name, double basePrice) {
		this(code, type, name, basePrice, 0);
	}

	public NewProduct(Item p, int quantity) {
		this(p.getCode(), p.getType(), p.getName(), p.getPrice(), quantity);
	}
	
	@Override
	public double getAmount() {
		return quantity;
	} 
	
	@Override
	public double getCost() {
		return this.getPrice() * this.quantity;
	}

	@Override
	public String toString() {
		String name = this.getName() + "\n";
		String result = String.format(" (New Item #%s%3.0f@%6.2f/ea)%-28s $%10.2f", this.getCode(), this.getAmount(), this.getPrice(), "", this.getCost());
		return name + result;
	}
	
}
