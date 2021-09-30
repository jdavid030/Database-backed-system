package com.mgg;

import java.io.FileNotFoundException; 
import java.io.PrintWriter;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * This class has methods to convert a list of instances of an object to 
 * a string with the XML data and store the XML data to a separate file.
 * 
 * @author jarondavid
 *
 */
public class SaveData {

	/**
	 * This method takes a list of instances of a Person and converts the data into XML.
	 *  
	 * @param List<Person>
	 * 
	 */
	public static void personToXML(List<Person> persons) {
		
		XStream xstream = new XStream();
		// the field "type" is omitted so it is not printed on the output
		xstream.omitField(Person.class, "type");
		// the alias is made so instead of printing "string" as the tag for each email it prints "email". 
		xstream.alias("email", String.class);
		
		// Converts each object to the XML output. Creates each section based on what type it is (C, E, P, G) 
		// and creates an alias to print out the full name instead. 
		
		String xml = "<persons>\n";
		for(int i=0; i<persons.size(); i++) {
			
			if(persons.get(i).getType().equals("C")) {
				xstream.alias("Customer", Person.class);
				xml += xstream.toXML(persons.get(i)) + "\n";
				
			} else if(persons.get(i).getType().equals("P")){
				xstream.alias("PlatinumCustomer", Person.class);
				xml += xstream.toXML(persons.get(i)) + "\n";
				
			} else if(persons.get(i).getType().equals("G")) {
				xstream.alias("GoldCustomer", Person.class);
				xml += xstream.toXML(persons.get(i)) + "\n";
				
			} else {
				xstream.alias("Employee", Person.class);
				xml += xstream.toXML(persons.get(i)) + "\n";
			}
		}
		xml += "</persons>";
		outputData(xml, "data/Persons.xml");
	}
	
	/**
	 * This method takes a list of instances of a Store and converts the data into XML.
	 * 
	 * @param List<Store>
	 * 
	 */
	public static void storeToXML(List<Store> stores) {
		
		XStream xstream = new XStream();
		
		// aliases are made to change the name of the tags of an element in the XML data. 
		xstream.alias("Store", Store.class);
		xstream.alias("email", String.class);
		// this is omitted so it doesn't print in the XML data.
		xstream.omitField(Person.class, "type");
		
		String xml = "<stores>\n";
		for(int i=0; i<stores.size(); i++) {
			xml += xstream.toXML(stores.get(i)) + "\n";
		}
		xml += "</stores>";
		outputData(xml, "data/Stores.xml");
	}
	
	/**
	 * This method takes a list of instances of an Item and converts the data into XML.
	 * 
	 * @param List<Item>
	 * 
	 */
	public static void itemToXML(List<Item> items) {
		
		XStream xstream = new XStream();
		xstream.omitField(Item.class, "type");
		
		String xml = "<items>\n";
		for(int i=0; i<items.size(); i++) {
			
			if(items.get(i).getType().equals("PN")) {
				xstream.alias("NewProduct", Product.class);
				xstream.aliasField("basePrice", Item.class, "price");
				xml += xstream.toXML(items.get(i)) + "\n";
				
			} else if(items.get(i).getType().equals("PU")) {
				xstream.alias("UsedProduct", Product.class);
				xstream.aliasField("basePrice", Item.class, "price");
				xml += xstream.toXML(items.get(i)) + "\n";
				
			} else if(items.get(i).getType().equals("PG")) {
				xstream.alias("GiftCard", Product.class);
				xml += xstream.toXML(items.get(i)) + "\n";
				
			} else if(items.get(i).getType().equals("SV")) {
				xstream.alias("Service", Service.class);
				xstream.aliasField("hourlyRate", Item.class, "price");
				xml += xstream.toXML(items.get(i)) + "\n";
				
			} else if(items.get(i).getType().equals("SB")) {
				xstream.alias("Subscription", Subscription.class);
				xstream.aliasField("annualFee", Item.class, "price");
				xml += xstream.toXML(items.get(i)) + "\n";
			}
		}
		xml += "</items>";
		outputData(xml, "data/Items.xml");
	}
	
	
	/**
	 * This method takes a string and a file path and prints the string into an output file.
	 * 
	 * @param input
	 */
	public static void outputData(String input, String filePath) {
		
		try {
			PrintWriter pw = new PrintWriter(filePath);
			pw.println(input);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
	
