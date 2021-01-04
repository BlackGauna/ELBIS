package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

import static view.sideStageState.createTopic;

public class FXMLController_AdministrationContent extends ELBIS_FXMLController implements Initializable {

    // Atrrib_______________________________________________________________________________________________________
    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<Topic> topicTable = new TableView<>();
    @FXML
    private TableView<User> userTable = new TableView<>();
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
        topicTable.getColumns().add(new TableColumn<Topic, String>("Eltern-Bereich"));
        TableColumn deleteTopicColumn = new TableColumn<Article, Boolean>(" ");
        deleteTopicColumn.setSortable(false);
        topicTable.getColumns().add(deleteTopicColumn);
        TableColumn editTopicColumn = new TableColumn<Article, Boolean>(" ");
        editTopicColumn.setSortable(false);
        topicTable.getColumns().add(editTopicColumn);


        userTable.getColumns().add(new TableColumn<User, String>("ID"));
        userTable.getColumns().add(new TableColumn<User, String>("E-Mail"));
        userTable.getColumns().add(new TableColumn<User, String>("Name"));
        userTable.getColumns().add(new TableColumn<User, String>("Geschlecht"));
        userTable.getColumns().add(new TableColumn<User, String>("Rolle"));
        userTable.getColumns().add(new TableColumn<User, String>("Anschrift"));
        userTable.getColumns().add(new TableColumn<User, String>("Geburtsdatum"));

        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

    @FXML
    void createTopicClicked(ActionEvent event) {
        try {
            mainController.callSideStage(createTopic);
            refreshAdministrationContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshAdministrationContent();
    }

    // Methods_______________________________________________________________________________________________________
    public void refreshAdministrationContent() {
        mainController.setStatus("Refreshing AdministratorContent...");
        setContent_TopicTable(mainController.refreshAdministrationContent_TopicTable(getContent_TopicTable()));
        setContent_UserTable(mainController.refreshAdministrationContent_UserTable(getContent_UserTable()));
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getContent_TopicTable() {
        return topicTable;
    }
    public void setContent_TopicTable(TableView table) {
        this.topicTable = table;
    }

    public TableView getContent_UserTable(){
        return userTable;
    }
    public void setContent_UserTable(TableView table){
        this.userTable = table;
    }

}