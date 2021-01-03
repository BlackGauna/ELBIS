package model;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.LinkedList;

public class DataController {
    //ATTRIBUTES--------------------------------------------------------------------------------------------------------
    // TODO Sort everything according to the database
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
    //allowed_topics
    public static final String TABLE_ALLOWED_TOPICS = "allowed_topics";
    public static final String COLUMN_ALLOWED_TOPICS_ENTRY = "id";
    public static final String COLUMN_ALLOWED_TOPICS_USER_ID = "userId";
    public static final String COLUMN_ALLOWED_TOPICS_TOPIC_ID = "topicId";
    public static final int INDEX_ALLOWED_TOPICS_ENTRY = 1;
    public static final int INDEX_ALLOWED_TOPICS_USER_ID = 2;
    public static final int INDEX_ALLOWED_TOPICS_TOPIC_ID = 3;
    // More ordering types?
    public static final int ORDER_BY_ASCENDING = 1;
    public static final int ORDER_BY_DESCENDING = 2;

    //QUERIES-----------------------------------------------------------------------------------------------------------

    //TODO Sort the queries

    //Create new Article
    public static final String SEND_NEW_ARTICLE = "INSERT INTO " + TABLE_ARTICLE +
            '(' + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + ", " + COLUMN_ARTICLE_STATUS + ", "
            + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_AUTHOR_ID + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ") "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
    //Create new Topic
    public static final String SEND_NEW_TOPIC = "INSERT INTO " + TABLE_TOPIC +
            '(' + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + ") VALUES(?, ?)";
    //Create new User
    public static final String SEND_NEW_USER = "INSERT INTO " + TABLE_USER +
            '(' + COLUMN_USER_EMAIL + ", " + COLUMN_USER_PASSWORD + ", " + COLUMN_USER_ROLE + ", " + COLUMN_USER_NAME + ", " +
            COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER + ", " + COLUMN_USER_DATE_OF_BIRTH + ") VALUES(?, ?, ?, ?, ?, ?, ?)";
    //Load a specific Article with ID
    public static final String LOAD_ARTICLE = "SELECT " + COLUMN_ARTICLE_TITLE + ", " + COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_CREATION_DATE +  ", " + COLUMN_ARTICLE_EXPIRE_DATE + ", "
            + COLUMN_ARTICLE_LAST_EDIT + ", " + COLUMN_ARTICLE_STATUS + ", " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_AUTHOR_ID + ", " + COLUMN_ARTICLE_PUBLISHER_ID + ", "
            + COLUMN_ARTICLE_PUBLISHER_COMMENT + " FROM " + TABLE_ARTICLE +
            " WHERE " + COLUMN_ARTICLE_ID + " = ?";
    //Load a specific Topic with ID
    public static final String LOAD_TOPIC = "SELECT " + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + " FROM " +
            TABLE_TOPIC + " WHERE " + COLUMN_TOPIC_ID + " = ?";
    //Load a specific User with ID
    public static final String LOAD_USER_BY_ID = "SELECT " + COLUMN_USER_ROLE + ", " + COLUMN_USER_EMAIL + ", "
            + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
            ", " + COLUMN_USER_DATE_OF_BIRTH + ", " + COLUMN_USER_PASSWORD + " FROM " +
            TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    //Load a specific User with Email
    public static final String LOAD_USER_BY_EMAIL = "SELECT " + COLUMN_USER_ROLE + ", " + COLUMN_USER_ID + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
            ", " + COLUMN_USER_DATE_OF_BIRTH + ", " + COLUMN_USER_PASSWORD + " FROM " +
            TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";
    //Edit a specific Article with ID
    public static final String EDIT_ARTICLE = "UPDATE " + TABLE_ARTICLE + " SET " +
              COLUMN_ARTICLE_TITLE + " = ?, " + COLUMN_ARTICLE_CONTENT + " = ?, " + COLUMN_ARTICLE_EXPIRE_DATE + " = ?, "
            + COLUMN_ARTICLE_STATUS + " = ?, " + COLUMN_ARTICLE_TOPIC + " = ?, " + COLUMN_ARTICLE_AUTHOR_ID + " = ?, "
            + COLUMN_ARTICLE_PUBLISHER_ID + " = ?, " + COLUMN_ARTICLE_PUBLISHER_COMMENT + " = ? WHERE " + COLUMN_ARTICLE_ID + " = ?";
    //Be careful with the UPDATE commands, if it is left empty it can update all tables and wipe the database.
    //Edit a specific User with ID
    public static final String EDIT_USER = "UPDATE " + TABLE_USER + " SET " + COLUMN_USER_EMAIL +
            " = ?, " + COLUMN_USER_PASSWORD + " = ?, " + COLUMN_USER_NAME + " = ?, " + COLUMN_USER_ADDRESS + " = ?, " + COLUMN_USER_GENDER +
            " = ?, " + COLUMN_USER_DATE_OF_BIRTH + " = ? WHERE " + COLUMN_USER_ID + " = ?";
    //Edit a specific Topic with ID
    public static final String EDIT_TOPIC = "UPDATE " + TABLE_TOPIC + " SET " +
            COLUMN_TOPIC_NAME + "= ?, " + COLUMN_TOPIC_PARENT_ID + " = ? WHERE " + COLUMN_TOPIC_ID + " = ?";
    //Load the 20 most recent Articles in a descending order.
    public static final String LOAD_RECENT_ARTICLE = "SELECT " + COLUMN_ARTICLE_TOPIC + ", " + COLUMN_ARTICLE_TITLE + ", " +
            COLUMN_ARTICLE_CONTENT + ", " + COLUMN_ARTICLE_PUBLISHER_COMMENT + ", " + COLUMN_ARTICLE_EXPIRE_DATE + " FROM " + TABLE_ARTICLE +
            " ORDER BY " + COLUMN_ARTICLE_CREATION_DATE + " DESC LIMIT 20 ";
    //Load the last article
    public static final String LOAD_LAST_ARTICLE_ID = "SELECT " + COLUMN_ARTICLE_ID + " FROM " + TABLE_ARTICLE +
            " ORDER BY " + COLUMN_ARTICLE_ID + " DESC LIMIT 1 ";
    //Check if user exists in database
    public static final String CHECK_USER = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
    //Load all articles
    public static final String LOAD_ALL_ARTICLES = "SELECT a.id, a.title, (SELECT datetime(a.creationDate, 'localtime') FROM Article), "
            + "(SELECT datetime(a.expireDate, 'localtime') FROM Article), (SELECT datetime(a.lastEdit, 'localtime') FROM Article), s.name, "
            + "t.id, t.name, t.parentTopic, u1.email as author, u2.email as publisher, a.publisherComment "
            + "FROM Article a "
            + "JOIN Status s ON a.status = s.id "
            + "JOIN Topic t ON a.topic = t.id "
            + "JOIN User u1 ON a.authorId = u1.id "
            + "LEFT JOIN User u2 ON a.publisherId = u2.id";
    //Load own articles by ID
    public static final String LOAD_OWN_ARTICLES_BY_ID = "SELECT a.id, a.title, (SELECT datetime(a.creationDate, 'localtime') FROM Article), "
            + "(SELECT datetime(a.expireDate, 'localtime') FROM Article), (SELECT datetime(a.lastEdit, 'localtime') FROM Article), s.name, "
            + "t.id, t.name, t.parentTopic, u1.email as author, u2.email as publisher, a.publisherComment "
            + "FROM Article a "
            + "JOIN Status s ON a.status = s.id "
            + "JOIN Topic t ON a.topic = t.id "
            + "JOIN User u1 ON a.authorId = u1.id "
            + "LEFT JOIN User u2 ON a.publisherId = u2.id "
            + "WHERE " + COLUMN_ARTICLE_AUTHOR_ID + " = ";
    //Load own articles by Email
    public static final String LOAD_OWN_ARTICLES_BY_EMAIL = "SELECT * FROM " + TABLE_ARTICLE + " INNER JOIN " + TABLE_USER + " ON " + COLUMN_ARTICLE_PUBLISHER_ID + " = " + COLUMN_USER_ID + " WHERE " + COLUMN_USER_EMAIL + " = ";
    //Load all articles which are in submitted state
    public static final String LOAD_ALL_SUBMITTED_ARTICLES = "SELECT a.id, a.title, (SELECT datetime(a.creationDate, 'localtime') FROM Article), "
            + "(SELECT datetime(a.expireDate, 'localtime') FROM Article), (SELECT datetime(a.lastEdit, 'localtime') FROM Article), s.name, "
            + "t.id, t.name, t.parentTopic, u1.email as author, u2.email as publisher, a.publisherComment "
            + "FROM Article a "
            + "JOIN Status s ON a.status = s.id "
            + "JOIN Topic t ON a.topic = t.id "
            + "JOIN User u1 ON a.authorId = u1.id "
            + "LEFT JOIN User u2 ON a.publisherId = u2.id "
            + "WHERE a.status = 2";
    //Load all users
    public static final String LOAD_ALL_USERS = "SELECT r.id, u.id, u.email, u.name, g.name, u.address, u.dateOfBirth "
            + "FROM user u "
            + "JOIN role r on u.role = r.id "
            + "LEFT JOIN gender g on u.gender = g.id";
    //Load all topics
    public static final String LOAD_ALL_TOPICS = "SELECT t2.id, t2.name, t1.name from Topic t1 "
            + "JOIN Topic t2 on t1.id=t2.parentTopic";
    //Add an allowed topic to a user by ID
    public static final String ADD_ALLOWED_TOPICS_BY_ID = "INSERT INTO " + TABLE_ALLOWED_TOPICS +
            '(' + COLUMN_ALLOWED_TOPICS_USER_ID + ", " + COLUMN_ALLOWED_TOPICS_TOPIC_ID + ") VALUES(?, ?)";
    //Delete an allowed topic from a user by ID
    public static final String DELETE_ALLOWED_TOPICS_BY_ID = "DELETE FROM " + TABLE_ALLOWED_TOPICS + " WHERE " + COLUMN_ALLOWED_TOPICS_USER_ID + " = ? " + COLUMN_ALLOWED_TOPICS_TOPIC_ID + " = ?";
    //Load Allowed topics for a user by ID
    //TODO Check if Load works
    public static final String LOAD_ALLOWED_TOPICS_BY_ID = "SELECT " + COLUMN_TOPIC_ID + ", " + COLUMN_TOPIC_NAME + ", " + COLUMN_TOPIC_PARENT_ID + " FROM " + TABLE_TOPIC + " INNER JOIN " + TABLE_ALLOWED_TOPICS + " ON " + COLUMN_TOPIC_ID + " = " + COLUMN_ALLOWED_TOPICS_TOPIC_ID + " WHERE " + COLUMN_ALLOWED_TOPICS_USER_ID + " = ?";
    //Delete an Article by ID
    public static final String DELETE_ARTICLE_BY_ID = "DELETE FROM " + TABLE_ARTICLE + " WHERE " + COLUMN_ARTICLE_ID + " = ?";
    //Delete a User by ID
    public static final String DELETE_USER_BY_ID = "DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    //Delete a Topic by ID
    public static final String DELETE_TOPIC_BY_ID = "DELETE FROM " + TABLE_TOPIC + " WHERE " + COLUMN_TOPIC_ID + " = ?";
    //Update database to archive timed out articles
    //TODO update doesnt work properly
    public static final String UPDATE_ARTICLE_LIST = "UPDATE " + TABLE_ARTICLE + " SET " + COLUMN_ARTICLE_STATUS + " = 6 " + "WHERE expireDate <= datetime('localtime')";

    //CONNECTION--------------------------------------------------------------------------------------------------------
    private Connection con;
    private final MainController mainController;

    public DataController(MainController mainController) {
        this.mainController = mainController;
    }

    //METHODS--------------------------------------------------------------------------------------------------------

    // TODO Every time a status or topic is laoded as int, it needs to be loaded as Topic or Status object
    //FINISHED: Check if user exists in DB
    public boolean login(String email, String pw) throws SQLException {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Checking User...");
            PreparedStatement pst = con.prepareStatement(CHECK_USER);
            pst.setString(1, email);
            pst.setString(2, pw);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                mainController.setStatus("Success!");
                con.close();
                return true;
            }
        } catch (SQLException e) {
            mainController.setStatus("Failed to check!");
            e.printStackTrace();
        }
        mainController.setStatus("Something is not right!");
        con.close();
        return false;
    }

    //Status?: Article creation
    public boolean DBSendNewArticle(Article article) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Creating new Article...");
            PreparedStatement pst = con.prepareStatement(SEND_NEW_ARTICLE);

            pst.setString(1, article.getTitle());
            pst.setString(2, article.getContent());
            pst.setString(3, article.getExpireDate());
            pst.setInt(4, article.getStatusID());
            pst.setInt(5, article.getTopicID());
            pst.setInt(6,article.getAuthorID());
            pst.setString(7, article.getPublisherComment());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't create Article!");
                throw new SQLException("Couldn't create Article!");
            } else
                mainController.setStatus("Successfully created!");
            System.out.println("Successfully created!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't create Article!");
            System.out.println("Couldn't create article: " + e.getMessage());
            return false;
        }
    }

    //FINISHED: Topic Creation
    public void DBSendNewTopic(String name, int parentTopic) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Creating new Topic...");
            PreparedStatement pst = con.prepareStatement(SEND_NEW_TOPIC);

            pst.setString(1, name);
            pst.setInt(2, parentTopic);

            pst.execute();
            mainController.setStatus("Successfully created!");
            con.close();
        } catch (SQLException e) {
            mainController.setStatus("Couldn't create Topic!");
            System.out.println("Couldn't create topic: " + e.getMessage());
        }
    }

    //FINISHED: User Creation
    public boolean DBSendNewUser(String email, String password, String name, int gender, int role, String address, String dateOfBirth) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Creating new User...");
            PreparedStatement pst = con.prepareStatement(SEND_NEW_USER);

            pst.setString(1, email);
            pst.setString(2, password);
            pst.setInt(3, role);
            pst.setString(4, name);
            pst.setString(5, address);
            pst.setInt(6, gender);
            pst.setString(7, dateOfBirth);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't create User!");
                throw new SQLException("Couldn't create User!");
            } else
                mainController.setStatus("Successfully created!");
            System.out.println("Successfully created!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't create User!");
            System.out.println("Couldn't create User: " + e.getMessage());
            return false;
        }
    }

    //Status?: Loading an Article by id
    public Article DBLoadArticle(int id) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Article...");
            PreparedStatement pst = con.prepareStatement(LOAD_ARTICLE);
            pst.setInt(1, id);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            Article article = new Article();
            while (rs.next()) {
                article.setId(id);
                article.setTitle(rs.getString(1));
                article.setContent(rs.getString(2));
                article.setCreationDate(rs.getString(3));
                article.setExpireDate(rs.getString(4));
                article.setLastEdit(rs.getString(5));
                article.setStatusByID(rs.getInt(6));
                article.setTopic(DBLoadTopic(rs.getInt(7)));
                article.setAuthor(DBLoadUserById(rs.getInt(8)));
                article.setPublisher(DBLoadUserById(rs.getInt(9)));
                article.setPublisherComment(rs.getString(10));

            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return article;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Article!");
            System.out.println("Couldn't load Article: " + e.getMessage());
            return null;
        }
    }

    //Status?: Loads the last Article
    public Article DBLoadLastArticle() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading last created Article...");
            PreparedStatement pst = con.prepareStatement(LOAD_LAST_ARTICLE_ID);
            ResultSet rs = pst.executeQuery();
            int id = 0;

            while (rs.next()) {
                id = rs.getInt(1);
            }

            Article article = DBLoadArticle(id);
            mainController.setStatus("Successfully loaded!");
            con.close();
            return article;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Article!");
            System.out.println("Couldn't load Article: " + e.getMessage());
            return null;
        }
    }

    //FINISHED: Get ObservableList of all own articles by authorId
    public ObservableList DBLoadOwnArticles(int authorId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Articles...");
            PreparedStatement pst = con.prepareStatement(LOAD_OWN_ARTICLES_BY_ID + authorId);
            ResultSet rs = pst.executeQuery();
            ObservableList<Article> articleList = FXCollections.observableArrayList();

            while (rs.next()) {
                Article temp = new Article();
                temp.setId(rs.getInt(1));
                temp.setTitle(rs.getString(2));
                temp.setCreationDate(rs.getString(3));
                temp.setExpireDate(rs.getString(4));
                temp.setLastEdit(rs.getString(5));
                temp.setStatusByString(rs.getString(6));
                temp.setTopic(new Topic(rs.getInt(7), rs.getString(8), rs.getInt(9)));
                temp.setAuthor(DBLoadUserByEmail(rs.getString(10)));
                temp.setPublisher(DBLoadUserByEmail(rs.getString(11)));
                temp.setPublisherComment(rs.getString(12));
                articleList.add(temp);
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return articleList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Articles!");
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    //Load all submitted articles
    public ObservableList DBLoadAllSubmittedArticles() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Articles...");
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_SUBMITTED_ARTICLES);
            ResultSet rs = pst.executeQuery();
            ObservableList<Article> articleList = FXCollections.observableArrayList();

            while (rs.next()) {
                Article temp = new Article();
                temp.setId(rs.getInt(1));
                temp.setTitle(rs.getString(2));
                temp.setCreationDate(rs.getString(3));
                temp.setExpireDate(rs.getString(4));
                temp.setLastEdit(rs.getString(5));
                temp.setStatusByString(rs.getString(6));
                temp.setTopic(new Topic(rs.getInt(7), rs.getString(8), rs.getInt(9)));
                temp.setAuthor(DBLoadUserByEmail(rs.getString(10)));
                temp.setPublisher(DBLoadUserByEmail(rs.getString(11)));
                temp.setPublisherComment(rs.getString(12));
                articleList.add(temp);
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return articleList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Articles!");
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    //FINISHED: Get ObservableList of all articles (needed for moderationTable)
    public ObservableList DBLoadAllArticles() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading all Articles...");
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_ARTICLES);
            ResultSet rs = pst.executeQuery();
            ObservableList<Article> articleList = FXCollections.observableArrayList();

            while (rs.next()) {
                Article temp = new Article();
                temp.setId(rs.getInt(1));
                temp.setTitle(rs.getString(2));
                temp.setCreationDate(rs.getString(3));
                temp.setExpireDate(rs.getString(4));
                temp.setLastEdit(rs.getString(5));
                temp.setStatusByString(rs.getString(6));
                temp.setTopic(new Topic(rs.getInt(7), rs.getString(8), rs.getInt(9)));
                temp.setAuthor(DBLoadUserByEmail(rs.getString(10)));
                temp.setPublisher(DBLoadUserByEmail(rs.getString(11)));
                temp.setPublisherComment(rs.getString(12));
                articleList.add(temp);
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return articleList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Articles!");
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }


    //Status?: Load a topic
    public Topic DBLoadTopic(int id) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            //mainController.setStatus("Loading Topic...");
            PreparedStatement pst = con.prepareStatement(LOAD_TOPIC);
            pst.setInt(1, id);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            Topic topic = new Topic();
            while (rs.next()) {

                topic.setId(id);
                topic.setName(rs.getString(1));
                topic.setParentTopic(rs.getInt(2));
                topic.setParent(DBLoadTopic(rs.getInt(2))); //Small test

            }
            //mainController.setStatus("Successfully loaded!");
            con.close();
            return topic;
        } catch (SQLException e) {
            //mainController.setStatus("Couldn't load Topic!");
            System.out.println("Couldn't load Topic: " + e.getMessage());
            return null;
        }

    }

    //FINISHED: Get ObservableList of all topics
    public ObservableList DBLoadAllTopics() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Topics...");
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_TOPICS);
            ResultSet rs = pst.executeQuery();
            ObservableList<Topic> topicList = FXCollections.observableArrayList();

            while (rs.next()) {
                topicList.add(new Topic(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)));
                topicList.get(topicList.size() - 1).setParent(DBLoadTopic(rs.getInt(3))); // small test 2
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return topicList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Topics!");
            System.out.println("Couldn't load Topics: " + e.getMessage());
            return null;
        }
    }

    //FINISHED: Get ObservableList of all users
    public ObservableList DBLoadAllUsers() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Users...");
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_USERS);
            ResultSet rs = pst.executeQuery();
            ObservableList<User> userList = FXCollections.observableArrayList();

            while (rs.next()) {
                int roleId = 0;
                User temp;
                roleId = rs.getInt(1);
                switch(roleId){
                    case 1 -> temp = new Administrator();
                    case 2 -> temp = new Moderator();
                    case 3 -> temp = new User();
                    default -> {
                        temp = new User();
                        mainController.setStatus("Couldn't load user Role - default set");
                    }
                }
                temp.setId(rs.getInt(2));
                temp.setEmail(rs.getString(3));
                temp.setName(rs.getString(4));
                temp.setGender(rs.getString(5));
                temp.setAddress(rs.getString(6));
                temp.setDateOfBirth(rs.getString(7));
                userList.add(temp);
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return userList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Users!");
            System.out.println("Couldn't load Users: " + e.getMessage());
            return null;
        }
    }

    //FINISHED: Load a User with ID (needed for login)
    public User DBLoadUserByEmail(String email) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading User...");
            PreparedStatement pst = con.prepareStatement(LOAD_USER_BY_EMAIL);
            pst.setString(1, email);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            User user;
            String name = null;
            String address = null;
            String dateOfBirth = null;
            String password = null;
            int gender = 0;
            int roleId = 0;
            int id = 0;

            while (rs.next()) {
                roleId = rs.getInt(1);
                id = rs.getInt(2);
                name = rs.getString(4);
                address = rs.getString(5);
                gender = rs.getInt(6);
                dateOfBirth = rs.getString(7);
                password = rs.getString(8);

            }
            switch (roleId) {
                case 1 -> user = new Administrator(id, email, name, address, password, dateOfBirth, gender);
                case 2 -> user = new Moderator(id, email, name, address, password, dateOfBirth, gender);
                case 3 -> user = new User(id, email, name, address, password, dateOfBirth, gender);
                default -> {
                    user = new User();
                    mainController.setStatus("Couldn't load user Role - default set");
                }
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return user;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load User!");
            System.out.println("Couldn't load User: " + e.getMessage());
            return null;
        }


    }

    //Status?: Edit Articles
    public boolean DBEditArticle(int id, String newTitle, String newContent, String newExpireDate, String newLastEdit, int newStatus,
                                 int newTopic, String newPublisherComment) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Editing Article...");
            PreparedStatement pst = con.prepareStatement(EDIT_ARTICLE);
            con.setAutoCommit(false);

            pst.setInt(1, id);
            pst.setString(2, newTitle);
            pst.setString(3, newContent);
            pst.setString(4,newExpireDate);
            pst.setInt(6, newStatus);
            pst.setInt(7, newTopic);
            pst.setString(8, newPublisherComment);


            int affectedRows = pst.executeUpdate();

            if (affectedRows == 1) {
                con.commit();
                mainController.setStatus("Successfully edited!");
                System.out.println("Successfully edited!");
                return true;
            } else {
                mainController.setStatus("Failed to edit!");
                System.out.println("Failed to edit!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
                return false;
            }

        } catch (Exception e) {
            mainController.setStatus("Failed to edit!");
            System.out.println("Article edit exception: " + e.getMessage());
            try {
                mainController.setStatus("Performing rollback");
                System.out.println("Performing rollback");
                con.rollback();
                return false;
            } catch (SQLException e2) {
                mainController.setStatus("Couldn't rollback!");
                System.out.println("Couldn't rollback! " + e2.getMessage());
                return false;
            }
        } finally {
            try {
                mainController.setStatus("Resetting default commit behavior");
                System.out.println("Resetting default commit behavior");
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                mainController.setStatus("Couldn't reset auto-commit!");
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    public boolean DBEditArticle(Article article) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Editing Article...");
            PreparedStatement pst = con.prepareStatement(EDIT_ARTICLE);
            con.setAutoCommit(false);

            pst.setString(1, article.getTitle());
            pst.setString(2, article.getContent());
            pst.setString(3,article.getExpireDate());
            pst.setInt(4, article.getStatusID());
            pst.setInt(5, article.getTopicID());
            pst.setInt(6, article.getAuthorID());
            pst.setInt(7, article.getPublisherID());
            pst.setString(8, article.getPublisherComment());
            pst.setInt(9, article.getId());


            int affectedRows = pst.executeUpdate();

            if (affectedRows == 1) {
                con.commit();
                mainController.setStatus("Successfully edited!");
                System.out.println("Successfully edited!");
                return true;
            } else {
                mainController.setStatus("Failed to edit!");
                System.out.println("Failed to edit!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
                return false;
            }

        } catch (Exception e) {
            mainController.setStatus("Failed to edit!");
            System.out.println("Article edit exception: " + e.getMessage());
            try {
                mainController.setStatus("Performing rollback");
                System.out.println("Performing rollback");
                con.rollback();
                return false;
            } catch (SQLException e2) {
                mainController.setStatus("Couldn't rollback!");
                System.out.println("Couldn't rollback! " + e2.getMessage());
                return false;
            }
        } finally {
            try {
                mainController.setStatus("Resetting default commit behavior");
                System.out.println("Resetting default commit behavior");
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                mainController.setStatus("Couldn't reset auto-commit!");
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    // TODO: DBEditTopic -> edit a topic (name, parentTopic) via UI
    // TODO: DBEditUser -> edit a user (name, email, pw(?), address, gender)


    // Currently unused methods----------------------------------------------------------------------------------------

    //Article creation via title, content, topic, publisherComment
    public boolean DBSendNewArticle(String title, String content, int topic, String publisherComment) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Creating new Article...");
            PreparedStatement pst = con.prepareStatement(SEND_NEW_ARTICLE);
            pst.setString(1, title);
            pst.setString(2, content);
            pst.setInt(3, topic);
            pst.setString(4, publisherComment);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't create Article!");
                throw new SQLException("Couldn't create Article!");
            } else
                System.out.println("Successfully created!");
            mainController.setStatus("Successfully created!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't create Article!");
            System.out.println("Couldn't create Article: " + e.getMessage());
            return false;
        }
    }

    //Get 20 most recent articles
    public LinkedList<Article> DBLoadRecentArticle() {

        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading recent Articles...");
            PreparedStatement pst = con.prepareStatement(LOAD_RECENT_ARTICLE);
            ResultSet rs = pst.executeQuery();

            LinkedList<Article> articles = new LinkedList<>();
            while (rs.next()) {
                Article article = new Article();

                article.setTopicID(rs.getInt(1));
                article.setTitle(rs.getString(2));
                article.setContent(rs.getString(3));
                article.setPublisherComment(rs.getString(4));
                article.setExpireDate(rs.getString(5));
                articles.add(article);
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return articles;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load recent Articles!");
            System.out.println("Couldn't load recent Articles: " + e.getMessage());
            return null;
        }

    }

    //Edit a topic
    public boolean DBEditTopic(int id, String newName, String newParentTopic) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Editing Topic...");
            PreparedStatement pst = con.prepareStatement(EDIT_TOPIC);
            con.setAutoCommit(false);

            pst.setInt(3, id);
            pst.setString(1, newName);
            pst.setString(2, newParentTopic);

            int affectedRecords = pst.executeUpdate();

            if (affectedRecords == 1) {
                con.commit();
                mainController.setStatus("Successfully Edited!");
                System.out.println("Successfully Edited!");
                con.close();
                return true;
            } else {
                mainController.setStatus("Failed to Edit!");
                System.out.println("Failed to Edit!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
                return false;
            }

        } catch (Exception e) {
            mainController.setStatus("Failed to Edit!");
            System.out.println("Topic edit exception: " + e.getMessage());
            try {
                mainController.setStatus("Performing rollback");
                System.out.println("Performing rollback");
                con.rollback();
                return false;
            } catch (SQLException e2) {
                mainController.setStatus("Couldn't rollback!");
                System.out.println("Couldn't rollback! " + e2.getMessage());
                return false;
            }
        } finally {
            try {
                mainController.setStatus("Resetting default commit behavior");
                System.out.println("Resetting default commit behavior");
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                mainController.setStatus("Couldn't reset auto-commit!");
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }

        }
    }

    //Edit User
    public boolean DBEditUser(int id, String newEmail, String newPassword, String newName, String newAddress, int newGender, String newDateOfBirth) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Editing User...");
            PreparedStatement pst = con.prepareStatement(EDIT_USER);
            con.setAutoCommit(false);

            pst.setInt(7, id);
            pst.setString(1, newEmail);
            pst.setString(2, newPassword);
            pst.setString(3, newName);
            pst.setString(4, newAddress);
            pst.setInt(5, newGender);
            pst.setString(6, newDateOfBirth);


            int affectedRows = pst.executeUpdate();

            if (affectedRows == 1) {
                con.commit();
                mainController.setStatus("Successfully edited!");
                System.out.println("Successfully edited!");
                con.close();
                return true;
            } else {
                mainController.setStatus("Failed to edit!");
                System.out.println("Failed to edit!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
                return false;
            }

        } catch (Exception e) {
            mainController.setStatus("Failed to edit!");
            System.out.println("User edit exception: " + e.getMessage());
            try {
                mainController.setStatus("Performing rollback");
                System.out.println("Performing rollback");
                con.rollback();
                return false;
            } catch (SQLException e2) {
                mainController.setStatus("Couldn't rollback!");
                System.out.println("Couldn't rollback! " + e2.getMessage());
                return false;
            }
        } finally {
            try {
                mainController.setStatus("Resetting default commit behavior");
                System.out.println("Resetting default commit behavior");
                con.setAutoCommit(true);
                con.close();
            } catch (SQLException e) {
                mainController.setStatus("Couldn't reset auto-commit!");
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    //Load a User with ID
    public User DBLoadUserById(int id) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading User "+id +" ...");
            PreparedStatement pst = con.prepareStatement(LOAD_USER_BY_ID);
            pst.setInt(1, id);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            User user;
            String name = null;
            String address = null;
            String dateOfBirth = null;
            String email = null;
            String password = null;
            int gender = 0;
            int roleId = 0;


            while (rs.next()) {
                roleId = rs.getInt(1);
                email = rs.getString(2);
                name = rs.getString(3);
                address = rs.getString(4);
                gender = rs.getInt(5);
                dateOfBirth = rs.getString(6);
                password = rs.getString(7);
            }
            switch (roleId) {
                case 1 -> user = new Administrator(id, email, name, address, password, dateOfBirth, gender);
                case 2 -> user = new Moderator(id, email, name, address, password, dateOfBirth, gender);
                case 3 -> user = new User(id, email, name, address, password, dateOfBirth, gender);
                default -> {
                    user = new User();
                    mainController.setStatus("Couldn't load user Role - default set");
                }
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return user;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load User!");
            System.out.println("Couldn't load User: " + e.getMessage());
            return null;
        }
    }



    //ObservableList of all own articles by email
    public ObservableList DBLoadOwnArticles(String userEmail) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading Articles...");
            PreparedStatement pst = con.prepareStatement(LOAD_OWN_ARTICLES_BY_EMAIL + userEmail);
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
            mainController.setStatus("Successfully loaded!");
            con.close();
            return articleList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load Articles!");
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    //Add an allowed topic to a user by ID
    public boolean DBAddAllowedTopic(int userId,int newAllowedTopicId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Adding allowed topic...");
            PreparedStatement pst = con.prepareStatement(ADD_ALLOWED_TOPICS_BY_ID);

            pst.setInt(1, userId);
            pst.setInt(2, newAllowedTopicId);


            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't add allowed Topic!");
                throw new SQLException("Couldn't add allowed Topic!");
            } else
                mainController.setStatus("Successfully added!");
            System.out.println("Successfully added!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't add allowed Topic!");
            System.out.println("Couldn't add allowed Topic: " + e.getMessage());
            return false;
        }
    }

    //Delete an allowed topic from a user by ID
    public boolean DBDeleteAllowedTopic(int userId,int deletedAllowedTopicId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Deleting allowed topic...");
            PreparedStatement pst = con.prepareStatement(DELETE_ALLOWED_TOPICS_BY_ID);

            pst.setInt(1, userId);
            pst.setInt(2, deletedAllowedTopicId);


            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't delete allowed Topic!");
                throw new SQLException("Couldn't delete allowed Topic!");
            } else
                mainController.setStatus("Successfully deleted!");
            System.out.println("Successfully deleted!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't delete allowed Topic!");
            System.out.println("Couldn't delete allowed Topic: " + e.getMessage());
            return false;
        }
    }

    //Load Allowed topics for a user by ID
    public ObservableList DBLoadAllowedTopics(int userId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Loading allowed Topics...");
            PreparedStatement pst = con.prepareStatement(LOAD_ALLOWED_TOPICS_BY_ID);
            pst.setInt(1,userId);
            ResultSet rs = pst.executeQuery();
            ObservableList<Topic> topicList = FXCollections.observableArrayList();

            while (rs.next()) {
                topicList.add(new Topic(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)));
            }
            mainController.setStatus("Successfully loaded!");
            con.close();
            return topicList;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't load allowed Topics!");
            System.out.println("Couldn't load allowed Topics: " + e.getMessage());
            return null;
        }
    }

    //Delete a User by ID
    public boolean DBDeleteUser(int userId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Deleting User...");
            PreparedStatement pst = con.prepareStatement(DELETE_USER_BY_ID);

            pst.setInt(1, userId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't delete User!");
                throw new SQLException("Couldn't delete User!");
            } else
                mainController.setStatus("Successfully deleted!");
            System.out.println("Successfully deleted!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't delete User!");
            System.out.println("Couldn't delete User: " + e.getMessage());
            return false;
        }
    }

    //Delete an Article by ID
    public boolean DBDeleteArticle(int articleId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Deleting Article...");
            PreparedStatement pst = con.prepareStatement(DELETE_ARTICLE_BY_ID);

            pst.setInt(1, articleId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't delete Article!");
                throw new SQLException("Couldn't delete Article!");
            } else
                mainController.setStatus("Successfully deleted!");
            System.out.println("Successfully deleted!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't delete Article!");
            System.out.println("Couldn't delete Article: " + e.getMessage());
            return false;
        }
    }

    //Delete a Topic by ID
    public boolean DBDeleteTopic(int topicId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Deleting Topic...");
            PreparedStatement pst = con.prepareStatement(DELETE_TOPIC_BY_ID);

            pst.setInt(1, topicId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("Couldn't delete Topic!");
                throw new SQLException("Couldn't delete Topic!");
            } else
                mainController.setStatus("Successfully deleted!");
            System.out.println("Successfully deleted!");
            con.close();
            return true;

        } catch (SQLException e) {
            mainController.setStatus("Couldn't delete Topic!");
            System.out.println("Couldn't delete Topic: " + e.getMessage());
            return false;
        }
    }

    //Archive timed out Articles
    public boolean DBUpdateAllArticles() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            mainController.setStatus("Updating Article List...");
            PreparedStatement pst = con.prepareStatement(UPDATE_ARTICLE_LIST);
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                mainController.setStatus("No timed out articles");
                throw new SQLException("Couldn't update!");
            } else
                mainController.setStatus("Successfully updated!");
            System.out.println("Successfully updated!");
            con.close();
            return true;
        } catch (SQLException e) {
            mainController.setStatus("Couldn't update!");
            System.out.println("Couldn't update: " + e.getMessage());
            return false;
        }
    }


}
