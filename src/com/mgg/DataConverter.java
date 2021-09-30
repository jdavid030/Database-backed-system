package com.mgg;

import java.util.List;

/** 
 * This class runs the program by using methods to create instances of each object and convert them to 
 * XML files and finally outputs those to another file. 
 * 
 * Authors: Jaron David
 * Date: February 20, 2021
 */
public class DataConverter {
	
	public static void main(String[] args) {
		 
		List<Person> persons = LoadData.loadPersons("data/Persons.csv");
		SaveData.personToXML(persons);
	
		List<Store> stores = LoadData.loadStores("data/Stores.csv", "data/Persons.csv");
		SaveData.storeToXML(stores);
	
		List<Item> items = LoadData.loadItems("data/Items.csv");
		SaveData.itemToXML(items);	
		
	}
}
