package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class FXMLController_ChangeUserRole extends ELBIS_FXMLController{
    // Atrrib_______________________________________________________________________________________________________
    int userID = 0;
    // UI_______________________________________________________________________________________________________
    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private Button btnOk;

    @FXML
    void okClicked(ActionEvent event) {
        if(choiceRole.getValue()!= null){
            mainController.changeUserRole(userID, choiceRole.getValue());
            mainController.sideStage.close();
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Rolle ändern");
            alert.setContentText("Bitte wählen sie eine Rolle.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        choiceRole.getItems().addAll("User", "Moderator", "Administrator");
    }
    // Getters,Setters_________________________________________________________________________________________________

    public void setUserID(int id){
        this.userID = id;
    }
}
