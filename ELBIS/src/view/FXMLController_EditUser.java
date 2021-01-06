package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Administrator;
import model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FXMLController_EditUser extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________
    User user;

    // UI_______________________________________________________________________________________________________

    @FXML
    private TextField txtEmail;
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
            if (!getEmail().equals("") && !getName().equals("") && !getGender().equals("") && !getRole().equals("") && !getAddress().equals("") && !getBirth().equals("")) {
                mainController.editUser(user.getId(), getEmail(), getName(), getGender(), getRole(), getAddress(), getBirth());
                mainController.sideStage.close();
            } else {
                throw new Exception("Empty field noticed");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Achtung");
            alert.setContentText("Bitte überprüfen sie ihre Eingaben auf Vollständigkeit");
            alert.setHeaderText(null);
            alert.showAndWait();
            //e.printStackTrace();
        } finally {
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

    public String getName() {
        return txtVorname.getText() + " " + txtNachname.getText();
    }

    public String getAddress() {
        return txtAnschrift.getText();
    }

    public String getGender() {
        if (choiceGender.getValue().isBlank()) {
            return "";
        } else {
            return choiceGender.getValue();
        }
    }

    public String getBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String birth = datefield_birthday.getValue().format(formatter);
        return birth;
    }

    private String getRole() {
        if (choiceRole.getValue().isBlank()) {
            return "";
        } else {
            return choiceRole.getValue();
        }
    }

    public void setUser(User user) {
        this.user = user;

        txtEmail.setText(user.getEmail());
        txtVorname.setText(user.getName().substring(0, user.getName().indexOf(' ')));
        txtNachname.setText(user.getName().substring(user.getName().indexOf(' ') + 1));
        txtAnschrift.setText(user.getAddress());
        switch (user.getGender()) {
            case Maennlich -> choiceGender.setValue("Maennlich");
            case Weiblich -> choiceGender.setValue("Weiblich");
            case Divers -> choiceGender.setValue("Divers");
        }
        if (user.getRole().equals("User")) {
            choiceRole.setValue("User");
        } else if (user.getRole().equals("Moderator")) {
            choiceRole.setValue("Moderator");
        } else if (user.getRole().equals("Administrator")) {
            choiceRole.setValue("Administrator");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        datefield_birthday.setValue(LocalDate.parse(user.getDateOfBirth(), formatter));
    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        if (mainController.getActiveUser() instanceof Administrator) {
            choiceRole.getItems().addAll("Moderator", "Administrator");
        }
    }
}
