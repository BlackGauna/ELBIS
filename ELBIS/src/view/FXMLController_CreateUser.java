package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class FXMLController_CreateUser {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;


    // UI_______________________________________________________________________________________________________

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword1;
    @FXML
    private PasswordField txtPassword2;
    @FXML
    private TextField txtVorname;
    @FXML
    private TextField txtNachname;
    @FXML
    private TextField txtAnschrift;
    @FXML
    private ChoiceBox<String> choiceGender;
    @FXML
    private DatePicker datefield_birthday;
    @FXML
    private Button btnOK;

    @FXML
    void okClicked(ActionEvent event) {
        try {
            //TODO throw exception if any field is empty
            //TODO integrate Role
            mainController.createUser(getEmail(), getPassword(), getName(), getAddress(), getGender(), getBirth());
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Bitte überprüfen sie ihre Eingaben auf Vollständigkeit");
            alert.setHeaderText(null);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        choiceGender.getItems().addAll("Maennlich", "Weiblich", "Divers");
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getPassword() {
        String password = "none";
        if (txtPassword1.getText().equals(txtPassword2.getText())) {
            password = txtPassword1.getText();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Passwortbestätigung");
            alert.setContentText("Die Bestätigung ihres Passworts ist fehlgeschlagen, \n Bitte überprüfen sie ihre Eingabe.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return password;
    }


    public String getName() {
        return txtVorname.getText() + " " + txtNachname.getText();
    }

    public String getAddress() {
        return txtAnschrift.getText();
    }

    public String getGender() {
        return choiceGender.getValue();
    }

    public String getBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String birth = datefield_birthday.getValue().format(formatter);
        return birth;
    }

}
