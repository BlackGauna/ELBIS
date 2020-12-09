package model;
import java.sql.*;

public class DataController {

	// Test

	Connection con = null;
	PreparedStatement pst = null;

	public void DBSendNewArticle() {
		//newArticle:Article , should return boolean
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

	/*public boolean DBSendNewTopic() {
		//newTopic:Topic

	}

	public boolean DBSendNewUser(){
		//newUser:User

	}

	public Article DBLoadArticle(){
		//dbrequest:String

	}
	public Topic DBLoadTopic(){
		//dbrequest:String

	}
	public User DBLoadUser(){
		//dbrequest:String

	}
	public boolean DBEditArticle(){
		//dbrequest:String

	}
	public boolean DBEditUser(){
		//dbrequest:String

	}
	public boolean DBEditTopic(){
		//dbrequest:String

	}
	public LinkedList<Article> DBLoadRecentArticle(){
		//dbrequest:String

	}
	*/

}