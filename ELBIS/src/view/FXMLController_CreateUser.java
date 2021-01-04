package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Administrator;
import model.Moderator;

import java.time.format.DateTimeFormatter;

public class FXMLController_CreateUser extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________

    int userID = 0; //User 0 means a new user gets created

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
    private ChoiceBox<String> choiceRole;
    @FXML
    private DatePicker datefield_birthday;
    @FXML
    private Button btnOK;

    @FXML
    void okClicked(ActionEvent event) {
        try {
            if(userID == 0){
                if(!getEmail().equals("") && !getPassword().equals("")&& !getName().equals("")&& !getGender().equals("")&&!getRole().equals("")&& !getAddress().equals("")&& !getBirth().equals("")){
                    mainController.createUser(getEmail(), getPassword(), getName(), getGender(), getRole(), getAddress(), getBirth());
                    mainController.sideStage.close();
                } else{
                    throw new Exception("Empty field noticed");
                }
            } else {
                    mainController.editUser(userID ,getEmail(), getPassword(), getName(), getGender(), getRole(), getAddress(), getBirth());
                    mainController.sideStage.close();
            }

        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Achtung");
            alert.setContentText("Bitte überprüfen sie ihre Eingaben auf Vollständigkeit");
            alert.setHeaderText(null);
            alert.showAndWait();
            //e.printStackTrace();
        } finally{

        }
    }



    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        choiceGender.getItems().addAll("Maennlich", "Weiblich", "Divers");
        choiceRole.getItems().addAll("User");
    }

    // Getters,Setters_________________________________________________________________________________________________

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

    private String getRole() {
        return choiceRole.getValue();
    }

    public void setUserID(int id){
        this.userID = id;
    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        if(mainController.getActiveUser() instanceof Administrator){
            choiceRole.getItems().addAll("Moderator", "Administrator");
        }
    }
}
