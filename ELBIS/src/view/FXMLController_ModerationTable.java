package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLController_ModerationTable {
    //TODO import Table variables
    //TODO buttonEvents

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;
    //Alert createUserAlert;
    private Dialog_CreateUser createUserDialog;
    private FXMLLoader createUserPaneLoader;
    private FXMLController_CreateUser createUserController;
    private Stage createUserStage;
    private Pane createUserPane;
    @FXML
    private Accordion dropDownAccordion;
    // UI_______________________________________________________________________________________________________
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

    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }

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
        //TODO implement as new stage
        createUserDialog = new Dialog_CreateUser(Alert.AlertType.INFORMATION, mainController);

        createUserDialog.show();

        //  for (int i = 0 ; i < list.size(); i ++){
        //     System.out.println(list.get(i));
        // }


        //result = mainController.createUser(email,password,name,address,gender,dateOfBirth);
        return result;
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTableView(TableView table) {

    }
}
