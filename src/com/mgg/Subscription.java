package com.mgg;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * This class models a subscription which is a specific 
 * item from the store. 
 * 
 * @author jarondavid
 *
 */
public class Subscription extends Item{
	
	private double annualFee;
	private LocalDate beginDate;
	private LocalDate endDate;

	public Subscription(String code, String type, String name, double annualFee, LocalDate beginDate, LocalDate endDate) {
		super(code, type, name);
		this.annualFee = annualFee;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	public Subscription(String code, String type, String name, double annualFee) {
		this(code, type, name, annualFee, null, null);
	}
	
	public Subscription(Item s, LocalDate beginDate, LocalDate endDate) {
		this(s.getCode(), s.getType(), s.getName(), s.getPrice(), beginDate, endDate);
	}
	
	public LocalDate getBeginDate() {
		return beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public int daysOfSubscription() {
		int result = 0;
			result = Days.daysBetween(this.beginDate, this.endDate).getDays()+1;
		return result;
	}

	@Override
	public double getCost() {
		return Math.round((this.daysOfSubscription()/365.0 * this.annualFee) * 100) / 100.0;
	}

	@Override
	public double getTaxRate() {
		return 0.0;
	}

	@Override
	public double getPrice() {
		return this.annualFee;
	}
	
	@Override
	public double getAmount() {
		return this.daysOfSubscription();
	}

	@Override
	public String toString() {
		String name = this.getName() + "\n";
		String result = String.format(" (Subscription #%s %5d days@$%6.2f/yr)%-15s $%10.2f", this.getCode(), this.daysOfSubscription(), this.getPrice(), "", this.getCost());
		return name + result;
	}
}
