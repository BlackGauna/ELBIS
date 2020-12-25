package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import view.FXMLController_Editor;
import view.FXMLController_Login;
import view.FXMLController_MainApplication;

import java.io.IOException;
import java.sql.SQLException;

public class MainController extends Application {

    // Atrrib_______________________________________________________________________________________________________
    private DataController dc;
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
        loginStage.setTitle("ELBIS Login");
        loginStage.setResizable(false);
        applicationStage = new Stage(); // Application Window
        applicationStage.setTitle("ELBIS");
        applicationStage.setResizable(false);
        editorStage = new Stage(); // Editor Window

    }

    // Start_______________________________________________________________________________________________________
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Stage content
        try {
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
            loginScene.getStylesheets().add("/view/dark.css");
            applicationScene = new Scene(applicationPane);
            applicationScene.getStylesheets().add("/view/dark.css");
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

    public void setStatus (String newStatus){
        mainApplicationController.setStatus(newStatus);
    }

    public boolean login(String email, String pw) throws SQLException {
        boolean login = dc.login(email, pw);
        if(login){
           //TODO set activeUser
            activeUser = new Administrator();
            //activeUser = dc.DBLoadUserByEmail(email);
           //setStatus("Logged in \""+ activeUser.geteMail() + "\" with password \"" + pw +"\"");
            mainApplicationController.openTabs(activeUser);
            openApplicationStage();
        } else if(!login){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter correct email and password");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return login;
    }

    public TableView refreshArticleTable(TableView table) {

        /*
        //TODO fill table with DB information
        //TODO add buttonpanel to delete and edit articles per article in table
        */

        //Test if table is empty
        if(table.getItems().size() ==0) {
            mainApplicationController.setStatus("Warning: Empty Table loaded?");
        }
        return table;
    }

    public TableView refreshModerationTable(TableView table) {

        /*
        //TODO fill table with DB information
        //TODO add buttonpanel to delete and edit users per user in table
        */

        //Test if table is empty
        if(table.getItems().size() ==0) {
            mainApplicationController.setStatus("Warning: Empty Table loaded?");
        }
        return table;
    }


    public TableView refreshAdministrationTable(TableView table) {

        /*
        //TODO fill table with DB information
        //TODO add buttonpanel to promote/degrade users per user in table
        */

        //Test if table is empty
        if(table.getItems().size() ==0) {
            mainApplicationController.setStatus("Warning: Empty Table loaded?");
        }
        return table;
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }


}

