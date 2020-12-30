package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_ModerationTable implements Initializable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    //Alert createUserAlert;
    private Dialog_CreateUser createUserDialog;


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
    void createUserClicked(ActionEvent event) {
        try {
            createUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Methods_______________________________________________________________________________________________________
    public boolean createUser() throws IOException {
        boolean result = false;
        createUserDialog = new Dialog_CreateUser(Alert.AlertType.INFORMATION, mainController);
        createUserDialog.showAndWait();
        return result;
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public TableView getTableView() {
        return userTable;
    }
    public void setTableView(TableView table) {
        this.userTable = table;
    }

}
