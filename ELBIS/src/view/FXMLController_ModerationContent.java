package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_ModerationContent extends ELBIS_FXMLController implements Initializable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private TableView<User> userTable = new TableView<>();
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
    @FXML
    private Button btnRefresh;

    // Ini_______________________________________________________________________________________________________
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userTable.getColumns().add(new TableColumn<User, String>("ID"));
        userTable.getColumns().add(new TableColumn<User, String>("E-Mail"));
        userTable.getColumns().add(new TableColumn<User, String>("Name"));
        userTable.getColumns().add(new TableColumn<User, String>("Gender"));
        userTable.getColumns().add(new TableColumn<User, String>("Role"));
        userTable.getColumns().add(new TableColumn<User, String>("Address"));
        userTable.getColumns().add(new TableColumn<User, String>("Date of Birth"));
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

    @FXML
    void createUserClicked(ActionEvent event) {
        try {
            mainController.openSideStage(sideStageState.createUser);
            refreshModerationContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refreshClicked(ActionEvent event) {
        refreshModerationContent();
    }

    // Methods_______________________________________________________________________________________________________

    public void refreshModerationContent() {
        mainController.setStatus("Refreshing ModeratorContent...");
        setContent_UserTable(mainController.refreshModerationContent_UserTable(getContent_UserTable()));
    }

    // Getters,Setters_________________________________________________________________________________________________

    public TableView getContent_UserTable() {
        return userTable;
    }

    public void setContent_UserTable(TableView table) {
        this.userTable = table;
    }

}
