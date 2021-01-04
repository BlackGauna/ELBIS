package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLController_ChangePassword extends ELBIS_FXMLController {
    // Atrrib_______________________________________________________________________________________________________
    int userID = 0;
    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnOk;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPassword2;

    @FXML
    void okClicked(ActionEvent event) {
        mainController.changePassword(userID ,getPassword());
    }

    // Ini_______________________________________________________________________________________________________
    // Getters,Setters_________________________________________________________________________________________________

    public String getPassword() {
        String password = "none";
        if (txtPassword.getText().equals(txtPassword2.getText()) && !txtPassword.getText().equals("")) {
            password = txtPassword.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Passwortbestätigung");
            alert.setContentText("Die Bestätigung ihres Passworts ist fehlgeschlagen, \n Bitte überprüfen sie ihre Eingabe.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return password;
    }

    public void setUserID(int id) {
        this.userID = id;
    }
}

