package view;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import java.io.IOException;


public class Dialog_CreateUser extends Alert {

    private FXMLLoader createUserPaneLoader;
    private FXMLController_CreateUser createUserController;
    private Pane createUserPane;
    private MainController mainController;


    public Dialog_CreateUser(AlertType alertType, MainController mainController) {
        super(alertType);
        this.mainController = mainController;
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
