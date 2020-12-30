package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainController extends Application {

    /*
    TODO create refresh methods for tabs
    TODO rename refresh methods of each element
    TODO make sure relogin reloads everything properly

    TODO reminder: fix displacement of columns
    TODO add buttons to delete or edit on every entry in table
    TODO maybe show the count of articles under a specific topic on topic views (count articles per topic)
     */

    private static Connection con;
    // Atrrib_______________________________________________________________________________________________________
    //Data
    private DataController dc;
    private User activeUser;

    //MainLoaders
    private FXMLLoader loginLoader;
    private FXMLLoader mainApplicationLoader;
    private FXMLLoader editorLoader;
    //MainControllers
    private FXMLController_Login loginController;
    private FXMLController_MainApplication mainApplicationController;
    private FXMLController_Editor editorController;
    //MainStages
    private Stage loginStage;
    private Stage applicationStage;
    private Stage editorStage;
    public Stage sideStage;
    //MainScenes
    private Scene loginScene;
    private Scene applicationScene;
    private Scene editorScene;
    private Scene sideScene;
    //MainPanes
    private Pane loginPane;
    private Pane applicationPane;
    private Pane editorPane;

    //SideContent
    private FXMLLoader sideLoader;

    private FXMLController_CreateUser createUserController;
    private Pane createUserPane;

    private FXMLController_CreateTopic createTopicController;
    private Pane createTopicPane;


    // Ctor_______________________________________________________________________________________________________
    public MainController() {

        dc = new DataController(this);
        // ###### Create Stages ######
        loginStage = new Stage(); // Login window
        loginStage.setTitle("Login");
        loginStage.setResizable(false);
        applicationStage = new Stage(); // Application Window
        applicationStage.setTitle("Verwaltung");
        applicationStage.setResizable(false);
        editorStage = new Stage(); // Editor Window
        sideStage = new Stage();
        sideStage.setResizable(false);

    }

    // Start_______________________________________________________________________________________________________
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Stage content
        try {
            loginStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            applicationStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            sideStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            //Manage loaders and load their controllers
            loginLoader = new FXMLLoader(getClass().getResource("/view/Pane_Login.fxml"));
            loginPane = (Pane) loginLoader.load();
            loginController = loginLoader.getController();
            loginController.setMainController(this);

            mainApplicationLoader = new FXMLLoader(getClass().getResource("/view/Pane_MainApplication.fxml"));
            applicationPane = (Pane) mainApplicationLoader.load();
            mainApplicationController = mainApplicationLoader.getController();
            mainApplicationController.setMainController(this);

            editorLoader = new FXMLLoader(getClass().getResource("/view/Pane_Editor.fxml"));
            editorPane = (Pane) editorLoader.load();
            editorController = editorLoader.getController();
            editorController.setMainController(this);

            //Manage scenes
            loginScene = new Scene(loginPane);
            loginScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            applicationScene = new Scene(applicationPane);
            applicationScene.getStylesheets().add("/ELBIS_graphic/dark.css");

            editorScene = new Scene(editorPane);
            loginStage.setScene(loginScene);
            applicationStage.setScene(applicationScene);
            editorStage.setScene(editorScene);

        } catch (IOException io) {
            System.out.println("Couldn't load scene File");
            io.printStackTrace();
        }
        openLoginStage();


    }

    // Methods_______________________________________________________________________________________________________
    public void setStatus(String newStatus) {
        mainApplicationController.setStatus(newStatus);
    }

    public boolean logout() {
        boolean logout = false;
        setStatus("Logging out: " + activeUser.getEmail());
        mainApplicationController.removeTabs();
        openLoginStage();
        this.activeUser = new User();
        return logout;
    }

    public boolean login(String email, String pw) throws SQLException {
        boolean login = dc.login(email, pw);
        if (login) {
            activeUser = dc.DBLoadUserByEmail(email);
            setStatus("Logged in \"" + activeUser.getEmail() + "\" with password \"" + activeUser.getPassword() + "\"");
            mainApplicationController.openTabs(activeUser);
            openApplicationStage();
        } else if (!login) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter correct email and password");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return login;
    }

    /*****************************
     *
     * OPen-Up Methods
     *
     ******************************/

    public void openLoginStage() {
        editorStage.close();
        applicationStage.close();
        loginStage.show();
    }

    public void openApplicationStage() {
        loginStage.close();
        applicationStage.show();
    }

    /**
     * open new article in editor
     */
    public void openEditorScene() {
        editorController.openNewArticle();
        editorStage.show();
    }

    /**
     * open an article in editor
     */
    // TODO: use this method for opening an article in ArticleTable for editing
    public void openEditorScene(Article article) {
        editorController.openArticle(article);
        editorStage.show();
    }

    public void openSideStage(sideStageState state) {
        String title = "";
        try {
            switch (state) {
                //TODO add all side Stages
                case createUser:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateUser.fxml"));
                    createUserPane = (Pane) sideLoader.load();
                    createUserController = sideLoader.getController();
                    createUserController.setMainController(this);
                    sideScene = new Scene(createUserPane);
                    title = "Nutzererstellung";
                    break;
                case createTopic:
                    sideLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateTopic.fxml"));
                    createTopicPane = (Pane) sideLoader.load();
                    createTopicController = sideLoader.getController();
                    createTopicController.setMainController(this);
                    sideScene = new Scene(createTopicPane);
                    title = "Bereichserstellung";
                    break;
            }
            sideStage.setTitle(title);
            sideScene.getStylesheets().add("/ELBIS_graphic/dark.css");
            sideStage.setScene(sideScene);
            sideStage.showAndWait();
        } catch (IOException io) {
            io.printStackTrace();
            setStatus("Could not load SideStage content");
        }
    }

    /******************************
     *
     * Refresh Table methods
     *
     ******************************/

    public TableView refreshUserContent_ArticleTable(TableView table) {
        /*
        //TODO add buttonpanel to delete and edit articles per article in table
        */
        ObservableList<Article> articleList = dc.DBLoadOwnArticles(activeUser.getId());
        // Getter from Article Class
        List<String> propertyKeys = Arrays.asList("id", "title", "creationDate", "expireDate", "lastEdit", "statusString", "topicName", "author", "publisherComment");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            setStatus("ArticleTable loading... " + ((TableColumn<Article, String>) table.getColumns().get(i)).getText());
            ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>(propertyKeys.get(i)));
        }
        table.setItems(articleList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ArticleTable loaded?");
        }
        return table;
    }

    public TreeView refreshUserContent_ArticleTree(TreeView tree){
        //TODO implement Topics as tree-titles and sort Articles into it
        return tree;
    }

    public TableView refreshModerationContent_UserTable(TableView table) {
        /*
        //TODO fill tables "New Submissions" and "Manage Articles" with DB information
        //TODO add buttonpanel to delete and edit (JUST) users per user in table
        */
        ObservableList<User> userList = dc.DBLoadAllUsers();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "email", "name", "genderString", "role", "address", "dobString");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            setStatus("UserTable loading... " + ((TableColumn<User, String>) table.getColumns().get(i)).getText());
            ((TableColumn<User, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<User, String>(propertyKeys.get(i)));
        }
        table.setItems(userList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }

    public TableView refreshModerationContent_SubmissionTable(TableView table){
        /*
        //TODO fill table with DB information (All Articles in state "submitted")
        //TODO add buttonpanel to edit state of an article with a new ModeratorComment
        */
        return table;
    }

    public TableView refreshModerationContent_ArticleTable(TableView table){
        /*
        //TODO fill table with DB information (all existing articles)
        //TODO add buttonpanel to edit state or delete of an article
        */
        return table;
    }

    public TableView refreshAdministrationContent_TopicTable(TableView table) {

        ObservableList<Topic> topicList = dc.DBLoadAllTopics();
        // Getter from User Class
        List<String> propertyKeys = Arrays.asList("id", "name", "parentTopicString");
        // fill columns with values
        for (int i = 0; i < table.getColumns().size(); i++) {
            setStatus("TopicTable loading... " + ((TableColumn<Topic, String>) table.getColumns().get(i)).getText());
            ((TableColumn<Topic, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Topic, String>((propertyKeys.get(i))));
        }
        table.setItems(topicList);
        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty AdministrationTable loaded?");
        }
        return table;
    }

    public TableView refreshAdministrationContent_UserTable(TableView table){
        /*
        //TODO fill table "Manage Roles" with DB information (all Users)
        //TODO add buttonpanel to edit roles of an user or to delete an User
        */
        return table;
    }

    /******************************
     *
     *  Creation methods
     *
     ******************************/

    public boolean createUser(String email, String password, String name, String gender, String role, String address, String dateOfBirth) {
        boolean result = false;
        int genderInt = 0;

        if (gender.equals("Maennlich")) {
            genderInt = 1;
        } else if (gender.equals("Weiblich")) {
            genderInt = 2;
        } else if (gender.equals("Divers")) {
            genderInt = 3;
        }

        int roleInt = 0;

        if (role.equals("Admin")) {
            roleInt = 1;
        } else if (role.equals("Moderator")) {
            roleInt = 2;
        } else if (role.equals("User")) {
            roleInt = 3;
        }

        //TODO send actual User
        result = dc.DBSendNewUser(email, password, name, genderInt, roleInt, address, dateOfBirth);

        return result;
    }

    public boolean createArticle(Article article) {
        boolean result = false;
        result = dc.DBSendNewArticle(article);
        return result;
    }

    public boolean createTopic(String name, String parent) {
        boolean result = false;

        int topicInt = 0;

        if (parent.equals("Organisationen")) {
            topicInt = 1;
        } else if (parent.equals("Gemeinde")) {
            topicInt = 2;
        } else if (parent.equals("Industrie")) {
            topicInt = 3;
        }

        dc.DBSendNewTopic(name, topicInt);
        return result;
    }

    /******************************
     *
     * Other
     *
     ******************************/

    public boolean saveArticle(Article article) {
        boolean result = false;
        if (article.getId() == 0) {
            result = createArticle(article);
        } else {
            result = dc.DBEditArticle(article.getId(), article.getTitle(), article.getTopic_int(), article.getContent(), article.getPublisherComment());
        }

        Article newArticle = dc.DBLoadLastArticle();
        editorController.openArticle(newArticle);

        return result;
    }

    public ObservableList<Topic> getAllTopics() {
        ObservableList<Topic> topics = dc.DBLoadAllTopics();

        return topics;
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }


}

