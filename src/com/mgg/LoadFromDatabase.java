package com.mgg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

/**
 * This class has methods to load data from a database and create objects
 * out of the data by processing them. 
 * 
 * @author jarondavid
 *
 */
public class LoadFromDatabase {
	
	/**
	 * Gets a list of the emails a person has based on their person code from the database
	 * 
	 * @param personCode
	 * @return
	 */
	public static List<String> getEmailsByCode(String personCode){
		List<String> emails = new ArrayList<>();
		String email = null;
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select email from Email e join Person p on e.personId = p.personId where p.personCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				email = rs.getString("email");
				emails.add(email);
			}
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return emails;
	}
	
	/**
	 * Takes data from a database using JDBC and processes 
	 * them and creates objects of a person and stores it in 
	 * a list.
	 * 
	 * @param 
	 * @return
	 */
	public static List <Person> loadPersons(){
		List<Person> persons = new ArrayList<>();
		Person person = null;
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select p.personCode, p.personType, p.lastName, p.firstName, "
				     + "a.street, a.city, a.state, a.zipCode, a.country "
				     + "from Person p join Address a "
				     + "on p.addressId = a.addressId;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String personCode = rs.getString("p.personCode");
				String type = rs.getString("p.personType");
				String lastName = rs.getString("p.lastName");
				String firstName = rs.getString("p.firstName");
				String street = rs.getString("a.street");
				String city = rs.getString("a.city");
				String state = rs.getString("a.state");
				String zipCode = rs.getString("a.zipCode");
				String country = rs.getString("a.country");
				
				List<String> emails = getEmailsByCode(personCode);
				
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
			
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
				
		return persons;
	}
	
	/**
	 * Takes data from a database using JDBC and processes 
	 * them and creates objects of a store and stores it in 
	 * a list.
	 * 
	 * @return
	 */
	public static List<Store> loadStores(){
		List<Store> stores = new ArrayList<>();
		List<Person> persons = loadPersons();
		Map <String, Person> codeToPerson = Person.codeToPerson(persons);
		
		Store store = null;
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select p.personCode, s.storeCode, a.street, a.city, a.state, a.zipCode, a.country\n"
					   + "from Person p join Store s \n"
					   + "on p.personId = s.personId\n"
					   + "join Address a \n"
					   + "on a.addressId = s.addressId;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String managerCode = rs.getString("p.personCode");
				String storeCode = rs.getString("s.storeCode");
				String street = rs.getString("a.street");
				String city = rs.getString("a.city");
				String state = rs.getString("a.state");
				String zipCode = rs.getString("a.zipCode");
				String country = rs.getString("a.country");
				
				Person manager = codeToPerson.get(managerCode);
				Address address = new Address(street, city, state, zipCode, country);
				
				store = new Store(storeCode, manager, address);
				stores.add(store);	
			}
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return stores;
	}
	
	/**
	 * Takes data from a database using JDBC and processes 
	 * them and creates objects of an item in the store
	 * and stores it in a list.
	 * 
	 * @return
	 */
	public static List<Item> loadItems() {
		List<Item> items = new ArrayList<>();
		Item item = null;
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select itemCode, itemType, itemName, basePrice from Item;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String code = rs.getString("itemCode");
				String type = rs.getString("itemType");
				String name = rs.getString("itemName");
				double basePrice = 0.0;
				if (type.equals("PG")) {
					 basePrice = 0.0;
				} else {
					basePrice = rs.getDouble("basePrice");
				}
				
				if(type.equals("PN")) {
					item = new NewProduct(code, type, name, basePrice);
				} else if(type.equals("PU")) {
					item = new UsedProduct(code, type, name, basePrice);
				} else if(type.equals("PG")) {
					item = new GiftCard(code, type, name, basePrice);
				} else if(type.equals("SV")) {
					item = new Service(code, type, name, basePrice);
				} else {
					item = new Subscription(code, type, name, basePrice);
				}
				
				items.add(item);		
				
			}
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return items;
	}
	
	/**
	 * Gets the items that are bought in one sale 
	 * (same sales code) from the database 
	 * and stores it in a list. 
	 * 
	 * @param salesCode
	 * @return
	 */
	public static List<Item> getItemsBySalesCode(String salesCode){
		List<Item> itemsInSale = new ArrayList<>();
		List<Item> items = loadItems();
		Map <String, Item> codeToItem = Item.codeToItem(items);
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select it.beginDate, it.endDate, it.numOfHours, it.amount,\n"
					 + "it.servicePersonId, i.itemCode \n"
					 + "from ItemSale it join Sale s\n"
					 + "on it.saleId = s.saleId\n"
					 + "join Item i \n"
					 + "on i.itemId = it.itemId\n"
					 + "where s.salesCode = ?;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, salesCode);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String code = rs.getString("i.itemCode");

				if(codeToItem.get(code).getType().equals("PN")) {
					int quantity = rs.getInt("it.amount");
					Item item = new NewProduct(codeToItem.get(code), quantity);
					itemsInSale.add(item);	
					
				} else if(codeToItem.get(code).getType().equals("PU")) {
					int quantity = rs.getInt("it.amount");
					Item item = new UsedProduct(codeToItem.get(code), quantity);
					itemsInSale.add(item);	
					
				} else if(codeToItem.get(code).getType().equals("PG")) {
					double amount = rs.getDouble("it.amount");
					Item item = new GiftCard(codeToItem.get(code), amount);
					itemsInSale.add(item);	
					
				} else if(codeToItem.get(code).getType().equals("SV")) {
					int id = rs.getInt("it.servicePersonId");
					double numOfHours = rs.getDouble("it.numOfHours");
					Person servicePerson = getPersonById(id);
					Item item = new Service(codeToItem.get(code), servicePerson, numOfHours);
					itemsInSale.add(item);	
					
				} else {
					String begin[] = rs.getString("it.beginDate").split("-");
					String end[] = rs.getString("it.endDate").split("-");
					int beginYear = Integer.parseInt(begin[0]);
					int beginMonth = Integer.parseInt(begin[1]);
					int beginDay = Integer.parseInt(begin[2]);
					int endYear = Integer.parseInt(end[0]);
					int endMonth = Integer.parseInt(end[1]);
					int endDay = Integer.parseInt(end[2]);
					LocalDate beginDate = new LocalDate(beginYear, beginMonth, beginDay);
					LocalDate endDate = new LocalDate(endYear, endMonth, endDay);
					Item item = new Subscription(codeToItem.get(code), beginDate, endDate);
					itemsInSale.add(item);
				}
			}
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return itemsInSale;
	}
	
	/**
	 * This methods gets a person that has a certain person Id from the database.
	 * 
	 * @param personId
	 * @return
	 */
	public static Person getPersonById(int personId) {
		String code = null;
		Connection conn = null;
		List<Person> persons = loadPersons();
		Map <String, Person> codeToPerson = Person.codeToPerson(persons);
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select personCode from Person where personId = ?;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				code = rs.getString("personCode");
			}
			
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return codeToPerson.get(code);
	}
	
	/**
	 * Takes data from a database using JDBC and processes 
	 * them and creates objects of a sale and stores it in 
	 * a list.
	 * 
	 * @return
	 */
	public static List<Sale> loadSales(){
		List<Sale> sales = new ArrayList<>();
		
		List<Person> persons = loadPersons();
		Map <String, Person> codeToPerson = Person.codeToPerson(persons);
		
		List<Store> stores = loadStores();
		Map <String, Store> codeToStore = Store.codeToStore(stores);
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select st.storeCode, p.personCode as customerCode, \n"
					 + "pe.personCode as employeeCode, s.salesCode\n"
					 + "from Sale s join Store st \n"
					 + "on s.storeId = st.storeId\n"
					 + "join Person p \n"
					 + "on s.customerId = p.personId\n"
					 + "join Person pe\n"
					 + "on s.salesPersonId = pe.personId;";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String salesCode = rs.getString("s.salesCode");
				String storeCode = rs.getString("st.storeCode");
				String customerCode = rs.getString("customerCode");
				String employeeCode = rs.getString("employeeCode");
				
				Person customer = codeToPerson.get(customerCode);
				Person salesPerson = codeToPerson.get(employeeCode);
				Store store = codeToStore.get(storeCode);
				List<Item> items = getItemsBySalesCode(salesCode);
				
				Sale sale = new Sale(salesCode, store, customer, salesPerson, items);
				
				sales.add(sale);
			}
			rs.close();
			ps.close();
			conn.close(); 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return sales;
	}
	
	/**
	 * Creates new instances of a store with the sales they made by using an copy constructor
	 * 
	 * @return
	 */
	public static List<Store> getStoreSales() {
		
		List<Store> stores = loadStores();
		List<Sale> sales = loadSales();
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
	 * @return
	 */
	public static List<Person> getEmployeeSales() {
		
		List<Person> persons = loadPersons();
		List<Sale> sales = loadSales();
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
