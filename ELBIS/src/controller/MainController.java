package controller;

import model.User;

public class MainController{

	//Atrrib_______________________________________________________________________________________________________
	User activeUser;
	
	//main_______________________________________________________________________________________________________
	public static void main(String[] args) {
		new MainController();

	}
	
	//ctor_______________________________________________________________________________________________________
	public MainController() {
		
	}
	
	//Methods_______________________________________________________________________________________________________
	public void login(String email, String pw) {
		System.out.println("MainController: Logging in: "+email+" with "+pw);
	}
	//Getters,Setters_______________________________________________________________________________________________________
	public User getActiveUser() {
		return activeUser;
	}
}
