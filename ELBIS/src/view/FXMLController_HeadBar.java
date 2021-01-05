package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_HeadBar extends ELBIS_FXMLController{
    // Atrrib_______________________________________________________________________________________________________

    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private Label lblUser;

    @FXML
    private Label lblRole;

    @FXML
    private Button btnLogout;

    @FXML
    void logoutClicked(ActionEvent event) {
        mainController.logout();
    }

    @FXML
    private ToggleButton btnDarkMode;

    @FXML
    void darkModeClicked(ActionEvent event) {
        mainController.switchDarkMode();
    }



    // Methods_______________________________________________________________________________________________________
    public void showUser(String user, String role) {
        lblUser.setText(user);
        lblRole.setText(role);
    }
    // Getters,Setters_________________________________________________________________________________________________

}

