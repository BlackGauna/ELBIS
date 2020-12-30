package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.DataController;

public class FXMLController_CreateTopic {

    // Atrrib_______________________________________________________________________________________________________
    MainController mainController;

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
        choiceTopic.getItems().addAll("Organisationen", "Gemeinde", "Industrie");
    }

    // Getters,Setters_________________________________________________________________________________________________
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public String getName() {
        return txtName.getText();
    }

    public String getParentTopic() {
        return choiceTopic.getValue();
    }

}
