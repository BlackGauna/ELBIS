package model;

import java.util.Date;

public class User {
	// Atrrib_______________________________________________________________________________________________________
	private int id;
	private String 
		eMail, 
		password, 
		address;
	private Date dateOfBirth;
	private Gender gender;
	
	// Ctor_______________________________________________________________________________________________________
	public User() {
		
	}
	
	// Methods_______________________________________________________________________________________________________
	public void editArticle(int articleId){
		//TODO Implement EditArticle - Only if the user is also the creator
	}
	public void createArticle(){
		//TODO Implement CreateArticle
	}
	public void deleteArticle(int articleId){
		//TODO Implement deleteArticle - Only if the user is also the creator
	}
	
	
	// Getters,Setters_______________________________________________________________________________________________________
	public int getId() {
		return id;
	}
	public void setId(int id) {
		System.out.println("Changing the User ID is not allowed");
		//this.id = id;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
