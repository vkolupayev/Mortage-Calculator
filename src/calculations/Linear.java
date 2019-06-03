package calculations;

public class Linear extends Calculator{
	
	public String typeName = "Linear";
    private double yearlyInterestDif;
    public double firstInterest;
	
	public Linear(double mortgage, int years, int months, double interest)
	{
		super( mortgage, years, months, interest);
		firstInterest = mortgage/12*interest/100;
		yearlyInterestDif = firstInterest/paymentAmount();
	}

	public double interestPayment(int index)
	{
		if(index==1)
            return roundPrice(firstInterest);
        else
            return roundPrice(firstInterest-yearlyInterestDif*(index-1));
	}
	
	public double creditPayment()
	{
		return super.creditPayment();
	}
	
	public double leftPayment(int index)
	{
		return roundPrice(mortgage - creditPayment() * index);
	}
	
}