package com.mgg;

/** 
 * This class models a gift card which is a specific type of product.
 * 
 * @author jarondavid
 *
 */
public class GiftCard extends Product {

	public double amount;
	
	public GiftCard(String code, String type, String name, double basePrice, double amount) {
		super(code, type, name, basePrice);
		this.amount = amount;
	}

	public GiftCard(String code, String type, String name, double basePrice) {
		this(code, type, name, basePrice, 0);
	}

	public GiftCard(Item g, double amount) {
		this(g.getCode(), g.getType(), g.getName(), g.getPrice(), amount);
	}
	
	@Override
	public double getAmount() {
		return this.amount;
	}

	@Override
	public double getCost() {
		return this.amount;
	}
	
	@Override
	public String toString() {
		String name = this.getName() + "\n";
		String result = String.format(" (Gift Card #%s)%-40s $%10.2f", this.getCode(), "", this.getCost());
		return name + result;
	}

}
