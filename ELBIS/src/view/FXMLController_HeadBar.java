package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_HeadBar extends ELBIS_FXMLController {
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

    // Methods_______________________________________________________________________________________________________
    public void showUser(String user, String role) {
        lblUser.setText(user);
        lblRole.setText(role);
    }
    // Getters,Setters_________________________________________________________________________________________________

}

