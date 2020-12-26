package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import view.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController extends Application {

    // Atrrib_______________________________________________________________________________________________________
    //Data
    private DataController dc;
    private static Connection con;
    private User activeUser;

    //Loaders
    private FXMLLoader loginLoader;
    private FXMLLoader mainApplicationLoader;
    private FXMLLoader editorLoader;

    //Controllers
    private FXMLController_Login loginController;
    private FXMLController_MainApplication mainApplicationController;
    private FXMLController_Editor editorController;

    //Stages
    private Stage loginStage;
    private Stage applicationStage;
    private Stage editorStage;

    //Scenes
    private Scene loginScene;
    private Scene applicationScene;
    private Scene editorScene;
    //Panes
    private Pane loginPane;
    private Pane applicationPane;
    private Pane editorPane;


    // Ctor_______________________________________________________________________________________________________
    public MainController() {

        dc = new DataController();
        // ###### Create Stages ######
        loginStage = new Stage(); // Login window
        loginStage.setTitle("Login");
        loginStage.setResizable(false);
        applicationStage = new Stage(); // Application Window
        applicationStage.setTitle("Verwaltung");
        applicationStage.setResizable(false);
        editorStage = new Stage(); // Editor Window

    }

    // Start_______________________________________________________________________________________________________
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Stage content
        try {
            loginStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
            applicationStage.getIcons().add(new Image("/ELBIS_graphic/ELBIS_E_small.png"));
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

    /*****************************
     *
     * OPen-Up Methods
     *
     ******************************/

    public void openLoginStage() {
        loginStage.show();
    }

    public void openApplicationStage() {
        loginStage.close();
        applicationStage.show();
    }

    public void openEditorScene() {
        editorStage.show();
    }

    public void setStatus(String newStatus) {
        mainApplicationController.setStatus(newStatus);
    }

    public boolean login(String email, String pw) throws SQLException {
        boolean login = dc.login(email, pw);
        if (login) {
            //TODO set activeUser
            activeUser = new Administrator();
            setStatus("Logged in as static administrator!");
            //activeUser = dc.DBLoadUserByEmail(email);
            //setStatus("Logged in \""+ activeUser.geteMail() + "\" with password \"" + pw +"\"");
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

    /******************************
     *
     * Refresh Table methods
     *
     ******************************/

    public TableView refreshArticleTable(TableView table) {

        /*
        //TODO add buttonpanel to delete and edit articles per article in table
        */

        ResultSet rs;
        ObservableList<Article> articleList = FXCollections.observableArrayList();

        try {
            con = SQLConnection.ConnectDB();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM ARTICLE");
            rs = pst.executeQuery();

            while (rs.next()){
                articleList.add(new Article(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("creationDate"),
                        rs.getString("expireDate"),
                        rs.getString("lastEdit"),
                        // TODO status?
                        rs.getString("publisherComment")));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(int i = 0; i< table.getColumns().size(); i++){
            TableColumn col = (TableColumn) table.getColumns().get(i);

            if(!col.getText().equals("Status")|| !col.getText().equals("id")) {
                setStatus("ArticleTable loading... "+((TableColumn<Article, String>) table.getColumns().get(i)).getText());
                ((TableColumn<Article, String>) table.getColumns().get(i)).setCellValueFactory(new PropertyValueFactory<Article, String>(((TableColumn<Article, String>) table.getColumns().get(i)).getText()));
            }
        }

        table.setItems(articleList);

        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ArticleTable loaded?");
        }


        return table;
    }

    public TableView refreshModerationTable(TableView table) {

        /*
        //TODO fill table with DB information
        //TODO add buttonpanel to delete and edit users per user in table
        */

        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty ModerationTable loaded?");
        }
        return table;
    }


    public TableView refreshAdministrationTable(TableView table) {

        /*
        //TODO fill table with DB information
        //TODO add buttonpanel to promote/degrade users per user in table
        */

        //Test if table is empty
        if (table.getItems().size() == 0) {
            setStatus("Warning: Empty AdministrationTable loaded?");
        }
        return table;
    }

    /******************************
     *
     *  Creation methods
     *
     ******************************/

    public boolean createUser(String email, String password, String name, String address, String gender, String dateOfBirth) {
        boolean result = false;
        int genderInt = 0;

        if (gender.equals("Maennlich")) {
            genderInt = 1;
        } else if (gender.equals("Weiblich")) {
            genderInt = 2;
        } else if (gender.equals("Divers")) {
            genderInt = 3;
        }

        //result = dc.DBSendNewUser(email, password, name, address, genderInt, dateOfBirth);
        setStatus("Tried to send User");
        return result;
    }

    public boolean createArticle() {
        boolean result = false;
        return result;
    }

    public boolean createTopic() {
        boolean result = false;
        return result;
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }


}

