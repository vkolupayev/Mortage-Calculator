package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class Results {
	
	ListView<String> listViewAll;
	ListView<String> listViewPeriod;
	Scene scene, scene2, scene3;
	Button button;
	Button button2;
	Text firstLabel = new Text("Pirmas menuo");
	Text lastLabel = new Text("Paskutnis menuo");
	TextField firstTextField = new TextField();
	TextField lastTextField = new TextField();
	int first;
	int last;
	
	public void resultManagement(double[] monthlyCredit, double[] monthlyInterest, double[] leftMortgage, boolean fileTest, boolean periodTest, String typeName, int time)
	{
        Stage window = new Stage();
        window.setTitle("Mokejimai "+typeName);
        listViewAll = new ListView<>();
        button = new Button("Periodo spausdinimas");
        button.setOnAction(e -> window.setScene(scene2));
        
        
        for(int i=1;i<=time;i++)
        {
        	String info = Integer.toString(i);
			info += " menesio mokejimas: kreditas ";
			info += Double.toString(monthlyCredit[i-1]);
			info += ", palukanos "; 
			info += Double.toString(monthlyInterest[i-1]);
			info += ", likusi dalis ";
			info += Double.toString(leftMortgage[i-1]);
			
			listViewAll.getItems().add(info);
        }
        
        //Langas su visa informacija
        
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(listViewAll, button);
        
        // pop up langas del periodo spausdinimo
        
        GridPane gridpane = new GridPane();
        Button button2 = new Button("Enter");
        gridpane.setMinSize(300, 150);
        gridpane.setPadding(new Insets(10, 10, 10, 10));
        gridpane.setVgap(5);
        gridpane.setHgap(5);
        
        
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e){
            	if(		(isNumeric(firstTextField.getText())) && 
            			(isNumeric(lastTextField.getText())) &&
            			(firstTextField.getText() != null && !firstTextField.getText().isEmpty()) &&
            			(Integer.parseInt(firstTextField.getText()) >= 1 && Integer.parseInt(firstTextField.getText()) < time) &&
            			(lastTextField.getText() != null && !lastTextField.getText().isEmpty()) &&
            			(Integer.parseInt(lastTextField.getText()) > 1 && Integer.parseInt(lastTextField.getText()) <= time) &&
            			(Integer.parseInt(lastTextField.getText()) > Integer.parseInt(firstTextField.getText()))){

            		first = Integer.parseInt(firstTextField.getText());
                    last = Integer.parseInt(lastTextField.getText());
                    
                    //naujas langas su periodo atspausdinta informacija
                    
                    Stage window2 = new Stage();
                    window2.setTitle("Mokejimai "+typeName);
                    listViewPeriod = new ListView<>();
                    
                    for(int i=first;i<=last;i++)
                    {
                    	String info = Integer.toString(i);
            			info += " menesio mokejimas: kreditas ";
            			info += Double.toString(monthlyCredit[i-1]);
            			info += ", palukanos "; 
            			info += Double.toString(monthlyInterest[i-1]);
            			info += ", likusi dalis ";
            			info += Double.toString(leftMortgage[i-1]);
            			
            			listViewPeriod.getItems().add(info);
                    }
                    
                    VBox layout2 = new VBox(10);
                    layout2.setPadding(new Insets(20, 20, 20, 20));
                    layout2.getChildren().add(listViewPeriod);
                    
                    scene3 = new Scene(layout2, 550, 400);
                    window.setScene(scene3);
                    window.show();
                    

            	} else {
            		System.out.println("Neteisingai ivesti duomenys");
            	}
            }
        };
        button2.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    
    	gridpane.add(firstLabel, 0, 0);
    	gridpane.add(firstTextField, 1, 0);
    	gridpane.add(lastLabel, 0, 1);
    	gridpane.add(lastTextField, 1, 1);
    	gridpane.add(button2, 1, 2);


        scene2 = new Scene(gridpane);
        scene = new Scene(layout, 550, 400);
        window.setScene(scene);
        window.show();
		
		if(fileTest)
		{
			PrintWriter Output = createFile("ataskaita.txt");
			printFile(monthlyCredit, monthlyInterest, leftMortgage, Output);
			Output.close();
		}
		
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
		
		
	private static PrintWriter createFile(String fileName)
 
	{
		
		try
		{
			
			// Creates a File object that allows you to work with files on the hardrive
			
			File listOfPayments = new File(fileName);
			
			// FileWriter is used to write streams of characters to a file
			// BufferedWriter gathers a bunch of characters and then writes
			// them all at one time (Speeds up the Program)
			// PrintWriter is used to write characters to the console, file
	
			PrintWriter infoToWrite = new PrintWriter(
					new BufferedWriter(
							new FileWriter(listOfPayments)));
			return infoToWrite;
		}
		
		// You have to catch this when you call FileWriter
		
		catch(IOException e)
		{
		
			System.out.println("An I/O Error Occurred");
			
			// Closes the program
			
			System.exit(0);
		
		}
		return null;
	}
		
	// Create a string and write it to the file
	
	private static void printFile(double[] monthlyCredit, double[] monthlyInterest, double[] leftMortgage, PrintWriter Output) 
	{
		
		for(int i=1;i<=monthlyCredit.length;i++)
		{
			// Create the String that contains the customer info	
			
			String info = Integer.toString(i);
			info += " menesio mokejimas: kreditas ";
			info += Double.toString(monthlyCredit[i-1]);
			info += ", palukanos "; 
			info += Double.toString(monthlyInterest[i-1]);
			info += ", likusi dalis ";
			info += Double.toString(leftMortgage[i-1]);
		
			// Writes the string to the file
		
			Output.println(info);
		
		}
		
	}
}