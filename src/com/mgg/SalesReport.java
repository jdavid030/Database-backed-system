package com.mgg;

import java.util.Collections;
import java.util.List;

/**
 * This class has methods to print an employee sales report, a store sales report,
 * and a detailed sales report that outlines the customer, sales person, and the 
 * items bought with the total cost, taxes, discounts, etc.  
 * 
 * It also has methods to print sorted reports from the sorted list ADT. 
 * 
 * @author jarondavid
 *
 */
public class SalesReport {
	
	/**
	 * This method prints the sales made by each of the sales people. Has the information on 
	 * how many sales they made and how much those sales were. 
	 */
	public static void getEmployeeSalesReport(){
		List<Person> employeeSales = LoadFromDatabase.getEmployeeSales();
	
		double totalSales = 0.0;
		int totalNumOfSales = 0;
		
		Collections.sort(employeeSales);
		
		System.out.println("+-----------------------------------------------------+");
		System.out.println("| Salesperson Summary Report                          |");
		System.out.println("+-----------------------------------------------------+");
		System.out.println("Salesperson                    # Sales    Grand Total");
		for(Person p : employeeSales) {
			double grandTotal = p.getTotal();
			int numOfSales = p.getNumOfSales();
			totalSales += grandTotal;
			totalNumOfSales += numOfSales;
			System.out.printf("%-29s  %-8d   $%10.2f\n", p.getName(), numOfSales, grandTotal);
		}
		System.out.println("+-----------------------------------------------------+");
		System.out.printf("%32d          $%10.2f\n\n", totalNumOfSales, totalSales);
	}
	
	/**
	 * This method prints a sales report on sales made at each store. 
	 * It has information on the store code, the manager of the store, the 
	 * number of sales made by that store, and how much those sales were. 
	 */
	public static void getStoreSalesReport() {
		List<Store> storeSales = LoadFromDatabase.getStoreSales();
		
		double totalSales = 0.0;
		int totalNumOfSales = 0;

		System.out.println("+----------------------------------------------------------------+");
		System.out.println("| Store Sales Summary Report                                     |");
		System.out.println("+----------------------------------------------------------------+");
		System.out.println("Store      Manager                        # Sales    Grand Total ");
		for(Store s : storeSales) {
			double grandTotal = s.getTotal();
			int numOfSales = s.getNumOfSales();
			totalSales += grandTotal;
			totalNumOfSales += numOfSales;
			System.out.printf("%-9s  %-29s  %-9d  $%10.2f\n", s.getStoreCode(), s.getManager().getName(), numOfSales, grandTotal);
		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%43d          $%10.2f\n\n", totalNumOfSales, totalSales);
	}
	
	/** 
	 * This prints a detailed report on a sale that was made. 
	 */
	public static void getDetailedReport() {
		List<Sale> sales = LoadFromDatabase.loadSales();
		
		double subTotal = 0.0;
		double tax = 0.0;
		double grandTotal = 0.0;
		double discount = 0.0;
		for(Sale s : sales) {
			
			System.out.println("Sale  #" + s.getSalesCode() + "\n" + "Store #" + s.getStore().getStoreCode());
			System.out.println("Customer:\n" + s.getCustomer());
			System.out.println("Sales Person:\n" + s.getSalesPerson());
			System.out.println("Item                                                               Total");
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=-");
			
			subTotal = s.getTotalCost();
			tax = s.getTotalTax();
			discount = s.getDiscount();
			grandTotal = s.getGrandTotal();
			for(Item i : s.getItems()) {
				System.out.printf("%s\n", i);
			}
			System.out.println("                                                             -=-=-=-=-=-");
			System.out.printf("%-52sSubtotal $   %7.2f\n", "", subTotal);
			System.out.printf("%-57sTax $   %7.2f\n", "", tax);
			System.out.printf("%-44sDiscount (%-5.2f%%)$%10.2f\n", "", s.getCustomer().getDiscount()*100, discount);
			System.out.printf("%-49sGrand Total $   %7.2f\n\n", "",grandTotal);
			
		}
	}
	
	/**
	 * Prints a report of each sale ordered by the customer name alphabetically. 
	 */
	public static void reportOrderedByCustomerName() {
		List<Sale> sales = LoadFromDatabase.loadSales();
		SortedList<Sale> list = new SortedList<>(Compare.cmpByName);
		
		for(Sale l : sales) {
			list.add(l);
		}
		
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("| Sales sorted by Customer name                                                   |");
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("  SalesCode    StoreCode      Customer             SalesPerson          Grand Total");
		
		for(Sale l : list) {
			String salesCode = l.getSalesCode();
			String storeCode = l.getStore().getStoreCode();
			String customer = l.getCustomer().getLastName() + ", " + l.getCustomer().getFirstName();
			String salesPerson = l.getSalesPerson().getLastName() + ", " + l.getSalesPerson().getFirstName();
			double total = l.getGrandTotal();
			
			System.out.printf("  %-12s %-14s %-20s %-20s $%10.2f\n", salesCode, storeCode, customer, salesPerson, total);
		}
		System.out.println("\n");
	}
	
	/**
	 * Prints a report of each sale ordered by the total sale highest to lowest
	 */
	public static void reportOrderedByTotalSale() {
		List<Sale> sales = LoadFromDatabase.loadSales();
		SortedList<Sale> list = new SortedList<>(Compare.cmpByTotal);
		
		for(Sale l : sales) {
			list.add(l);
		}
		
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("| Sales sorted by Total Sale                                                      |");
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("  SalesCode    StoreCode      Customer             SalesPerson          Grand Total");
		
		for(Sale l : list) {
			String salesCode = l.getSalesCode();
			String storeCode = l.getStore().getStoreCode();
			String customer = l.getCustomer().getLastName() + ", " + l.getCustomer().getFirstName();
			String salesPerson = l.getSalesPerson().getLastName() + ", " + l.getSalesPerson().getFirstName();
			double total = l.getGrandTotal();
			
			System.out.printf("  %-12s %-14s %-20s %-20s $%10.2f\n", salesCode, storeCode, customer, salesPerson, total);
		}
		System.out.println("\n");
	}
	
	/**
	 * Prints a report of each sale ordered by the store then by the salesPerson name.
	 */
	public static void reportOrderedByStoreThenEmployee() {
		List<Sale> sales = LoadFromDatabase.loadSales();
		SortedList<Sale> list = new SortedList<>(Compare.cmpByStoreThenEmployeeName);
		
		for(Sale l : sales) {
			list.add(l);
		}
		
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("| Sales ordered by store then sorted by sales person                              |");
		System.out.println("+---------------------------------------------------------------------------------+");
		System.out.println("  SalesCode    StoreCode      Customer             SalesPerson          Grand Total");
		
		for(Sale l : list) {
			String salesCode = l.getSalesCode();
			String storeCode = l.getStore().getStoreCode();
			String customer = l.getCustomer().getLastName() + ", " + l.getCustomer().getFirstName();
			String salesPerson = l.getSalesPerson().getLastName() + ", " + l.getSalesPerson().getFirstName();
			double total = l.getGrandTotal();
			
			System.out.printf("  %-12s %-14s %-20s %-20s $%10.2f\n", salesCode, storeCode, customer, salesPerson, total);
		}
		System.out.println("\n");
	}
	
	
	/**
	 * The main driver that runs the methods
	 * @param args
	 */
	public static void main(String[] args) {
		
		reportOrderedByCustomerName();
		reportOrderedByTotalSale();
		reportOrderedByStoreThenEmployee();
		
		}

}
