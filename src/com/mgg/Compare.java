package com.mgg;

import java.util.Comparator;

/**
 * This class contains comparators for the sorted list ADT.
 * 
 * @author jarondavid
 *
 */
public class Compare{

	/**
	 * This comparator compares by the last name then the first name of a customer 
	 */
	public static final Comparator<Sale> cmpByName = new Comparator<Sale>() {
		
        public int compare(Sale s1, Sale s2) {
        	
            String lastName1 = s1.getCustomer().getLastName();
            String lastName2 = s2.getCustomer().getLastName();
            String firstName1 = s1.getCustomer().getFirstName();
            String firstName2 = s2.getCustomer().getFirstName();
            
            int result = lastName1.compareToIgnoreCase(lastName2);
            if(result == 0) {
            	return firstName1.compareToIgnoreCase(firstName2);
            } 
            return result;
        }
    };
    
    
    /**
     * This comparator orders the sales by the value of sale highest to lowest.
     */
    public static final Comparator<Sale> cmpByTotal = new Comparator<Sale>() {
    
    	public int compare(Sale s1, Sale s2) {
    		
    		Double total1 = s1.getGrandTotal();
    		Double total2 = s2.getGrandTotal();
    		
    		return -total1.compareTo(total2);
    	}
    };
    
    
    /**
     * This comparator orders the sales by the stores then by the employee name.
     */
   public static final Comparator<Sale> cmpByStoreThenEmployeeName = new Comparator<Sale>() {
	   
	   public int compare(Sale s1, Sale s2) {
		   
		   String storeCode1 = s1.getStore().getStoreCode();
		   String storeCode2 = s2.getStore().getStoreCode();
		   
		   String lastName1 = s1.getSalesPerson().getLastName();
           String lastName2 = s2.getSalesPerson().getLastName();
           String firstName1 = s1.getSalesPerson().getFirstName();
           String firstName2 = s2.getSalesPerson().getFirstName();
           
           int result = storeCode1.compareToIgnoreCase(storeCode2);
           if(result == 0) {
        	   int result2 = lastName1.compareToIgnoreCase(lastName2);
        	   if(result2 == 0) {
               	return firstName1.compareToIgnoreCase(firstName2);
               } 
               return result2;
           }
           return result;
	   }
   };
    
    
}
