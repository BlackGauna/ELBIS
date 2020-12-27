package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	public static Connection ConnectDB() {
		try 
		{
			Connection con = DriverManager.getConnection("jdbc:sqlite:src/model/ELBIS_DB.db");
			if (con != null){
				System.out.println("Connected to database ELBIS_DB");
			}
			return con;
		} 
		
		catch(SQLException e)
		{
			System.out.println("An SQL error occurred");
			return null;
		}	
	}
}