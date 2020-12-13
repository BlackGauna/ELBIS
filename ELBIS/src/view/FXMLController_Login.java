package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class FXMLController_Login {

    //reference to mainView
    MainView mainView;

    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }

    //Pane_Login UI
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    void loginClicked(ActionEvent event) {
        System.out.println("loginClicked with: " + txtEmail.getText());
        mainView.openApplicationStage();
    }



}
