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
        String email = txtEmail.getText();
        String pw = txtPassword.getText();
        boolean flag = mainController.login(email, pw);

        if (!flag){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter correct email and password");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            mainController.openApplicationStage();
        }
    }

    // Getters,Setters_________________________________________________________________________________________________

    public String getTxtPassword() {
        return txtPassword.toString();
    }

    public String getTxtEmail() {
        return txtEmail.toString();
    }
}
