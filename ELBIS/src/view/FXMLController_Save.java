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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    DatePicker expireDate;

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

        // Test for all Topics
        for (int i=0;i<topics.size();i++)
        {
            System.out.println(topics.get(i).getName());
        }

    }

    public String getExpireDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return expireDate.getValue().format(formatter);
    }

    public void setExpireDate(String dateString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        expireDate.setValue(LocalDate.parse(dateString, formatter));
    }

}
