package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FXMLController_MainApplication {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    //Loaders
    FXMLLoader statusBarLoader;
    FXMLLoader headBarLoader;
    //Panes
    Pane statusBar;
    Pane headBar;
    //Controllers
    FXMLController_StatusBar statusBarController;
    FXMLController_HeadBar headBarController;

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


    public void addToTabPane(Tab newTab) {
        //TODO Implement table views
        tabPane.getTabs().add(newTab);
    }

    // Methods_______________________________________________________________________________________________________
    //reference to mainView
    public void setMainView(MainController mainController){
        this.mainController = mainController;
    }

    public void setStatus(String newStatus){
        statusBarController.setStatus(newStatus);
    }
}
