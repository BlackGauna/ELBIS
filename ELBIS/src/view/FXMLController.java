package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    void loginClicked(ActionEvent event) {
    	System.out.println("loginClicked with: "+txtEmail.getText());
    }
  
}

