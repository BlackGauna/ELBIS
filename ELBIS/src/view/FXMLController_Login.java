package view;

import controller.Launch;
import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class FXMLController_Login {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;

    // Ini_______________________________________________________________________________________________________

    public void initialize() {

    }

    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;


    // Methods_______________________________________________________________________________________________________
    //reference to mainView
    public void setMainView(MainController mainController){
        this.mainController = mainController;
    }

    public void loginClicked(ActionEvent event) throws SQLException {
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
