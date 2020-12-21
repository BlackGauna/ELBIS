package controller;

import javafx.application.Application;
import model.DataController;
import model.User;
import view.MainView;

import javax.swing.*;
import java.sql.SQLException;

public class MainController{

	//Atrrib_______________________________________________________________________________________________________
	User activeUser;
	static DataController dc = new DataController();
	
	//main_______________________________________________________________________________________________________
	public static void main(String[] args) {
		new MainController();
		//Start the FX application
		try {
			Application.launch(MainView.class);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error on launch in View_TEST", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}
	
	//ctor_______________________________________________________________________________________________________
	public MainController() {

	}
	
	//Methods_______________________________________________________________________________________________________
	public boolean login(String email, String pw) throws SQLException {
		return dc.login(email, pw);
		//TODO set active User
	}

	//Getters,Setters_______________________________________________________________________________________________________
	public User getActiveUser() {
		return activeUser;
	}
}
