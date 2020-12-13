package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_Login {

    // Atrrib_______________________________________________________________________________________________________
    MainView mainView;

    // Ini_______________________________________________________________________________________________________


    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    void loginClicked(ActionEvent event) {
        mainView.openApplicationStage();
        mainView.setStatus("Logged in \""+txtEmail.getText() + "\" with password \"" +txtPassword.getText()+"\"");
    }

    // Methods_______________________________________________________________________________________________________
    //reference to mainView
    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }

}
