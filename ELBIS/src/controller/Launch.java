package controller;

import javafx.application.Application;

import javax.swing.*;

public class Launch {

	//main_______________________________________________________________________________________________________
	public static void main(String[] args) {
		//Start the FX application
		try {
			Application.launch(MainController.class);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error on launch in View_TEST", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
