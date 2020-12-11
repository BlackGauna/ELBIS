package model;
import java.sql.*;

public class DataController {

	public static final String TABLE_ARTICLE = "Article";
	public static final String COLUMN_ARTICLE_ID = "id";
	public static final String COLUMN_ARTICLE_TITLE = "title";
	public static final String COLUMN_ARTICLE_CONTENT = "content";
	public static final String COLUMN_ARTICLE_CREATION_DATE = "creationDate";
	public static final String COLUMN_ARTICLE_EXPIRE_DATE = "expireDate";
	public static final String COLUMN_ARTICLE_LAST_EDIT = "lastEdit";
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


	// More ordering types?
	public static final int ORDER_BY_ASCENDING = 1;
	public static final int ORDER_BY_DESCENDING = 2;

	//How exactly will the creation, expiration, last edit dates set ? plus the other stuff ?
	//The Stuff that we need can be changed anytime to however we want to receive something from the database, do not hesitate to make changes.

	public static final String SEND_NEW_ARTICLE =  "INSERT INTO " + TABLE_ARTICLE +
			'(' + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_CONTENT +
			", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ") VALUES(?, ?, ?, ?)";

	public static final String SEND_NEW_TOPIC =  "INSERT INTO " + TABLE_TOPIC +
			'(' + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + ") VALUES(?, ?)";

	public static final String SEND_NEW_USER =  "INSERT INTO " + TABLE_USER +
			'(' + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " +
			COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_DATE_OF_BIRTH + ") VALUES(?, ?, ?, ?, ?, ?)";

	public static final String LOAD_ARTICLE = "SELECT " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_TITLE + ", " +
	COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + " FROM " + TABLE_ARTICLE +
			" WHERE " + COLUMN_ARTICLE_TITLE + " = ?";

	public static final String LOAD_TOPIC = "SELECT " + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_CONTENT + ", "
			+ COLUMN_ARTICLE_PUBLISHER_COMMENT + " FROM " +
	TABLE_ARTICLE + " WHERE " + COLUMN_ARTICLE_TOPIC + " = ?";

	public static final String LOAD_USER = "SELECT " + COLUMN_USER_EMAIL + ", "
			+ COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " FROM " +
			TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";

	//Be careful with the UPDATE commands, if it is left empty it can update all tables and wipe the database.

	public static final String EDIT_ARTICLE = "UPDATE " + TABLE_ARTICLE + " SET " +
			COLUMN_ARTICLE_TITLE +  ", " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_CONTENT + ", "
			+ COLUMN_ARTICLE_PUBLISHER_COMMENT + " = ? WHERE " + COLUMN_ARTICLE_ID + " = ?";

	public static final String EDIT_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_EMAIL +
			", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " = ? WHERE " + COLUMN_USER_ID + " = ?";

	public static final String EDIT_TOPIC = "UPDATE " + TABLE_TOPIC + " SET " +
			COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + " = ? WHERE " + COLUMN_TOPIC_ID + " = ?";

	public static final String LOAD_RECENT_ARTICLE = "SELECT * FROM " + TABLE_ARTICLE +
			" ORDER BY " + COLUMN_ARTICLE_CREATION_DATE + " DESC LIMIT 1";


	Connection con;

	private PreparedStatement DBSendNewArticle;
	private PreparedStatement DBSendNewTopic;
	private PreparedStatement DBSendNewUser;
	private PreparedStatement DBLoadArticle;
	private PreparedStatement DBLoadTopic;
	private PreparedStatement DBLoadUser;
	private PreparedStatement DBEditArticle;
	private PreparedStatement DBEditUser;
	private PreparedStatement DBEditTopic;
	private PreparedStatement DBLoadRecentArticle;

	private static DataController instance = new DataController();

	private DataController() {

	}

	public static DataController getInstance() {
		return instance;
	}

	public boolean open() {
		try {
			con =  SQLConnection.ConnectDB();

			DBSendNewArticle = con.prepareStatement(SEND_NEW_ARTICLE,Statement.RETURN_GENERATED_KEYS);
			DBSendNewTopic = con.prepareStatement(SEND_NEW_TOPIC,Statement.RETURN_GENERATED_KEYS);
			DBSendNewUser = con.prepareStatement(SEND_NEW_USER,Statement.RETURN_GENERATED_KEYS);
			DBLoadArticle = con.prepareStatement(LOAD_ARTICLE);
			DBLoadTopic = con.prepareStatement(LOAD_TOPIC);
			DBLoadUser = con.prepareStatement(LOAD_USER);
			DBEditArticle = con.prepareStatement(EDIT_ARTICLE);
			DBEditUser = con.prepareStatement(EDIT_USER);
			DBEditTopic = con.prepareStatement(EDIT_TOPIC);
			DBLoadRecentArticle = con.prepareStatement(LOAD_RECENT_ARTICLE);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	public void close() {
		try {


			if(DBSendNewArticle != null) {
				DBSendNewArticle.close();
			}

			if(DBSendNewTopic != null) {
				DBSendNewTopic.close();
			}

			if(DBSendNewUser != null) {
				DBSendNewUser.close();
			}

			if(DBLoadArticle !=  null) {
				DBLoadArticle.close();
			}

			if(DBLoadTopic != null) {
				DBLoadTopic.close();
			}

			if(DBLoadUser != null) {
				DBLoadUser.close();
			}

			if(DBEditArticle != null) {
				DBEditArticle.close();
			}

			if(DBEditUser != null) {
				DBEditUser.close();
			}

			if(DBEditTopic != null) {
				DBEditTopic.close();
			}

			if(DBLoadRecentArticle != null) {
				DBLoadRecentArticle.close();
			}



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

	//add methods; delete, sort ?
	//TODO open and close in main, methods to FXML

}