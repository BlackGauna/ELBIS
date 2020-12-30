package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FXMLController_CreateTopic extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________


    // UI_______________________________________________________________________________________________________
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtParentTopic;
    @FXML
    private ChoiceBox<String> choiceTopic;
    @FXML
    void topicOkClicked(ActionEvent event) {
        mainController.createTopic(getName(), getParentTopic());
    }

    // Ini_______________________________________________________________________________________________________
    public void initialize() {
        //TODO load actual topics
        choiceTopic.getItems().addAll("Organisationen", "Gemeinde", "Industrie");
    }

    // Getters,Setters_________________________________________________________________________________________________

    public String getName() {
        return txtName.getText();
    }

    public String getParentTopic() {
        return choiceTopic.getValue();
    }

}
