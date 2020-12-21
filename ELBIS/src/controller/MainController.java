package controller;

import model.DataController;
import model.User;

import java.sql.SQLException;

public class MainController{

	//Atrrib_______________________________________________________________________________________________________
	User activeUser;
	static DataController dc = new DataController();
	
	//main_______________________________________________________________________________________________________
	public static void main(String[] args) {
		new MainController();

	}
	
	//ctor_______________________________________________________________________________________________________
	public MainController() {
		
	}
	
	//Methods_______________________________________________________________________________________________________
	public boolean login(String email, String pw) throws SQLException {
		return dc.login(email, pw);
	}

	//Getters,Setters_______________________________________________________________________________________________________
	public User getActiveUser() {
		return activeUser;
	}
}
