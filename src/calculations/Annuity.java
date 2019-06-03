package calculations;

public class Annuity extends Calculator{

	public String typeName = "Annuity";
	double annuity;
	
	public Annuity(double mortgage, int years, int months, double interest)
	{
		super(mortgage, years, months, interest);
		annuity = muchAnnuity();
	}
	
	public double muchAnnuity(){
        return ((mortgage * monthlyInterest()) * (Math.pow((1 + monthlyInterest()), paymentAmount()))) / (Math.pow(1 + monthlyInterest(), paymentAmount()) - 1);
    }
	
    public double interestPayment(double leftMortgage)
    {
        return roundPrice(leftMortgage * monthlyInterest());
    }
	
	public double creditPayment(double leftMortgage)
	{
		if(annuity - interestPayment(leftMortgage) > leftMortgage)
            return roundPrice(leftMortgage);
        return roundPrice(annuity - interestPayment(leftMortgage));
	}
	
}
