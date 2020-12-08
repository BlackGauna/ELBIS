package model;

import java.sql.*;

import javax.swing.JOptionPane;

public class SQLConnection {
	
// 	Example 
	
	static PreparedStatement pst = null;
	static ResultSet rs = null;
	

	
//	public static void main(String[] args) {
//		Connection con = ConnectDB();
//		String sql = "INSERT INTO User VALUES (5, 'testmail@mail.de', 'xyz', ?, 'testuser', 'teststrasse 1', ?,'01.01.1900')";
//		try {
//			pst = con.prepareStatement(sql);
//			pst.execute();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
	public static Connection ConnectDB() {
		try 
		{
			Class.forName("org.sqlite.JDBC");
			Connection con = DriverManager.getConnection("jdbc:sqlite:src/model/ELBIS_DB.db");
			return con;
		} 
		
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,  e);
			return null;
		}	
	}
	

			
}
	
