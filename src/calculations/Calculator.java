package calculations;

import java.text.DecimalFormat;

public class Calculator {

	public double mortgage;
	public int years;
	public int months;
	//public boolean paymentType; //1-linear 0-annuity
	public double interest;
	public String typeName = "Basic";
	
	public Calculator(double mortgage, int years, int months, double interest)
	{
		this.mortgage = mortgage;
		this.years = years;
		this.months = months;
		this.interest = interest;
	}
	
    public double roundPrice(double value)
    {
        return  Math.round(value*100)/100;
    }
	
	public int paymentAmount()
	{
		return years * 12 + months;
	}
	
	public double monthlyInterest()
	{
		return interest/(100*12);
	}
	
	public double creditPayment()
	{
		return roundPrice(mortgage / paymentAmount());
	}
	 
}
