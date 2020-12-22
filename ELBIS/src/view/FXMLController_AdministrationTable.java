package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_AdministrationTable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________

    @FXML
    private Accordion tPane_dropDownAccordion;

    @FXML
    private TitledPane tPane_ManageTopics;

    @FXML
    private TitledPane tPane_ManageRoles;

    @FXML
    private ButtonBar btnBar;

    @FXML
    private Button btnCreateTopic;

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
    // Getters,Setters_________________________________________________________________________________________________

}
