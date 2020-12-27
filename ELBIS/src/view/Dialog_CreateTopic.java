package view;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Dialog_CreateTopic extends Alert{

    private FXMLLoader createTopicPaneLoader;
    private FXMLController_CreateTopic createTopicController;
    private Pane createTopicPane;


    public Dialog_CreateTopic(Alert.AlertType alertType, MainController mainController) {
        super(alertType);

        setTitle("Bereich anlegen");
        setHeaderText("Bereichserstellung");
        setResizable(false);

        try {
            //load WindowContent
            createTopicPaneLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateTopic.fxml"));
            createTopicPane = (Pane) createTopicPaneLoader.load();
            createTopicController = createTopicPaneLoader.getController();
            createTopicController.setMainController(mainController);

            getDialogPane().setContent(createTopicPane);
        } catch (IOException io) {
            io.printStackTrace();

        }


    }
}
