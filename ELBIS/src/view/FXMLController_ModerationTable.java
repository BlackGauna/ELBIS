package view;
import controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_ModerationTable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________

    @FXML
    private Accordion dropDownAccordion;

    @FXML
    private TitledPane tPane_NewSubmissions;

    @FXML
    private TitledPane tPane_ManageArticles;

    @FXML
    private TitledPane tPane_ManageUsers;

    @FXML
    private ButtonBar btnBar;

    @FXML
    private Button btn_CreateUser;

    // Methods_______________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
    public void setTableView(TableView table){

    }
    // Getters,Setters_________________________________________________________________________________________________

}
