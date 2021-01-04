package view;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import model.User;

public class ActionCell_UserTable extends TableCell<User, Boolean> {

    // Atrrib_______________________________________________________________________________________________________
    Button btn;
    MainController mainController;
    User hisUser;

    // Ctor_______________________________________________________________________________________________________
    public ActionCell_UserTable(MainController mainController, String label, sideStageState state) {
        this.mainController = mainController;
        btn = new Button(label);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainController.setStatus("Aktion auf UserID " + hisUser.getId() + " ausgeführt.");
                mainController.callSideStage(state, hisUser.getId());
            }
        });
    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            hisUser = (User) getTableView().getItems().get(getIndex());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(btn);
        } else {
            setGraphic(null);
        }
    }
}
