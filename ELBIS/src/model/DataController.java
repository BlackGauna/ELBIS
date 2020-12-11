package model;
import javafx.scene.text.Text;

import java.sql.*;

public class DataController {

	//ATTRIBUTES--------------------------------------------------------------------------------------------------------

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
	public static final String COLUMN_ARTICLE_PUBLISHER_ID = "publisherId";
	public static final String COLUMN_ARTICLE_PUBLISHER_COMMENT = "publisherComment";
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



	//QUERIES-----------------------------------------------------------------------------------------------------------

	//How exactly will the creation, expiration, last edit dates set ? plus the other stuff ?
	//The Stuff that we need can be changed anytime to however we want to receive something from the database, do not hesitate to make changes.

	public static final String SEND_NEW_ARTICLE = "INSERT INTO " + TABLE_ARTICLE +
			'(' + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_CONTENT +
			", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ") VALUES(?, ?, ?, ?)";

	public static final String SEND_NEW_TOPIC = "INSERT INTO " + TABLE_TOPIC +
			'(' + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + ") VALUES(?, ?)";

	public static final String SEND_NEW_USER = "INSERT INTO " + TABLE_USER +
			'(' + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " +
			COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_DATE_OF_BIRTH + ") VALUES(?, ?, ?, ?, ?, ?)";

	public static final String LOAD_ARTICLE = "SELECT " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_TITLE + ", " +
			COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + " FROM " + TABLE_ARTICLE +
			" WHERE " + COLUMN_ARTICLE_ID + " = ?";

	public static final String LOAD_TOPIC = "SELECT " + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_CONTENT + ", "
			+ COLUMN_ARTICLE_PUBLISHER_COMMENT + " FROM " +
			TABLE_ARTICLE + " WHERE " + COLUMN_ARTICLE_TOPIC + " = ?";

	public static final String LOAD_USER = "SELECT " + COLUMN_USER_EMAIL + ", "
			+ COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " FROM " +
			TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";

	//Be careful with the UPDATE commands, if it is left empty it can update all tables and wipe the database.

	public static final String EDIT_ARTICLE = "UPDATE " + TABLE_ARTICLE + " SET " +
			COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_CONTENT + ", "
			+ COLUMN_ARTICLE_PUBLISHER_COMMENT + " = ? WHERE " + COLUMN_ARTICLE_ID + " = ?";

	public static final String EDIT_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_EMAIL +
			", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " = ? WHERE " + COLUMN_USER_ID + " = ?";

	public static final String EDIT_TOPIC = "UPDATE " + TABLE_TOPIC + " SET " +
			COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + " = ? WHERE " + COLUMN_TOPIC_ID + " = ?";

	public static final String LOAD_RECENT_ARTICLE = "SELECT * FROM " + TABLE_ARTICLE +
			" ORDER BY " + COLUMN_ARTICLE_CREATION_DATE + " DESC LIMIT 1";


	//CONNECTION--------------------------------------------------------------------------------------------------------

	Connection con;

	private PreparedStatement SendNewArticle;
	private PreparedStatement SendNewTopic;
	private PreparedStatement SendNewUser;
	private PreparedStatement LoadArticle;
	private PreparedStatement LoadTopic;
	private PreparedStatement LoadUser;
	private PreparedStatement EditArticle;
	private PreparedStatement EditUser;
	private PreparedStatement EditTopic;
	private PreparedStatement LoadRecentArticle;


	private static DataController instance = new DataController();

	private DataController() {

	}

	public static DataController getInstance() {
		return instance;
	}

	public boolean open() {
		try {
			con = SQLConnection.ConnectDB();

			SendNewArticle = con.prepareStatement(SEND_NEW_ARTICLE, Statement.RETURN_GENERATED_KEYS);
			SendNewTopic = con.prepareStatement(SEND_NEW_TOPIC, Statement.RETURN_GENERATED_KEYS);
			SendNewUser = con.prepareStatement(SEND_NEW_USER, Statement.RETURN_GENERATED_KEYS);
			LoadArticle = con.prepareStatement(LOAD_ARTICLE);
			LoadTopic = con.prepareStatement(LOAD_TOPIC);
			LoadUser = con.prepareStatement(LOAD_USER);
			EditArticle = con.prepareStatement(EDIT_ARTICLE);
			EditUser = con.prepareStatement(EDIT_USER);
			EditTopic = con.prepareStatement(EDIT_TOPIC);
			LoadRecentArticle = con.prepareStatement(LOAD_RECENT_ARTICLE);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
	}

	public void close() {
		try {


			if (SendNewArticle != null) {
				SendNewArticle.close();
			}

			if (SendNewTopic != null) {
				SendNewTopic.close();
			}

			if (SendNewUser != null) {
				SendNewUser.close();
			}

			if (LoadArticle != null) {
				LoadArticle.close();
			}

			if (LoadTopic != null) {
				LoadTopic.close();
			}

			if (LoadUser != null) {
				LoadUser.close();
			}

			if (EditArticle != null) {
				EditArticle.close();
			}

			if (EditUser != null) {
				EditUser.close();
			}

			if (EditTopic != null) {
				EditTopic.close();
			}

			if (LoadRecentArticle != null) {
				LoadRecentArticle.close();
			}


			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//METHODS--------------------------------------------------------------------------------------------------------

	public boolean DBSendNewArticle(String title, String content, String publisherComment) {

		try {
			SendNewArticle.setString(2, title);
			SendNewArticle.setString(3, content);
			SendNewArticle.setString(11, publisherComment);

			int affectedRows = SendNewArticle.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Couldn't save article!");
			} else
				System.out.println("Successful submit!");

			return true;

		} catch (SQLException e) {
			System.out.println("Couldn't save article: " + e.getMessage());
			return false;
		}
	}

	public boolean DBSendNewTopic(String name) {

		try {
			SendNewTopic.setString(2, name);

			int affectedRows = SendNewTopic.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Couldn't save topic!");
			} else
				System.out.println("Successfully added!");

			return true;

		} catch (SQLException e) {
			System.out.println("Couldn't save topic: " + e.getMessage());
			return false;
		}
	}

	public boolean DBSendNewUser(String email, String password, String name, String address, int gender, String dateOfBirth) {
		try {
			SendNewUser.setString(2, email);
			SendNewUser.setString(3, password);
			SendNewUser.setString(5, name);
			SendNewUser.setString(6, address);
			SendNewUser.setInt(2, gender);
			SendNewUser.setString(2, dateOfBirth); //In database dateOfBirth is saved as TEXT not as date.

			int affectedRows = SendNewUser.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Couldn't save User!");
			} else
				System.out.println("User successfully added!");

			return true;

		} catch (SQLException e) {
			System.out.println("Couldn't save User: " + e.getMessage());
			return false;
		}

	}

	public Article DBLoadArticle(int id) {
		try {
			Article article = LoadArticle.

					//TODO Learn to load the article LoadArticle


			if (article == null) {
				throw new SQLException("Couldn't get Article!");
			} else {
				System.out.println("Article successfully loaded!");
				return article;
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get Article: " + e.getMessage());

		}
	}

	public Topic DBLoadTopic() {
		//TODO LoadTopic


	}

	public User DBLoadUser() {
		//TODO LoadUser

	}

	public boolean DBEditArticle(int id, String newTitle, String newContent, String newPublisherComment) {
		try {
			con.setAutoCommit(false);

			//TODO Learn how to pick the specific article to edit EditArticle

			EditArticle.setString(2, newTitle);
			EditArticle.setString(3, newContent);
			EditArticle.setNString(11, newPublisherComment);
			int affectedRows = EditArticle.executeUpdate();

			if (affectedRows == 1) {
				con.commit();
				System.out.println("Successful!");
				return true;
			} else {
				System.out.println("Failed!");
				con.rollback();
				con.setAutoCommit(true);
				return false;
			}

		} catch (Exception e) {
			System.out.println("Article edit exception: " + e.getMessage());
			try {
				System.out.println("Performing rollback");
				con.rollback();
				return false;
			} catch (SQLException e2) {
				System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
				return false;
			}
		} finally {
			try {
				System.out.println("Resetting default commit behavior");
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Couldn't reset auto-commit! " + e.getMessage());
			}
		}
	}

	public boolean DBEditUser ( int id, String newEmail, String newPassword, String newName, String newAddress, int newGender, String newDateOfBirth) {
		try {
			con.setAutoCommit(false);

			//TODO EditUser

			EditUser.setString(2, newEmail);
			EditUser.setString(3, newPassword);
			EditUser.setString(5, newName);
			EditUser.setString(6, newAddress);
			EditUser.setInt(7, newGender);
			EditUser.setString(8, newDateOfBirth);


			int affectedRows = EditUser.executeUpdate();

			if (affectedRows == 1) {
				con.commit();
				System.out.println("Successful!");
				return true;
			} else {
				System.out.println("Failed!");
				con.rollback();
				con.setAutoCommit(true);
				return false;
			}

		} catch (Exception e) {
			System.out.println("User edit exception: " + e.getMessage());
			try {
				System.out.println("Performing rollback");
				con.rollback();
				return false;
			} catch (SQLException e2) {
				System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
				return false;
			}
		} finally {
			try {
				System.out.println("Resetting default commit behavior");
				con.setAutoCommit(true);
			} catch (SQLException e) {
				System.out.println("Couldn't reset auto-commit! " + e.getMessage());
			}
		}
	}

	public boolean DBEditTopic (int id,String newName,String newParentTopic) {
			try {
				con.setAutoCommit(false);

				//TODO EditTopic

				EditTopic.setString(2, newName);
				EditArticle.setString(3, newParentTopic);

				int affectedRecords = EditTopic.executeUpdate();

				if (affectedRecords == 1) {
					con.commit();
					System.out.println("Successful!");
					return true;
				} else {
					System.out.println("Failed!");
					con.rollback();
					con.setAutoCommit(true);
					return false;
				}

			} catch (Exception e) {
				System.out.println("Topic edit exception: " + e.getMessage());
				try {
					System.out.println("Performing rollback");
					con.rollback();
					return false;
				} catch (SQLException e2) {
					System.out.println("Oh boy! Things are really bad! " + e2.getMessage());
					return false;
				}
			} finally {
				try {
					System.out.println("Resetting default commit behavior");
					con.setAutoCommit(true);
				} catch (SQLException e) {
					System.out.println("Couldn't reset auto-commit! " + e.getMessage());
				}

			}
	}

	public LinkedList<Article> DBLoadRecentArticle () {
			//dbrequest:String

		//TODO LoadRecentArticle

	}

	//add methods; delete, sort ?
	//there is no method to view all the articles in an order ?
	//there is no way to browse topics ?
	//TODO open and close in main, methods to FXML

}