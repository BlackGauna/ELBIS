package model;

import controller.MainController;
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
    public static final String LOAD_USER_BY_ID = "SELECT " + COLUMN_USER_ROLE + ", " + COLUMN_USER_EMAIL + ", "
            + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
            ", " + COLUMN_USER_DATE_OF_BIRTH +  ", " + COLUMN_USER_PASSWORD + " FROM " +
            TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";
    //Load a specific User with Email
    public static final String LOAD_USER_BY_EMAIL = "SELECT " + COLUMN_USER_ROLE + ", " + COLUMN_USER_ID + ", " + COLUMN_USER_EMAIL + ", " + COLUMN_USER_NAME + ", " + COLUMN_USER_ADDRESS + ", " + COLUMN_USER_GENDER +
            ", " + COLUMN_USER_DATE_OF_BIRTH +  ", " + COLUMN_USER_PASSWORD + " FROM " +
            TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = ?";
    //Edit a specific Article with ID
    public static final String EDIT_ARTICLE = "UPDATE " + TABLE_ARTICLE + " SET " +
            COLUMN_ARTICLE_TITLE + " = ?, " + COLUMN_ARTICLE_TOPIC + " = ?, " + COLUMN_ARTICLE_CONTENT + " = ?, "
            + COLUMN_ARTICLE_PUBLISHER_COMMENT + " = ? WHERE " + COLUMN_ARTICLE_ID + " = ?";
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
    public static final String LOAD_ALL_ARTICLES = "SELECT * FROM " + TABLE_ARTICLE;
    //Load own articles by ID
    public static final String LOAD_OWN_ARTICLES_BY_ID = "SELECT * FROM " + TABLE_ARTICLE + " WHERE " + COLUMN_ARTICLE_AUTHOR_ID + " = ?";
    //Load own articles by Email
    public static final String LOAD_OWN_ARTICLES_BY_EMAIL = "SELECT * FROM " + TABLE_ARTICLE + " INNER JOIN " + TABLE_USER + " ON " + COLUMN_ARTICLE_PUBLISHER_ID + " = " + COLUMN_USER_ID + " WHERE " + COLUMN_USER_EMAIL + " = ?";
    //Load all articles which are in submitted state
    public static final String LOAD_ALL_SUBMITTED_ARTICLES = "SELECT * FROM " + TABLE_ARTICLE + " WHERE " + COLUMN_ARTICLE_STATUS + " = 2";
    //Load all users
    public static final String LOAD_ALL_USERS = "SELECT * FROM " + TABLE_USER;
    //Load all topics
    public static final String LOAD_ALL_TOPICS = "SELECT * FROM " + TABLE_TOPIC;


    //CONNECTION--------------------------------------------------------------------------------------------------------
    private Connection con;
    private final MainController mainController;
    public DataController(MainController mainController) {
        this.mainController = mainController;
    }

    //METHODS--------------------------------------------------------------------------------------------------------

//TODO SET STATUSES FOR METHODS

    //Article creation
    public boolean DBSendNewArticle(String title, String content, int topic, String publisherComment) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(SEND_NEW_ARTICLE);
            pst.setString(1, title);
            pst.setString(2, content);
            pst.setInt(3, topic);
            pst.setString(4, publisherComment);

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Couldn't save article!");
            } else
                System.out.println("Successful submit!");
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Couldn't save article: " + e.getMessage());
            return false;
        }
    }

    public boolean DBSendNewArticle(Article article) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Couldn't save article: " + e.getMessage());
            return false;
        }
    }


    // Topic Creation
    public void DBSendNewTopic(String name, String parentTopic) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(SEND_NEW_TOPIC);

            pst.setString(1, name);
            pst.setString(2, parentTopic);

            pst.execute();
            con.close();
        } catch (SQLException e) {
            System.out.println("Couldn't create topic: " + e.getMessage());
        }
    }

    //User Creation
    public boolean DBSendNewUser(String email, String password, String name, String address, int gender, String dateOfBirth) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
            assert con != null;
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
            con.close();
            return article;
        } catch (SQLException e) {
            System.out.println("Couldn't load Article: " + e.getMessage());
            return null;
        }

    }

    public Article DBLoadLastArticle() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_LAST_ARTICLE_ID);
            ResultSet rs = pst.executeQuery();
            int id = 0;

            while (rs.next()) {
                id = rs.getInt(1);
            }

            Article article = DBLoadArticle(id);
            con.close();
            return article;
        } catch (SQLException e) {
            System.out.println("Couldn't load Article: " + e.getMessage());
            return null;
        }
    }

    // Get ObservableList of all articles
    public ObservableList DBLoadAllArticles() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
            con.close();
            return articleList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    public ObservableList DBLoadOwnArticles(int authorId) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_OWN_ARTICLES_BY_ID + authorId);
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
            con.close();
            return articleList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    public ObservableList DBLoadOwnArticles(String userEmail) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
            con.close();
            return articleList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    public ObservableList DBLoadAllSubmittedArticles() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_SUBMITTED_ARTICLES);
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
            con.close();
            return articleList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Articles: " + e.getMessage());
            return null;
        }
    }

    // Get ObservableList of all users
    public ObservableList DBLoadAllUsers() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_USERS);
            ResultSet rs = pst.executeQuery();
            ObservableList<User> userList = FXCollections.observableArrayList();

            while (rs.next()) {
                userList.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(5),
                        rs.getString(6)));
            }
            con.close();
            return userList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Users: " + e.getMessage());
            return null;
        }
    }

    // Load all topics
    public ObservableList DBLoadAllTopics() {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_ALL_TOPICS);
            ResultSet rs = pst.executeQuery();
            ObservableList<Topic> topicList = FXCollections.observableArrayList();

            while (rs.next()) {
                topicList.add(new Topic(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)));
            }
            con.close();
            return topicList;
        } catch (SQLException e) {
            System.out.println("Couldn't load Topics: " + e.getMessage());
            return null;
        }
    }

    //Load a topic
    public Topic DBLoadTopic(int id) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_TOPIC);
            pst.setInt(1, id);
            pst.execute();
            ResultSet rs = pst.getResultSet();
            Topic topic = new Topic();
            while (rs.next()) {

                topic.setId(id);
                topic.setName(rs.getString(1));
                topic.setParentTopic(rs.getInt(2));

            }
            con.close();
            return topic;
        } catch (SQLException e) {
            System.out.println("Couldn't load Topic: " + e.getMessage());
            return null;
        }

    }

    //Load a User with ID
    public User DBLoadUserById(int id) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
                case 1 -> user = new Administrator(id,email,name, address, password, dateOfBirth, gender);
                case 2 -> user = new Moderator(id, email, name, address, password, dateOfBirth, gender);
                case 3 -> user = new User(id, email, name, address, password, dateOfBirth, gender);
                default -> {
                    user = new User();
                    mainController.setStatus("Couldn't load user Role - default set");
                }
            }
            con.close();
            return user;
        } catch (SQLException e) {
            System.out.println("Couldn't load User: " + e.getMessage());
            return null;
        }

    }

    //Load a User with ID
    public User DBLoadUserByEmail(String email) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
                case 1 -> user = new Administrator(id,email,name, address, password, dateOfBirth, gender);
                case 2 -> user = new Moderator(id, email, name, address, password, dateOfBirth, gender);
                case 3 -> user = new User(id, email, name, address, password, dateOfBirth, gender);
                default -> {
                    user = new User();
                    mainController.setStatus("Couldn't load user Role - default set");
                }
            }
            con.close();
            return user;
        } catch (SQLException e) {
            System.out.println("Couldn't load User: " + e.getMessage());
            return null;
        }


    }

    //Edit Articles
    public boolean DBEditArticle(int id, String newTitle, int newTopic, String newContent, String newPublisherComment) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
                con.close();
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
                con.close();
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    //Edit User
    public boolean DBEditUser(int id, String newEmail, String newPassword, String newName, String newAddress, int newGender, String newDateOfBirth) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
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
                System.out.println("Successful!");
                con.close();
                return true;
            } else {
                System.out.println("Failed!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
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
                con.close();
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }
        }
    }

    //Edit a topic
    public boolean DBEditTopic(int id, String newName, String newParentTopic) {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(EDIT_TOPIC);
            con.setAutoCommit(false);

            pst.setInt(3, id);
            pst.setString(1, newName);
            pst.setString(2, newParentTopic);

            int affectedRecords = pst.executeUpdate();

            if (affectedRecords == 1) {
                con.commit();
                System.out.println("Successful!");
                con.close();
                return true;
            } else {
                System.out.println("Failed!");
                con.rollback();
                con.setAutoCommit(true);
                con.close();
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
                con.close();
            } catch (SQLException e) {
                System.out.println("Couldn't reset auto-commit! " + e.getMessage());
            }

        }
    }

    //Get 20 most recent articles
    public LinkedList<Article> DBLoadRecentArticle() {

        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(LOAD_RECENT_ARTICLE);
            ResultSet rs = pst.executeQuery();

            LinkedList<Article> articles = new LinkedList<>();
            while (rs.next()) {
                Article article = new Article();

                article.setTopic(rs.getInt(1));
                article.setTitle(rs.getString(2));
                article.setContent(rs.getString(3));
                article.setPublisherComment(rs.getString(4));
                article.setExpireDate(rs.getString(5));
                articles.add(article);
            }
            con.close();
            return articles;
        } catch (SQLException e) {
            System.out.println("Getting recent articles failed: " + e.getMessage());
            return null;
        }

    }

    // Check if user exists in DB
    public boolean login(String email, String pw) throws SQLException {
        try {
            con = SQLConnection.ConnectDB();
            assert con != null;
            PreparedStatement pst = con.prepareStatement(CHECK_USER);
            pst.setString(1, email);
            pst.setString(2, pw);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.close();
        return false;
    }

}