package view;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.LinkedList;

public class Dialog_CreateUser extends Alert {

    private FXMLLoader createUserPaneLoader;
    private FXMLController_CreateUser createUserController;
    private Pane createUserPane;


    public Dialog_CreateUser(AlertType alertType, MainController mainController) {
        super(alertType);

        setTitle("Nutzer Anlegen");
        setHeaderText("Nutzererstellung");
        setResizable(false);

        try {
            //load WindowContent
            createUserPaneLoader = new FXMLLoader(getClass().getResource("/view/Pane_CreateUser.fxml"));
            createUserPane = (Pane) createUserPaneLoader.load();
            createUserController = createUserPaneLoader.getController();
            createUserController.setMainController(mainController);

            getDialogPane().setContent(createUserPane);
        } catch (IOException io) {
            io.printStackTrace();

        }


    }


}
