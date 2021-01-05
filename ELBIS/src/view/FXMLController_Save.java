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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
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
    @FXML
    Spinner<Integer> hourSpinner;
    @FXML
    Spinner<Integer> minSpinner;
    @FXML
    Spinner<Integer> secSpinner;

    public FXMLController_Save(MainController mainController)
    {
        this.mainController=mainController;
    }

    public void initialize()
    {
        statusChoice.getItems().addAll(Status.values());
        statusChoice.getSelectionModel().selectFirst();

        //topics= mainController.getAllTopics();
        topics = mainController.getActiveUser().getTopics();
        topicChoice.getItems().addAll(topics);

        // Test for all Topics
        /*for (int i=0;i<topics.size();i++)
        {
            System.out.println(topics.get(i).getName());
        }*/

        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23));
        minSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59));
        secSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59));

    }

    public String getExpireDate()
    {
        LocalDate date= expireDate.getValue();
        LocalDateTime expire= date.atTime(hourSpinner.getValue(),minSpinner.getValue(),secSpinner.getValue());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return expire.format(formatter);
    }

    public void setExpireDate(String dateString)
    {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime expire= LocalDateTime.parse(dateString, formatter);
        System.out.println(expire);
        expireDate.setValue(expire.toLocalDate());
        hourSpinner.getValueFactory().setValue(expire.getHour());
        minSpinner.getValueFactory().setValue(expire.getMinute());
        secSpinner.getValueFactory().setValue(expire.getSecond());
    }

}
