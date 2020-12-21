package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.*;


public class FXMLController_MainApplication {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    //Loaders
    FXMLLoader statusBarLoader;
    FXMLLoader headBarLoader;
    FXMLLoader userTableLoader;
    FXMLLoader moderatorTableLoader;
    FXMLLoader administrationTableLoader;
    //Panes
    Pane statusBar;
    Pane headBar;
    Pane userTable;
    Pane moderationTable;
    Pane AdministrationTable;
    //Controllers
    FXMLController_StatusBar statusBarController;
    FXMLController_HeadBar headBarController;
    FXMLController_ArticleTable articleTableController;
    FXMLController_ModerationTable moderationTableController;
    FXMLController_AdministrationTable administrationTableController;

    // Ini_______________________________________________________________________________________________________

    @FXML
    public void initialize() {
        //TODO Create Head bar and add refresh button
        try {
            //StatusBar
            statusBarLoader = new FXMLLoader(getClass().getResource("/view/StatusBar.fxml"));
            statusBar = (Pane) statusBarLoader.load();
            statusBarController = statusBarLoader.getController();
            borderPane.setBottom(statusBar);

            //HeadBar
            headBarLoader = new FXMLLoader(getClass().getResource("/view/HeadBar.fxml"));
            headBar = (Pane) headBarLoader.load();
            headBarController = headBarLoader.getController();
            borderPane.setTop(headBar);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // UI_______________________________________________________________________________________________________
    @FXML
    private TabPane tabPane;

    @FXML
    private BorderPane borderPane;


    // Methods_______________________________________________________________________________________________________
    //reference to mainView
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public void setStatus(String newStatus){
        statusBarController.setStatus(newStatus);
    }

    public void openTabs(User user) {
        if(user instanceof Administrator){
            addUserTab();
            addModeratorTab();
            addAdminTab();
        }
        else if(user instanceof Moderator){
            addUserTab();
            addModeratorTab();
        }
        else if(user instanceof User){
            addUserTab();
        }

    }

    public void addUserTab(){
        //tabPane.getTabs().add(newTab);
    }
    public void addModeratorTab(){
        //tabPane.getTabs().add(newTab);
    }
    public void addAdminTab(){
        //tabPane.getTabs().add(newTab);
    }

}
