package data;

import calculations.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class Main extends Application {
	
	Stage window;
    Scene scene;
    Button button;
    static double mortgage;
    static int years;
    static int months;
    static double interest;
    static boolean paymentType;
    static boolean fileTest;
    static boolean periodTest;
    
    TextField mortgageTextField = new TextField();
    Text mortgageLabel = new Text("Paskolos suma:");
    
    TextField interestTextField = new TextField();
    Text interestLabel = new Text("Metinis procentas:");
    
    TextField yearsTextField = new TextField();
    Text yearsLabel = new Text("Terminas, metai:");
    
    TextField monthsTextField = new TextField();
    Text monthsLabel = new Text("Terminas, menesiai:");
    
    ChoiceBox<String> paymentTypeChoiceBox = new ChoiceBox<>();
    Text paymentTypeLabel = new Text("Grazinimo grafikas:");
    
    CheckBox fileCheckBox = new CheckBox("Ar norite ataskaitos faile?");
    
    CheckBox periodCheckBox = new CheckBox("Ar noresite atspausdinti periodo mokejimus?");
    
	public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Busto paskolos skaiciuokle");

        //Form
        
        paymentTypeChoiceBox.getItems().addAll("Linijinis", "Anuiteto");
        paymentTypeChoiceBox.setValue("Anuiteto");
        
        button = new Button("Enter");
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e){
                if(		(isNumeric(mortgageTextField.getText())) && 
                		(isNumeric(interestTextField.getText())) &&
                		(isNumeric(yearsTextField.getText())) &&
                		(isNumeric(monthsTextField.getText())) &&
                		(mortgageTextField.getText() != null && !mortgageTextField.getText().isEmpty()) &&
                		(Double.parseDouble(mortgageTextField.getText()) > 0 && Double.parseDouble(interestTextField.getText()) <= 99) &&
                        (interestTextField.getText() != null && !interestTextField.getText().isEmpty()) &&
                        (Double.parseDouble(interestTextField.getText()) > 0 && Double.parseDouble(interestTextField.getText()) <= 99) &&
                        (yearsTextField.getText() != null && !yearsTextField.getText().isEmpty()) &&
                        (Integer.parseInt(yearsTextField.getText()) > 0 && Integer.parseInt(yearsTextField.getText()) <= 50) &&
                        (monthsTextField.getText() != null && !monthsTextField.getText().isEmpty()) &&
                        (Integer.parseInt(monthsTextField.getText()) >= 0 && Integer.parseInt(yearsTextField.getText()) <= 12)){

                    parseData();
                    System.out.println(mortgage);
                    System.out.println(interest);
                    System.out.println(years);
                    System.out.println(months);
                    
                    if(paymentType)
                        calculateLinear();
                    	else
                    		calculateAnnuity();

                } else {
                    System.out.println("Neteisingai ivesti duomenys");
                }
            }
        };
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        //Layout
        GridPane gridpane = new GridPane();
        gridpane.setMinSize(400, 200);
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setVgap(5);
        gridpane.setHgap(5);
        
        gridpane.add(mortgageLabel, 0, 0);
        gridpane.add(mortgageTextField, 1, 0);
        gridpane.add(interestLabel, 0, 1);
        gridpane.add(interestTextField, 1, 1);
        gridpane.add(yearsLabel, 0, 2);
        gridpane.add(yearsTextField, 1, 2);
        gridpane.add(monthsLabel, 0, 3);
        gridpane.add(monthsTextField, 1, 3);
        gridpane.add(paymentTypeLabel, 0, 4);
        gridpane.add(paymentTypeChoiceBox, 1, 4);
        gridpane.add(fileCheckBox, 0, 5);
        gridpane.add(button, 1, 6);
        scene = new Scene(gridpane, 400, 250);
        window.setScene(scene);
        window.show();
    }
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	public void parseData(){
		
		mortgage = Double.parseDouble(mortgageTextField.getText());
        years = Integer.parseInt(yearsTextField.getText());
        months = Integer.parseInt(monthsTextField.getText());
        interest = Double.parseDouble(interestTextField.getText());
        Calculator calcu = new Calculator(mortgage, years, months, interest);
        mortgage = calcu.roundPrice(mortgage);

        if(paymentTypeChoiceBox.getValue()=="Linijinis")
            paymentType=true;
        else
            paymentType=false;

        if(fileCheckBox.isSelected())
            fileTest=true;
        else
            fileTest=false;

    }
	
	public void calculateLinear()
	{
		Linear calc;
		calc = new Linear(mortgage, years, months, interest);
		
		System.out.println(calc.firstInterest);
		
		double[] monthlyCredit = new double[calc.paymentAmount()];
		double[] monthlyInterest = new double[calc.paymentAmount()];
		double[] leftMortgage = new double[calc.paymentAmount()];
		
		
		for(int i=1;i<=calc.paymentAmount();i++)
		{	
			monthlyCredit[i-1] = calc.creditPayment();
			monthlyInterest[i-1] = calc.interestPayment(i);
			if(i==calc.paymentAmount())
				leftMortgage[i-1]=0;
			else
				leftMortgage[i-1] = calc.leftPayment(i);
		}
		
		Results rez;
		rez = new Results();
		
		rez.resultManagement(monthlyCredit, monthlyInterest, leftMortgage, fileTest, periodTest, calc.typeName, calc.paymentAmount());
		
		
	}
	
	public void calculateAnnuity()
	{
		Annuity calc;
		calc = new Annuity(mortgage, years, months, interest);
		
		double[] monthlyCredit = new double[calc.paymentAmount()];
		double[] monthlyInterest = new double[calc.paymentAmount()];
		double[] leftMortgage = new double[calc.paymentAmount()];
		
		leftMortgage[0] = mortgage;
		
		for(int i=1;i<=calc.paymentAmount();i++)
		{	
			
			monthlyCredit[i-1] = calc.creditPayment(leftMortgage[i-1]);
			monthlyInterest[i-1] = calc.interestPayment(leftMortgage[i-1]);
			if(i==calc.paymentAmount())
			{
				leftMortgage[i-1]=0;
			}
			else
				{
					leftMortgage[i-1] = calc.roundPrice(leftMortgage[i-1] - calc.creditPayment(leftMortgage[i-1]));
					leftMortgage[i] = leftMortgage[i-1];
				}
    	}
    
		Results rez;
		rez = new Results();
		
		rez.resultManagement(monthlyCredit, monthlyInterest, leftMortgage, fileTest, periodTest, calc.typeName, calc.paymentAmount());
		
		
	}


	public static void main(String[] args) {
		
		launch(args);

	}

}
