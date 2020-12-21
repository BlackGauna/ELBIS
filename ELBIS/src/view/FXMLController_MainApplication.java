package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class FXMLController_MainApplication {

    // Atrrib_______________________________________________________________________________________________________
    MainView mainView;
    Pane statusBar;
    FXMLController_StatusBar statusBarController;

    // Ini_______________________________________________________________________________________________________

    @FXML
    public void initialize() {
        //TODO Create Head bar and add refresh button
        //TODO optimize Window Appearance
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StatusBar.fxml"));
            statusBar = (Pane) loader.load();
            statusBarController = loader.getController();
            borderPane.setBottom(statusBar);
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
    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }

    public void setStatus(String newStatus){
        statusBarController.setStatus(newStatus);
    }
}
