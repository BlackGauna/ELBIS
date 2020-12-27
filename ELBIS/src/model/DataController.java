package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.LinkedList;

public class DataController {
	//ATTRIBUTES--------------------------------------------------------------------------------------------------------

	// Article
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

	// Gender
	public static final String TABLE_GENDER = "Gender";
	public static final String COLUMN_GENDER_ID = "id";
	public static final String COLUMN_GENDER_NAME = "name";
	public static final int INDEX_GENDER_ID = 1;
	public static final int INDEX_GENDER_NAME = 2;

	// Role
	public static final String TABLE_ROLE = "Role";
	public static final String COLUMN_ROLE_ID = "id";
	public static final String COLUMN_ROLE_NAME = "name";
	public static final int INDEX_ROLE_ID = 1;
	public static final int INDEX_ROLE_NAME = 2;

	// Status
	public static final String TABLE_STATUS = "Status";
	public static final String COLUMN_STATUS_ID = "id";
	public static final String COLUMN_STATUS_NAME = "name";
	public static final int INDEX_STATUS_ID = 1;
	public static final int INDEX_STATUS_NAME = 2;

	// Topic
	public static final String TABLE_TOPIC = "Topic";
	public static final String COLUMN_TOPIC_ID = "id";
	public static final String COLUMN_TOPIC_NAME = "name";
	public static final String COLUMN_TOPIC_PARENT_ID = "parentTopic";
	public static final int INDEX_TOPIC_ID = 1;
	public static final int INDEX_TOPIC_NAME = 2;
	public static final int INDEX_TOPIC_PARENT_ID = 3;

	// User
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


	//TODO Discuss what queries we exactly might need to adapt them.

	//How exactly will the creation, expiration, last edit dates set ? plus the other stuff ?

	//The Stuff that we need can be changed anytime to however we want to receive something from the database, do not hesitate to make changes.

	//Create new Article
	public static final String SEND_NEW_ARTICLE = "INSERT INTO " + TABLE_ARTICLE +
			'(' + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_TOPIC +
			", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ") VALUES(?, ?, ?, ?)";

	//Create new Topic
	public static final String SEND_NEW_TOPIC = "INSERT INTO " + TABLE_TOPIC +
			'(' + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + ") VALUES(?, ?)";

	//Create new User
	public static final String SEND_NEW_USER = "INSERT INTO " + TABLE_USER +
			'(' + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_NAME + ", " +
			COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_DATE_OF_BIRTH + ") VALUES(?, ?, ?, ?, ?, ?)";


	//Load a specific Article with ID
	public static final String LOAD_ARTICLE = "SELECT " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_TITLE + ", " +
			COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + " FROM " + TABLE_ARTICLE +
			" WHERE " + COLUMN_ARTICLE_ID + " = ?";

	//Load a specific Topic with ID
	public static final String LOAD_TOPIC = "SELECT " + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + " FROM " +
			TABLE_TOPIC + " WHERE " + COLUMN_TOPIC_ID + " = ?";

	//Load a specific User with ID
	public static final String LOAD_USER_BY_ID = "SELECT " + COLUMN_USER_EMAIL + ", "
			+ COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " FROM " +
			TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

	//Load a specific User with Email
	public static final String LOAD_USER_BY_EMAIL = "SELECT " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
			", " + COLUMN_USER_DATE_OF_BIRTH + " FROM " +
			TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";

	//Be careful with the UPDATE commands, if it is left empty it can update all tables and wipe the database.

	//Edit a specific Article with ID
	public static final String EDIT_ARTICLE = "UPDATE " + TABLE_ARTICLE + " SET " +
			COLUMN_ARTICLE_TITLE + " = ?, " + COLUMN_ARTICLE_TOPIC + " = ?, " + COLUMN_ARTICLE_CONTENT + " = ?, "
			+ COLUMN_ARTICLE_PUBLISHER_COMMENT + " = ? WHERE " + COLUMN_ARTICLE_ID + " = ?";

	//Edit a specific User with ID
	public static final String EDIT_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_EMAIL +
			" = ?, " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_ADDRESS + " = ?, " + COLUMN_USER_GENDER +
			" = ?, " + COLUMN_USER_DATE_OF_BIRTH + " = ? WHERE " + COLUMN_USER_ID + " = ?";

	//Edit a specific Topic with ID
	public static final String EDIT_TOPIC = "UPDATE " + TABLE_TOPIC + " SET " +
			COLUMN_TOPIC_NAME + "= ?, " + COLUMN_TOPIC_PARENT_ID + " = ? WHERE " + COLUMN_TOPIC_ID + " = ?";

	//Load the 20 most recent Articles in a descending order.
	public static final String LOAD_RECENT_ARTICLE = "SELECT "  + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_TITLE + ", " +
			COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + " FROM " + TABLE_ARTICLE +
			" ORDER BY " + COLUMN_ARTICLE_CREATION_DATE + " DESC LIMIT 20 ";

	public static final String LOAD_LAST_ARTICLE_ID = "SELECT "  + COLUMN_ARTICLE_ID + " FROM " + TABLE_ARTICLE +
			" ORDER BY " + COLUMN_ARTICLE_ID + " DESC LIMIT 1 ";

	// Check if user exists in database
	public static final String CHECK_USER = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";

	// Load all articles
	public static final String LOAD_ALL_ARTICLES = "SELECT * FROM " + TABLE_ARTICLE;

	// Load all users
	public static final String LOAD_ALL_USERS = "SELECT * FROM " + TABLE_USER;

	// Load all topics
	public static final String LOAD_ALL_TOPICS = "SELECT * FROM " + TABLE_TOPIC;


	//CONNECTION--------------------------------------------------------------------------------------------------------

	private Connection con;

	private PreparedStatement SendNewArticle;
	private PreparedStatement SendNewTopic;
	private PreparedStatement SendNewUser;
	private PreparedStatement LoadArticle;
	private PreparedStatement LoadLastArticle;
	private PreparedStatement LoadTopic;
	private PreparedStatement LoadUserById;
	private PreparedStatement LoadUserByEmail;
	private PreparedStatement EditArticle;
	private PreparedStatement EditUser;
	private PreparedStatement EditTopic;
	private PreparedStatement LoadRecentArticle;
	private PreparedStatement LoadAllArticles;
	private PreparedStatement LoadAllUsers;
	private PreparedStatement LoadAllTopics;


	private static DataController instance = new DataController();
	//private, public for testing
	public DataController() {

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
			LoadLastArticle = con.prepareStatement(LOAD_LAST_ARTICLE_ID);
			LoadTopic = con.prepareStatement(LOAD_TOPIC);
			LoadUserById = con.prepareStatement(LOAD_USER_BY_ID);
			LoadUserByEmail = con.prepareStatement(LOAD_USER_BY_EMAIL);
			EditArticle = con.prepareStatement(EDIT_ARTICLE);
			EditUser = con.prepareStatement(EDIT_USER);
			EditTopic = con.prepareStatement(EDIT_TOPIC);
			LoadRecentArticle = con.prepareStatement(LOAD_RECENT_ARTICLE); //Statement fails to load because of no topic error on method
			LoadAllArticles = con.prepareStatement(LOAD_ALL_ARTICLES);
			LoadAllUsers = con.prepareStatement(LOAD_ALL_USERS);
			LoadAllTopics = con.prepareStatement(LOAD_ALL_TOPICS);

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

			if (LoadLastArticle != null) {
				LoadLastArticle.close();
			}

			if (LoadTopic != null) {
				LoadTopic.close();
			}

			if (LoadUserById != null) {
				LoadUserById.close();
			}

			if (LoadUserByEmail != null) {
				LoadUserByEmail.close();
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

			if (LoadAllArticles != null){
				LoadAllArticles.close();
			}

			if (LoadAllUsers != null){
				LoadAllUsers.close();
			}

			if (LoadAllTopics != null){
				LoadAllTopics.close();
			}


			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//METHODS--------------------------------------------------------------------------------------------------------


	//Article creation
	public boolean DBSendNewArticle(String title, String content, int topic, String publisherComment) {
		try {
			SendNewArticle.setString(1, title);
			SendNewArticle.setString(2, content);
			SendNewArticle.setInt(3, topic);
			SendNewArticle.setString(4, publisherComment);

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

	public boolean DBSendNewArticle(Article article) {
		try {
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(SEND_NEW_ARTICLE);

			pst.setString(1, article.getTitle());
			pst.setString(2, article.getContent());
			pst.setInt(3, article.getTopic());
			pst.setString(4, article.getPublisherComment());

			int affectedRows = pst.executeUpdate();

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

	//Topic Creation
	public boolean DBSendNewTopic(String name,int parentTopic) {

		try {
			SendNewTopic.setString(1, name);
			SendNewTopic.setInt(2,parentTopic);

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

	// Create Topic via UI
	public void DBCreateNewTopic(String name, String parentTopic){
		try{
			Connection con = SQLConnection.ConnectDB();
			String sql = "INSERT INTO Topic (name, parentTopic) VALUES (?,?)";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, name);
			pst.setString(2, parentTopic);

			pst.execute();
		} catch (SQLException e) {
			System.out.println("Couldn't create topic: " + e.getMessage());
		}
	}

	//User Creation
	public boolean DBSendNewUser(String email, String password, String name, String address, int gender, String dateOfBirth) {
		try {
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(SEND_NEW_USER);
			pst.setString(1, email);
			pst.setString(2, password);
			pst.setString(3, name);
			pst.setString(4, address);
			pst.setInt(5, gender);
			pst.setString(6, dateOfBirth);

			int affectedRows = pst.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Couldn't save User!");
			} else
				System.out.println("User successfully added!");
			con.close();
			return true;

		} catch (SQLException e) {
			System.out.println("Couldn't save User: " + e.getMessage());
			return false;
		}

	}

	//Loading an Article
	public Article DBLoadArticle(int id) {
		try {
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(LOAD_ARTICLE);
			pst.setInt(1, id);
			pst.execute();
			ResultSet rs = pst.getResultSet();
			Article article = new Article();
			while (rs.next()) {
				article.setId(id);
				article.setTopic(rs.getInt(1));
				article.setTitle(rs.getString(2));
				article.setContent(rs.getString(3));
				article.setPublisherComment(rs.getString(4));
				article.setExpireDate(rs.getString(5));
			}
			return article;
		} catch (SQLException e) {
			System.out.println("Couldn't load Article: " + e.getMessage());
			return null;
		}

	}

	public Article DBLoadLastArticle()
	{
		try
		{
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(LOAD_LAST_ARTICLE_ID);
			ResultSet rs = pst.executeQuery();
			int id=0;

			while (rs.next())
			{
				id = rs.getInt(1);
			}

			Article article = DBLoadArticle(id);

			return article;
		} catch (SQLException e)
		{
		System.out.println("Couldn't load Article: " + e.getMessage());
		return null;
		}
	}

	// Get ObservableList of all articles
	public ObservableList DBLoadAllArticles(){
		try{
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(LOAD_ALL_ARTICLES);
			ResultSet rs = pst.executeQuery();
			ObservableList<Article> articleList = FXCollections.observableArrayList();

			while (rs.next()) {
				articleList.add(new Article(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(8),
						rs.getString(11)));
			}
			return articleList;
		} catch (SQLException e) {
			System.out.println("Couldn't load Articles: " + e.getMessage());
			return null;
		}
	}

	// Get ObservableList of all users
	public ObservableList DBLoadAllUsers(){
		try{
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(LOAD_ALL_USERS);
			ResultSet rs = pst.executeQuery();
			ObservableList<User> userList = FXCollections.observableArrayList();

			while (rs.next()){
				userList.add(new User(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(5),
						rs.getString(6)));
			}
			return userList;
		} catch (SQLException e) {
			System.out.println("Couldn't load Users: " + e.getMessage());
			return null;
		}
	}

	// Load all topics
	public ObservableList DBLoadAllTopics(){
		try{
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(LOAD_ALL_TOPICS);
			ResultSet rs = pst.executeQuery();
			ObservableList<Topic> topicList = FXCollections.observableArrayList();

			while (rs.next()){
				topicList.add(new Topic(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3)));
			}
			return topicList;
		} catch (SQLException e) {
			System.out.println("Couldn't load Topics: " + e.getMessage());
			return null;
		}
	}

	//Load a topic
	public Topic DBLoadTopic(int id) {
		try {
			LoadTopic.setInt(1, id);
			LoadTopic.execute();
			ResultSet rs = LoadTopic.getResultSet();
			Topic topic = new Topic();
			while (rs.next()) {

				topic.setId(id);
				topic.setName(rs.getString(1));
				topic.setParentTopic(rs.getInt(2));

			}
			return topic;
		} catch (SQLException e) {
			System.out.println("Couldn't load Topic: " + e.getMessage());
			return null;
		}

	}

	//Load a User with ID
	public User DBLoadUserById(int id) {
		try {
			LoadUserById.setInt(1, id);
			LoadUserById.execute();
			ResultSet rs = LoadUserById.getResultSet();
			User user = new User();
			while (rs.next()) {
				//user.setId(id); //user ID cant be set outside of constructors.
				user.seteMail(rs.getString(1));
				user.setName(rs.getString(2));
				user.setAddress(rs.getString(3));
				user.setGender(rs.getInt(4));
				user.setDateOfBirth(rs.getString(5));
			}
			return user;
		} catch (SQLException e) {
			System.out.println("Couldn't load User: " + e.getMessage());
			return null;
		}

	}

	//Load a User with ID
	public User DBLoadUserByEmail(String email) {
		try {
			LoadUserByEmail.setString(1, email);
			LoadUserByEmail.execute();
			ResultSet rs = LoadUserByEmail.getResultSet();
			User user = new User();
			while (rs.next()) {
				//user.setId(id); //user ID cant be set outside of constructors.
				user.seteMail(rs.getString(1));
				user.setName(rs.getString(2));
				user.setAddress(rs.getString(3));
				user.setGender(rs.getInt(4));
				user.setDateOfBirth(rs.getString(5));
			}
			return user;
		} catch (SQLException e) {
			System.out.println("Couldn't load User: " + e.getMessage());
			return null;
		}


	}

	//Edit Articles
	public boolean DBEditArticle(int id, String newTitle,int newTopic, String newContent, String newPublisherComment) {
		try {
			con = SQLConnection.ConnectDB();
			PreparedStatement pst = con.prepareStatement(EDIT_ARTICLE);
			con.setAutoCommit(false);

			pst.setInt(5, id);
			pst.setString(1, newTitle);
			pst.setInt(2, newTopic);
			pst.setString(3, newContent);
			pst.setString(4, newPublisherComment);
			int affectedRows = pst.executeUpdate();

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

	//Edit User
	public boolean DBEditUser(int id, String newEmail, String newPassword, String newName, String newAddress, int newGender, String newDateOfBirth) {
		try {
			con.setAutoCommit(false);

			EditUser.setInt(7, id);
			EditUser.setString(1, newEmail);
			EditUser.setString(2, newPassword);
			EditUser.setString(3, newName);
			EditUser.setString(4, newAddress);
			EditUser.setInt(5, newGender);
			EditUser.setString(6, newDateOfBirth);


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

	//Edit a topic
	public boolean DBEditTopic(int id, String newName, String newParentTopic) {
		try {
			con.setAutoCommit(false);

			EditTopic.setInt(3, id);
			EditTopic.setString(1, newName);
			EditArticle.setString(2, newParentTopic);

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

	//Get 20 most recent articles
	public LinkedList<Article> DBLoadRecentArticle() {

		try {
			ResultSet rs = LoadRecentArticle.executeQuery();

			LinkedList<Article> articles = new LinkedList<>();
			while (rs.next()) {
				Article article = new Article();

				article.setTopic(rs.getInt(1));
				article.setTitle(rs.getString(2));
				article.setContent(rs.getString(3));
				article.setPublisherComment(rs.getString(4));  //Article Class must be initialized
				article.setExpireDate(rs.getString(5));
				articles.add(article);
			}

			return articles;
		} catch (SQLException e) {
			System.out.println("Getting recent articles failed: " + e.getMessage());
			return null;
		}

	}

	// Check if user exists in DB
	public boolean login(String email, String pw) throws SQLException {
		try (Connection con = SQLConnection.ConnectDB();
			 PreparedStatement pst = con.prepareStatement(CHECK_USER)) {
			pst.setString(1, email);
			pst.setString(2, pw);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//there is no method to view all the articles in an order ?
	//there is no way to browse topics ?
	//TODO open and close in main, methods to FXML

}