package com.mgg;

/**
 * This class models a specific item in the store,
 * a service. 
 * 
 * @author jarondavid
 *
 */
public class Service extends Item{

	private double hourlyRate;
	private Person servicePerson; 
	private double numOfHours;
	
	public Service(String code, String type, String name, double hourlyRate, Person servicePerson, double numOfHours) {
		super(code, type, name);
		this.hourlyRate = hourlyRate;
		this.servicePerson = servicePerson;
		this.numOfHours = numOfHours;
	}
	
	public Service(String code, String type, String name, double hourlyRate) {
		this(code, type, name, hourlyRate, null, 0.0);
	}
	
	public Service(Item s, Person servicePerson, double numOfHours) {
		this(s.getCode(), s.getType(), s.getName(), s.getPrice(), servicePerson, numOfHours);
	}
	
	public Person getServicePerson() {
		return servicePerson;
	}

	@Override
	public double getCost() {
		return Math.round(this.hourlyRate * this.numOfHours * 100) / 100.0;
	}

	@Override
	public double getTaxRate() {
		return 0.0285;
	}

	@Override
	public double getPrice() {
		return this.hourlyRate;
	}

	@Override
	public double getAmount() {
		return this.numOfHours;
	}
	
	@Override
	public String toString() {
		String name = this.getName() + "\n";
		String result = String.format(" (Service #%s by %s %.2fhrs@$%5.2f/hr)%-2s $%10.2f", this.getCode(), this.servicePerson.getName(), this.getAmount(), this.getPrice(), "", this.getCost());
		return name + result;
	}
}
