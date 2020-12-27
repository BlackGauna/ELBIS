package view;

import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Topic;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_AdministrationTable implements Initializable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topicTable.getColumns().add(new TableColumn<Topic, String>("id"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("name"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("parentTopic"));
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }
    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<Topic> topicTable = new TableView<>();

    @FXML
    private Accordion dropDownAccordion;

    @FXML
    private TitledPane tPane_ManageTopics;

    @FXML
    private TitledPane tPane_ManageRoles;

    @FXML
    private ButtonBar btnBar;

    @FXML
    private Button btnCreateTopic;


    // Methods_______________________________________________________________________________________________________

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public TableView getTableView() {
        return topicTable;
    }

    public void setTableView(TableView table) {
        this.topicTable = table;
    }

}