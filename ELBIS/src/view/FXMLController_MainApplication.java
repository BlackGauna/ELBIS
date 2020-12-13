package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_MainApplication {

    //reference to mainView
    MainView mainView;

    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }

    //Pane_MainApplication UI
    @FXML
    private TabPane tabPane;

}
