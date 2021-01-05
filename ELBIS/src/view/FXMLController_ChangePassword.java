package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class FXMLController_ChangePassword extends ELBIS_FXMLController{
    // Atrrib_______________________________________________________________________________________________________
    int userID = 0;
    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnOk;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassword2;

    @FXML
    void okClicked(ActionEvent event) {
        boolean result = mainController.changePassword(userID ,getPassword());
        if (result == true){
            mainController.sideStage.close();
        } else{

        }
    }

    // Ini_______________________________________________________________________________________________________
    // Getters,Setters_________________________________________________________________________________________________

    public String getPassword() {
        String password = "";
        System.out.println("matching passwords: "+txtPassword.getText() + " *with* " + txtPassword2.getText());
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

