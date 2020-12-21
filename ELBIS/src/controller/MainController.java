package controller;

import controller.Launch;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataController;
import model.User;
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
    //Controllers
    private FXMLController_Login loginController;
    private FXMLController_MainApplication mainApplicationController;
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
            loginController.setMainView(this);

            mainApplicationLoader = new FXMLLoader(getClass().getResource("/view/Pane_MainApplication.fxml"));
            applicationPane = (Pane) mainApplicationLoader.load();
            mainApplicationController = mainApplicationLoader.getController();
            mainApplicationController.setMainView(this);

            //Manage scenes
            loginScene = new Scene(loginPane);
            applicationScene = new Scene(applicationPane);

            loginStage.setScene(loginScene);
            applicationStage.setScene(applicationScene);

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
            //activeUser = dc.DBLoadUserByEmail(email);
           // setStatus("Logged in \""+ activeUser.geteMail() + "\" with password \"" + pw +"\"");
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
        //TODO fill table with DB information
        //TODO add buttonpanel to delete and edit articles per article in table
        return table;
    }

    public TableView refreshModerationTable(TableView table) {
        //TODO fill table with DB information
        //TODO add buttonpanel to delete and edit users per user in table
        return table;
    }

    public TableView refreshAdministrationTable(TableView table) {
        //TODO fill table with DB information
        //TODO add buttonpanel to promote/degrade users per user in table
        return table;
    }

    //Getters,Setters_______________________________________________________________________________________________________
    public User getActiveUser() {
        return activeUser;
    }


}

