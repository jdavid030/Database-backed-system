package com.mgg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class models an item in the store
 * 
 * @author jarondavid
 *
 */
public abstract class Item {

	private String code;
	private String name;
	private String type;
	
	public Item(String code, String type, String name) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * Takes a list of items and maps the code of the item to the 
	 * item object itself. 
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, Item> codeToItem(List<Item> input){
		
		Map<String, Item> codeAndItem = new HashMap<>();
		for(Item i : input) {
			codeAndItem.put(i.getCode(), i);
		}
		return codeAndItem;
	}
		
	public abstract double getCost();
	
	public abstract double getTaxRate();
	
	public abstract double getPrice();
	
	public abstract double getAmount();
	
	public double getTax() {
		return Math.round(this.getCost() * this.getTaxRate() * 100) / 100.0;
	}
	
	public double getTotalCost() {
		return (this.getCost() + this.getTax());
	}
	
}
