package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_HeadBar {
    //TODO buttonEvent
    //TODO refresh based on which type of user is logged in

    // Atrrib_______________________________________________________________________________________________________

    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private Label lblUser;

    @FXML
    private Label lblRole;

    @FXML
    private Button btnRefresh;
    // Methods_______________________________________________________________________________________________________
    public void showUser(String user, String role){
        lblUser.setText(user);
        lblRole.setText(role);
    }
    // Getters,Setters_________________________________________________________________________________________________

}

