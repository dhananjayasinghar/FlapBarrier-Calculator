package com.itc.calculator;

import com.itc.calculator.ui.CalculatorUI;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Dhananjay Samanta
 */
@SuppressWarnings("restriction")
public class AppInitializer extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	 new CalculatorUI().setVisible(true);  
    }
    
	public static void main(String[] args) {
        launch(args);
    }
    
}
