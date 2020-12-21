package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.DataController;

import java.sql.SQLException;

public class FXMLController_Login {

    // Atrrib_______________________________________________________________________________________________________
    MainView mainView;
    static MainController mc = new MainController();

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
    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }

    public void loginClicked(ActionEvent event) throws SQLException {
        String email = txtEmail.getText();
        String pw = txtPassword.getText();
        boolean flag = mc.login(email, pw);

        if (!flag){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter correct email and password");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            mainView.openApplicationStage();
            mainView.setStatus("Logged in \""+ email + "\" with password \"" + pw +"\"");
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
