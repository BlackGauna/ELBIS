package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class FXMLController_Login extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________

    // Ini_______________________________________________________________________________________________________

    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;


    // Methods_______________________________________________________________________________________________________

    public void loginClicked(ActionEvent event) throws SQLException {
        boolean flag = mainController.login(txtEmail.getText(),txtPassword.getText());
    }

    // alternative to loginClicked. Login when pressing enter key
    public void onEnter(ActionEvent event) throws SQLException
    {
        boolean flag = mainController.login(txtEmail.getText(),txtPassword.getText());
    }

    // Getters,Setters_________________________________________________________________________________________________

    public String getTxtPassword() {
        return txtPassword.toString();
    }

    public String getTxtEmail() {
        return txtEmail.toString();
    }
}
