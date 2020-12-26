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
    Alert createUserAlert;
    private FXMLLoader createUserPaneLoader;
    private FXMLController_CreateUser createUserController;
    private Stage createUserStage;
    private Pane createUserPane;

    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        dropDownAccordion.setExpandedPane(dropDownAccordion.getPanes().get(0));
    }
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

        //Create window
        createUserAlert = new Alert(Alert.AlertType.INFORMATION);
        createUserAlert.setTitle("Nutzer Anlegen");
        createUserAlert.setHeaderText("Nutzererstellung");


        //load WindowContent
        createUserPaneLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateUser.fxml"));
        createUserPane = (Pane) createUserPaneLoader.load();
        createUserController = createUserPaneLoader.getController();
        createUserController.setMainController(mainController);

        createUserAlert.getDialogPane().setContent(createUserPane);
        createUserAlert.setResizable(false);

        createUserAlert.showAndWait();

        /*

        String email = createUserController.getEmail();
        String password = createUserController.getPassword();
        String name = createUserController.getName();
        String address = createUserController.getAnschrift();
        String gender = createUserController.getGender();
        String dateOfBirth = createUserController.getBirth();

         */
        //result = mainController.createUser(email,password,name,address,gender,dateOfBirth);
        return result;
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setTableView(TableView table){

    }
}
