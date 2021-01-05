package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Topic;

public class FXMLController_ChangeUserTopic extends ELBIS_FXMLController {
    // Atrrib_______________________________________________________________________________________________________
    int userID = 0;
    ObservableList<Topic> topicList;
    sideStageState state;

    // UI_______________________________________________________________________________________________________
    @FXML
    private Button btnOK;

    @FXML
    private ChoiceBox<Topic> choiceTopic;

    @FXML
    private Label lblAction;

    @FXML
    void okClicked(ActionEvent event) {
        if(choiceTopic.getValue() != null){
            switch(state){
                case allowTopic -> mainController.allowTopic(userID, getChoosenTopicID());
                case denyTopic -> mainController.denyTopic(userID, getChoosenTopicID());
            }
            mainController.sideStage.close();
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bereich ändern");
            alert.setContentText("Bitte wählen sie einen Bereich.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    // Getters,Setters_________________________________________________________________________________________________
    public int getChoosenTopicID(){
        return choiceTopic.getValue().getId();
    }
    public void setUserID(int id){
        this.userID = id;
    }
    public void setState(sideStageState state){
        this.state = state;
        switch (state){
            case allowTopic -> lblAction.setText("zuteilen");
            case denyTopic -> lblAction.setText("entziehen");
        }
    }
    public void setTopicList (ObservableList<Topic> topicList){
        this.topicList = topicList;
        choiceTopic.getItems().addAll(topicList);
    }
}


