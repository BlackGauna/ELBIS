package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Topic;

public class FXMLController_CreateTopic extends ELBIS_FXMLController {

    // Atrrib_______________________________________________________________________________________________________
    int topicID = 0; //if 0 then we create a new topic, else editing the one of the id
    Topic currentTopic = new Topic();
    // UI_______________________________________________________________________________________________________
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtParentTopic;
    @FXML
    private ChoiceBox<String> choiceTopic;
    @FXML
    void topicOkClicked(ActionEvent event) {
        //TODO open Error Dialog if any field is empty
        if(!getName().equals("") && getParentTopic() != null ){
            if(topicID == 0){
                mainController.createTopic(getName(), getParentTopic());
                mainController.sideStage.close();
            } else{
                mainController.editTopic(topicID ,getName(), getParentTopic());
                mainController.sideStage.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erstellen nicht möglich");
            alert.setContentText("Bitte überprüfen sie auf vollständige Eingabe.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }


    }

    // Ini_______________________________________________________________________________________________________
    public void initialize() {

    }

    // Getters,Setters_________________________________________________________________________________________________

    public String getName() {
        return txtName.getText();
    }

    public String getParentTopic() {
        return choiceTopic.getValue();
    }

    public void setTopicID(int ID){
        this.topicID = ID;
    }

    @Override
    public void setMainController(MainController mainController) {
        super.setMainController(mainController);
        for(int i = 0; i < mainController.getActiveUser().getTopics().size(); i++){
            choiceTopic.getItems().add(mainController.getActiveUser().getTopics().get(i).getName());
        }
    }

    public void setCurrentTopic(Topic topic){
        this.currentTopic = topic;
        txtName.setText(topic.getName());
        choiceTopic.getSelectionModel().select(topic.getParent().getName());
    }

}
