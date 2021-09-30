package com.mgg;

import java.io.File; 
import java.io.FileNotFoundException;
import org.joda.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * A class that contains methods to load a CSV file and store the data into 
 * their specific classes. 
 * 
 * @author jarondavid
 *
 */
public class LoadData {
	
	
	/**
	 * 
	 * This method takes in a filename as a string and tokenizes the data by the delimiter and 
	 * creates instances of a Person Object and stores each person in a List. 
	 * 
	 * @param filename as a string
	 * @return List
	 */
	public static List<Person> loadPersons(String personsFile){
		
		List<Person> persons = new ArrayList<>();
		
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File(personsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// this line takes care of the first line in the CSV file which correlates to the number of lines in the file. 
		fileInput.nextLine();
		
		// Goes through each line tokenizing each element and creating instances of addresses and persons and stores 
		// each person in a list. 
		while(fileInput.hasNext()) {
			Person person = null;
			String line = fileInput.nextLine();
			String tokens[] = line.split(",");
			String personCode = tokens[0];
			String type = tokens[1];
			String lastName = tokens[2];
			String firstName = tokens[3];
			String street = tokens[4];
			String city = tokens[5];
			String state = tokens[6];
			String zipCode = tokens[7];
			String country = tokens[8];
			
			// list to hold the email(s) of each person
			List<String> emails = new ArrayList<>();
			
			// Collects the email(s) of the person in the given data and stores it in the list of emails. 
			for(int i=9; i<tokens.length; i++) {
				emails.add(tokens[i]);
			}
			
			Address address = new Address(street, city, state, zipCode, country);
			if(type.equals("P")) {
				person = new PlatinumCustomer(personCode, type, lastName, firstName, address, emails);
			} else if(type.equals("G")) {
				person = new GoldCustomer(personCode, type, lastName, firstName, address, emails);
			} else if(type.equals("C")) {
				person = new Customer(personCode, type, lastName, firstName, address, emails);
			} else {
				person = new Employee(personCode, type, lastName, firstName, address, emails);
			}
			persons.add(person);
		}
		
		fileInput.close();
		
		return persons;
	}
	
	
	/**
	 * 
	 * This method takes in a filename as a string and tokenizes the data by the delimiter and 
	 * creates instances of a Store Object and stores each store in a List. 
	 * 
	 * @param filename as a string
	 * @return List
	 */
	public static List<Store> loadStores(String storesFile, String personsFile){
	
		List<Person> persons = LoadData.loadPersons(personsFile);
		Map <String, Person> codeToPerson = Person.codeToPerson(persons);
		
		List<Store> stores = new ArrayList<>();
		
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File(storesFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// this line takes care of the first line in the CSV file which correlates to the number of lines in the file. 
		fileInput.nextLine();
		
		// Goes through each line tokenizing each element and creating instances of addresses and stores and stores 
		// each store in a list. 
		while(fileInput.hasNext()) {
			String line = fileInput.nextLine();
			String tokens[] = line.split(",");
			String storeCode = tokens[0];
			String managerCode = tokens[1];
			String street = tokens[2];
			String city = tokens[3];
			String state = tokens[4];
			String zipCode = tokens[5];
			String country = tokens[6];
			
			Person manager = codeToPerson.get(managerCode);
			
			Address address = new Address(street, city, state, zipCode, country);
			Store store = new Store(storeCode, manager, address);
			stores.add(store);
			
		}
		fileInput.close();
		return stores;
	}
	
	/**
	 * 
	 * This method takes in a filename as a string and tokenizes the data by the delimiter and 
	 * creates instances of an Item Object and stores each item in a List. 
	 * 
	 * @param filename as a string
	 * @return List
	 */
	public static List<Item> loadItems(String input){
		
		List<Item> items = new ArrayList<>();
		
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File(input));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// this line takes care of the first line in the CSV file which correlates to the number of lines in the file. 
		fileInput.nextLine();
		
		// Goes through each line tokenizing each element and creating instances of an Item and stores 
		// each item in a list. 
		while(fileInput.hasNext()) {
			Item item = null;
			String line = fileInput.nextLine();
			String tokens[] = line.split(",", -1);
			String code = tokens[0];
			String type = tokens[1];
			String name = tokens[2];
			Double price = 0.0;
			if (type.equals("PG")) {
				price = 0.0;
			} else {
				price = Double.parseDouble(tokens[3]);
			}
			
			if(type.equals("PN")) {
				item = new NewProduct(code, type, name, price);
			} else if(type.equals("PU")) {
				item = new UsedProduct(code, type, name, price);
			} else if(type.equals("PG")) {
				item = new GiftCard(code, type, name, price);
			} else if(type.equals("SV")) {
				item = new Service(code, type, name, price);
			} else {
				item = new Subscription(code, type, name, price);
			}
			
			items.add(item);
		}
		fileInput.close();
		return items;
	}
	
	/**
	 * 
	 * This method takes in a filename as a string, and three maps that have the code of a object mapped to the object itself. 
	 * The method then goes through the file and creates instances of a sale that is made. 
	 * 
	 * @param filename as a string
	 * @return List
	 */
	public static List<Sale> loadSales (String personsFile, String storesFile, String itemsFile, String salesFile){
		List<Person> persons = LoadData.loadPersons(personsFile);
		Map <String, Person> codeToPerson = Person.codeToPerson(persons);
	
		List<Store> stores = LoadData.loadStores(storesFile, personsFile);
		Map <String, Store> codeToStore = Store.codeToStore(stores);
	
		List<Item> items = LoadData.loadItems(itemsFile);
		Map <String, Item> codeToItem = Item.codeToItem(items);
		
		List<Sale> sales = new ArrayList<>();
	
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File(salesFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		fileInput.nextLine();
		
		// goes through and tokenizes each element and creates instances of a sale, and stores it in a list of sales. 
		while(fileInput.hasNext()) {
			Sale sale = null;
			String line = fileInput.nextLine();
			String tokens[] = line.split(",", -1);
			String salesCode = tokens[0];
			Store store = codeToStore.get(tokens[1]);
			Person customer = codeToPerson.get(tokens[2]);
			Person salesPerson = codeToPerson.get(tokens[3]);
			
			List<Item> itemsInSale = new ArrayList<>();
			// goes through and creates instances of items and adds the specifics of how much/many of that item 
			// a person bought using a copy constructor. This is then added to a list of items which is added to a sale. 
			for(int i=0; i<tokens.length; i++) {
				if(codeToItem.get(tokens[i]) != null) {
					
					if(codeToItem.get(tokens[i]).getType().equals("PN")) {						
						int quantity = Integer.parseInt(tokens[i+1]);
						Item item = new NewProduct(codeToItem.get(tokens[i]), quantity);
						itemsInSale.add(item);						
					} else if(codeToItem.get(tokens[i]).getType().equals("PU")) {					
						int quantity = Integer.parseInt(tokens[i+1]);
						Item item = new UsedProduct(codeToItem.get(tokens[i]), quantity);
						itemsInSale.add(item);					
					} else if(codeToItem.get(tokens[i]).getType().equals("PG")) {					
						double amount = Double.parseDouble(tokens[i+1]);
						Item item = new GiftCard(codeToItem.get(tokens[i]), amount);
						itemsInSale.add(item);						
					} else if(codeToItem.get(tokens[i]).getType().equals("SV")) {						
						Person servicePerson = codeToPerson.get(tokens[i+1]);
						double numOfHours = Double.parseDouble(tokens[i+2]);
						Item item = new Service(codeToItem.get(tokens[i]), servicePerson, numOfHours);
						itemsInSale.add(item);						
					} else {	
						String begin[] = tokens[i+1].split("-");
						String end[] = tokens[i+2].split("-");
						int beginYear = Integer.parseInt(begin[0]);
						int beginMonth = Integer.parseInt(begin[1]);
						int beginDay = Integer.parseInt(begin[2]);
						int endYear = Integer.parseInt(end[0]);
						int endMonth = Integer.parseInt(end[1]);
						int endDay = Integer.parseInt(end[2]);
						LocalDate beginDate = new LocalDate(beginYear, beginMonth, beginDay);
						LocalDate endDate = new LocalDate(endYear, endMonth, endDay);
						Item item = new Subscription(codeToItem.get(tokens[i]), beginDate, endDate);
						itemsInSale.add(item);
					}
				}
				sale = new Sale(salesCode, store, customer, salesPerson, itemsInSale);
			}
			sales.add(sale);
		}
		
		fileInput.close();
		return sales;
	}
	
	/**
	 * This method takes a list of stores and a list of sales and finds the sales that are related 
	 * to the specific stores and creates a new object of the store with the sale. This is done 
	 * using a copy constructor within the store class. 
	 * 
	 * @param personsFile
	 * @param storesFile
	 * @param itemsFile
	 * @param salesFile
	 * @return
	 */
	public static List<Store> getStoreSales(String personsFile, String storesFile, String itemsFile, String salesFile) {
	
		List<Store> stores = LoadData.loadStores(storesFile, personsFile);
		List<Sale> sales = LoadData.loadSales(personsFile, storesFile, itemsFile, salesFile);
		List<Store> storeSales = new ArrayList<>();
		
		/* Goes through the list of stores and finds the sales that 
		 * has been made from that store and adds the sales to a list.
		 * This list of sales is then used to create new instances of a store 
		 * using a copy constructor. 
		 */
		for(Store s : stores) {
			List<Sale> salesOfStores = new ArrayList<>();
			for(Sale l : sales) {
				if (s.getStoreCode().equals(l.getStore().getStoreCode())) {
					salesOfStores.add(l);
				}
			}
			Store store = new Store(s, salesOfStores);
			storeSales.add(store);
		}
		
		return storeSales;
	}
	
	/**
	 * Creates new instances of an employee with the sales they made by using an copy constructor
	 * 
	 * @param personsFile
	 * @param storesFile
	 * @param itemsFile
	 * @param salesFile
	 * @return
	 */
	public static List<Person> getEmployeeSales(String personsFile, String storesFile, String itemsFile, String salesFile) {
	
		List<Person> persons = LoadData.loadPersons(personsFile);
		List<Sale> sales = LoadData.loadSales(personsFile, storesFile, itemsFile, salesFile);
		List<Person> employeeSales = new ArrayList<>();
		
		/* Finds the employees in the list of persons and finds
	 	 * the sales they have made and adds that to a list, which is then 
		 * used to create new instances of an employee using a copy constructor. 
		 */
		for(Person p : persons) {
			if(p.getType().equals("E")){
				List<Sale> salesOfEmployees = new ArrayList<>();
				for(Sale s : sales) {
					if(p.getPersonCode().equals(s.getSalesPerson().getPersonCode())) {
						salesOfEmployees.add(s);
					}
				}
				Person person = new Employee(p, salesOfEmployees);
				employeeSales.add(person);
			}
		}
		return employeeSales;
	}
	
}
