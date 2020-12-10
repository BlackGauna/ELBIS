package model;
import java.sql.*;

public class DataController {

	public static final String TABLE_ARTICLE = "Article";
	public static final String COLUMN_ARTICLE_ID = "id";
	public static final String COLUMN_ARTICLE_TITLE = "title";
	public static final String COLUMN_ARTICLE_CONTENT = "content";
	public static final String COLUMN_ARTICLE_CREATION_DATE = "creationDate";
	public static final String COLUMN_ARTICLE_EXPIRE_DATE = "expireDate";
	public static final String COLUMN_ARTICLE_LASTED_IT = "lastEdit";
	public static final String COLUMN_ARTICLE_STATUS = "status";
	public static final String COLUMN_ARTICLE_TOPIC = "topic";
	public static final String COLUMN_ARTICLE_AUTHOR_ID = "authorId";
	public static final String COLUMN_ARTICLE_PUBLISHER_ID= "publisherId";
	public static final String COLUMN_ARTICLE_PUBLISHER_COMMENT= "publisherComment";
	public static final int INDEX_TABLE_ARTICLE = 1;
	public static final int INDEX_ARTICLE_ID = 2;
	public static final int INDEX_ARTICLE_TITLE = 3;
	public static final int INDEX_ARTICLE_CONTENT = 4;
	public static final int INDEX_ARTICLE_CREATION_DATE = 5;
	public static final int INDEX_ARTICLE_EXPIRE_DATE = 6;
	public static final int INDEX_ARTICLE_LAST_EDIT = 7;
	public static final int INDEX_ARTICLE_STATUS = 8;
	public static final int INDEX_ARTICLE_TOPIC = 9;
	public static final int INDEX_ARTICLE_AUTHOR_ID = 10;
	public static final int INDEX_ARTICLE_PUBLISHER_ID = 11;
	public static final int INDEX_ARTICLE_PUBLISHER_COMMENT = 12;

	public static final String TABLE_GENDER = "Gender";
	public static final String COLUMN_GENDER_ID = "id";
	public static final String COLUMN_GENDER_NAME = "name";
	public static final int INDEX_GENDER_ID = 1;
	public static final int INDEX_GENDER_NAME = 2;

	public static final String TABLE_ROLE = "Role";
	public static final String COLUMN_ROLE_ID = "id";
	public static final String COLUMN_ROLE_NAME = "name";
	public static final int INDEX_ROLE_ID = 1;
	public static final int INDEX_ROLE_NAME = 2;

	public static final String TABLE_STATUS = "Status";
	public static final String COLUMN_STATUS_ID = "id";
	public static final String COLUMN_STATUS_NAME = "name";
	public static final int INDEX_STATUS_ID = 1;
	public static final int INDEX_STATUS_NAME = 2;

	public static final String TABLE_TOPIC = "Topic";
	public static final String COLUMN_TOPIC_ID = "id";
	public static final String COLUMN_TOPIC_NAME = "name";
	public static final String COLUMN_TOPIC_PARENT_ID = "parentTopic";
	public static final int INDEX_TOPIC_ID = 1;
	public static final int INDEX_TOPIC_NAME = 2;
	public static final int INDEX_TOPIC_PARENT_ID = 3;

	public static final String TABLE_USER = "User";
	public static final String COLUMN_USER_ID = "id";
	public static final String COLUMN_USER_EMAIL = "email";
	public static final String COLUMN_USER_PASSWORD = "password";
	public static final String COLUMN_USER_ROLE = "role";
	public static final String COLUMN_USER_NAME = "name";
	public static final String COLUMN_USER_ADDRESS = "address";
	public static final String COLUMN_USER_GENDER = "gender";
	public static final String COLUMN_USER_DATE_OF_BIRTH = "dateOfBirth";
	public static final int INDEX_USER_ID = 1;
	public static final int INDEX_USER_EMAIL = 2;
	public static final int INDEX_USER_PASSWORD = 3;
	public static final int INDEX_USER_ROLE = 4;
	public static final int INDEX_USER_NAME = 5;
	public static final int INDEX_USER_ADDRESS = 6;
	public static final int INDEX_USER_GENDER = 7;
	public static final int INDEX_USER_DATE_OF_BIRTH = 8;


	//TODO More ordering types?
	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASCENDING = 2;
	public static final int ORDER_BY_DESCENDING = 3;

	//TODO Prepare querys

	Connection con;

	//TODO PreparedStatement blabla;

	private static DataController instance = new DataController();

	private DataController() {

	}

	public static DataController getInstance() {
		return instance;
	}

	public boolean open() {
		try {
			con =  SQLConnection.ConnectDB();

			//TODO Preload statements at connection


			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		try {

			//TODO querys != null and close them

			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	//TODO Implement Methods


	/*Connection con = null;
	PreparedStatement pst = null;

	public boolean DBSendNewArticle() {
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

	public boolean DBSendNewTopic() {
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

	//TODO add methods; delete, sort ?

}