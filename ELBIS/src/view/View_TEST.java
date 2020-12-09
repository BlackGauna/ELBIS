package view;

import javax.swing.JOptionPane;

import javafx.application.Application;

public class View_TEST {

	//VM args: 
	//--module-path "\lib\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml
	
	
	public static void main(String[] args) {
		new MainView();
		try {
		Application.launch(MainView.class);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error on launch in View_TEST", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
}
