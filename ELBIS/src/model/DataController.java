package model;
import java.sql.*;

public class DataController {
	
	Connection con = null;
	PreparedStatement pst = null;
	
	public void DBSendNewArticle() {
		con =  SQLConnection.ConnectDB();
		try 
		{
			String query = "INSERT INTO Article (title, content, creationDate, expireDate, lastEdit, status, topic, authorId, publisherId, publisherComment)"
					+ "VALUES ()";
			pst = con.prepareStatement(query);
			pst.execute();
			con.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void DBSendNewTopic() {
		
	}
	
}