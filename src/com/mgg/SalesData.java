package com.mgg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database interface class
 */
public class SalesData {

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "delete from ItemSale";
		String query2 = "delete from Sale";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			ps = conn.prepareStatement(query2);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes the single sales record associated with the given
	 * <code>saleCode</code>
	 * 
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		int saleId = getSaleIdByCode(saleCode);
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "delete from ItemSale where saleId = ?";
		String query2 = "delete from Sale where salesCode = ?";
	
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.executeUpdate();
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			ps = conn.prepareStatement(query2);
			ps.setString(1, saleCode);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "delete from ItemSale";
		String query2 = "delete from Item";
		String query3 = "delete from Sale";
		String query4 = "delete from Email";
		String query5 = "delete from Store";
		String query6 = "delete from Person";
		String query7 = "delete from Address";
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		PreparedStatement ps6 = null;
		PreparedStatement ps7 = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps2 = conn.prepareStatement(query2);
			ps3 = conn.prepareStatement(query3);
			ps4 = conn.prepareStatement(query4);
			ps5 = conn.prepareStatement(query5);
			ps6 = conn.prepareStatement(query6);
			ps7 = conn.prepareStatement(query7);
			
			ps.executeUpdate();
			ps2.executeUpdate();
			ps3.executeUpdate();
			ps4.executeUpdate();
			ps5.executeUpdate();
			ps6.executeUpdate();
			ps7.executeUpdate();
			
			ps.close();
			ps2.close();
			ps3.close();
			ps4.close();
			ps5.close();
			ps6.close();
			ps7.close();
			
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 * 
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String type, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int addressId = insertAddress(street, city, state, zip, country);
		String query = "insert into Person (personCode, personType, lastName, firstName, addressId) values (?,?,?,?,?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, type);
			ps.setString(3, lastName);
			ps.setString(4, firstName);
			ps.setInt(5, addressId);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		int personId = getPersonIdByCode(personCode);
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "insert into Email (email, personId) values (?,?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setInt(2, personId);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 * 
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int addressId = insertAddress(street, city, state, zip, country);
		int personId = getPersonIdByCode(managerCode);
		String query = "insert into Store (storeCode, addressId, personId) values (?,?,?)";
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			ps.setInt(2, addressId);
			ps.setInt(3, personId);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 * 
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "insert into Item (itemCode, itemType, itemName, basePrice) values (?,?,?,?)";
		PreparedStatement ps = null;
		
		if(basePrice == null) {
			basePrice = 0.0;
		}
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			ps.setString(2, type);
			ps.setString(3, name);
			ps.setObject(4, basePrice);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a sales record to the database with the given data.
	 * 
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int storeId = getStoreIdByCode(storeCode);
		int customerId = getPersonIdByCode(customerCode);
		int salesPersonId = getPersonIdByCode(salesPersonCode);
		
		String query = "insert into Sale (salesCode, customerId, salesPersonId, storeId) values (?,?,?,?)";
		PreparedStatement ps = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, saleCode);
			ps.setInt(2, customerId);
			ps.setInt(3, salesPersonId);
			ps.setInt(4, storeId);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int saleId = getSaleIdByCode(saleCode);
		int itemId = getItemIdByCode(itemCode);
		
		String query = "insert into ItemSale (saleId, itemId, amount) values (?,?,?);";
		PreparedStatement ps = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setDouble(3, quantity);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int saleId = getSaleIdByCode(saleCode);
		int itemId = getItemIdByCode(itemCode);
		
		String query = "insert into ItemSale (saleId, itemId, amount) values (?,?,?);";
		PreparedStatement ps = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setDouble(3, amount);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * will be performed by the given employee for the specified number of
	 * hours.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int saleId = getSaleIdByCode(saleCode);
		int itemId = getItemIdByCode(itemCode);
		int servicePersonId = getPersonIdByCode(employeeCode);
		
		String query = "insert into ItemSale (saleId, itemId, numOfHours, servicePersonId) values (?,?,?,?);";
		PreparedStatement ps = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setDouble(3, billedHours);
			ps.setInt(4, servicePersonId);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which
	 * is effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		int saleId = getSaleIdByCode(saleCode);
		int itemId = getItemIdByCode(itemCode);
		
		String query = "insert into ItemSale (saleId, itemId, beginDate, endDate) values (?,?,?,?);";
		PreparedStatement ps = null;
		
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, saleId);
			ps.setInt(2, itemId);
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.executeUpdate();
			
			ps.close();
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	/**
	 * Given the salesCode this method returns the primary key associated with 
	 * that sale in the database. 
	 * 
	 * @param salesCode
	 * @return
	 */
	public static int getSaleIdByCode(String salesCode) {
		int id = 0;
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select saleId from Sale where salesCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, salesCode);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("saleId");
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if(id == 0) {
			throw new RuntimeException("Sale doesn't exist");
		}
		
		return id;
	}
	
	/** 
	 * Given the address this method inserts it into the database and returns the addressId so it can 
	 * be used to insert other records with the address foreign keys such as person and store. 
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return
	 */
	public static int insertAddress(String street, String city, String state, String zip, String country) {
		Connection conn = null;
		int addressId = 0;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String addressQuery = "insert into Address (street, city, state, zipCode, country) values (?,?,?,?,?);";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(addressQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.setString(5, country);
			ps.executeUpdate();
			
			ResultSet keys = ps.getGeneratedKeys();
			keys.next();
			addressId = keys.getInt(1);
			
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return addressId;
	}


	/**
	 * Given the personCode this  method returns the primary key associated with 
	 * that person in the database. 
	 * 
	 * @param personCode
	 * @return
	 */
	public static int getPersonIdByCode(String personCode) {
		int personId = 0;
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select personId from Person where personCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				personId = rs.getInt("personId");
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if(personId == 0) {
			throw new RuntimeException("Person doesn't exist");
		}
		
		return personId;
	}
	
	
	/**
	 * Given the storeCode this  method returns the primary key associated with 
	 * that store in the database. 
	 * 
	 * @param storeCode
	 * @return
	 */
	public static int getStoreIdByCode(String storeCode) {
		int storeId = 0;
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select storeId from Store where storeCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, storeCode);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				storeId = rs.getInt("storeId");
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if(storeId == 0) {
			throw new RuntimeException("Store doesn't exist");
		}
		
		return storeId;
	}
	
	
	/**
	 * Given the itemCode this method returns the primary key associated with 
	 * that item in the database. 
	 * 
	 * @param itemCode
	 * @return
	 */
	public static int getItemIdByCode(String itemCode) {
		int itemId = 0;
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		String query = "select itemId from Item where itemCode = ?;";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, itemCode);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				itemId = rs.getInt("itemId");
			}
			
			rs.close();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		if(itemId == 0) {
			throw new RuntimeException("Item doesn't exist");
		}
		
		return itemId;
	}
}

	