package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.DataController;
import model.Topic;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

import static view.sideStageState.createTopic;

public class FXMLController_AdministrationContent implements Initializable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    private Dialog_CreateTopic createTopicDialog;
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
    @FXML
    private Button btnRefresh;

    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topicTable.getColumns().add(new TableColumn<Topic, String>("ID"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("Name"));
        topicTable.getColumns().add(new TableColumn<Topic, String>("Parent Topic"));
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

    @FXML
    void createTopicClicked(ActionEvent event) {
        try {
            mainController.openSideStage(createTopic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshAdministrationContent();
    }

    // Methods_______________________________________________________________________________________________________
    public void createTopic() {
       // createTopicDialog = new Dialog_CreateTopic(Alert.AlertType.INFORMATION, mainController);
        //createTopicDialog.showAndWait();
        refreshAdministrationContent();
    }

    public void refreshAdministrationContent() {
        mainController.setStatus("Refreshing AdministratorContent...");
        setContent_TopicTable(mainController.refreshAdministrationContent_TopicTable(getContent_TopicTable()));
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public TableView getContent_TopicTable() {
        return topicTable;
    }

    public void setContent_TopicTable(TableView table) {
        this.topicTable = table;
    }

}