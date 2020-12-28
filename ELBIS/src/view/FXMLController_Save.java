package view;

import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Status;
import model.Topic;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController_Save
{
    MainController mainController;
    ObservableList<Topic> topics;

    @FXML
    AnchorPane anchorPane;

    @FXML
    Button saveButton;

    @FXML
    Button cancelButton;

    @FXML
    TextField saveTitle;

    @FXML
    ChoiceBox<Status> statusChoice;

    @FXML
    ChoiceBox<Topic> topicChoice;

    public FXMLController_Save(MainController mainController)
    {
        this.mainController=mainController;
    }

    public void initialize()
    {
        statusChoice.getItems().addAll(Status.values());
        statusChoice.getSelectionModel().selectFirst();

        topics= mainController.getAllTopics();
        topicChoice.getItems().addAll(topics);


    }
}
